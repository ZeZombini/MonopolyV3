package UI;

import Jeu.Carreau;
import Jeu.CarreauPropriete;
import Jeu.CarteCaisseEnum;
import Jeu.CarteChanceEnum;
import Jeu.Joueur;
import Jeu.Monopoly;
import java.util.Scanner;

public class Interface {
	public Monopoly monopoly;
        
    public Interface(Monopoly monopoly) {
        setMonopoly(monopoly);
    }
       
    public Monopoly getMonopoly() {
        return monopoly;
    }
    public void setMonopoly(Monopoly monopoly) {
        this.monopoly = monopoly;
    }
    
    public void initialiserPartie (){
        Scanner sc = new Scanner(System.in);
        Scanner sc1 = new Scanner(System.in);
        String[] nomj = new String[6];
        int[]    nbrj = new int[6];
        int nbrJoueur = 0;
        String nomJoueur;
        while(nbrJoueur<2 | nbrJoueur>6){
            System.out.println("Veuillez indiquer le nombre de joueur : ");
            nbrJoueur = sc.nextInt();
        }
        for(int i=0; i<nbrJoueur; i++){
            System.out.println("Veuillez saisir le nom du joueur n°"+(i+1)+" : ");
            nomJoueur = sc1.nextLine();
            nomj[i] = nomJoueur;
            //monopoly.nouveauJoueur(nomJoueur);
        }
        
        // met un lancé de dé pour chaque joueur pour savoir qui commence
        System.out.println("---------------------------------------");
        System.out.println("Les joueurs vont jouer dans cet ordre :");
        for(int i=0; i<nbrJoueur; i++){ 
            nbrj[i] = monopoly.lancerDe();
            //System.out.println(nbrj[i]);
        }
        
        for(int i=0; i<nbrJoueur; i++){
            int maxi = 0; //  le nombre max des lancés de dés
            int jmax = 0; //  le joueur ayant le nombre max
            for(int j=0; j<nbrJoueur; j++){
                if (nbrj[j]>maxi) {
                   maxi = nbrj[j]; 
                   jmax = j;
                }
            }
            nbrj[jmax] = 0; // met la valeur du lancé du joueur selectionné à 0
            System.out.println(nomj[jmax]);
            monopoly.nouveauJoueur(nomj[jmax]); // crée un joueur avec son nom
        
        }
        monopoly.setJoueurCourant(monopoly.getJoueurs().get(0));
        
    }

        
        /* METHODE D'AFFICHAGE SIMPLE */
	public void afficherLancerDes(int de1, int de2) {
            System.out.println("Le résultat du premier dé est " + de1 + " et le résultat du deuxième est " + de2 + "\nCe qui donne une somme de " + (de1 + de2));
	}

	public void afficherEtatJoueur(Joueur j) {
            System.out.println("Vous arrivez sur la case : " + j.getPositionCourante().getNomCarreau());
	}
        
        public void afficherAllerPrison(Joueur j) {
            if (j.getNbDouble() == 3) {
                System.out.println( "Le joueur " + j.getNomJoueur() + " va en prison car il a fait 3 doubles d'affilé !");
            } else {
                System.out.println("Le joueur " + j.getNomJoueur() + " va en prison!");
            }
        }
        
        public void afficheMontantPayer(Joueur jProprio, int montant){
             System.out.println("Vous payez " + montant + " au joueur " + jProprio.getNomJoueur());
        }
        
        public void afficherActionCarteChance(CarteChanceEnum action){
            System.out.println("************** Carte Chance **************");
            System.out.println(action.toString());
            System.out.println("******************************************");
        }

        public void afficherActionCarteCaisse(CarteCaisseEnum action){
            System.out.println("********** Caisse de communauté **********");
            System.out.println(action.toString());
            System.out.println("******************************************");
        }
        
        /* METHODE DE DEMANDE (RETOURNANT AUTRE CHOSE QU'UN VOID */
	public boolean demandeAchatPropriété(CarreauPropriete c) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Voulez vous acheter " + c.getNomCarreau() + " pour la somme de " + c.getPrixAchat() + " euros ? (oui/non)");
            String choix = sc.nextLine();
            while (!"oui".equals(choix) & !"non".equals(choix)){
                System.out.println("Je n'ai pas compris votre choix");
                System.out.println("oui/non : ");
                choix = sc.nextLine();
            }
                return "oui".equals(choix);
        }

}