package Jeu;

public abstract class CarreauPropriete extends Carreau {
    private int prixAchat;
    private Joueur proprietaire;

    public CarreauPropriete(int numero, String nom, int prixAchat, Monopoly monopoly) {
        super(numero, nom, monopoly);
        setPrixAchat(prixAchat);
    }

    public void setPrixAchat(int prixAchat){
        this.prixAchat = prixAchat;
    }
    public int getPrixAchat() {
        return prixAchat;
    }

    public void setProprietaire(Joueur j) {
        this.proprietaire = j;
    }
    public Joueur getProprietaire() {
        return this.proprietaire;
    }

    public abstract void achatPropriété(Joueur j);
    
    public abstract int calculLoyer(Joueur proprio);
    

}