package fourcats.interfaceAdapters;

import fourcats.entity.API;
import fourcats.interfaceadapters.DataPresenterGui;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DataPresenterGuiTest {

    DataPresenterGui dataPresGui;

    @Before
    public void before(){
        dataPresGui = new DataPresenterGui();
    }

    @Test
    public void testingCostructCorrectly(){
        assertNotNull(dataPresGui);
    }

    @Test
    public void testGettingString(){
        assertEquals("",dataPresGui.getStringToShow());
    }

    @Test
    public void testGettingCombo(){
        assertEquals("",dataPresGui.getComboToShow());
    }

    @Test
    public void testGettingMessagePla(){
        assertEquals("",dataPresGui.getMessagePla());
    }

    @Test
    public void testGettingModifyApiPla(){
        assertEquals("",dataPresGui.getModifyApiPla());
    }

    @Test
    public void testGettingModifyCustomPla(){
        assertEquals("",dataPresGui.getModifyCustomPla());
    }

    @Test
    public void testGettingMessage(){
        assertEquals("",dataPresGui.getMessage());
    }

    @Test
    public void testShowOutput(){
        dataPresGui.showMessagePla("output");
        assertEquals("output",dataPresGui.getMessagePla());
    }

    @Test
    public void testShowLoadPla(){
        dataPresGui.showLoadPla("api","custom","text");
        assertEquals("api",dataPresGui.getModifyApiPla());
        assertEquals("custom",dataPresGui.getModifyCustomPla());
        assertEquals("text",dataPresGui.getModifyTestPla());
    }

    @Test
    public void testShowModifyPla(){
        dataPresGui.showMessagePla("modifypla");
        assertEquals("modifypla",dataPresGui.getMessagePla());
    }

    @Test
    public void testShowGenMessage(){
        dataPresGui.showGenerationMessage("output");
        assertEquals("output",dataPresGui.getMessage());
    }

    @Test
    public void testShowOutputMap(){
        Map<Integer, API> maps = new HashMap<>();
        API api = new API();
        api.setText("testo");
        api.setFilename("filename");
        maps.put(1,api);
        dataPresGui.showOutput(maps);
        assertEquals("testo",dataPresGui.getStringToShow());
        assertEquals("filename",dataPresGui.getComboToShow());
    }

}
