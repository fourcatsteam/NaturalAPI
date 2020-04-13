package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.DeclineBALSuggestionOutputPort;
import org.junit.Test;
import org.mockito.*;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class DeclineBALSuggestionTest {

    RepositoryAccess repositoryMock = Mockito.mock(RepositoryAccess.class);
    DeclineBALSuggestionOutputPort outputMock = Mockito.mock(DeclineBALSuggestionOutputPort.class);

    @InjectMocks
    DeclineBALSuggestion declineSuggestion = new DeclineBALSuggestion(repositoryMock, outputMock);

    @Test
    public void DeclineSuggestionFromWrongID() {
        try{
            declineSuggestion.declineSuggestion(-2, -3);
        }
        catch(Throwable e)
        {
            fail("Nessuna eccezione specificata");
        }
        assertTrue(true);
    }

}