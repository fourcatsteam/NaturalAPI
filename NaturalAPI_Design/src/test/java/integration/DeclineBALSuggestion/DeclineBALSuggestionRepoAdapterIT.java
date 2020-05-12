package integration.DeclineBALSuggestion;

import fourcats.entities.Action;
import fourcats.entities.Bal;
import fourcats.entities.Scenario;
import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceadapters.DataPresenterGUI;
import fourcats.usecaseinteractor.DeclineBalSuggestion;
import fourcats.usecaseinteractor.GenerateBal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeclineBALSuggestionRepoAdapterIT {

    @Mock
    RepositoryAccess repo = Mockito.mock(RepositoryAccess.class);

    DataPresenterGUI data;

    @InjectMocks
    DeclineBalSuggestion declineBalSuggestion;

    @Before
    public void before(){
        data = new DataPresenterGUI();
        declineBalSuggestion = new DeclineBalSuggestion(repo,data);
    }

    @Test
    public void verifyLinkFromDataPresenterGuiAndDeclineBalSuggestion() throws IOException {
        Map<Integer, Action> mapsAction = new HashMap<>();

        mapsAction.put(1,new Action("azione1","tipo1"));
        mapsAction.put(1,new Action("azione2","tipo2"));

        Scenario scen1 = new Scenario("nomeScenario1",mapsAction,"contenuto1","attore1","feature1");
        Scenario scen2 = new Scenario("nomeScenario2",mapsAction,"contenuto2","attore2","feature2");

        Map<Integer, Scenario> maps = new HashMap<>();
        maps.put(1,scen1);
        maps.put(2,scen2);

        when(repo.readScenarios()).thenReturn(maps);

        declineBalSuggestion.declineSuggestion(1,1);
        verify(repo).deleteSuggestion(any(Integer.class),any(Integer.class));
        assertTrue(data.isOkOperation());
        assertEquals("",data.getMessage());
    }

}
