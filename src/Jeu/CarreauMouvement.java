package Jeu;


import Jeu.CarreauAction;

public class CarreauMouvement extends CarreauAction {
    
    public CarreauMouvement(int numero, String nomCarreau, Monopoly monopoly) {
        super(numero, nomCarreau, monopoly);
    }

    @Override
    public void action(Joueur j) {
        j.allerPrison();
    }
}