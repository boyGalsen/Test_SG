import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ndiaye on 12/12/2016.
 */
public class JeuTest {

    @Test
    public void initEtatNormalTest(){
        Jeu j= new Jeu();
        Assert.assertTrue(j.getEtatCourant()== Jeu.State.NORMAL);
    }

    @Test
    public void eatDeuceTest(){

       Joueur j1=  new Joueur("j1");
        j1.setJeu(40);
        Joueur j2=  new Joueur("j2");
        j2.setJeu(30);
        Jeu jeu= new Jeu(j1,j2);
        jeu.joueur2MarqueContreJoueur1();

        Assert.assertTrue(jeu.getEtatCourant()== Jeu.State.DEUCE);
    }

    @Test
    public void eatTieBreakTest(){

        Joueur j1=  new Joueur("j1");
        Joueur j2=  new Joueur("j2");
        Jeu jeu= new Jeu(j1,j2);
        j2.setJeu(5);
        j1.setJeu(6);
        j2.incrementSet();
        Assert.assertTrue(jeu.getEtatCourant()== Jeu.State.TIE_BREAK);
    }
}
