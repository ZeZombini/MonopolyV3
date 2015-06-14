package Jeu;

public class CarteChance extends Carte {
	private CarteChanceEnum action;
        
        public CarteChance(Monopoly monopoly, CarteChanceEnum action){
            super(monopoly);
            setAction(action);
        }
        
        public CarteChanceEnum getAction(){
            return action;
        }
        private void setAction(CarteChanceEnum action){
            this.action = action;
        }
        
        public void actionAEffectuer (){
            Joueur joueur = this.getMonopoly().getJoueurCourant();
            CarteChanceEnum action = this.getAction();
            this.getMonopoly().getInter().afficherActionCarteChance(action);
            switch(action){
                case libere_prison:
                    break;
                case payez_reparations_voirie:
                    payezReparationsVoirie(joueur);
                    break;
                case payez_reparations_maison:
                    payezReparationsMaison(joueur);
                    break;
                case payez_amende_vitesse:
                    payezAmendeVitesse(joueur);
                    break;
                case payez_amende_ivresse:
                    payezAmendeIvresse(joueur);
                    break;
                case payez_scolarite:
                    payezScolarite(joueur);
                    break;
                case recevez_prix_mots_croises:
                    recevezPrixMotsCroises(joueur);
                    break;
                case recevez_banque_dividende:
                    recevezBanqueDividende(joueur);
                    break;
                case recevez_immeuble_prêt:
                    recevezImmeublePret(joueur);
                    break;
                case allez_prison:
                    allezPrison(joueur);
                    break;
                case reculez_3cases:
                    reculez3Cases(joueur);
                    break;
                case avancez_depart:
                    avancezDepart(joueur);
                    break;
                case avancez_rue_paix:
                    avancezRuePaix(joueur);
                    break;
                case avancez_henri_martin:
                    avancezHenriMartin(joueur);
                    break;
                case avancez_gare_lyon:
                    avancezGareLyon(joueur);
                    break;
                case avancez_boulevard_villette:
                    avancezBoulevardVillette(joueur);
                    break;
                default:
            }
        }
        
        public void payezReparationsVoirie(Joueur joueur){
            int i = (joueur.getNbMaisonsConstruites()*40);
            i += (joueur.getNbHotelsConstruits()*115);
            joueur.removeCash(i);
        }
        public void payezReparationsMaison(Joueur joueur){
            int i = (joueur.getNbMaisonsConstruites()*25);
            i += (joueur.getNbHotelsConstruits()*100);
            joueur.removeCash(i);
        }
        public void payezAmendeVitesse(Joueur joueur){
            joueur.removeCash(15);
        }
        public void payezAmendeIvresse(Joueur joueur){
            joueur.removeCash(20);
        }
        public void payezScolarite(Joueur joueur){
            joueur.removeCash(150);
        }
        public void recevezPrixMotsCroises(Joueur joueur){
            joueur.addCash(100);
        }
        public void recevezBanqueDividende(Joueur joueur){
            joueur.addCash(50);
        }
        public void recevezImmeublePret(Joueur joueur){
            joueur.addCash(150);
        }
        public void allezPrison(Joueur joueur){
            joueur.allerPrison();
        }
        public void reculez3Cases(Joueur joueur){
            joueur.reculer(3);
            joueur.getPositionCourante().action(joueur);
        }
        public void avancezDepart(Joueur joueur){
            joueur.avancerViaCarte(1);
            getMonopoly().getCarreau(1).action(joueur);
        }
        public void avancezRuePaix(Joueur joueur){
            joueur.avancerViaCarte(40);
            getMonopoly().getCarreau(40).action(joueur);
        }
        public void avancezHenriMartin(Joueur joueur){
            joueur.avancerViaCarte(25);
            getMonopoly().getCarreau(25).action(joueur);
        }
        public void avancezGareLyon(Joueur joueur){
            joueur.avancerViaCarte(16);
            getMonopoly().getCarreau(16).action(joueur);
        }
        public void avancezBoulevardVillette(Joueur joueur){
            joueur.avancerViaCarte(12);
            getMonopoly().getCarreau(12).action(joueur);
        }
}