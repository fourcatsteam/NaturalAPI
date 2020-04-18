package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceaccess.TextAnalyzer;
import fourcats.port.GenerateBALSuggestionsOutputPort;
import org.junit.Test;
import org.mockito.*;

import static org.junit.Assert.*;

public class GenerateBALSuggestionsTest {

    RepositoryAccess repositoryMock = Mockito.mock(RepositoryAccess.class);
    TextAnalyzer analyzerMock = Mockito.mock(TextAnalyzer.class);
    GenerateBALSuggestionsOutputPort outputMock = Mockito.mock(GenerateBALSuggestionsOutputPort.class);

    @InjectMocks
    GenerateBALSuggestions correctGenerator = new GenerateBALSuggestions(repositoryMock, analyzerMock, outputMock);

    @Test
    public void GenerateBALSuggestionsFromNotExistingFeature() {
        correctGenerator.generateSuggestions("");
        Mockito.verifyZeroInteractions(analyzerMock);
    }

}