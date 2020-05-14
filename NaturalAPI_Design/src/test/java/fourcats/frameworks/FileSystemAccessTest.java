package fourcats.frameworks;

import fourcats.entities.Bdl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class FileSystemAccessTest {

    private FileSystemAccess fileSystemAccess;

    @Before
    public void CreateFileSystemAccess() {
        fileSystemAccess = new FileSystemAccess();
    }

   @Test
    public void FileSystemAccessReadFileCorrectly() {
        String result = "Not correct now";
        try {
            result = fileSystemAccess.readFile("src/test/java/fourcats/test_documents/prova.feature");
        }
        catch(IOException e)
        {
            fail();
        }
        assertEquals("Feature: aFeature\n" +
                "  As a actor\n" +
                "  Scenario: firstScenario\n" +
                "    Given A\n" +
                "    Then B\n\n", result);
    }

    @Test
    public void FileSystemAccessReadFileWthError() {
        String result = "Not correct now";
        try {
            result = fileSystemAccess.readFile("noSuchFile.nsf");
        }
        catch(FileNotFoundException e)
        {
           result = "Correct";
        }
        assertEquals("Correct",result);
    }

    @Test
    public void FileSystemAccessWriteFileCorrectly() {
        try{
            fileSystemAccess.writeFile("My correct content", "correctlyWroteFile");
        }
        catch(IOException e)
        {
            fail();
        }
        File existingFile = new File("src/test/java/fourcats/test_documents/correctlyWroteFile.json");
        assertTrue(existingFile.exists());
    }


    @Test
    public void FileSystemAccessLoadBdl() throws IOException {
        assertNotNull(fileSystemAccess.loadBdl(".\\BDL\\ATM"));
    }

    @Test
    public void FileSystemAccessgetBdl() throws IOException {
        String[] v = new String[3];
        v[0] = "BDL\\ATM.nouns.bdl.csv";
        v[1] = "BDL\\ATM.verbs.bdl.csv";
        v[2] = "BDL\\ATM.predicates.bdl.csv";

        assertNotNull(fileSystemAccess.getBdl(v));
    }


    @Test
    public void FileSystemAccessLoadBdlWthError() {
        try{
            fileSystemAccess.loadBdl("bdl");
            fail();
        }
        catch(IOException e)
        {
            assertTrue(true);
        }
    }

}