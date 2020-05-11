package integration.modifyAPI;

import fourcats.entity.*;
import fourcats.frameworks.DataKeeper;
import fourcats.frameworks.FileSystem;
import fourcats.frameworks.Repository;
import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceadapters.DataPresenterGui;
import fourcats.usecaseinteractor.ModifyApi;
import fourcats.usecaseinteractor.SuggestApi;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ModifyAPIRepoAccessIT {


    RepositoryAccess repo;
    @Mock
    BalAnalyzer analyzerMock = Mockito.mock(BalAnalyzer.class);
    @Mock
    DataPresenterGui data;

    @InjectMocks
    ModifyApi modifyAPI;

    @Before
    public void before(){
        DataKeeper keeper = new DataKeeper();
        API api = new API();
        api.setText("a");
        api.setFilename("aaaaa");
        keeper.addApi(api);
        FileSystem system = new FileSystem();
        data = new DataPresenterGui();
        repo = new Repository(keeper,system);
        modifyAPI = new ModifyApi(analyzerMock,repo,data);
    }

    @Test
    public void verifyLinkFromDataPresenterGuiAndModifyApi() throws Exception {
        modifyAPI.modifyGui("a","b");
        assertEquals("b",repo.getApi(0).getText());
    }


}
