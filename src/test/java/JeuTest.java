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
}
