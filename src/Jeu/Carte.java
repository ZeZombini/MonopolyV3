package Jeu;

public abstract class Carte {
    public Monopoly monopoly;
        
    public Carte(Monopoly monopoly){
        setMonopoly(monopoly);
    }
    
    public Monopoly getMonopoly(){
        return monopoly;
    }
    private void setMonopoly(Monopoly monopoly){
        this.monopoly = monopoly;
    }
}