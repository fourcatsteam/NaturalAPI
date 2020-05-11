package integration.generateAPI;

import fourcats.entity.API;
import fourcats.frameworks.DataKeeper;
import fourcats.frameworks.FileSystem;
import fourcats.frameworks.Repository;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceadapters.DataPresenterGui;
import fourcats.usecaseinteractor.GenerateApi;
import fourcats.view.CLI;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GenerateAPIRepoAccessIT {

    RepositoryAccess repo;

    DataPresenterGui data;

    @InjectMocks
    GenerateApi generator;

    RepositoryAccess spyrepo;

    @Before
    public void before(){
        DataKeeper keeper = new DataKeeper();
        FileSystem system = new FileSystem();
        API api1 = new API();
        api1.setFilename("api1.java");
        api1.setText("APIIII");
        API api2 = new API();
        api1.setFilename("api2.java");
        api1.setText("APIIII2");
        keeper.addApi(api1);
        keeper.addApi(api2);

        repo = new Repository(keeper,system);
        spyrepo = Mockito.spy(repo);
        data = new DataPresenterGui();
        generator = new GenerateApi(spyrepo,data);
    }

    @Test
    public void verifyLinkGenerateAPIAndRepositoryAccess() throws Exception {

        generator.generate(".\\API\\TestFiles\\");
        verify(spyrepo,times(2)).writeApi(any(String.class),any(API.class));
        assertEquals(2,spyrepo.getApiMap().size());
        assertEquals("APIs generated!",data.getMessage());
    }


}
