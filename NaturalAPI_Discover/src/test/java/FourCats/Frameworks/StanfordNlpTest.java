package FourCats.Frameworks;

import FourCats.DataStructure.AnalyzedData;
import org.junit.Test;

import static org.junit.Assert.*;

public class StanfordNlpTest {

    StanfordNlp nlp = new StanfordNlp();

    @Test
    public void testParseDocumentTokenization() {
        String content = "My dog also likes eating sausage.";

        AnalyzedData data = nlp.parseDocumentContent(content);

        assertEquals("dog",data.getTaggedData().get(1).getLemma());
        assertEquals("NN",data.getTaggedData().get(1).getTag());
    }

    @Test
    public void testParseDocumentDependencies() {
        String content = "I want to withdraw money from the ATM.";

        AnalyzedData data = nlp.parseDocumentContent(content);

        assertEquals("dobj",data.getDependenciesList().get(4).getRelation());
    }

    @Test
    public void testParseNoContent() {
        String content = "";

        AnalyzedData data = nlp.parseDocumentContent(content);

        assertEquals(0,data.getDependenciesList().size());
        assertEquals(0,data.getTaggedData().size());
    }
}