package Jeu;

import java.util.ArrayList;

public class Joueur {
	private String nomJoueur;
	private int cash = 1500;
	private Monopoly monopoly;
	private ArrayList<Compagnie> compagnies = new ArrayList<Compagnie>();
	private ArrayList<Gare> gares = new ArrayList<Gare>();
	private Carreau positionCourante;
	private ArrayList<ProprieteAConstruire> proprietesAConstruire = new ArrayList<ProprieteAConstruire>();
        
        private int dernierJetDes;
        private boolean drapeauPrison;
        private boolean carteChanceLibere = false;
        private boolean carteCaisseLibere = false;
        private boolean doubleDe;
        private int nbDouble;
        private int nbTourPrison = 0;
        private boolean peutJouer = true;

        public Joueur(String nomJoueur,Carreau positionCourante, Monopoly monopoly) {
            setNomJoueur(nomJoueur);
            setPositionCourante(positionCourante);
            setMonopoly(monopoly);
        }

	public void allerPrison() {
            this.setPositionCourante(monopoly.getCarreau(11)); // 11 = position de la prison
            this.setDrapeauPrison(true);
            this.monopoly.getInter().afficherAllerPrison(this);
	}
        public void liberePrison() {
            this.setDrapeauPrison(false);
            this.setNbTourPrison(0);
        }

	public void avancer(int dés) {
            this.dernierJetDes = dés;
            if (getPositionCourante().getNumero() + dés > 40) {
                this.setPositionCourante(monopoly.getCarreau(getPositionCourante().getNumero() + dés - 40));
                this.addCash(200);
            } else {
                this.setPositionCourante(monopoly.getCarreau(getPositionCourante().getNumero() + dés));
            }
	}
        
        public void avancerViaCarte(int numcase){ // Méthode appelée exclusivement par une Carte (Chance ou Caisse)
        int i = getPositionCourante().getNumero();
            if (i > numcase){
                i = 40-i + numcase;
            }
            else {
                i = numcase-i;
            }
            avancer(i);
        }
        
        public void payer(Joueur creancier, int loyer) {
            int argentEffectif = removeCash(loyer);
            creancier.addCash(argentEffectif);
            getMonopoly().getInter().afficheMontantPayer(creancier, loyer);
	}
        
        public void reculer(int nbcases) {
            if (getPositionCourante().getNumero()-nbcases < 1) {
                this.setPositionCourante(monopoly.getCarreau(getPositionCourante().getNumero() - nbcases + 40));
            } else {
                this.setPositionCourante(monopoly.getCarreau(getPositionCourante().getNumero() - nbcases));
            }
        }

        
        /* ----------------- */
        /* GETTEUR / SETTEUR */
        /* ----------------- */

        public String getNomJoueur() {
            return nomJoueur;
        }
        public void setNomJoueur(String nomJoueur) {
            this.nomJoueur = nomJoueur;
        }
        
        public void incDouble() {
            this.nbDouble++;
	}


        public void setCash(int cash){
            this.cash = cash;
        }
	public int getCash() {
		return this.cash;
	}
        public void addCash(int cash){
            this.cash += cash;
        }
        /**
         * Réduit le capital financier du joueur
         * @param cash L'argent à enlevé au joueur
         * @return L'argent réellement enlevé au joueur.
         */
	public int removeCash(int cash) {
            int cashJoueur = this.getCash();
            if (cashJoueur >= cash){ // Si le joueur a asser d'argent on lui enleve cash
                this.setCash(cashJoueur - cash);
                return cash; // Et on retourne cash
            } else { // Sinon, on lui enlève tout son argent
                this.setCash(-1);
                this.setPeutJouer(false);
                return cashJoueur; // et on retourne ce qu'il avait
            }
	}
        

        
	public int getNbGare() {
            return this.gares.size();
	}
	public void addGare(Gare gare) {
            this.gares.add(gare);
	}
        public ArrayList<Gare> getGares(){
            return this.gares;
        }
        
        
        public int getNbCompagnie() {
            return this.gares.size();
        }
	public void addCompagnie(Compagnie compagnie) {
            this.compagnies.add(compagnie);
	}
        public ArrayList<Compagnie> getCompagnies(){
            return this.compagnies;
        }
        
        public ArrayList<ProprieteAConstruire> getProprietesAConstruire() {
            return this.proprietesAConstruire;
        }
	public void addPropriete(ProprieteAConstruire prop) {
            int i = 0;
            while (i <= proprietesAConstruire.size()-1){
                if (proprietesAConstruire.get(i).getNumero() < prop.getNumero()){
                    i++;
                } else {
                    break;
                }
            }
            

            this.proprietesAConstruire.add(i,prop);
	}
        
        public int getNbMaisonsConstruites(){
            int nbMaisons = 0;
            for (ProprieteAConstruire prop : getProprietesAConstruire()){
                nbMaisons += prop.getNbMaisons();
            }
            return nbMaisons;
        }
        
        public int getNbHotelsConstruits(){
            int nbHotels = 0;
            for (ProprieteAConstruire prop : getProprietesAConstruire()){
                nbHotels += prop.getNbHotel();
            }
            return nbHotels;
        }


	public Carreau getPositionCourante() {
            return this.positionCourante;
	}
        public void setPositionCourante(Carreau c){
            this.positionCourante = c;
        }

	public int getDernierJetDés() {
            return this.dernierJetDes;
	}
        
        public boolean getDrapeauPrison(){
            return this.drapeauPrison;
        }
        public void setDrapeauPrison(boolean drapeau){
            this.drapeauPrison = drapeau;
        }

        public void setCarteChanceLibere(boolean carteChanceLibere) {
            this.carteChanceLibere = carteChanceLibere;
        }
        public boolean isCarteChanceLibere() {
            return carteChanceLibere;
        }

        
        public void setCarteCaisseLibere(boolean carteCaisseLibere) {
            this.carteCaisseLibere = carteCaisseLibere;
        }
        public boolean isCarteCaisseLibere() {
            return carteCaisseLibere;
        }
        
        
        public boolean getDoubleDe(){
            return this.doubleDe;
        }
        public void setDoubleDe(boolean doubleDe){
            this.doubleDe = doubleDe;
        }
        
        public int getNbDouble(){
            return this.nbDouble;
        }
        public void setNbDouble(int nbDouble){
            this.nbDouble = nbDouble;
        }
        public void incrNbDouble(){
            this.nbDouble++;
        }

        public Monopoly getMonopoly(){
            return this.monopoly;
        }
        private void setMonopoly(Monopoly monopoly) {
            this.monopoly = monopoly;
        }

        public int getNbTourPrison(){
            return this.nbTourPrison;
        }
        private void setNbTourPrison(int nbTourPrison){
            this.nbTourPrison = nbTourPrison;
        }
        public void incrNbTourPrison(){
            this.nbTourPrison += 1;
        }
        
        public boolean getPeutJouer(){
            return this.peutJouer;
        }
        public void setPeutJouer(boolean peutJouer){
            this.peutJouer = peutJouer;
        }
}