package fourcats.usecaseinteractor;

import fourcats.interfaceAccess.BalAnalyzer;
import fourcats.interfaceAccess.RepositoryAccess;
import fourcats.port.ApiOutputPort;
import org.junit.Test;
import org.mockito.*;
import static org.junit.Assert.*;

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

}