package fourcats.frameworks;

import fourcats.entities.Scenario;
import fourcats.interfaceaccess.PersistentMemoryAccess;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest {

    @Mock
    DataKeeper dataKeeper;

    @Mock
    PersistentMemoryAccess memoryAccess;

    @InjectMocks
    Repository repository;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void readTest() throws FileNotFoundException {
        repository.read("filename");
        verify(memoryAccess,times(1)).readFile("filename");
    }

    @Test
    public void testCreateScenario() {
        Scenario scenario = mock(Scenario.class);
        repository.createScenario(scenario);
        verify(dataKeeper, times(1)).addScenarioToMap(scenario);
    }

    @Test
    public void testDeleteSuggestion() {
        Scenario scenario = mock(Scenario.class);
        repository.deleteSuggestion(1,1);
        verify(dataKeeper,times(1)).removeAction(1,1);
    }

    @Test
    public void testReadScenarios() {
        repository.readScenarios();
        verify(dataKeeper,times(1)).getScenarioMap();
    }

    @Test
    public void testReadTypes() {
        repository.readTypes();
        verify(dataKeeper, times(1)).getTypeMap();
    }

    @Test
    public void testDeleteScenarios() {
        repository.deleteScenarios();
        verify(dataKeeper, times(1)).clearScenariosMap();
    }

    @Test
    public void testCreateBAL() throws IOException {
        repository.createBAL("bal","filename");
        verify(memoryAccess,times(1)).writeFile("bal","filename");
    }

    @Test
    public void testUpdateActionName() {
        repository.updateActionName(1,1,"actionName");
        verify(dataKeeper,times(1)).updateActionName(1,1,"actionName");
    }

    @Test
    public void testUpdateActionType() {
        repository.updateActionType(1,1,"newActionType");
        verify(dataKeeper,times(1)).updateActionType(1,1,"newActionType");
    }

    @Test
    public void testUpdateObjectName() {
        repository.updateObjectName(1,1,1,"newObjName");
        verify(dataKeeper,times(1)).updateObjectName(1,1,1,"newObjName");
    }

    @Test
    public void testUpdateObjectType() {
        repository.updateObjectType(1,1,1, "newObjType");
        verify(dataKeeper,times(1)).updateObjectType(1,1,1,"newObjType");
    }

    @Test
    public void testCreateCustomType() {
        Map<String, String> mAttributes = new HashMap<>();
        repository.createCustomType("name",mAttributes);
        verify(dataKeeper,times(1)).addCustomType("name", mAttributes);
    }

    @Test
    public void testUpdateObjectTypeById() {
        repository.updateObjectTypeById(1,1,1,1);
        verify(dataKeeper, times(1)).updateObjectTypeById(1,1,1,1);
    }

    @Test
    public void testUpdateActionTypeById() {
        repository.updateActionTypeById(1,1,1);
        verify(dataKeeper, times(1)).updateActionTypeById(1,1,1);
    }

    @Test
    public void testCreateObject() {
        repository.createObject(1,1,"objName",1);
        verify(dataKeeper,times(1)).addObject(1,1,"objName",1);
    }

    @Test
    public void testDeleteObject() {
        repository.deleteObject(1,1,1);
        verify(dataKeeper, times(1)).removeObject(1,1,1);
    }

    @Test
    public void testCreateSuggestion() {
        repository.createSuggestion(1,"suggName","suggType");
        verify(dataKeeper, times(1)).addSuggestion(1,"suggName", "suggType");
    }

    @Test
    public void testCrateSuggestionByIdType() {
        repository.crateSuggestionByIdType(1,"suggName",1);
        verify(dataKeeper,times(1)).addSuggestionByIdType(1,"suggName",1);
    }

    @Test
    public void testReadBdl() {
        String[] myStr = new String[1];
        repository.readBdl(myStr);
        verify(memoryAccess,times(1)).getBdl(myStr);
    }
}