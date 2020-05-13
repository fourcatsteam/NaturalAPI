package integration.DeclineBALSuggestion;

import fourcats.entities.Action;
import fourcats.entities.Bal;
import fourcats.entities.Scenario;
import fourcats.frameworks.DataKeeper;
import fourcats.frameworks.FileSystemAccess;
import fourcats.frameworks.Repository;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceadapters.DataPresenterGUI;
import fourcats.usecaseinteractor.DeclineBalSuggestion;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeclineBALSuggestionRepoAccessIT {

    RepositoryAccess repo;

    @Mock
    DataPresenterGUI data = Mockito.mock(DataPresenterGUI.class);

    @InjectMocks
    DeclineBalSuggestion declineBalSuggestion;

    @Before
    public void before(){
        Map<Integer, Action> mapsAction = new HashMap<>();

        mapsAction.put(1,new Action("azione1","tipo1"));
        mapsAction.put(2,new Action("azione2","tipo2"));

        Map<Integer, Action> mapsAction2 = new HashMap<>();

        mapsAction.put(1,new Action("azione3","tipo3"));
        mapsAction.put(2,new Action("azione4","tipo4"));

        Scenario scen1 = new Scenario("nomeScenario1",mapsAction,"contenuto1","attore1","feature1");
        Scenario scen2 = new Scenario("nomeScenario2",mapsAction2,"contenuto2","attore2","feature2");

        DataKeeper keeper = new DataKeeper();

        keeper.addScenarioToMap(scen1);
        keeper.addScenarioToMap(scen2);

        repo = new Repository(keeper,new FileSystemAccess());
        declineBalSuggestion = new DeclineBalSuggestion(repo,data);
    }

    @Test
    public void verifyLinkFromRepoAndGenerateBAL() throws IOException {
        declineBalSuggestion.declineSuggestion(1,0);
        assertEquals(2,repo.readScenarios().get(0).getActionsMap().size());

    }
}
