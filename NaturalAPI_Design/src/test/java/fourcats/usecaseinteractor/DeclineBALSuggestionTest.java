package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.DeclineBalSuggestionOutputPort;
import org.junit.Test;
import org.mockito.*;

import static org.junit.Assert.*;

public class DeclineBALSuggestionTest {

    RepositoryAccess repositoryMock = Mockito.mock(RepositoryAccess.class);
    DeclineBalSuggestionOutputPort outputMock = Mockito.mock(DeclineBalSuggestionOutputPort.class);

    @InjectMocks
    DeclineBalSuggestion declineSuggestion = new DeclineBalSuggestion(repositoryMock, outputMock);

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