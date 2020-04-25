package FourCats.InterfaceAdapters;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.PersistentMemoryAccess;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RepositoryTest {

    PersistentMemoryAccess memoryMock = Mockito.mock(PersistentMemoryAccess.class);

    @InjectMocks
    Repository repo = new Repository(memoryMock);

    @Test
    public void createBdl() {
        String name = "nuovoBdl";
        Bdl bdl = repo.createBdl(name);

        assertTrue(bdl!=null);
    }

    @Test
    public void testReadBdlPresentInMemory() {
        when(memoryMock.loadBdl(any(String.class))).thenReturn(new Bdl());
        String name = "aBdl";
        Bdl bdl = repo.readBdl(name);

        assertTrue(bdl!=null);
    }

    @Test
    public void testReadBdlNotFound() {
        when(memoryMock.loadBdl(any(String.class))).thenReturn(null);
        String name = "aBdlThatIsNotInMemory";
        Bdl bdl = repo.readBdl(name);

        assertTrue(bdl==null);
    }

    @Test
    public void testUpdateBdlWithNoError() {
        when(memoryMock.saveBdl(any(Bdl.class))).thenReturn(true);
        Bdl bdl = Mockito.mock(Bdl.class);

        boolean result = repo.updateBdl(bdl);

        assertTrue(result);
    }

    @Test
    public void testUpdateBdlWithError() {
        when(memoryMock.saveBdl(any(Bdl.class))).thenReturn(false);
        Bdl bdl = Mockito.mock(Bdl.class);

        boolean result = repo.updateBdl(bdl);

        assertTrue(!result);
    }

    @Test
    public void testReadDocumentWithNoError() {
        when(memoryMock.loadDocument(any(String.class))).thenReturn(new Document("",""));
        String t = "title";
        Document doc = repo.readDocument(t);

        assertTrue(doc!=null);
    }

    @Test
    public void testReadDocumentWithError() {
        when(memoryMock.loadDocument(any(String.class))).thenReturn(null);
        String t = "title";
        Document doc = repo.readDocument(t);

        assertTrue(doc==null);
    }

    @Test
    public void testReadAssociationWithNoError() {
        when(memoryMock.loadAssociation(any(String.class))).thenReturn(new LinkedList<>());
        String name = "bdlName";
        LinkedList<String> association = repo.readAssociation(name);

        assertTrue(association!=null);
    }

    @Test
    public void testReadAssociationWithError() {
        when(memoryMock.loadAssociation(any(String.class))).thenReturn(null);
        String name = "bdlName";
        LinkedList<String> association = repo.readAssociation(name);

        assertTrue(association==null);
    }

    @Test
    public void testUpdateAssociationWithNoError() {
        when(memoryMock.saveAssociation(any(String.class),any(List.class))).thenReturn(true);
        String name = "bdlName";
        LinkedList<String> association = new LinkedList<>();
        boolean result = repo.updateAssociation(name,association);

        assertTrue(result);
    }

    @Test
    public void testUpdateAssociationWithError() {
        when(memoryMock.saveAssociation(any(String.class),any(List.class))).thenReturn(false);
        String name = "bdlName";
        LinkedList<String> association = new LinkedList<>();
        boolean result = repo.updateAssociation(name,association);

        assertTrue(!result);
    }
}