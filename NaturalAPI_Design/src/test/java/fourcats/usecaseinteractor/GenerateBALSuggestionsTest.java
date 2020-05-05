package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceaccess.TextAnalyzer;
import fourcats.port.GenerateBalSuggestionsOutputPort;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;


public class GenerateBALSuggestionsTest {

    RepositoryAccess repositoryMock = mock(RepositoryAccess.class);
    TextAnalyzer analyzerMock = mock(TextAnalyzer.class);
    GenerateBalSuggestionsOutputPort outputMock = mock(GenerateBalSuggestionsOutputPort.class);

    GenerateBalSuggestions generateBALSuggestions;

    @Before
    public void setup(){
        generateBALSuggestions =
                new GenerateBalSuggestions(repositoryMock, analyzerMock, outputMock);
    }

    @Test
    public void testRepoDeleteScenario(){
        List<String> listString = new ArrayList<>();
        generateBALSuggestions.generateSuggestions(listString,true);
        verify(repositoryMock,times(1) ).deleteScenarios();
    }

    @Test
    public void testEmptyPathOutInvocation() throws FileNotFoundException {
        List<String> listString = Arrays.asList("String");
        when(repositoryMock.read("String")).thenThrow(FileNotFoundException.class);
        generateBALSuggestions.generateSuggestions(listString, true);
        verify(outputMock).showErrorFileLoad(false);
    }

    @Test
    public void test() throws FileNotFoundException {
        String feature = "Scenario";

        List<String> listString = Arrays.asList("String");
        when(repositoryMock.read("String")).thenReturn(feature);
    }

//    @Test
//    public void GenerateBALSuggestionsFromNotExistingFeature() {
//        correctGenerator.generateSuggestions(new ArrayList<>(),true);
//        Mockito.verifyZeroInteractions(analyzerMock);
//    }

}