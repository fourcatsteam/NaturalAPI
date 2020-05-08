package fourcats.frameworks;

import fourcats.entity.API;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class FileSystemTest {

    FileSystem file;

    @Before
    public void before(){
        file = new FileSystem();
    }

    @Test
    public void loadingPLACorrectlyFound(){
        String name = "C:\\Users\\matte\\OneDrive\\Desktop\\NaturalAPI\\NaturalAPI_Develop\\PLA\\TestFiles\\Prova";

        String pla = file.loadPLA(name);
        assertTrue(pla!=null);
        assertEquals("ciao\n",pla);
    }

    @Test
    public void loadingPLACorrectlyNotFound(){
        String name = "anAssociationThatIsNotPresent";
        String pla = file.loadPLA(name);
        assertTrue(pla==null);
    }

    @Test
    public void loadingFileCorrectly(){
        String name = "TestFiles/Prova";
        assertNotNull(file.openFile(name));
    }

    @Test
    public void writingAPITestCorrectly(){

        API apis = new API();
        apis.addApi("apifilename","realapi");
        file.writeApi("path",apis);
        assertTrue(true);


    }

}
