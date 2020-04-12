package FourCats.DataStructure;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LemmatizerDataTest {

    LemmatizerData ld;

    @Before
    public void before() {
        ld = new LemmatizerData();
    }

    @Test
    public void getList() {
        assertTrue(ld.getList()!=null);
    }

    @Test
    public void addElement() {
        ld.addElement("dogs","NN","dog");
        assertEquals(1,ld.getList().size());
    }

    @Test
    public void testToString() {
        ld.addElement("dogs","NN","dog");
        ld.addElement("went","VBP","go");
        String expected = "-- Lemmatize Data -- \n[dogs,NN,dog]\n[went,VBP,go]\n";
        assertEquals(expected,ld.toString());
    }
}