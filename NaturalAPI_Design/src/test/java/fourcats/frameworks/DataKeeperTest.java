package fourcats.frameworks;

import fourcats.entities.Action;
import fourcats.entities.ObjectParam;
import fourcats.entities.Scenario;
import fourcats.entities.Type;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataKeeperTest {

    DataKeeper dataKeeper;

    @Mock
    Scenario scenario;

    @Mock
    Action action;

    HashMap<Integer, Action> map;

    ArrayList<ObjectParam> objectsList;

    @Before
    public void CreateDataKeeperWthOneScenario() {
        dataKeeper = new DataKeeper();
        dataKeeper.addScenarioToMap(scenario);
        map  = new HashMap<Integer, Action>();
        map.put(0, new Action("Action", new Type("Type")));
        objectsList = new ArrayList<ObjectParam>();
        objectsList.add(new ObjectParam("Name", "Type"));
    }

    @Test
    public void DataKeeperAddScenarioCorrectly() {
        when(scenario.getName()).thenReturn("Scenario");
        assertEquals(dataKeeper.getScenarioMap().get(0).getName(), "Scenario");
    }

    @Test
    public void DataKeeperRemoveActionCorrectly() {
        when(scenario.getActionsMap()).thenReturn(map);
        dataKeeper.removeAction(0, 0);
        assertTrue(dataKeeper.getScenarioMap().get(0).getActionsMap().isEmpty());
    }

    @Test
    public void DataKeeperClearsScenariosMap() {
        dataKeeper.clearScenariosMap();
        assertTrue(dataKeeper.getScenarioMap().isEmpty());
    }

    @Test
    public void DataKeeperUpdateActionName() {
        when(scenario.getActionsMap()).thenReturn(map);
        dataKeeper.updateActionName(0, 0, "NewActionName");
        assertEquals(dataKeeper.getScenarioMap().get(0).getActionsMap().get(0).getName(), "NewActionName");
    }

    @Test
    public void DataKeeperUpdateActionType() {
        when(scenario.getActionsMap()).thenReturn(map);
        dataKeeper.updateActionType(0, 0, "NewActionType");
        assertEquals(dataKeeper.getScenarioMap().get(0).getActionsMap().get(0).getType().getName(), "NewActionType");
    }

    @Test
    public void DataKeeperUpdateObjectName() {
        when(scenario.getActionsMap()).thenReturn(map);
        when(action.getObjectParams()).thenReturn(objectsList);
        map.clear();
        map.put(0, action);
        dataKeeper.updateObjectName(0, 0, 0, "NewName");
        assertEquals(dataKeeper.getScenarioMap().get(0).getActionsMap().get(0).getObjectParams().get(0).getName(), "NewName");
    }

    @Test
    public void DataKeeperUpdateObjectType() {
        when(scenario.getActionsMap()).thenReturn(map);
        when(action.getObjectParams()).thenReturn(objectsList);
        map.clear();
        map.put(0, action);
        dataKeeper.updateObjectType(0, 0, 0, "NewType");
        assertEquals(dataKeeper.getScenarioMap().get(0).getActionsMap().get(0).getObjectParams().get(0).getType().getName(), "NewType");
    }

    @Test
    public void DataKeeperAddCustomTypeWthEmptyMap() {
        dataKeeper.addCustomType("NewType", new HashMap<String, String>());
        assertEquals(dataKeeper.getTypeMap().size(), 6);
    }

    @Test
    public void DataKeeperUpdateObjectTypeById() {
        when(scenario.getActionsMap()).thenReturn(map);
        when(action.getObjectParams()).thenReturn(objectsList);
        map.clear();
        map.put(0, action);
        dataKeeper.updateObjectTypeById(0, 0, 0, 0);
        assertEquals(dataKeeper.getScenarioMap().get(0).getActionsMap().get(0).getObjectParams().get(0).getType().getName(), "string");
    }

    @Test
    public void DataKeeperUpdateActionTypeById() {
        when(scenario.getActionsMap()).thenReturn(map);
        dataKeeper.updateActionTypeById(0, 0, 0);
        assertEquals(dataKeeper.getScenarioMap().get(0).getActionsMap().get(0).getType().getName(), "string");
    }

    @Test
    public void DataKeeperAddObject() {
        when(scenario.getActionsMap()).thenReturn(map);
        Action realAction = new Action("Action", "Type");
        map.clear();
        map.put(0, realAction);
        dataKeeper.addObject(0, 0, "SecondName", 3);
        assertEquals(dataKeeper.getScenarioMap().get(0).getActionsMap().get(0).getObjectParams().get(0).getName(), "SecondName");
    }

    @Test
    public void DataKeeperRemoveObjectAndCheckRemainingObject() {
        when(scenario.getActionsMap()).thenReturn(map);
        Action realAction = new Action("Action", "Type");
        map.clear();
        map.put(0, realAction);
        dataKeeper.addObject(0, 0, "FirstName", 3);
        dataKeeper.addObject(0, 0, "SecondName", 1);
        dataKeeper.removeObject(0, 0, 1);
        assertEquals(dataKeeper.getScenarioMap().get(0).getActionsMap().get(0).getObjectParams().size(), 1);
        assertEquals(dataKeeper.getScenarioMap().get(0).getActionsMap().get(0).getObjectParams().get(0).getName(), "FirstName");
    }




}