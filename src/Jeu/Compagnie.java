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
        } else if (jProprio != j){
            int loyer = calculLoyer(jProprio);
            getMonopoly().getInter().afficheMontantPayer(jProprio, loyer);
            j.payer(jProprio, loyer);
        }
    }
    
    @Override
    public int calculLoyer(Joueur jProprio) {
        Joueur j = getMonopoly().getJoueurCourant();
        int dernierJetDes = j.getDernierJetDés();
        int nbCompagnie = jProprio.getNbCompagnie();
        if (nbCompagnie == 2){
            return dernierJetDes*10;
        } else {
            return dernierJetDes*4;
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
                j.addCompagnie(this);
            }
        }
    }


}