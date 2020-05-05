package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.GenerateBalOutputPort;
import org.junit.Test;
import org.mockito.*;

import static org.junit.Assert.*;

public class GenerateBALTest {

    RepositoryAccess repositoryMock = Mockito.mock(RepositoryAccess.class);
    GenerateBalOutputPort outputMock = Mockito.mock(GenerateBalOutputPort.class);
    BalAnalyzer analyzerMock = Mockito.mock(BalAnalyzer.class);

    @InjectMocks
    GenerateBal correctGenerator = new GenerateBal(repositoryMock, outputMock, analyzerMock);

    @Test
    public void GenerateBALCorrectCreation() {
        assertNotNull(correctGenerator);
    }

    @Test
    public void GenerateBALFromEmptyFilename() {
        correctGenerator.generateBAL("");
        assertTrue(true);
    }

    @Test
    public void GenerateBALFromCorrectFilename() {
        correctGenerator.generateBAL("GenerateBALCorrectFile.txt");
        assertTrue(true);
    }

}