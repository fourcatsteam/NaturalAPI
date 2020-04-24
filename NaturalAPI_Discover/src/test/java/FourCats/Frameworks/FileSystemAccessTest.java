package FourCats.Frameworks;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class FileSystemAccessTest {

    FileSystemAccess fs = new FileSystemAccess();

    @Test
    public void testLoadDocumentFound() {
        String title = "prova.txt";
        String content = "Hi, i'm Simone. I'm trying to coding following Clean Architecture! See you soon. ";

        Document doc = fs.loadDocument(title);

        assertTrue(doc != null);
        assertEquals(content, doc.getContent());
    }

    @Test
    public void testLoadDocumentNotFound() {
        String title = "thisDocumentIsNotPresent.txt";

        Document doc = fs.loadDocument(title);

        assertTrue(doc==null);
    }

    @Test
    public void testLoadBdlFound() {
        String name = "nuova";

        Bdl bdl = fs.loadBdl(name);

        assertTrue(bdl != null);
    }

    @Test
    public void testLoadBdlNotFound() {
        String name = "aBdlThaIsNotInMemory";

        Bdl bdl = fs.loadBdl(name);

        assertTrue(bdl == null);
    }

    @Test
    public void testSaveBdl() {
        Bdl bdl = Mockito.mock(Bdl.class);
        when(bdl.getName()).thenReturn("example");
        when(bdl.getNouns()).thenReturn(new LinkedList<>());
        when(bdl.getVerbs()).thenReturn(new LinkedList<>());
        when(bdl.getPredicates()).thenReturn(new LinkedList<>());

        boolean result = fs.saveBdl(bdl);

        assertTrue(result);
    }

    @Test
    public void testLoadAssociationFound() {
        String name = "nuova";

        LinkedList<String> association = fs.loadAssociation(name);

        assertTrue(association!=null);
    }

    @Test
    public void testLoadAssociationNotFound() {
        String name = "anAssociationThatIsNotPresent";

        LinkedList<String> association = fs.loadAssociation(name);

        assertTrue(association==null);
    }

    @Test
    public void testSaveAssociation() {
        String name = "example";
        LinkedList<String> list = new LinkedList<>();
        list.add("prova.txt");

        boolean result = fs.saveAssociation(name, list);

        assertTrue(result);
    }

    @Test
    public void testAddAssociationFound() {
        String name = "example";
        LinkedList<String> list = new LinkedList<>();
        list.add("doc.txt");

        boolean result = fs.addAssociation(name,list);

        assertTrue(result);
    }

    @Test
    public void testAddAssociationNotFound() {
        String name = "anAssociationThatIsNotPresent";
        LinkedList<String> list = new LinkedList<>();

        boolean result = fs.addAssociation(name,list);

        assertTrue(!result);
    }

    @Test
    public void testRemoveAssociationFound() {
        String name = "example";
        LinkedList<String> list = new LinkedList<>();

        boolean result = fs.removeAssociation(name,list);

        assertTrue(result);
    }

    @Test
    public void testRemoveAssociationNotFound() {
        String name = "anAssociationThatIsNotPresent";
        LinkedList<String> list = new LinkedList<>();

        boolean result = fs.removeAssociation(name,list);

        assertTrue(!result);
    }
}