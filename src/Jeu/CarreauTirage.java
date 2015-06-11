package Jeu;


import Jeu.CarreauAction;

public class CarreauTirage extends CarreauAction {
    
    public CarreauTirage(int numero, String nomCarreau, Monopoly monopoly) {
        super(numero, nomCarreau, monopoly);
    }
    
    @Override
    public void action(Joueur j){
        if ("Caisse de Communauté".equals(this.getNomCarreau())){
            CarteCaisse carteATirer = this.getMonopoly().getCartesCaisse().pollFirst();
            carteATirer.actionAEffectuer();
            if (carteATirer.getAction() != CarteCaisseEnum.libere_prison){
                this.getMonopoly().getCartesCaisse().add(carteATirer);
            }
            else {
                this.getMonopoly().getJoueurCourant().setCarteCaisseLibere(true);
            }
        }
        else {
            CarteChance carteATirer = this.getMonopoly().getCartesChance().pollFirst();
            carteATirer.actionAEffectuer();
            if (carteATirer.getAction() != CarteChanceEnum.libere_prison){
                this.getMonopoly().getCartesChance().add(carteATirer);
            }
            else {
                this.getMonopoly().getJoueurCourant().setCarteChanceLibere(true);
            }
        }
    }
}