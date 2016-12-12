/**
 * Created by ndiaye on 12/12/2016.
 */
public class Joueur {
    protected int set;
    protected int jeu;
    protected int tieBreak;
    protected boolean avantage;
    protected String nom;


    public Joueur(String nom) {
        this.set = 0 ;
        this.jeu = 0;
        this.tieBreak = 0;
        this.avantage = false;
        this.nom = nom;
    }
    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getJeu() {
        return jeu;
    }

    public void setJeu(int jeu) {
        this.jeu = jeu;
    }

    public int getTieBreak() {
        return tieBreak;
    }

    public void setTieBreak(int tieBreak) {
        this.tieBreak = tieBreak;
    }

    public boolean isAvantage() {
        return avantage;
    }

    public void setAvantage(boolean avantage) {
        this.avantage = avantage;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void incrementSet(){
        this.setSet(this.getSet()+1);
        this.setAvantage(false);
        this.setJeu(0);
        this.setTieBreak(0);
    }

}
