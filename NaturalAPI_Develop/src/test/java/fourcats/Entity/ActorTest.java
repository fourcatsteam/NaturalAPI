package fourcats.Entity;

import fourcats.entity.Action;
import fourcats.entity.Actor;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ActorTest {
    Actor act;

    @Before
    public void before(){
        act = new Actor("Actor");
    }

    @Test
    public void testActorEmptyCorrectlyCreate(){
        Actor acto = new Actor();
        assertNotNull(acto);
    }

    @Test
    public void testActorWithNameCorrectlyCreate(){
        Actor acto = new Actor("NomeActor");
        assertNotNull(acto);
    }

    @Test
    public void testAddingOneActionCorrectly(){
        act.addAction(new Action("Action","Type"));
        assertEquals(1,act.getActions().size());
    }

    @Test
    public void testAddingMoreActionCorrectly(){
        List<Action> a = new LinkedList<>();
        a.add(new Action("Action1","Type1"));
        a.add(new Action("Action2","Type2"));
        act.addActions(a);
        assertEquals(2,act.getActions().size());
    }

    @Test
    public void testRequestActionByName(){
        act.addAction(new Action("Action","Type"));
        assertEquals(1,act.getActionsByName("Action").size());
    }

    @Test
    public void testActorToString(){
        act.addAction(new Action("Action","TypeAction"));
        assertEquals("TypeAction Action()",act.toString());
    }

    public void testGettingNameOfActor(){
        assertEquals("Actor",act.getName());
    }

    @Test
    public void testSettingNameOfActor(){
        act.setName("newName");
        assertEquals("newName",act.getName());
    }


}
