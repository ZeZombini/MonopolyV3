package Jeu;

import java.util.ArrayList;

public class Groupe {
	private CouleurPropriete couleur;
	private int prixAchatMaison;
	private int prixAchatHotel;
	private ArrayList<ProprieteAConstruire> proprietes = new ArrayList<ProprieteAConstruire>();

    public Groupe(CouleurPropriete couleur, int prixAchatMaison, int prixAchatHotel) {
        this.couleur = couleur;
        this.prixAchatMaison = prixAchatMaison;
        this.prixAchatHotel = prixAchatHotel;
    }
    
    
    
    public boolean estPossede(Joueur p) {
        Boolean res = true;
        for (ProprieteAConstruire prop : proprietes){
            if (p != prop.getProprietaire()){
                res = false;
            }
        }
        return res;
    }

    
    /* GETTEUR / SETTEUR */

    public ArrayList<ProprieteAConstruire> getProprietes() {
        return proprietes;
    }
    public void addProprietes(ProprieteAConstruire c){
        proprietes.add(c);
    }
}