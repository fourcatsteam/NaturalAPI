package integration.GenerateBALSuggestion;

import fourcats.datastructure.AnalyzedData;
import fourcats.entities.Scenario;
import fourcats.frameworks.DataKeeper;
import fourcats.frameworks.FileSystemAccess;
import fourcats.frameworks.Repository;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenerateBALSuggestionRepoAccessIT {
    RepositoryAccess repo;
    @Mock
    TextAnalyzer analyzerMock = Mockito.mock(TextAnalyzer.class);
    @Mock
    DataPresenterGUI data;
    RepositoryAccess spyrepo;
    @InjectMocks
    GenerateBalSuggestions generatorSuggestion;
    @Before
    public void before(){
        DataKeeper keeper = new DataKeeper();
        repo = new Repository(keeper,new FileSystemAccess());
        spyrepo = Mockito.spy(repo);
        generatorSuggestion = new GenerateBalSuggestions(spyrepo,analyzerMock,data);
    }

    @Test
    public void verifyLinkFromDataPresenterGuiAndGenerateBALSuggestion() throws IOException {
        List<String> list = new LinkedList<>();
        list.add(".\\gherkin_documents\\prova.feature");
        AnalyzedData data = new AnalyzedData();
        data.addDependency("a","b","c");
        data.addDependency("1","2","3");
        when(analyzerMock.parseDocumentContent(any(String.class))).thenReturn(data);
        generatorSuggestion.generateSuggestions(list,true);

       assertEquals(2,spyrepo.readScenarios().size());



    }
}
