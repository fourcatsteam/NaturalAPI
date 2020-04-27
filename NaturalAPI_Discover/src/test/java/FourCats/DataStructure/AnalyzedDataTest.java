package FourCats.DataStructure;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class AnalyzedDataTest {

    @InjectMocks
    AnalyzedData data;

    @Before
    public  void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testToString() {
        data.addDependency("withdraw","cash","dobj");
        data.addLemmaData("dogs","NNP","dog");

        String s = data.toString();
        String expected = "-- Token and lemma Data -- \n[dogs,NNP,dog]\n-- Dependencies Data -- \nwithdraw cash dobj\n";

        assertEquals(expected,s);
    }
}