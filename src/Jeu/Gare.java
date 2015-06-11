package Jeu;

public class Gare extends CarreauPropriete {
    
    public Gare (int numero, String nomCarreau, Monopoly monopoly, int prixAchat){
        super(numero, nomCarreau, prixAchat, monopoly);
    }

    
    @Override
    public int calculLoyer(Joueur jProprio) {
        int nbGare = jProprio.getNbGare();
        return 25 * nbGare;
    }

    @Override
    public void action(Joueur j) {
        Joueur jProprio = getProprietaire();
        if (jProprio == null){
            achatPropriété(j);
        } else if (jProprio != j){
            int loyer = calculLoyer(jProprio);
            j.payer(jProprio, loyer);
        }
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