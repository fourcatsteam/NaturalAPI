package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.ModifyPlaOutputPort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class ModifyPlaTest {

    @Mock
    RepositoryAccess repositoryMock;
    @Mock
    ModifyPlaOutputPort outputMock;
    @InjectMocks
    ModifyPla modifyPla;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void correctlyCreatingModifyApi(){
        assertNotNull(modifyPla);
    }

    @Test
    public void testingLoadingPlaCorrectly(){
        File file = new File(".\\PLA\\javaClassPLA.txt");
        when(repositoryMock.openFile(".\\PLA\\javaClassPLA.txt")).thenReturn(file);
        modifyPla.loadPlaToModify(".\\PLA\\javaClassPLA.txt");
        verify(repositoryMock).openFile(any(String.class));
       // verify(outputMock).showLoadPla(any(String.class),any(String.class),any(String.class));
    }

    @Test
    public void testingModify() throws IOException {
       when(repositoryMock.loadPLA(any(String.class))).thenReturn("pla");
       modifyPla.modify("nomefile","testo");
       verify(repositoryMock).writePla(any(String.class),any(String.class));
       verify(outputMock).showMessage(any(String.class));
    }


}
