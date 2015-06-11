package Jeu;

public abstract class Carreau {
	private int numero;
	private String nomCarreau;
	private Monopoly monopoly;
        
        
        public Carreau(int numero, String nom, Monopoly monopoly){
            setNumero(numero);
            setNomCarreau(nom);
            setMonopoly(monopoly);
        }
        
        public int getNumero() {
            return numero;
        }
        private void setNumero(int numero) {
            this.numero = numero;
        }

        public String getNomCarreau() {
            return nomCarreau;
        }
        private void setNomCarreau(String nomCarreau) {
            this.nomCarreau = nomCarreau;
        }

        public Monopoly getMonopoly() {
            return monopoly;
        }
        private void setMonopoly(Monopoly monopoly) {
            this.monopoly = monopoly;
        }
        
        /**
         * Effectue l'action correspondant au carreau où se trouve le joueur j.
         * @param j
         */
        public abstract void action(Joueur j);
}