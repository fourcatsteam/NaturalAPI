package fourcats.entities;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

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