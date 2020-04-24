package FourCats.UseCaseUtilities;

import FourCats.DataStructure.AnalyzedData;
import FourCats.DataStructure.Dependency;
import FourCats.DataStructure.WordTag;
import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.TextAnalyzer;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.LinkedList;

import static org.mockito.Mockito.*;

public class AnalyzeDocumentTest {

    TextAnalyzer analyzerMock = Mockito.mock(TextAnalyzer.class);

    @InjectMocks
    AnalyzeDocument analyzeDocument = new AnalyzeDocument(analyzerMock);

    @Test
    public void removeDocumentFromBdl() {
        AnalyzedData data = Mockito.mock(AnalyzedData.class);
        Bdl bdl = Mockito.mock(Bdl.class);
        Document doc = Mockito.mock(Document.class);

        LinkedList<WordTag> list = new LinkedList<>();
        list.add(new WordTag("dogs","NNP","dog"));
        list.add(new WordTag("dogs","NNP","dog"));
        list.add(new WordTag("went","VBP","go"));

        LinkedList<Dependency> parseList = new LinkedList<>();
        parseList.add(new Dependency("withdraw", "cash","dobj"));

        when(data.getTaggedData()).thenReturn(list);
        when(data.getDependenciesList()).thenReturn(parseList);
        when(doc.getContent()).thenReturn("dogs went withdraw cash. dogs");
        when(analyzerMock.parseDocumentContent(any(String.class))).thenReturn(data);

        analyzeDocument.removeDocumentFromBdl(bdl,doc);

        verify(analyzerMock,times(1)).parseDocumentContent(any(String.class));
        verify(bdl,times(1)).removeVerb(any(String.class));
        verify(bdl,times(2)).removeNoun(any(String.class));
        verify(bdl,times(1)).removePredicate(any(String.class));
    }

    @Test
    public void addDocumentToBdl() {
        AnalyzedData data = Mockito.mock(AnalyzedData.class);
        Bdl bdl = Mockito.mock(Bdl.class);
        Document doc = Mockito.mock(Document.class);

        LinkedList<WordTag> list = new LinkedList<>();
        list.add(new WordTag("dogs","NNP","dog"));
        list.add(new WordTag("dogs","NNP","dog"));
        list.add(new WordTag("went","VBP","go"));

        LinkedList<Dependency> parseList = new LinkedList<>();
        parseList.add(new Dependency("withdraw", "cash","dobj"));

        when(data.getTaggedData()).thenReturn(list);
        when(data.getDependenciesList()).thenReturn(parseList);
        when(doc.getContent()).thenReturn("dogs went withdraw cash. dogs");
        when(analyzerMock.parseDocumentContent(any(String.class))).thenReturn(data);

        analyzeDocument.addDocumentToBdl(bdl,doc);

        verify(analyzerMock,times(1)).parseDocumentContent(any(String.class));
        verify(bdl,times(1)).addVerb(any(String.class));
        verify(bdl,times(2)).addNoun(any(String.class));
        verify(bdl,times(1)).addPredicate(any(String.class));
    }
}