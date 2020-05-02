package fourcats.usecaseinteractor;

import fourcats.entity.*;
import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.ApiOutputPort;
import org.junit.Test;
import org.mockito.*;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

public class SuggestApiTest {

    BalAnalyzer analyzerMock = Mockito.mock(BalAnalyzer.class);
    RepositoryAccess repositoryMock = Mockito.mock(RepositoryAccess.class);
    ApiOutputPort outputMock = Mockito.mock(ApiOutputPort.class);

    @InjectMocks
    SuggestApi suggestAPI = new SuggestApi(analyzerMock, repositoryMock, outputMock);

    @Test
    public void SuggestAPITestCreationFromWrongInput() {

        try{
            suggestAPI.create("", "");
            fail();
        }
        catch (Throwable e)
        {
            assertTrue(true);
        }

    }

    @Test
    public void correctlyCreatingSuggestApi(){
        assertNotNull(suggestAPI);
    }

    @Test
    public void correctlysuggestionApiCreated(){
        String filebal = "bal.json";
        String filepla = "pla";

        List<Actor> l = new LinkedList<>();
        Actor act = new Actor("Actor1");
        Action action = new Action("gioca","tipo");
        action.addObjectParam(new ObjectParam("object","tipoObj"));
        act.addAction(action);
        l.add(act);
        BAL bal = new BAL(l);

        when(analyzerMock.getBAL()).thenReturn(bal);
        when(repositoryMock.loadPLA(filepla)).thenReturn("ciao\nciao2");
        when(repositoryMock.openFile(any(String.class))).thenReturn(new File("./API/"));

        suggestAPI.create(filebal,filepla);

        verify(repositoryMock,times(2)).openFile(any(String.class));
        verify(repositoryMock).addApi(any(API.class));
        verify(outputMock).showOutput(anyMap());
    }

}