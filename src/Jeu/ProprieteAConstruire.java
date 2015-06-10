package Jeu;

import java.util.ArrayList;

public class ProprieteAConstruire extends CarreauPropriete {
	private int nbMaisons = 0;
	private ArrayList<Integer> loyers;
	private int nbHotel = 0;
	private Groupe groupePropriete;
        
        public ProprieteAConstruire(int numero, String nomCarreau, Monopoly monopoly, int prixAchat, ArrayList<Integer> loyers, Groupe groupePropriete){
            super(numero, nomCarreau, prixAchat, monopoly);
            setLoyers(loyers);
            setGroupePropriete(groupePropriete);
            groupePropriete.addProprietes(this);
        }

        @Override
	public int calculLoyer(Joueur proprio) {
            Groupe g = getGroupePropriete();
            if(g.estPossede(proprio)){
                if (this.getNbHotel() == 1){
                    return getLoyers().get(5);
                } else if (this.getNbMaisons() != 0){
                    return getLoyers().get(getNbMaisons());
                } else {
                    return getLoyers().get(0)*2;
                }
            } else {
                return getLoyers().get(0);
            }
	}

        @Override
        public void achatPropriété(Joueur j) {
            
        }

        @Override
        public void action(Joueur j) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        
        /* GETTEUR ET SETTEUR */
        
        public int getNbMaisons() {
            return nbMaisons;
        }
        public void setNbMaisons(int nbMaisons) {
            this.nbMaisons = nbMaisons;
        }
        public void construireMaison(){
            this.nbMaisons += 1;
        }

        public int getNbHotel() {
            return nbHotel;
        }
        public void setNbHotel(int nbHotel) {
            this.nbHotel = nbHotel;
        }
        
        /**
         * Rajoute un hotel et supprime 4 maison à la propriété
         */
        public void construireHotel(){
            this.nbHotel += 1;
            this.nbMaisons -= 4;
        }
        
        public ArrayList<Integer> getLoyers() {
            return loyers;
        }

        public void setLoyers(ArrayList<Integer> loyerMaison) {
            this.loyers = loyerMaison;
        }

        public Groupe getGroupePropriete() {
            return groupePropriete;
        }

        public void setGroupePropriete(Groupe groupePropriete) {
            this.groupePropriete = groupePropriete;
        }
        
        public ArrayList<ProprieteAConstruire> peutConstruire(){
            Joueur j = this.getMonopoly().getJoueurCourant();
            Groupe groupe = this.getGroupePropriete();
            ArrayList <ProprieteAConstruire> res = new ArrayList();
            
            if (groupe.estPossede(j) && groupe.getPrixAchatConstruction() <= j.getCash()){
                for (ProprieteAConstruire prop : groupe.getProprietes()){
                    if ( (prop.getNbMaisons() + (prop.getNbHotel()*5)) <= groupe.getMoyenne() 
                            && prop.getNbMaisons()<4 
                            && this.getMonopoly().getNbMaisons() != 0){
                        res.add(prop);
                    }
                    else if ((prop.getNbMaisons() + (prop.getNbHotel()*5)) <= groupe.getMoyenne() 
                            && prop.getNbMaisons() == 4 
                            && this.getMonopoly().getNbHotels() != 0){
                        res.add(prop);
                    }
                }
            }
            return res;
        }
        
        public void Construire(){
            if (this.getNbMaisons()<4){
                this.construireMaison();
                this.getMonopoly().decrNbMaisons(1);
            }
            else {
                this.construireHotel();
                this.getMonopoly().decrNbHotels(1);
            }
            this.getMonopoly().getJoueurCourant().removeCash(this.getGroupePropriete().getPrixAchatConstruction());
        }

}