package FourCats.InterfaceAdapters;

import FourCats.Port.AddDocumentsInputPort;
import FourCats.Port.CreateBdlInputPort;
import FourCats.Port.RemoveDocumentsInputPort;
import FourCats.Port.ViewBdlInputPort;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ControllerTest {

    CreateBdlInputPort cbMock = Mockito.mock(CreateBdlInputPort.class);
    AddDocumentsInputPort adMock = Mockito.mock(AddDocumentsInputPort.class);
    RemoveDocumentsInputPort rdMock = Mockito.mock(RemoveDocumentsInputPort.class);
    ViewBdlInputPort vbMock = Mockito.mock(ViewBdlInputPort.class);

    @InjectMocks
    Controller controller = new Controller(cbMock,adMock,rdMock,vbMock);

    @Test
    public void testCreateBdl() {
        String name = "name";
        LinkedList<String> titles = new LinkedList<>();
        controller.createBdl(name,titles);

        verify(cbMock).create(any(String.class),any(List.class));
    }

    @Test
    public void testAddDocument() {
        String name = "name";
        LinkedList<String> titles = new LinkedList<>();
        controller.addDocument(name,titles);

        verify(adMock).add(any(String.class),any(List.class));
    }

    @Test
    public void testRemoveDocument() {
        String name = "name";
        LinkedList<String> titles = new LinkedList<>();
        controller.removeDocument(name,titles);

        verify(rdMock).remove(any(String.class),any(List.class));
    }

    @Test
    public void testViewBdl() {
        String name = "name";
        controller.viewBdl(name);

        verify(vbMock).view(any(String.class));
    }
}