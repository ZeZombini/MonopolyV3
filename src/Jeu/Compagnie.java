package Jeu;

public class Compagnie extends CarreauPropriete {

    public Compagnie(int numero, String nomCarreau, Monopoly monopoly, int prixAchat){
        super(numero, nomCarreau, prixAchat, monopoly);
    }
    
    @Override
    public void action(Joueur j) {
        Joueur jProprio = getProprietaire();
        if (jProprio == null){
            achatPropriété(j);
        }
    }
    
    @Override
    public int calculLoyer(Joueur proprio) {
        return 0;
    }

    @Override
    public void achatPropriété(Joueur j) {
        int cash = j.getCash();
        int prix = getPrixAchat();
        
        if (cash > prix){
            if(getMonopoly().inter.demandeAchatPropriété(this)){
                j.removeCash(prix);
                setProprietaire(j);
                j.addCompagnie(this);
            }
            
        }
    }


}