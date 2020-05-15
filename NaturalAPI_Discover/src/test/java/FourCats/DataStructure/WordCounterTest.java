package FourCats.DataStructure;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

public class WordCounterTest {

    WordCounter w;

    @Before
    public void setUp() {
        w = new WordCounter("parola");
    }

    @Test
    public void testIncrementCounter() {
        w.incrementCounter();
        assertEquals(2,w.getCount().intValue());
    }

    @Test
    public void testDecrementCounter() {
        w.decrementCounter();
        w.decrementCounter();
        assertEquals(0,w.getCount().longValue());
    }

    @Test
    public void testToString() {
        assertEquals("[parola,1]",w.toString());
    }

    @Test
    public void testCompareLessThan() {
        WordCounter w2 = new WordCounter("cane",3);

        assertEquals(-1,w.compareTo(w2));
    }

    @Test
    public void testCompareEquals() {
        WordCounter w2 = new WordCounter("cane",1);

        assertEquals(0,w.compareTo(w2));
    }

    @Test
    public void testCompareMoreThan() {
        WordCounter w2 = new WordCounter("cane",1);
        w.incrementCounter();

        assertEquals(1,w.compareTo(w2));
    }
}