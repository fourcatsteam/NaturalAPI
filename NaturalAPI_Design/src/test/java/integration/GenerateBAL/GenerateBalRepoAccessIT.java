package integration.GenerateBAL;

import fourcats.entities.Action;
import fourcats.entities.Bal;
import fourcats.entities.Scenario;
import fourcats.frameworks.DataKeeper;
import fourcats.frameworks.FileSystemAccess;
import fourcats.frameworks.Repository;
import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceadapters.DataPresenterGUI;
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
public class GenerateBalRepoAccessIT {

    RepositoryAccess repo;

    @Mock
    BalAnalyzer analyzerMock = Mockito.mock(BalAnalyzer.class);
    @Mock
    DataPresenterGUI data = Mockito.mock(DataPresenterGUI.class);

    @InjectMocks
    GenerateBal generatorBAL;
    RepositoryAccess spyrepo;
    @Before
    public void before(){
        Map<Integer, Action> mapsAction = new HashMap<>();

        mapsAction.put(1,new Action("azione1","tipo1"));
        mapsAction.put(1,new Action("azione2","tipo2"));


        Scenario scen1 = new Scenario("nomeScenario1",mapsAction,"contenuto1","attore1","feature1");
        Scenario scen2 = new Scenario("nomeScenario2",mapsAction,"contenuto2","attore2","feature2");

        DataKeeper keeper = new DataKeeper();

        keeper.addScenarioToMap(scen1);
        keeper.addScenarioToMap(scen2);
        repo = new Repository(keeper,new FileSystemAccess());
        spyrepo = Mockito.spy(repo);
        generatorBAL = new GenerateBal(spyrepo,data,analyzerMock);
    }

    @Test
    public void verifyLinkFromRepoAndGenerateBAL() throws IOException {
        when(analyzerMock.createJsonFromBAL(any(Bal.class))).thenReturn("stringa");
        generatorBAL.generateBAL("");
        assertEquals(2,repo.readScenarios().size());
        verify(spyrepo).createBAL(any(String.class),any(String.class));
    }

}
