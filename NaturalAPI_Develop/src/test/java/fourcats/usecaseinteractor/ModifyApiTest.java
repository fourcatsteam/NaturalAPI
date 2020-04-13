package fourcats.usecaseinteractor;

import fourcats.interfaceAccess.BalAnalyzer;
import fourcats.interfaceAccess.RepositoryAccess;
import fourcats.port.ModifyOutputPort;
import org.junit.Test;
import org.mockito.*;

import static org.junit.Assert.*;

public class ModifyApiTest {

    BalAnalyzer analyzerMock = Mockito.mock(BalAnalyzer.class);
    RepositoryAccess repositoryMock = Mockito.mock(RepositoryAccess.class);
    ModifyOutputPort outputMock = Mockito.mock(ModifyOutputPort.class);

    @InjectMocks
    ModifyApi modifyApi = new ModifyApi(analyzerMock, repositoryMock, outputMock);

    @Test
    public void ModifyAPITestFromWrongInput() {

        try{
            modifyApi.modify(-1, "", "");
            fail();
        }
        catch(Throwable e)
        {
            assertTrue(true);
        }
    }

}