package fourcats.usecaseinteractor;

import fourcats.frameworks.Repository;
import fourcats.port.RemoveBdlOutputPort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RemoveBdlTest {

    RemoveBdl removeBdl;

    @Mock
    Repository repository;

    @Mock
    RemoveBdlOutputPort removeBdlOutputPort;

    @Before
    public void CreateRemoveBDL() {
        removeBdl = new RemoveBdl(repository, removeBdlOutputPort);
    }

    @Test
    public void RemoveBDLRemoveLoadedBDL() {
        removeBdl.removeLoadedBdl();
        verify(repository, times(1)).deleteBdl();
        verify(removeBdlOutputPort, times(1)).showRemoveBdlStatus();
    }

}