package fourcats.frameworks;

import fourcats.entity.API;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public void loadingPLACorrectlyFound() throws IOException{
        String name = "PLA/TestFiles/Prova";
        String pla = file.loadPLA(name);
        assertTrue(pla!=null);
        assertEquals("ciao\n",pla);
    }

    @Test
    public void loadingFileCorrectly(){
        String name = "PLA/TestFiles/Prova";
        assertNotNull(file.openFile(name));
    }

    @Test
    public void writingAPITestCorrectly(){
        //Da rivedere
       // API apis = new API();
//        file.writeApi("path",apis);
        assertTrue(true);
    }

    @Test
    public void writingPLATestCorrectly(){
        //Da rivedere
        file.writePla("path","pla");
        assertTrue(true);
    }


}
