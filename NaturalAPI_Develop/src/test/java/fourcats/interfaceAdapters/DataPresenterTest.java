package fourcats.interfaceAdapters;

import fourcats.entity.API;
import fourcats.interfaceadapters.DataPresenter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class DataPresenterTest {

    @InjectMocks
    DataPresenter dataPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetMessageToShow() {
        assertEquals("",dataPresenter.getStringToShow());
    }

    @Test
    public void testingGettingMessage(){
        dataPresenter = new DataPresenter();
        assertEquals("",dataPresenter.getMessage());
    }

    @Test
    public void testingShowGenerationMessage(){
        dataPresenter.showGenerationMessage("messaggio");
        assertEquals("",dataPresenter.getMessage());
    }

   @Test
    public void testShowOuput(){
        API api = new API();

        Map<Integer,API> map = new HashMap<>();
        map.put(1,api);

        dataPresenter.showOutput(map);
        assertEquals("",dataPresenter.getStringToShow());
    }

}
