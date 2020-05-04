package fourcats.entities;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ActorTest {

    List<Action> listAction;

    String name;

    Actor actor;

    @Before
    public void setup(){
        listAction = new ArrayList<>();
        name = "actor";
        actor = new Actor(name, listAction);
    }

    @Test
    public void testAddAction(){
        Action action = new Action("act","int");
        actor.addAction(action);

        List<Action> myActionList = actor.getActions();
        Action retAction = myActionList.get(0);
        assertEquals(1, myActionList.size());
        assertEquals(action,retAction);
    }

    @Test
    public void testAddListAction(){

        List<Action> ListAction = Arrays.asList( new Action("act","int"), new Action("act","int"));

        actor.addActions(ListAction);

        List<Action> myActionList = actor.getActions();
        assertEquals(2, myActionList.size());

    }



}