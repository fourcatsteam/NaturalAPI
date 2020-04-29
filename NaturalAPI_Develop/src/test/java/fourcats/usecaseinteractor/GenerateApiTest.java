package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import org.junit.Test;
import org.mockito.*;

import static org.junit.Assert.*;

public class GenerateApiTest {

    RepositoryAccess repositoryMock = Mockito.mock(RepositoryAccess.class);

    @Test
    public void GenerateAPITestCorrectCreation() {
        GenerateApi correctGenerator = new GenerateApi(repositoryMock);
        assertNotNull(correctGenerator);
    }

}