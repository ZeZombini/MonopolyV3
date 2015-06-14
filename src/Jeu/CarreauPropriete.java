package Jeu;

public abstract class CarreauPropriete extends Carreau {
    private int prixAchat;
    private Joueur proprietaire;

    public CarreauPropriete(int numero, String nom, int prixAchat, Monopoly monopoly) {
        super(numero, nom, monopoly);
        setPrixAchat(prixAchat);
    }

    private void setPrixAchat(int prixAchat){
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

    /**
     * Lance la procédure d'achat de propriété.
     * @param j Le joueur à qui le carreau proposera l'achat.
     */
    public abstract void achatPropriété(Joueur j);
    
    /**
     * Calcul le loyer de la propriété.
     * @param proprio Le propriétaire de la propriété.
     * @return Le loyer a payer
     */
    public abstract int calculLoyer(Joueur proprio);
    

}