package Jeu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import UI.Interface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Monopoly {
	private int nbMaisons = 32;
	private int nbHotels = 12;
        private HashMap<String, Groupe> groupes = new HashMap<>();
        private HashMap<Integer, Carreau> carreaux;
	public  Interface inter;
	private LinkedList<Joueur> joueurs = new LinkedList<>();
	public  LinkedList<CarteChance> cartesChance = new LinkedList<>();
	public  LinkedList<CarteCaisse> cartesCaisse = new LinkedList<>();

        public Monopoly(String dataFilename){
                initGroupe();
		buildGamePlateau(dataFilename);
                initCarte();
	}

        /**
         * Procédure permettant le déroulement complet d'une partie
         */
        public void jouer(){
            Joueur j = this.getJoueurCourant();
            j.setDoubleDe(true);// Réinitialisation des variables pour le tour du joueur
            j.setNbDouble(0);
            if (j.getDrapeauPrison() && j.getPeutJouer()) { //Cas où le joueur est en prison et n'est pas éliminé.
                int des = this.lancerDes();
                if (j.getDoubleDe()){   // Le joueur se libère de prison avec un double, il commence un tour de jeu normal
                    j.liberePrison();   // pour finir dans la boucle correspondant à un joueur libre.
                    j.incDouble();
                    j.avancer(des);
                    j.getPositionCourante().action(j);
                }
                else{                   // Le joueur n'a pas réussi à se libérer par un lancer de dés.
                    j.incrNbTourPrison(); // On incrémente donc sa durée de détention.
                    if (j.getNbTourPrison() == 3){  // Si la durée de détention est arrivée à sa limite,
                        j.removeCash(50);           // le joueur doit payer une amende.
                        if (j.getPeutJouer()){      // On vérifie que le paiement de la facture n'ait pas
                            j.liberePrison();       // fait faire faillite au joueur.
                            j.avancer(des);
                            j.getPositionCourante().action(j);
                        }
                    }
                    else {  // Dans le cas où le joueur n'a pas fait de double et n'est pas arrivé à sa durée maximale de détention
                        if (j.isCarteCaisseLibere() && this.getInter().demandeUtilisationCarte()){     // On vérifie s'il possède une carte Caisse de Communauté
                            this.getCartesCaisse().addLast(new CarteCaisse(this, CarteCaisseEnum.libere_prison)); // et on lui propose de s'en servir
                            j.setCarteCaisseLibere(false);
                            j.liberePrison();
                        }
                        else if (j.isCarteChanceLibere() && this.getInter().demandeUtilisationCarte()){ // On vérifie s'il possède une carte Chance
                            this.getCartesChance().addLast(new CarteChance(this, CarteChanceEnum.libere_prison)); // et on lui propose de s'en servir.
                            j.setCarteChanceLibere(false);
                            j.liberePrison();
                        }
                    }
                }
            }
            while (j.getDoubleDe() && !j.getDrapeauPrison() && j.getPeutJouer()){ // Le joueur n'est pas en prison, il est donc lancé dans une boucle standard 
                jouerUnCoup(j);                                                   // lui permettant de réaliser les actions adéquates.
            }
            for (Joueur jTemp : getJoueurs()){
                // Si le joueur a fait faillite durant le tour, il est éliminé.
                if (!jTemp.getPeutJouer()){ 
                    this.elimineJoueur(jTemp);
                }
            }
            
            // Si le premier joueur est toujours le même après la boucle d'élimination
            // Alors on le met en dernier.
            if (j == getJoueurs().getFirst()){
                Joueur jTemp = getJoueurs().pollFirst();
                getJoueurs().addLast(jTemp);
            }

            this.getInter().afficherFinDuTour(j);
        }       
        
        /**
         * Lance le déroulement d'un coup normal, avec lancer de dés et action
         * @param joueur le joueur qui doit jouer
         */
        public void jouerUnCoup(Joueur joueur){
            lancerDesEtAvancer();
            joueur.getPositionCourante().action(joueur);
        }

        /**
         * Lance un jet de dé à 6 faces
         * @return une valeur entre 1 et 6
         */
        public int lancerDe() {
            Random jet = new Random();
            int valDe = jet.nextInt(5)+1;
            return valDe;
        }
        /**
         * Fais deux jets de dés aléatoire.
         * Et indique dans doubleDe si le joueur à fait un double
         * @return La somme des dés
         */
        protected int lancerDes(){
            int de1;
            int de2;
            de1 = lancerDe();
            de2 = lancerDe();
            getJoueurCourant().setDoubleDe(de1 == de2);
            inter.afficherLancerDes(de1,de2);
            return de1 + de2;
        }
        /**
         * Lance les des et fais avancer le joueur normalement ou en prison
         * Affiche ensuite l'état du joueur
         */
        public void lancerDesEtAvancer(){
            Joueur joueur = this.getJoueurCourant();
            int des;

            des = lancerDes();
            // Si il fait un double on incremente
            if (joueur.getDoubleDe()) {joueur.incrNbDouble();}

            if (joueur.getNbDouble() == 3){
                joueur.allerPrison();
            } else {
                joueur.avancer(des);
            }
            inter.afficherEtatJoueur(joueur);
        }
        
        /**
         * Supprime toutes les propriétés d'un joueur et rajoute
         * les maisons/hotels, qui étaient construit dessus,
         * dans le monopoly.
         * Supprimer ensuite le joueur.
         * @param j Le joueur a éliminé du jeu
         */
        public void elimineJoueur(Joueur j) {
            for (ProprieteAConstruire prop : j.getProprietesAConstruire()){
                prop.setProprietaire(null);
                this.incrNbMaisons(prop.getNbMaisons());
                prop.setNbMaisons(0);
                this.incrNbHotels(prop.getNbHotel());
                prop.setNbHotel(0);
            }
            for (Gare gare : j.getGares()){
                gare.setProprietaire(null);
            }
            for (Compagnie comp : j.getCompagnies()){
                comp.setProprietaire(null);
            }
            this.getInter().afficherJoueurPerdu(j);
            this.getJoueurs().remove(j);
        }
             
        

        /* GETTEUR / SETTEUR */

        /**
         * Ajoute un nouveau joueur au monopoly
         * @param nomJoueur Le nom du joueur
         */
        public void nouveauJoueur(String nomJoueur){
            Joueur j = new Joueur(nomJoueur, getCarreau(1), this);
            addJoueur(j);
        }
        private void addJoueur (Joueur joueur){
            joueurs.add(joueur);
        }
        public LinkedList<Joueur> getJoueurs(){
            return this.joueurs;
        }
        
        public Carreau getCarreau(int indice){
            return carreaux.get(indice);
        }
        
        public Joueur getJoueurCourant() {
            return joueurs.getFirst();
	}
        
       public Interface getInter(){
           return this.inter;
       }
       
        public LinkedList<CarteChance> getCartesChance(){
            return this.cartesChance;
        }
        public void setCartesChance(LinkedList<CarteChance> carteChances){
            this.cartesChance = carteChances;
        }

        public LinkedList<CarteCaisse> getCartesCaisse(){
            return this.cartesCaisse;
        }
        public void setCartesCaisse(LinkedList<CarteCaisse> carteCaisses){
            this.cartesCaisse = carteCaisses;
        }
        
        
        
        public int getNbMaisons(){
            return this.nbMaisons;
        }
        public void setNbMaisons(int nbMaisons){
            this.nbMaisons = nbMaisons;
        }
        public void incrNbMaisons(int i){
            this.nbMaisons += i;
        }
        public void decrNbMaisons(int i){
            this.nbMaisons -= i;
        }
        
        public int getNbHotels(){
            return this.nbHotels;
        }
        public void setNbHotels(int nbHotels){
            this.nbHotels = nbHotels;
        }
        public void incrNbHotels(int i){
            this.nbHotels += i;
        }
        public void decrNbHotels(int i){
            this.nbHotels -= i;
        }
        
       
        
        
        
        /* CREATION DU JEU */
        
        /**
         * Crée un plateau de jeu à partir du fichier dataFilename.
         * @param dataFilename
         */
        private void buildGamePlateau(String dataFilename)
        {
                try{
                        ArrayList<String[]> data = readDataFile(dataFilename, ",");

                        //TODO: create cases instead of displaying
                        for(int i=0; i<data.size(); ++i){
                                String caseType = data.get(i)[0];
                                if(caseType.compareTo("P") == 0){
                                    // Recupération des différentes variables
                                        int num = Integer.parseInt(data.get(i)[1]);
                                        String nomC = data.get(i)[2];
                                        Groupe grp = groupes.get(data.get(i)[3]);
                                        int prix = Integer.parseInt(data.get(i)[4]);
                                        int nu = Integer.parseInt(data.get(i)[5]);
                                        int mais1 = Integer.parseInt(data.get(i)[6]);
                                        int mais2 = Integer.parseInt(data.get(i)[7]);
                                        int mais3 = Integer.parseInt(data.get(i)[8]);
                                        int mais4 = Integer.parseInt(data.get(i)[9]);
                                        int hotel = Integer.parseInt(data.get(i)[10]);
                                        ArrayList<Integer> loyer = new ArrayList<>();
                                        loyer.add(nu);
                                        loyer.add(mais1);
                                        loyer.add(mais2);
                                        loyer.add(mais3);
                                        loyer.add(mais4);
                                        loyer.add(hotel);

                                        ProprieteAConstruire c = new ProprieteAConstruire(num,nomC,this,prix,loyer,grp);                                    
                                        carreaux.put(num,c);
                                }
                                else if(caseType.compareTo("G") == 0){
                                        int num = Integer.parseInt(data.get(i)[1]);
                                        String nomC = data.get(i)[2];
                                        int prix = Integer.parseInt(data.get(i)[3]);

                                        Gare c = new Gare(num,nomC,this,prix);
                                        carreaux.put(num,c);
                                }
                                else if(caseType.compareTo("C") == 0){
                                        int num = Integer.parseInt(data.get(i)[1]);
                                        String nomC = data.get(i)[2];
                                        int prix = Integer.parseInt(data.get(i)[3]);

                                        Compagnie c = new Compagnie(num,nomC,this,prix);
                                        carreaux.put(num,c);
                                }
                                else if(caseType.compareTo("CT") == 0){
                                        int num = Integer.parseInt(data.get(i)[1]);
                                        String nomC = data.get(i)[2];

                                        CarreauTirage c = new CarreauTirage(num,nomC,this);
                                        carreaux.put(num,c);
                                }
                                else if(caseType.compareTo("CA") == 0){
                                        int num = Integer.parseInt(data.get(i)[1]);
                                        String nomC = data.get(i)[2];
                                        int montant = Integer.parseInt(data.get(i)[3]);

                                        CarreauArgent c = new CarreauArgent(num,nomC,this,montant);
                                        carreaux.put(num,c);
                                }
                                else if(caseType.compareTo("CM") == 0){
                                        int num = Integer.parseInt(data.get(i)[1]);
                                        String nomC = data.get(i)[2];

                                        CarreauTirage c = new CarreauTirage(num,nomC,this);
                                        carreaux.put(num,c);
                                }
                                else
                                        System.err.println("[buildGamePleateau()] : Invalid Data type");
                        }

                } 
                catch(FileNotFoundException e){
                        System.err.println("[buildGamePlateau()] : File is not found!");
                }
                catch(IOException e){
                        System.err.println("[buildGamePlateau()] : Error while reading file!");
                }
        }
        
        /**
         * Crée les différents groupe de propriétés.
         */
        private void initGroupe(){
            Groupe bleuFonce = new Groupe(CouleurPropriete.bleuFonce, 200);
            Groupe orange    = new Groupe(CouleurPropriete.orange, 100);
            Groupe mauve     = new Groupe(CouleurPropriete.mauve, 50);
            Groupe violet    = new Groupe(CouleurPropriete.violet, 100);
            Groupe bleuCiel  = new Groupe(CouleurPropriete.bleuCiel, 50);
            Groupe jaune     = new Groupe(CouleurPropriete.jaune, 150);
            Groupe vert      = new Groupe(CouleurPropriete.vert, 200);
            Groupe rouge     = new Groupe(CouleurPropriete.rouge, 150);

            groupes.put("bleuFonce", bleuFonce);
            groupes.put("orange", orange);
            groupes.put("mauve", mauve);
            groupes.put("violet", violet);
            groupes.put("bleuCiel", bleuCiel);
            groupes.put("jaune", jaune);
            groupes.put("vert", vert);
            groupes.put("rouge", rouge);
        }


        /**
         * Crée les cartes chance et caisse de communauté et les ajoutes
         * au monopoly après les avoirs mélangé.
         */
        private void initCarte(){
            Random res = new Random();
            LinkedList<CarteCaisse> carteCaissesTemp = new LinkedList();
            for (CarteCaisseEnum carteCaisse : CarteCaisseEnum.values()){
                carteCaissesTemp.add(new CarteCaisse(this, carteCaisse));
            }
            for (int i = carteCaissesTemp.size()-1; i!=0; i--){
                this.cartesCaisse.add(carteCaissesTemp.remove(res.nextInt(i)));
            }
            
            LinkedList<CarteChance> carteChancesTemp = new LinkedList();
            for (CarteChanceEnum carteChance : CarteChanceEnum.values()){
                carteChancesTemp.add(new CarteChance(this, carteChance));
            }
            for (int i = carteChancesTemp.size()-1; i!=0; i--){
                this.cartesChance.add(carteChancesTemp.remove(res.nextInt(i)));
            }
        }

        private ArrayList<String[]> readDataFile(String filename, String token) throws FileNotFoundException, IOException
        {
                ArrayList<String[]> data = new ArrayList<String[]>();

                BufferedReader reader  = new BufferedReader(new FileReader(filename));
                String line = null;
                while((line = reader.readLine()) != null){
                        data.add(line.split(token));
                }
                reader.close();
                
                return data;
        }

}
