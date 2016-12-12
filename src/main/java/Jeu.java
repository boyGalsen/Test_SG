import java.util.Scanner;

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
    public Jeu(Joueur j1, Joueur j2){
        this.joueur1=j1;
         this.joueur2=j2;
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
    private void verifierScoreNormal(Joueur j1, Joueur j2){
        if(j1.getSet() == 6 && j2.getSet() == 6){
            this.setEtatCourant(State.TIE_BREAK);
        }else if(j1.getSet() >= 6 && (j1.getSet() - j2.getSet() >=2 )){
            System.out.println(j1.getNom()+" a gagné la partie !");
        }
    }

    private void verifierScoreTieBreak(Joueur j1, Joueur j2){
        if(j1.getTieBreak() > 6 && (j1.getTieBreak() - j2.getTieBreak() >=2 )){
            System.out.println(j1.getNom()+" a gagné la partie !");
        }
    }
    public void afficherScore(){
        switch (this.getEtatCourant()) {
            case NORMAL:
                System.out.println(joueur1.getNom()+" : Set("+joueur1.getSet()+") | Points("+joueur1.getJeu()+")");
                System.out.println(joueur2.getNom()+" : Set("+joueur2.getSet()+") | Points("+joueur2.getJeu()+")");
                break;
            case DEUCE:
                System.out.println("DEUCE");
                break;
            case AVANTAGE:
                System.out.println(joueur1.getNom()+" : Set("+joueur1.getSet()+") | AVANTAGE("+joueur1.isAvantage()+")");
                System.out.println(joueur2.getNom()+" : Set("+joueur2.getSet()+") | AVANTAGE("+joueur2.isAvantage()+")");

                break;
            case TIE_BREAK:
                System.out.println("TIE BREAK ");
                System.out.println(joueur1.getNom()+" : Points("+joueur1.getTieBreak()+")");
                System.out.println(joueur2.getNom()+" : Points("+joueur2.getTieBreak()+")");
                break;
        }
    }
    private void marquer(Joueur j1, Joueur j2) {
        switch (this.getEtatCourant()) {
            case NORMAL:
                if (j1.getJeu() < 40) {
                    j1.incrementJeu();

                    if(j1.getJeu() == 40 && j2.getJeu() == 40){
                        this.setEtatCourant(State.DEUCE);
                    }
                } else {
                    if (j2.getJeu() < 40) {
                        j1.incrementSet();
                        j2.setJeu(0);
                        verifierScoreNormal(j1,j2);
                    } /*else {
					this.setEtatCourant(State.DEUCE);
				}*/
                }

                break;
            case DEUCE:
                j1.setAvantage(true);
                this.setEtatCourant(State.AVANTAGE);
                break;
            case AVANTAGE:
                if(j2.isAvantage()){
                    this.setEtatCourant(State.DEUCE);
                    j2.setAvantage(false);
                }else{
                    j1.incrementSet();
                    j2.setJeu(0);
                    verifierScoreNormal(j1,j2);
                    this.setEtatCourant(State.NORMAL);
                }
                break;
            case TIE_BREAK:
                j1.incrementTieBreak();
                verifierScoreTieBreak(j1,j2);
                break;
        }

        afficherScore();
    }

    public void joueur1MarqueContreJoueur2() {
        marquer(this.joueur1,this.joueur2);
    }

    public void joueur2MarqueContreJoueur1() {
        marquer(this.joueur2,this.joueur1);
    }

    public void jouer(){
        Scanner in = new Scanner(System.in);
        System.out.println("taper 1 ou 2 pour le joueur qui vient de marquer, sinon taper q pour finir");

        String entree = in.nextLine();

        while(!entree.equals("q")){
            if(entree.equals("1")){
                this.joueur1MarqueContreJoueur2();
            }else if(entree.equals("2")){
                this.joueur2MarqueContreJoueur1();
            }
            entree = in.nextLine();
        }
    }

    public static void main(String [] args){
        Jeu jeu = new Jeu();
        jeu.jouer();

    }
}
