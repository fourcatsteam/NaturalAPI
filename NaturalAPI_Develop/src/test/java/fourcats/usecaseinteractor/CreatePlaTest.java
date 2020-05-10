package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.CreatePlaOutputPort;
import fourcats.port.ModifyOutputPort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreatePlaTest {

    @Mock
    RepositoryAccess repositoryMock;
    @Mock
    CreatePlaOutputPort outputMock;
    @InjectMocks
    CreatePla createPla;

    @Before
    public void before(){
       // MockitoAnnotations.initMocks(this);
        createPla = new CreatePla(repositoryMock,outputMock);
    }

    @Test
    public void correctlyCreatingModifyApi(){
        assertNotNull(createPla);
    }

    @Test
    public void testingCorrectlyMethodCallModifyGui(){
        File file = new File(".\\PLA\\TestFiles\\Prova.txt");
        when(repositoryMock.openFile(".\\PLA\\TestFiles\\Prova.txt")).thenReturn(file);
        createPla.create(".\\PLA\\TestFiles\\Prova","ext","text");


        verify(outputMock).showOutput(any(String.class));
        verify(repositoryMock).writePla(".\\PLA\\TestFiles\\Prova.txt","ext\ntext");

    }
}
