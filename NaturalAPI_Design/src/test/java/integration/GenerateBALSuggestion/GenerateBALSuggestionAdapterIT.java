package integration.GenerateBALSuggestion;

import fourcats.entities.Action;
import fourcats.entities.BAL;
import fourcats.entities.Scenario;
import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceaccess.TextAnalyzer;
import fourcats.interfaceadapters.DataPresenterGUI;
import fourcats.usecaseinteractor.GenerateBal;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenerateBALSuggestionAdapterIT {
    @Mock
    RepositoryAccess repo = Mockito.mock(RepositoryAccess.class);
    @Mock
    TextAnalyzer analyzerMock = Mockito.mock(TextAnalyzer.class);

    DataPresenterGUI data;

    @InjectMocks
    GenerateBalSuggestions generatorSuggestion;

    @Before
    public void before(){
        data = new DataPresenterGUI();
        generatorSuggestion = new GenerateBalSuggestions(repo,analyzerMock,data);
    }

    @Test
    public void verifyLinkFromDataPresenterGuiAndGenerateBALSuggestion() throws IOException {
        List<String> list = new LinkedList<>();
        list.add("path1");
        list.add("path2");
        when(repo.read(any(String.class))).thenReturn(null);

        Map<Integer,Scenario> maps = new HashMap<>();
        when(repo.readScenarios()).thenReturn(maps);
        generatorSuggestion.generateSuggestions(list,false);

        assertFalse(data.isSuggestionToAdd());

    }


}
