package fourcats.frameworks;

import fourcats.entity.API;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest {
    @Mock
    DataKeeper dataMock;

    @Mock
    FileSystem fileMock;

    @InjectMocks
    Repository repo;

    @Test
    public void openingFileCorrectly(){
        repo.openFile("filename");
        verify(fileMock).openFile(any(String.class));
    }

    @Test
    public void loadingPLACorrectly(){
        repo.loadPLA("filename");
        verify(fileMock).loadPLA(any(String.class));
    }

    @Test
    public void writingAPICorrectly(){
        repo.writeApi("path",new API());
        verify(fileMock).writeApi(any(String.class),any(API.class));
    }

    @Test
    public void addingAPIWithoutIdToDataKeeperCorrectly(){
        repo.addApi(new API());
        verify(dataMock).addApi(any(API.class));
    }

    @Test
    public void addingAPIWithIdToDataKeeperCorrectly(){
        repo.addApiWithId(1,new API());
        verify(dataMock).addApiWithId(any(Integer.class),any(API.class));
    }

    @Test
    public void deletingAPIWithIdFromDataKeeperCorrectly(){
        repo.deleteApi(1);
        verify(dataMock).deleteApi(any(Integer.class));
    }

    @Test
    public void gettingAPIWithIdFromDataKeeperCorrectly(){
        repo.getApi(1);
        verify(dataMock).getApi(any(Integer.class));
    }

    @Test
    public void gettingADIMapWithIdFromDataKeeperCorrectly(){
        repo.getApiMap();
        verify(dataMock).getApiMap();
    }

    @Test
    public void gettingSizeFromDataKeeperCorrectly(){
        repo.getSize();
        verify(dataMock).getSize();
    }

    @Test
    public void updatingAPIToDataKeepeerCorrectly(){
        repo.updateApi("api1","api2");
        verify(dataMock).updateApi(any(String.class),any(String.class));
    }

    @Test
    public void writingPLAToFileSystemCorrectly(){
        repo.writePla("plafile","pla");
        verify(fileMock).writePla(any(String.class),any(String.class));
    }

    @Test
    public void addingCoupleBalPlaToDataKeeperCorrectly(){
        repo.addCoupleBalPla("BAL","pla");
        verify(dataMock).addCoupleBalPla(any(String.class),any(String.class));
    }

    @Test
    public void isCoupleBalPlaPresentInDataKeepeerCorrectly(){
        when(dataMock.isCoupleBalPlaPresent("BAL","pla")).thenReturn(true);
        assertTrue(repo.isCoupleBalPlaPresent("BAL","pla"));
    }

    @Test
    public void isThisAPIPresentInDataKeeperCorrectly(){
        API a = new API();
        when(dataMock.isThisApiPresent(a)).thenReturn(true);
        assertTrue(repo.isThisApiPresent(a));
    }

    @Test
    public void isThisClassNamePresentInDataKeeperCorrectly(){
        API a = new API();
        when(dataMock.isThisClassNamePresent(a)).thenReturn(true);
        assertTrue(repo.isThisClassNamePresent(a));
    }


}
