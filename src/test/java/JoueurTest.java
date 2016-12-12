/**
 * Created by ndiaye on 12/12/2016.
 */

import org.junit.Assert;
import org.junit.Test;

public class JoueurTest   {

    @Test
    public void incrementSetTest(){
        Joueur j=new Joueur("J1");
        j.incrementSet();
        Assert.assertTrue(j.getSet()==1);
    }


}
