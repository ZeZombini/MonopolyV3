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
    
    /**
     * Informe si le joueur définit possède la totalité des propriétés du groupe
     * @param p le joueur dont on veut savoir si il est propriétaire.
     * @return vrai si le joueur est propriétaire de toutes les propriétés.
     */
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
    
    public int getPrixAchatConstruction(){
        return prixAchatConstruction;
    }
    public void setPrixAchatConstruction(int prixAchatConstruction){
        this.prixAchatConstruction = prixAchatConstruction;
    }
    
    public CouleurPropriete getCouleur(){
        return this.couleur;
    }
    
    /**
     * Permet de savoir le nombre de propriétés que possèdent en moyenne 
     * les propriétés du groupe (les hôtels comptant pour 5 constructions).
     * @return le nombre de constructions moyen des propriétés du groupe.
     */
    public float getMoyenne(){
        float res=0;
        for (ProprieteAConstruire prop : proprietes){
            res += (prop.getNbHotel()*5) + prop.getNbMaisons();
        }
        return res/proprietes.size();
    }
}