package FourCats.Entities;

import FourCats.AppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DocumentTest {

    Document doc;

    @Before
    public void before() {
        doc = new Document("My document","In this document there are seven words");
    }

    @Test
    public void testConstructor() {
        Document d = new Document("","");
        assertEquals("",d.getContent());
    }

    @Test
    public void testToString() {
        String expected = "Document{title='My document', content='In this document there are seven words'}";
        assertEquals(expected,doc.toString());
    }
}