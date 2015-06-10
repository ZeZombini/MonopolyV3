package Jeu;

public class Gare extends CarreauPropriete {
    
    public Gare (int numero, String nomCarreau, Monopoly monopoly, int prixAchat){
        super(numero, nomCarreau, prixAchat, monopoly);
    }

    
    @Override
    public int calculLoyer(Joueur proprio) {
        int nbGare = proprio.getNbGare();
        return 25 * nbGare;
    }

    @Override
    public void action(Joueur j) {
        
    }

    @Override
    public void achatPropriété(Joueur j) {
        int cash = j.getCash();
        int prix = getPrixAchat();
        
        if (cash > prix){
            if(getMonopoly().inter.demandeAchatPropriété(this)){
                j.removeCash(prix);
                setProprietaire(j);
                j.addGare(this);
            }
            
        }
    }
}