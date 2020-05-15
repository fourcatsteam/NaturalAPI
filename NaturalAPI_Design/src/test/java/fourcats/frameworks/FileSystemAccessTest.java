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