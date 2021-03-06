package fourcats.datastructure;

import edu.stanford.nlp.ling.Word;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordCounterTest {

    WordCounter w;

    @Before
    public void before() {
        w = new WordCounter("parola",1);
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
    public void compareToTest(){
        assertEquals(-1,w.compareTo(new WordCounter("parola",2)));
    }

    @Test
    public void testToString() {
        assertEquals("[parola,1]",w.toString());
    }
}