package integration.GenerateBAL;

import fourcats.entities.Action;
import fourcats.entities.BAL;
import fourcats.entities.Scenario;
import fourcats.frameworks.BalAnalyzerImplementation;
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

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GenerateBALAnalyzerIT {


    BalAnalyzer analyzer = new BalAnalyzerImplementation();
    @Mock
    DataPresenterGUI data = Mockito.mock(DataPresenterGUI.class);

    @Mock
    RepositoryAccess repo = Mockito.mock(RepositoryAccess.class);

    @InjectMocks
    GenerateBal generatorBAL;

    BalAnalyzer spyAnalyzer;

    @Before
    public void before(){
        spyAnalyzer = Mockito.spy(analyzer);
        generatorBAL = new GenerateBal(repo,data,spyAnalyzer);
    }

    @Test
    public void verifyLinkSuggestAPIAndBalAnalyzer() throws Exception {
        generatorBAL.generateBAL("");
        verify(spyAnalyzer).createJsonFromBAL(any(BAL.class));
        assertNotNull(spyAnalyzer.createJsonFromBAL(any(BAL.class)));
    }
}
