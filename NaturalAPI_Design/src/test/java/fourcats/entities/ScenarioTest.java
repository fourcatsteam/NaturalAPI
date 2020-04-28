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
        scenario = new Scenario("Name", map, "Content", "Actor");
    }

    @Test
    public void ScenarioCreateCorrectly() {
        assertNotNull(scenario);
    }

    @Test
    public void ScenarioGetNameCorrectly() {
        assertEquals(scenario.getName(), "Name");
    }

    @Test
    public void ScenarioGetActionsMapCorrectly() {
        assertEquals(scenario.getActionsMap(), map);
    }

    @Test
    public void ScenarioGetActionsCorrectly() {
        map = new HashMap<Integer, Action>();
        map.put(1, action);
        scenario = new Scenario("Name", map, "Content", "Actor");
        assertTrue(scenario.getActions().contains(action));
    }

    @Test
    public void ScenarioGetContentCorrectly() {
        assertEquals(scenario.getContent(), "Content");
    }

    @Test
    public void ScenarioGetActorNameCorrectly() {
        assertEquals(scenario.getActorName(), "Actor");
    }

}