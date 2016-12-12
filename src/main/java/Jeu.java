/**
 * Created by ndiaye on 12/12/2016.
 */
public class Jeu {
    protected Joueur joueur1;
    protected Joueur joueur2;
    protected enum State {
        NORMAL, DEUCE, AVANTAGE, TIE_BREAK
    }
    protected State etatCourant;

    public Jeu() {
        super();
        this.joueur1 = new Joueur("Joueur 1");
        this.joueur2 = new Joueur("Joueur 2");
        this.etatCourant = State.NORMAL;
    }

    public Joueur getJoueur1() {
        return joueur1;
    }

    public void setJoueur1(Joueur joueur1) {
        this.joueur1 = joueur1;
    }

    public Joueur getJoueur2() {
        return joueur2;
    }

    public void setJoueur2(Joueur joueur2) {
        this.joueur2 = joueur2;
    }

    public State getEtatCourant() {
        return etatCourant;
    }

    public void setEtatCourant(State etatCourant) {
        this.etatCourant = etatCourant;
    }
}
