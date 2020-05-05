package fourcats.entities;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ScenarioTest {

    @Mock
    Action action;

    Map<Integer, Action> map;

    Scenario scenario;

    @Before public void CreateScenario() {
        scenario = new Scenario("Name", map, "Content", "Actor","FeatureName");
    }

    @Test
    public void ScenarioCreateCorrectly() {
        assertNotNull(scenario);
    }

    @Test
    public void ScenarioGetNameCorrectly() {
        assertEquals("Name",scenario.getName());
    }

    @Test
    public void ScenarioGetActionsMapCorrectly() {
        assertEquals(map,scenario.getActionsMap());
    }


    @Test
    public void ScenarioGetActionsCorrectly() {
        map = new HashMap<Integer, Action>();
        map.put(1, action);
        scenario = new Scenario("Name", map, "Content", "Actor","FeatureName");
        assertTrue(scenario.getActions().contains(action));
    }

    @Test
    public void ScenarioGetContentCorrectly() {
        assertEquals("Content",scenario.getContent());
    }

    @Test
    public void ScenarioGetActorNameCorrectly() {
        assertEquals("Actor",scenario.getActorName());
    }

    @Test
    public void ScenarioGetFeatureNameCorrectly() {
        assertEquals("FeatureName",scenario.getFeatureName());
    }

}