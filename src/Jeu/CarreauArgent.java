package Jeu;

public class CarreauArgent extends CarreauAction {
	private int montant;
        
    public CarreauArgent(int numero, String nomCarreau, Monopoly monopoly, int montant) {
        super(numero, nomCarreau, monopoly);
        setMontant(montant);
    }

    public int getMontant() {
        return montant;
    }
    private void setMontant(int montant) {
        this.montant = montant;
    } 

    @Override
    public void action(Joueur j) {
        j.removeCash(montant);
    }
}