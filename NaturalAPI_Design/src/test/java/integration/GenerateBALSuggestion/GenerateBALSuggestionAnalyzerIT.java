package integration.GenerateBALSuggestion;


import fourcats.entities.Scenario;
import fourcats.frameworks.DataKeeper;
import fourcats.frameworks.FileSystemAccess;
import fourcats.frameworks.Repository;
import fourcats.frameworks.StanfordNlp;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceaccess.TextAnalyzer;
import fourcats.interfaceadapters.DataPresenterGUI;
import fourcats.usecaseinteractor.GenerateBalSuggestions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GenerateBALSuggestionAnalyzerIT {

    TextAnalyzer analyzerMock = new StanfordNlp();


    RepositoryAccess repo;

    @Mock
    DataPresenterGUI data;

    @InjectMocks
    GenerateBalSuggestions generatorSuggestion;

    TextAnalyzer spyanalyzer;
    @Before
    public void before(){
        repo = new Repository(new DataKeeper(),new FileSystemAccess());
        spyanalyzer = Mockito.spy(analyzerMock);
        generatorSuggestion = new GenerateBalSuggestions(repo,spyanalyzer,data);
    }

    @Test
    public void verifyLinkGenerateBALSuggestionAndAnalyzer() throws FileNotFoundException {
        List<String> list = new LinkedList<>();
        list.add(".\\gherkin_documents\\prova.feature");

        generatorSuggestion.generateSuggestions(list,false);

        verify(spyanalyzer,times(12)).parseDocumentContent(any(String.class));
        
    }
}
