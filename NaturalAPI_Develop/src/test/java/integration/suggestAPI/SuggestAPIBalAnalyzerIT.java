package integration.suggestAPI;

import fourcats.entity.*;
import fourcats.frameworks.BalAnalyzerImplementation;
import fourcats.frameworks.DataKeeper;
import fourcats.frameworks.FileSystem;
import fourcats.frameworks.Repository;
import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceadapters.DataPresenterGui;
import fourcats.usecaseinteractor.SuggestApi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SuggestAPIBalAnalyzerIT {

    RepositoryAccess repo;
    @Mock
    DataPresenterGui data;

    BalAnalyzer analyzer = new BalAnalyzerImplementation();
    @InjectMocks
    SuggestApi suggestAPI;
    RepositoryAccess spyrepo;

    @Before
    public void before(){
        DataKeeper keeper = new DataKeeper();
        FileSystem system = new FileSystem();
        data = new DataPresenterGui();
        repo = new Repository(keeper,system);
        spyrepo = Mockito.spy(repo);
        suggestAPI = new SuggestApi(analyzer,spyrepo,data);
    }


    @Test
    public void verifyLinkSuggestAPIAndRepositoryAccess() throws Exception {
        String filenamebal = ".\\BAL\\TestFiles\\balAtm.json";
        String filenamepla = ".\\PLA\\javaClassPLA.txt";
        suggestAPI.create(filenamebal,filenamepla);
        assertNotNull(analyzer.getBAL());
    }


}
