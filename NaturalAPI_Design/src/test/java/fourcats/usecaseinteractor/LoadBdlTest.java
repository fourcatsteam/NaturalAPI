package fourcats.usecaseinteractor;

import fourcats.frameworks.Repository;
import fourcats.port.LoadBdlOutputPort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoadBdlTest {

    LoadBdl loadBdl;

    @Mock
    Repository repository;

    @Mock
    LoadBdlOutputPort loadBdlOutputPort;

    @Before
    public void CreateLoadBdl() {
        loadBdl = new LoadBdl(repository, loadBdlOutputPort);
    }

    @Test
    public void LoadBdlLoadFilesCorrectly() {
        String[] pathList = new String[2];
        pathList[0] = "path1";
        pathList[1] = "path2";

        try{
            loadBdl.loadBdlFiles(pathList);
            verify(repository, times(1)).readAndCreateBdl(pathList);
            verify(loadBdlOutputPort, times(1)).showBDLOutput(repository.readBdl(), true);
        }
        catch (Exception e)
        {
            fail();
        }
    }

    @Test
    public void LoadBdlLoadFilesWthException() {
        String[] pathList = new String[2];
        pathList[0] = "path1";
        pathList[1] = "path2";
        Exception io = new Exception();

        try{
            doThrow(io).when(repository).readAndCreateBdl(pathList);
            loadBdl.loadBdlFiles(pathList);
            fail();
        }
        catch (Exception e)
        {
            assertTrue(true);
        }
    }
}