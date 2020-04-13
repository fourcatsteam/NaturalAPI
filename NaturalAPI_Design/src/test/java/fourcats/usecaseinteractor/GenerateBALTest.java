package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.GenerateBALOutputPort;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.io.IOException;

import static org.junit.Assert.*;

public class GenerateBALTest {

    RepositoryAccess repositoryMock = Mockito.mock(RepositoryAccess.class);
    GenerateBALOutputPort outputMock = Mockito.mock(GenerateBALOutputPort.class);
    BalAnalyzer analyzerMock = Mockito.mock(BalAnalyzer.class);

    @InjectMocks
    GenerateBAL correctGenerator = new GenerateBAL(repositoryMock, outputMock, analyzerMock);

    @Test
    public void GenerateBALCorrectCreation() {
        assertNotNull(correctGenerator);
    }

    @Test
    public void GenerateBALFromEmptyFilename() {
        try{
            correctGenerator.generateBAL("");
        }
        catch(IOException e)
        {
            fail();
        }
        assertTrue(true);
    }

    @Test
    public void GenerateBALFromCorrectFilename() {
        try{
            correctGenerator.generateBAL("GenerateBALCorrectFile.txt");
        }
        catch(IOException e)
        {
            fail();
        }
        assertTrue(true);
    }

}