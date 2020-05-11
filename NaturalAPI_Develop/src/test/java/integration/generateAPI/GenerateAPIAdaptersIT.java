package integration.generateAPI;

import fourcats.entity.API;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceadapters.DataPresenterGui;
import fourcats.usecaseinteractor.GenerateApi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GenerateAPIAdaptersIT {

    @Mock
    RepositoryAccess repo;

    DataPresenterGui data;

    @InjectMocks
    GenerateApi generatorAPI;

    @Before
    public void before(){
        data = new DataPresenterGui();
        generatorAPI = new GenerateApi(repo,data);
    }

    @Test
    public void verifyLinkFromDataPresenterGuiAndGenerateAPI() throws Exception {
        API api1 = new API();
        api1.setFilename("api1");
        api1.setText("APIIII");

        Map<Integer, API> mappa = new HashMap<>();
        mappa.put(1,api1);
        mappa.put(2,new API());


        when(repo.getApiMap()).thenReturn(mappa);
        generatorAPI.generate("path");
        verify(repo,times(2)).writeApi(any(String.class),any(API.class));

        assertEquals("APIs generated!",data.getMessage());
    }




}
