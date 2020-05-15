package FourCats.DataStructure;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordTagTest {

    WordTag w;

    @Before
    public void before() {
        w = new WordTag("dogs","NN","dog");
    }

    @Test
    public void testToString() {
        assertEquals("[dogs,NN,dog]",w.toString());
    }
}