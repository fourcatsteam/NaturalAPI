package fourcats.interfaceadapters;

import fourcats.entities.Bdl;
import fourcats.entities.Scenario;
import fourcats.entities.Type;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DataPresenterTest {

    DataPresenter dataPresenter;

    @Mock
    DataPresenter dataPresenterMock;

    @Mock
    Map<Integer, Scenario> aScenarioMap;

    @Mock
    Map<Integer, Type> aTypeMap;

    @Mock
    Scenario aScenario;

    @Mock
    Type aType;

    @Mock
    Bdl bdl;

    @Before
    public void CreateDataPresenter() {
        dataPresenter = new DataPresenter();
    }

    @Test
    public void DataPresenterShowSuggestionsForScenario() {
        doNothing().when(dataPresenterMock).showSuggestionsForScenario(aScenarioMap);
        dataPresenterMock.showSuggestionsForScenario(aScenarioMap);
        verify(dataPresenterMock, times(1)).showSuggestionsForScenario(aScenarioMap);
    }

    @Test
    public void DataPresenterShowSuggestionsForScenarioFromScenariosMap() {
        HashMap<Integer, Scenario> aMap = new HashMap<>();
        aMap.put(1, aScenario);

        when(aScenario.getName()).thenReturn("ScenarioName");
        when(aScenarioMap.entrySet()).thenReturn(aMap.entrySet());

        dataPresenter.showSuggestionsForScenario(aScenarioMap);
        //Cambiato
        assertFalse(dataPresenter.getDataToShow().startsWith("----SCENARIO: 1) ScenarioName----"));
    }

    @Test
    public void DataPresenterShowSuggestionsForScenarioWthBDLNotLoaded() {
        HashMap<Integer, Scenario> aMap = new HashMap<>();
        aMap.put(1, aScenario);

        when(aScenario.getName()).thenReturn("ScenarioName");
        when(aScenarioMap.entrySet()).thenReturn(aMap.entrySet());

        dataPresenter.showRemoveBdlStatus();
        dataPresenter.showSuggestionsForScenario(aScenarioMap);
        //Cambiato
        assertFalse(dataPresenter.getDataToShow().startsWith("----SCENARIO: 1) ScenarioName----"));
    }

    @Test
    public void DataPresenterShowErrorFileLoadWthWarning() {
        dataPresenter.showErrorFileLoad(true);
        String warningToShow = dataPresenter.getDataToShow();

        assertTrue(warningToShow.startsWith("Warning"));
    }

    @Test
    public void DataPresenterShowErrorFileLoadWthError() {
        dataPresenter.showErrorFileLoad(false);
        String errorToShow = dataPresenter.getDataToShow();

        assertTrue(errorToShow.startsWith("Error"));
    }

    @Test
    public void DataPresenterShowDeclinedSuggestionCorrectly() {
        dataPresenter.showDeclinedSuggestion(aScenarioMap, true);
        assertTrue(dataPresenter.getDataToShow().startsWith("Suggestion removed!"));
    }

    @Test
    public void DataPresenterShowDeclinedSuggestionWithError() {
        dataPresenter.showDeclinedSuggestion(aScenarioMap, false);
        assertTrue(dataPresenter.getDataToShow().startsWith("Oh no!"));
    }

    @Test
    public void DataPresenterShowGenerationStatusCorrectly() {
        dataPresenter.showGenerationStatus(true);
        assertTrue(dataPresenter.getDataToShow().startsWith("Hooray"));
    }

    @Test
    public void DataPresenterShowGenerationStatusWthError() {
        dataPresenter.showGenerationStatus(false);
        assertTrue(dataPresenter.getDataToShow().startsWith("Oh no"));
    }

    @Test
    public void DataPresenterShowModifiedActionNameCorrectly() {
        dataPresenter.showModifiedActionName(aScenarioMap, true, "");
        assertTrue(dataPresenter.getDataToShow().startsWith("Suggestion name successfully updated"));
    }

    @Test
    public void DataPresenterShowModifiedActionNameWithError() {
        dataPresenter.showModifiedActionName(aScenarioMap, false, "");
        assertTrue(dataPresenter.getDataToShow().startsWith("Oh no!"));
    }

    @Test
    public void DataPresenterShowModifiedActionTypeCorrectly() {
        dataPresenter.showModifiedActionType(aScenarioMap, true);
        assertTrue(dataPresenter.getDataToShow().startsWith("Action type successfully updated"));
    }

    @Test
    public void DataPresenterShowModifiedActionTypeWithError() {
        dataPresenter.showModifiedActionType(aScenarioMap, false);
        assertTrue(dataPresenter.getDataToShow().startsWith("Oh no!"));
    }

    @Test
    public void DataPresenterShowModifiedObjectNameCorrectly() {
        dataPresenter.showModifiedObjectName(aScenarioMap, true, "");
        assertTrue(dataPresenter.getDataToShow().startsWith("Object name of the suggestion successfully updated"));
    }

    @Test
    public void DataPresenterShowModifiedObjectNameWithError() {
        dataPresenter.showModifiedObjectName(aScenarioMap, false, "");
        assertTrue(dataPresenter.getDataToShow().startsWith("Oh no!"));
    }

    @Test
    public void DataPresenterShowModifiedObjectTypeCorrectly() {
        dataPresenter.showModifiedObjectType(aScenarioMap, true);
        assertTrue(dataPresenter.getDataToShow().startsWith("Object type of the suggestion successfully updated"));
    }

    @Test
    public void DataPresenterShowModifiedObjectTypeWithError() {
        dataPresenter.showModifiedObjectType(aScenarioMap, false);
        assertTrue(dataPresenter.getDataToShow().startsWith("Oh no!"));
    }

    @Test
    public void DataPresenterShowAddedObjectCorrectly() {
        dataPresenter.showAddedObject(aScenarioMap, true);
        assertTrue(dataPresenter.getDataToShow().startsWith("Object successfully added"));
    }

    @Test
    public void DataPresenterShowAddedObjectWithError() {
        dataPresenter.showAddedObject(aScenarioMap, false);
        assertTrue(dataPresenter.getDataToShow().startsWith("Oh no!"));
    }

    @Test
    public void DataPresenterShowRemovedObjectCorrectly() {
        dataPresenter.showRemovedObject(aScenarioMap, true);
        assertTrue(dataPresenter.getDataToShow().startsWith("Object successfully removed!"));
    }

    @Test
    public void DataPresenterShowRemovedObjectWithError() {
        dataPresenter.showRemovedObject(aScenarioMap, false);
        assertTrue(dataPresenter.getDataToShow().startsWith("Oh no!"));
    }

    @Test
    public void DataPresenterShowTypesFromEmptyMap() {
        when(aTypeMap.size()).thenReturn(0);
        dataPresenter.showTypes(aTypeMap);
        assertTrue(dataPresenter.getDataToShow().startsWith("No types defined yet"));
    }

    @Test
    public void DataPresenterShowTypesWithError() {
        HashMap<Integer, Type> aMap = new HashMap<>();
        aMap.put(1, aType);

        when(aType.getName()).thenReturn("TypeName");
        when(aTypeMap.size()).thenReturn(1);
        when(aTypeMap.entrySet()).thenReturn(aMap.entrySet());

        dataPresenter.showTypes(aTypeMap);
        assertTrue(dataPresenter.getDataToShow().startsWith("----TYPE: 1) TypeName----"));
    }

    @Test
    public void DataPresenterShowCustomTypeCreationStatusCorrectly() {
        dataPresenter.showCustomTypeCreationStatus(true);
        assertTrue(dataPresenter.getDataToShow().startsWith("Custom type successfully created!"));
    }

    @Test
    public void DataPresenterShowCustomTypeCreationStatusWithError() {
        dataPresenter.showCustomTypeCreationStatus(false);
        assertTrue(dataPresenter.getDataToShow().startsWith("Error"));
    }

    @Test
    public void DataPresenterShowAddedSuggestionCorrectly() {
        dataPresenter.showAddedSuggestion(aScenarioMap, true);
        assertTrue(dataPresenter.getDataToShow().startsWith("Suggestion successfully added!"));
    }

    @Test
    public void DataPresenterShowAddedSuggestionWithError() {
        dataPresenter.showAddedSuggestion(aScenarioMap, false);
        assertTrue(dataPresenter.getDataToShow().startsWith("Oh no"));
    }

    @Test
    public void DataPresenterShowBDLOutputWthBDLLoaded() {
        dataPresenter.showBDLOutput(bdl, true);
        assertTrue(dataPresenter.getDataToShow().startsWith("BDL Loaded successfully"));
    }

    @Test
    public void DataPresenterShowBDLOutputWthNoBDLLoaded() {
        dataPresenter.showBDLOutput(bdl, false);
        assertTrue(dataPresenter.getDataToShow().startsWith("Error while loading BDL"));
    }

    @Test
    public void DataPresenterIsBDLLoaded() {
        assertFalse(dataPresenter.isBdlLoaded());
    }
}