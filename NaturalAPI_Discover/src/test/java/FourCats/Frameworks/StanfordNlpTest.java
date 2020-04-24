package FourCats.Frameworks;

import FourCats.DataStructure.AnalyzedData;
import org.junit.Test;

import static org.junit.Assert.*;

public class StanfordNlpTest {

    StanfordNlp nlp = new StanfordNlp();

    /*@Test
    public void testParseDocumentContent() {
        String content = "My dog also likes eating sausage.";

        AnalyzedData data = nlp.parseDocumentContent(content);

        assertEquals("dog",data.getTaggedData().get(1).getLemma());
        assertEquals("NN",data.getTaggedData().get(1).getTag());

        //not very correct
        assertEquals(1,data.getParseList().size());
        assertEquals("eat sausage",data.getParseList().get(0));

    }*/

    @Test
    public void testParseNoContent() {
        String content = "";

        AnalyzedData data = nlp.parseDocumentContent(content);

        assertEquals(0,data.getParseList().size());
        assertEquals(0,data.getTaggedData().size());
    }
}