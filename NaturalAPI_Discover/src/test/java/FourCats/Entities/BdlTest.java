package FourCats.Entities;

import FourCats.AppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class BdlTest {

    Bdl bdl = null;

    @Before
    public void before() {
        bdl = new Bdl("myFirstBdl");
    }

    @Test
    public void testConstructorSetName() {
        assertEquals("myFirstBdl",bdl.getName());
    }

    @Test
    public void testAddNoun() {
        bdl.addNoun("dog");
        bdl.addNoun("cat");
        bdl.addNoun("dog");
        assertEquals(2,bdl.getNouns().size());
    }

    @Test
    public void testAddVerb() {
        bdl.addVerb("eat");
        bdl.addVerb("sleep");
        bdl.addVerb("watch");
        assertEquals(3,bdl.getVerbs().size());
    }

    @Test
    public void testAddPredicate() {
        bdl.addPredicate("withdraw cash");
        bdl.addPredicate("withdraw cash");
        bdl.addPredicate("withdraw cash");
        assertEquals(3,bdl.getPredicates().get(0).getCount().longValue());
    }

    @Test
    public void testAddNounWithFrequency() {
        bdl.storeBdlNoun("dog", 5);
        assertEquals(5,bdl.getNouns().get(0).getCount().longValue());
    }

    @Test
    public void testAddVerbWithFrequency() {
        bdl.storeBdlVerb("eat", 5);
        assertEquals(5,bdl.getVerbs().get(0).getCount().longValue());
    }

    @Test
    public void testAddPredicateWithFrequency() {
        bdl.storeBdlPredicate("withdraw cash", 5);
        assertEquals(5,bdl.getPredicates().get(0).getCount().longValue());
    }

    @Test
    public void testRemoveNoun() {
        bdl.addNoun("dog");
        bdl.removeNoun("dog");
        assertTrue(bdl.getNouns().isEmpty());
    }

    @Test
    public void testRemoveVerb() {
        bdl.storeBdlVerb("eat",4);
        bdl.removeVerb("eat");
        assertEquals(3,bdl.getVerbs().get(0).getCount().longValue());
    }

    @Test
    public void testRemovePredicate() {
        bdl.addPredicate("withdraw cash");
        bdl.storeBdlPredicate("eat pizza", 2);
        bdl.removePredicate("withdraw cash");
        assertEquals(1, bdl.getPredicates().size());
    }

    @Test
    public void testStringConversion() {
        bdl.addNoun("dog");
        bdl.addNoun("cat");
        bdl.storeBdlVerb("eat", 5);
        bdl.addPredicate("withdraw cash");
        String conversion = bdl.toString();
        String expected = "-- NOUNS -- \n[dog,1]\n[cat,1]\n-- VERBS -- \n[eat,5]\n-- PREDICATES -- \n[withdraw cash,1]\n";
        assertEquals(expected,conversion);
    }

    @Test
    public void testNotNullNounsReference() {
        assertTrue(bdl.getNouns()!=null);
    }

    @Test
    public void testNotNullVerbsReference() {
        assertTrue(bdl.getVerbs()!=null);
    }

    @Test
    public void testNotNullPredicatesReference() {
        assertTrue(bdl.getPredicates()!=null);
    }
}