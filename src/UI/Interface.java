package UI;

import Jeu.Carreau;
import Jeu.CarreauPropriete;
import Jeu.CarteCaisseEnum;
import Jeu.CarteChanceEnum;
import Jeu.Compagnie;
import Jeu.Gare;
import Jeu.Joueur;
import Jeu.Monopoly;
import Jeu.ProprieteAConstruire;
import java.util.ArrayList;
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
    }

        
        /* METHODE D'AFFICHAGE SIMPLE */
        public void lancementDes(){
            Scanner sc = new Scanner(System.in);
            System.out.println("Appuyez sur ENTRE pour lancer les dés");
            sc.nextLine();
            
        }
        public void afficheRecapDebutTour(Joueur j){
            System.out.println("");
            System.out.println("---------------------------------------");
            System.out.println("Recapitulatif :");
            for (Joueur jTemp : getMonopoly().getJoueurs()){
                System.out.println(" - Le joueur " + jTemp.getNomJoueur() + " se trouve sur la case " 
                        + jTemp.getPositionCourante().getNumero() + " : "
                        + jTemp.getPositionCourante().getNomCarreau());
                System.out.println("   Il possède " + jTemp.getCash() + " euros");
                for (Gare g : jTemp.getGares()){
                    System.out.println("    - " + g.getNomCarreau());
                }
                for (Compagnie c : jTemp.getCompagnies()){
                    System.out.println("    - " + c.getNomCarreau());
                }
                for (ProprieteAConstruire p : jTemp.getProprietesAConstruire()){
                    System.out.println("    - " + p.getGroupePropriete().getCouleur() + " : " + p.getNomCarreau());
                }
            }
            System.out.println("");
            System.out.println("Tour de " + j.getNomJoueur());
        }
        
        public void afficherFinDuTour(Joueur j){
            System.out.println(j.getNomJoueur() + " a fini son tour.");
        }
        
	public void afficherLancerDes(int de1, int de2) {
            System.out.println("Vous faites un lancé de " + (de1 + de2));
            if (de1==de2){
                System.out.println("Bravo, vous faites un double !");
            }
	}

	public void afficherEtatJoueur(Joueur j) {
            System.out.println("Vous arrivez sur la case " + j.getPositionCourante().getNumero() + " : " + j.getPositionCourante().getNomCarreau());
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
        
        public void afficherJoueurPerdu(Joueur j){
            System.out.println("******************************************");
            System.out.println("Le joueur " + j.getNomJoueur() + " est éliminiée.");
            System.out.println("Toutes ses propriétés sont remisent en jeux");
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
        
        public boolean demandeUtilisationCarte(){
            Scanner sc = new Scanner(System.in);
            System.out.println("Voulez vous utiliser votre carte Libéré de prison ?");
            String choix = sc.nextLine();
            while (!"oui".equals(choix) && !"non".equals(choix)){
                System.out.println("Je n'ai pas compris votre choix");
                System.out.println("oui/non : ");
                choix = sc.nextLine();
            }
                return "oui".equals(choix);          
        }
        
        public int demanderChoixProp(ArrayList<ProprieteAConstruire> proprietesConstructibles){
            Scanner sc = new Scanner(System.in);
            System.out.println("Voulez-vous construire sur une des propriétés suivantes ?");
            System.out.println("0 - Ne pas construire.");
            int i = 0;
            for (ProprieteAConstruire prop : proprietesConstructibles){
                System.out.println((i+1) + " - Construire sur la case " + prop.getNomCarreau());
                i++;
            }
            int choix = sc.nextInt();
            while (choix<0 | choix>i){
                System.out.println("Veuillez saisir un chiffre présent dans la liste.");
                choix = sc.nextInt();
            }
            return choix;
        }
        

}