package integration.suggestAPI;

import fourcats.entity.*;
import fourcats.frameworks.DataKeeper;
import fourcats.frameworks.FileSystem;
import fourcats.frameworks.Repository;
import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceadapters.DataPresenterGui;
import fourcats.usecaseinteractor.GenerateApi;
import fourcats.usecaseinteractor.SuggestApi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class SuggestAPIRepoAccessIT {
    RepositoryAccess repo;

    DataPresenterGui data;
    @Mock
    BalAnalyzer analyzerMock = Mockito.mock(BalAnalyzer.class);
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
        suggestAPI = new SuggestApi(analyzerMock,spyrepo,data);
    }

    @Test
    public void verifyLinkSuggestAPIAndRepositoryAccess() throws Exception {
        String filenamebal = ".\\BAL\\TestFiles\\balAtm.json";
        String filenamepla = ".\\PLA\\javaClassPLA.txt";

        List<Actor> l = new LinkedList<>();
        Actor act = new Actor("Actor1");
        Type tipo = new Type("tipo");
        tipo.addAttribute("nomeattributo1","tipoattributo1");
        tipo.addAttribute("nomeattributo2","tipoattributo2");
        Action action = new Action("gioca",tipo,"scenario","step");

        ObjectParam op = new ObjectParam("object","tipoObj");
        op.getType().addAttribute("attribute","attributetype");

        action.addObjectParam(op);
        act.addAction(action);
        l.add(act);
        BAL bal = new BAL(l);
        when(analyzerMock.getBAL()).thenReturn(bal);

      //  when(spyrepo.isCoupleBalPlaPresent(filenamebal,filenamebal)).thenReturn(false);

        API api1 = new API();
        api1.setFilename("api1");
        api1.setText("APIIII");

        Map<Integer, API> mappa = new HashMap<>();
        mappa.put(1,api1);

       // when(spyrepo.getApiMap()).thenReturn(mappa);

        suggestAPI.create(filenamebal,filenamepla);

        verify(spyrepo,times(1)).openFile(any(String.class));
        verify(spyrepo,times(4)).addApi(any(API.class));
        verify(spyrepo,times(3)).isThisApiPresent(any(API.class));
        assertEquals(4,spyrepo.getApiMap().size());

    }


}
