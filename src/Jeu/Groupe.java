package Jeu;

import java.util.ArrayList;

public class Groupe {
	private CouleurPropriete couleur;
	private int prixAchatConstruction;
	private ArrayList<ProprieteAConstruire> proprietes = new ArrayList<ProprieteAConstruire>();

    public Groupe(CouleurPropriete couleur, int prixAchatConstruction) {
        this.couleur = couleur;
        setPrixAchatConstruction(prixAchatConstruction);
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
    
    public int getPrixAchatConstruction(){return prixAchatConstruction;}
    public void setPrixAchatConstruction(int prixAchatConstruction){
        this.prixAchatConstruction = prixAchatConstruction;
    }
    
    public float getMoyenne(){
        float res=0;
        for (ProprieteAConstruire prop : proprietes){
            res += (prop.getNbHotel()*5) + prop.getNbMaisons();
        }
        return res/proprietes.size();
    }
}