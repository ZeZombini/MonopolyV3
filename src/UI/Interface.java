package UI;

import Jeu.Carreau;
import Jeu.CarreauPropriete;
import Jeu.CarteCaisseEnum;
import Jeu.CarteChanceEnum;
import Jeu.Compagnie;
import Jeu.Gare;
import Jeu.Joueur;
import Jeu.Monopoly;
import Jeu.ProprieteAConstruire;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Interface {
	public Monopoly monopoly;
        
    public Interface(Monopoly monopoly) {
        setMonopoly(monopoly);
    }
       
    public Monopoly getMonopoly() {
        return monopoly;
    }
    public void setMonopoly(Monopoly monopoly) {
        this.monopoly = monopoly;
    }
    
    /**
     * Lance l'inscription des joueurs.
     */
    public void initialiserPartie (){
        Scanner sc1 = new Scanner(System.in);
        boolean entier;
        String[] nomj = new String[6];
        int[]    nbrj = new int[6];
        int nbrJoueur = 0;
        String nomJoueur;
        while(nbrJoueur<2 | nbrJoueur>6){
            do {
                Scanner sc = new Scanner(System.in);
                entier = true;
                System.out.println("Veuillez indiquer le nombre de joueur : ");
                try{
                    nbrJoueur = sc.nextInt();
                }catch(InputMismatchException e){
                    System.out.println("La valeur saisie n'est pas un entier!");
                    entier = false;
                }
            } while(entier != true);
        }
        for(int i=0; i<nbrJoueur; i++){
            System.out.println("Veuillez saisir le nom du joueur n°"+(i+1)+" : ");
            nomJoueur = sc1.nextLine();
            nomj[i] = nomJoueur;
        }
        
        // met un lancé de dé pour chaque joueur pour savoir qui commence
        System.out.println("---------------------------------------");
        System.out.println("Les joueurs vont jouer dans cet ordre :");
        for(int i=0; i<nbrJoueur; i++){ 
            nbrj[i] = monopoly.lancerDe();
        }
        
        for(int i=0; i<nbrJoueur; i++){
            int maxi = 0; //  le nombre max des lancés de dés
            int jmax = 0; //  le joueur ayant le nombre max
            for(int j=0; j<nbrJoueur; j++){
                if (nbrj[j]>maxi) {
                   maxi = nbrj[j]; 
                   jmax = j;
                }
            }
            nbrj[jmax] = 0; // met la valeur du lancé du joueur selectionné à 0
            System.out.println(nomj[jmax]);
            monopoly.nouveauJoueur(nomj[jmax]); // crée un joueur avec son nom
        }
    }

        
        /* METHODE D'AFFICHAGE SIMPLE */

        /**
         * Demande un simple retour à la ligne pour pouvoir lancer les dés.
         */
        public void lancementDes(){
            Scanner sc = new Scanner(System.in);
            System.out.println("Appuyez sur ENTRE pour lancer les dés");
            sc.nextLine();
            
        }

        /**
         * Affiche le patrimoine de tous les joueurs et propose l'abandon au 
         * joueur qui commence son tour.
         * @param j le joueur qui commence son tour.
         */
        public void afficheRecapDebutTour(Joueur j){
            Scanner sc = new Scanner(System.in);
            String resAbandon;
            System.out.println("");
            System.out.println("---------------------------------------");
            System.out.println("Recapitulatif :");
            for (Joueur jTemp : getMonopoly().getJoueurs()){
                System.out.println(" - Le joueur " + jTemp.getNomJoueur() + " se trouve sur la case " 
                        + jTemp.getPositionCourante().getNumero() + " : "
                        + jTemp.getPositionCourante().getNomCarreau());
                System.out.println("   Il possède " + jTemp.getCash() + " euros");
                for (Gare g : jTemp.getGares()){
                    System.out.println("    - " + g.getNomCarreau());
                }
                for (Compagnie c : jTemp.getCompagnies()){
                    System.out.println("    - " + c.getNomCarreau());
                }
                for (ProprieteAConstruire p : jTemp.getProprietesAConstruire()){
                    System.out.println("    - " + p.getGroupePropriete().getCouleur() + " : " + p.getNomCarreau());
                }
            }
            System.out.println("");
            System.out.println("Tour de " + j.getNomJoueur());
            System.out.println("");
            System.out.println("Voulez vous abandonner la partie ? (oui/non)");
            resAbandon = sc.nextLine();
            while (!"oui".equals(resAbandon) && !"non".equals(resAbandon)){
                System.out.println("Je n'ai pas compris votre choix.");
                System.out.println("oui/non");
                resAbandon = sc.nextLine();
            }
            if ("oui".equals(resAbandon)){
                j.setPeutJouer(false);
            }
        }
        
        /**
         * Affiche un simple message indiquant que le joueur a fini son tour.
         * @param j le joueur qui a fini son tour.
         */
        public void afficherFinDuTour(Joueur j){
            System.out.println(j.getNomJoueur() + " a fini son tour.");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
        }
        
        /**
         * Affiche le lancé de dés du joueur et si il a fait un double.
         * @param de1 le résultat du premier dé lancé.
         * @param de2 le résultat du deuxième dé lancé.
         */
        public void afficherLancerDes(int de1, int de2) {
            System.out.println("Vous faites un lancé de " + (de1 + de2));
            if (de1==de2){
                System.out.println("Bravo, vous faites un double !");
            }
	}

        /**
         * Affiche la case ou arrive le joueur.
         * @param j le joueur courant.
         */
        public void afficherEtatJoueur(Joueur j) {
            System.out.println("Vous arrivez sur la case " + j.getPositionCourante().getNumero() + " : " + j.getPositionCourante().getNomCarreau());
	}
        
        /**
         * Affiche si le joueur est allé en prison après 3 doubles ou après être
         * arrivé sur la case "Allez en prison" .
         * @param j le joueur qui va en prison.
         */
        public void afficherAllerPrison(Joueur j) {
            if (j.getNbDouble() == 3) {
                System.out.println( "Le joueur " + j.getNomJoueur() + " va en prison car il a fait 3 doubles d'affilé !");
            } else {
                System.out.println("Le joueur " + j.getNomJoueur() + " va en prison!");
            }
        }
        
        /** 
        * Inidique le montant que payer un débiteur à un créancier.
        * @param debiteur le joueur qui paye.
        * @param creancier le joueur qui recevra l'argent.
        * @param montant le montant que paye le debiteur au créancier.
        */
       public void afficheMontantPayer(Joueur debiteur, Joueur creancier, int montant){
                System.out.println(debiteur.getNomJoueur() + " paye " + montant + " au joueur " + creancier.getNomJoueur());
        }
        
        /**
         * Affiche le nom de la carte chance tirée.
         * @param action l'action de la carte chance tirée.
         */
        public void afficherActionCarteChance(CarteChanceEnum action){
            System.out.println("************** Carte Chance **************");
            System.out.println(action.toString());
            System.out.println("******************************************");
        }

        /**
         * Affiche le nom de la carte caisse de communauté tirée.
         * @param action l'action de la carte caisse de communauté tirée.
         */
        public void afficherActionCarteCaisse(CarteCaisseEnum action){
            System.out.println("********** Caisse de communauté **********");
            System.out.println(action.toString());
            System.out.println("******************************************");
        }
        
        /**
         * Affiche lorsque qu'un joueur a perdu.
         * @param j le joueur qui a perdu.
         */
        public void afficherJoueurPerdu(Joueur j){
            System.out.println("******************************************");
            System.out.println("Le joueur " + j.getNomJoueur() + " est éliminiée.");
            System.out.println("Toutes ses propriétés sont remisent en jeux");
            System.out.println("******************************************");
        }
        
        /* METHODE DE DEMANDE (RETOURNANT AUTRE CHOSE QU'UN VOID) */

        /**
         * Demande au joueur si il veut acheter une propriété.
         * @param c la propriété a acheté.
         * @return le choix du joueur.
         */
	public boolean demandeAchatPropriété(CarreauPropriete c) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Voulez vous acheter " + c.getNomCarreau() + " pour la somme de " + c.getPrixAchat() + " euros ? (oui/non)");
            String choix = sc.nextLine();
            while (!"oui".equals(choix) && !"non".equals(choix)){
                System.out.println("Je n'ai pas compris votre choix");
                System.out.println("oui/non : ");
                choix = sc.nextLine();
            }
                return "oui".equals(choix);
        }
        
        /** 
         * Demande à un joueur si il veut utiliser une carte "Libéré de prison".
         * @return le choix du joueur.
         */
        public boolean demandeUtilisationCarte(){
            Scanner sc = new Scanner(System.in);
            System.out.println("Voulez vous utiliser votre carte Libéré de prison ? (oui/non)");
            String choix = sc.nextLine();
            while (!"oui".equals(choix) && !"non".equals(choix)){
                System.out.println("Je n'ai pas compris votre choix.");
                System.out.println("oui/non : ");
                choix = sc.nextLine();
            }
                return "oui".equals(choix);          
        }
        
        /**
         * Affiche les propriétés où le joueur peut construire des maisons ou hôtels.
         * @param proprietesConstructibles la liste des propriétés.
         * @return le choix du joueur.
         */
        public int demanderChoixProp(ArrayList<ProprieteAConstruire> proprietesConstructibles){
            System.out.println("Voulez-vous construire sur une des propriétés suivantes ?");
            System.out.println("0 - Ne pas construire.");
            boolean entier;
            int i = 0;
            int choix = -1;
            for (ProprieteAConstruire prop : proprietesConstructibles){
                System.out.println((i+1) + " - Construire sur la case " + prop.getNomCarreau());
                i++;
            }
            
            while (choix<0 | choix>i){
                System.out.println("Veuillez saisir une valeur présente dans le tableau : ");
                do{
                    entier = true;
                    Scanner sc = new Scanner(System.in);
                    try{
                        choix = sc.nextInt();
                    } catch (InputMismatchException e){
                        System.out.println("La valeur saisie n'est pas un entier!");
                        entier = false;
                    }
                } while (entier != true);
            }
            return choix;
        }
        

}