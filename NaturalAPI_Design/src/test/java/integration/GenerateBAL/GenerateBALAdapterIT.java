package integration.GenerateBAL;

import fourcats.entities.Action;
import fourcats.entities.BAL;
import fourcats.entities.Scenario;
import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceadapters.DataPresenterGUI;
import fourcats.port.GenerateBalOutputPort;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenerateBALAdapterIT {
    @Mock
    RepositoryAccess repo = Mockito.mock(RepositoryAccess.class);
    @Mock
    BalAnalyzer analyzerMock = Mockito.mock(BalAnalyzer.class);

    DataPresenterGUI data;

    @InjectMocks
    GenerateBal generatorBAL;

    @Before
    public void before(){
        data = new DataPresenterGUI();
        generatorBAL = new GenerateBal(repo,data,analyzerMock);
    }

    @Test
    public void verifyLinkFromDataPresenterGuiAndGenerateBAL() throws IOException {
        Map<Integer, Action> mapsAction = new HashMap<>();

        mapsAction.put(1,new Action("azione1","tipo1"));
        mapsAction.put(1,new Action("azione2","tipo2"));


        Scenario scen1 = new Scenario("nomeScenario1",mapsAction,"contenuto1","attore1","feature1");
        Scenario scen2 = new Scenario("nomeScenario2",mapsAction,"contenuto2","attore2","feature2");

        Map<Integer, Scenario> maps = new HashMap<>();
        maps.put(1,scen1);
        maps.put(2,scen2);

        when(repo.readScenarios()).thenReturn(maps);
        when(analyzerMock.createJsonFromBAL(any(BAL.class))).thenReturn("stringa");
        generatorBAL.generateBAL(".\\BAL\\FileTests");
        assertTrue(data.isOkOperation());
        assertEquals("",data.getMessage());
    }
}
