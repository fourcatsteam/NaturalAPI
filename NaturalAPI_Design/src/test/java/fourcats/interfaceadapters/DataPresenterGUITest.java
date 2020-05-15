package fourcats.interfaceadapters;

import fourcats.entities.*;
import fourcats.suggestionbdlalgorithm.SuggestionFeedback;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DataPresenterGUITest {

    DataPresenterGUI dataPresenterGUI;

    @Mock
    Scenario scenario;

    @Mock
    Action action;

    @Mock
    ObjectParam objectParam;

    @Mock
    Type type;

    @Mock
    Bdl bdl;

    @Mock
    Map<Integer, Scenario> scenarioMap;

    @Mock
    Map<Integer, Action> actionMap;

    @Mock
    ArrayList<ObjectParam> objectList;

    @Mock
    Map<Integer, Type> typeMap;

    private void InitializeMockCollection() {
        scenarioMap = new HashMap<>();
        scenarioMap.put(1, scenario);

        actionMap = new HashMap<>();
        actionMap.put(1, action);

        objectList = new ArrayList<>();
        objectList.add(objectParam);
    }

    @Before
    public void CreateDataPresenterGUI() {
        dataPresenterGUI = new DataPresenterGUI();
        InitializeMockCollection();
    }

    @Test
    public void DataPresenterGUIShowSuggestions() {
        dataPresenterGUI.showSuggestionsForScenario(scenarioMap);

        assertFalse(dataPresenterGUI.isSuggestionToAdd());
    }

    @Test
    public void DataPresenterGUIShowSuggestionsWthFocusOnlObjects() {
        when(scenario.getActionsMap()).thenReturn(actionMap);
        when(action.getObjectParams()).thenReturn(objectList);
        when(action.getName()).thenReturn("Action");

        dataPresenterGUI.showSuggestionsForScenario(scenarioMap);

        verify(scenario, times(2)).getActionsMap();
        verify(action, times(1)).getObjectParams();
        verify(objectParam, times(1)).getName();
    }

    @Test
    public void DataPresenterGUIGetActionName() {
        when(scenario.getActionsMap()).thenReturn(actionMap);
        when(action.getName()).thenReturn("Action");

        dataPresenterGUI.showSuggestionsForScenario(scenarioMap);

        assertEquals("Action", dataPresenterGUI.getActionName());
    }

    @Test
    public void DataPresenterGUIGetActionType() {
        when(scenario.getActionsMap()).thenReturn(actionMap);
        when(action.getType()).thenReturn(type);
        when(action.getName()).thenReturn("Action");
        when(type.toString()).thenReturn("Type");

        dataPresenterGUI.showSuggestionsForScenario(scenarioMap);

        assertEquals("Type", dataPresenterGUI.getActionType());
    }

    @Test
    public void DataPresenterGUIGetMessageEmpty() {
        dataPresenterGUI.showErrorFileLoad(true);
        assertTrue(dataPresenterGUI.getMessage().startsWith(""));
    }

    @Test
    public void DataPresenterGUIGetScenarioContent() {
        when(scenario.getContent()).thenReturn("Content");

        dataPresenterGUI.showSuggestionsForScenario(scenarioMap);

        assertEquals("Scenario:Content", dataPresenterGUI.getScenarioContent());
    }

    @Test
    public void DataPresenterGUIGetScenarioId() {
        dataPresenterGUI.showSuggestionsForScenario(scenarioMap);

        assertEquals("1", dataPresenterGUI.getScenarioId());
    }

    @Test
    public void DataPresenterGUIGetActor() {
        when(scenario.getActorName()).thenReturn("Actor");

        dataPresenterGUI.showSuggestionsForScenario(scenarioMap);

        assertEquals("Actor", dataPresenterGUI.getActor());
    }

    @Test
    public void DataPresenterGUIGetSuggestionId() {
        when(scenario.getActionsMap()).thenReturn(actionMap);
        when(action.getName()).thenReturn("Action");
        dataPresenterGUI.showSuggestionsForScenario(scenarioMap);

        assertEquals("1", dataPresenterGUI.getSuggestionId());
    }

    @Test
    public void DataPresenterGUIGetlTypeShouldBeEmpty() {
        dataPresenterGUI.showSuggestionsForScenario(scenarioMap);
        assertTrue(dataPresenterGUI.getlTypes().isEmpty());
    }

    @Test
    public void DataPresenterGUIGetlObjectIdShouldBeEmpty() {
        dataPresenterGUI.showSuggestionsForScenario(scenarioMap);
        assertTrue(dataPresenterGUI.getlObjectId().isEmpty());
    }

    @Test
    public void DataPresenterGUIGetlObjectTypeShouldBeEmpty() {
        dataPresenterGUI.showSuggestionsForScenario(scenarioMap);
        assertTrue(dataPresenterGUI.getlObjectTypes().isEmpty());
    }

    @Test
    public void DataPresenterGUIGetlObjectNameShouldBeEmpty() {
        dataPresenterGUI.showSuggestionsForScenario(scenarioMap);
        assertTrue(dataPresenterGUI.getlObjectNames().isEmpty());
    }

    @Test
    public void DataPresenterGUIIsOkOperationYes() {
        dataPresenterGUI.showErrorFileLoad(true);
        assertTrue(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIIsSuggestionRefreshNeededAlwaysFalse() {
        assertFalse(dataPresenterGUI.isSuggestionsRefreshNeeded());
    }

    @Test
    public void DataPresenterGUIGetFrequencyInBDLFromEmptyScenario() {
        assertEquals(0, dataPresenterGUI.getFrequencyInBdl());
    }

    @Test
    public void DataPresenterGUIGetFeaturePathFromEmptyScenario() {
        assertEquals(null, dataPresenterGUI.getFeaturePath());
    }

    @Test
    public void DataPresenterGUIShowErrorFileLoadedFromTrue() {
        dataPresenterGUI.showErrorFileLoad(true);
        assertEquals("", dataPresenterGUI.getMessage());
    }

    @Test
    public void DataPresenterGUIShowErrorFileLoadedFromFalse() {
        dataPresenterGUI.showErrorFileLoad(false);
        assertEquals("", dataPresenterGUI.getMessage());
    }

    @Test
    public void DataPresenterGUIShowDeclinedSuggestionFromTrue() {
        dataPresenterGUI.showDeclinedSuggestion(scenarioMap,true);
        assertEquals("", dataPresenterGUI.getMessage());
    }

    @Test
    public void DataPresenterGUIShowDeclinedSuggestionFromFalse() {
        dataPresenterGUI.showDeclinedSuggestion(scenarioMap, false);
        assertEquals("", dataPresenterGUI.getMessage());
    }

    @Test
    public void DataPresenterGUIShowModifiedActionNameFromTrue() {
        dataPresenterGUI.showModifiedActionName(scenarioMap,true, "");
        assertTrue(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIShowModifiedActionNameFromFalse() {
        dataPresenterGUI.showModifiedActionName(scenarioMap, false, "");
        assertFalse(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIShowModifiedActionTypeFromTrue() {
        dataPresenterGUI.showModifiedActionType(scenarioMap,true);
        assertTrue(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIShowModifiedActionTypeFromFalse() {
        dataPresenterGUI.showModifiedActionType(scenarioMap, false);
        assertFalse(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIShowModifiedObjectNameFromTrue() {
        dataPresenterGUI.showModifiedObjectName(scenarioMap,true, "");
        assertTrue(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIShowModifiedObjectNameFromFalse() {
        dataPresenterGUI.showModifiedObjectName(scenarioMap, false, "");
        assertFalse(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIShowModifiedObjectTypeFromTrue() {
        dataPresenterGUI.showModifiedObjectType(scenarioMap,true);
        assertTrue(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIShowModifiedObjectTypeFromFalse() {
        dataPresenterGUI.showModifiedObjectType(scenarioMap, false);
        assertFalse(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIShowAddedObjectFromTrue() {
        dataPresenterGUI.showAddedObject(scenarioMap,true);
        assertTrue(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIShowAddedObjectFromFalse() {
        dataPresenterGUI.showAddedObject(scenarioMap, false);
        assertFalse(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIShowRemovedObjectFromTrue() {
        dataPresenterGUI.showRemovedObject(scenarioMap,true);
        assertTrue(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIShowRemovedObjectFromFalse() {
        dataPresenterGUI.showRemovedObject(scenarioMap, false);
        assertFalse(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIShowCustomTypeCreationStatusFromTrue() {
        dataPresenterGUI.showCustomTypeCreationStatus(true);
        assertTrue(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIShowCustomTypeCreationStatusFromFalse() {
        dataPresenterGUI.showCustomTypeCreationStatus(false);
        assertFalse(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIShowGenerationStatusFromTrue() {
        dataPresenterGUI.showGenerationStatus(true);
        assertTrue(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIShowGenerationStatusFromFalse() {
        dataPresenterGUI.showGenerationStatus(false);
        assertFalse(dataPresenterGUI.isOkOperation());
    }

    @Test
    public void DataPresenterGUIShowTypes() {
        typeMap = new HashMap<>();
        typeMap.put(1, type);

        when(type.getName()).thenReturn("Type");

        dataPresenterGUI.showTypes(typeMap);
        assertTrue(dataPresenterGUI.getlTypes().get(0).startsWith("Type"));
    }

    @Test
    public void DataPresenterGUIGetWorldObjectFrequency() {
        dataPresenterGUI.showBDLOutput(bdl, true);
        assertEquals(0, dataPresenterGUI.getWordObjectFrequency("NoWord"));
    }

    @Test
    public void DataPresenterGUIShowBDLOutputFromTrue() {
        dataPresenterGUI.showBDLOutput(bdl, true);
        assertEquals(true, dataPresenterGUI.isBdlLoaded());
    }

    @Test
    public void DataPresenterGUIShowBDLOutputFromFalse() {
        dataPresenterGUI.showBDLOutput(bdl, false);
        assertEquals(false, dataPresenterGUI.isBdlLoaded());
    }

}
