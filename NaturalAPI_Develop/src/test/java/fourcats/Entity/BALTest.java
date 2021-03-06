package fourcats.Entity;

import fourcats.entity.Actor;
import fourcats.entity.BAL;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BALTest {

    @Mock
    Actor actorMock;

    @Mock
    Actor actorMock2;

    @Spy
    public LinkedList<Actor> listMock = new LinkedList<>();

    @InjectMocks
    BAL bal;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConstructor(){
        bal = new BAL();
        assertTrue(bal!=null);
    }

    @Test
    public void tesAddDuplicateUserToBAL() {
        when(actorMock.getName()).thenReturn("act1");

        bal.addUserToBAL(actorMock);
        bal.addUserToBAL(actorMock);

        verify(listMock,times(1)).add(any(Actor.class));
    }

    @Test
    public void getActors() {
        List<Actor> l = bal.getActors();
        assertTrue(l!=null);
    }

    @Test
    public void testToString() {
        listMock.add(actorMock);
        listMock.add(actorMock2);
        when(actorMock.toString()).thenReturn("actor1");
        when(actorMock2.toString()).thenReturn("actor2");

        String expected = "actor1actor2";
        String s = bal.toString();

        assertEquals(expected,s);
    }

}
