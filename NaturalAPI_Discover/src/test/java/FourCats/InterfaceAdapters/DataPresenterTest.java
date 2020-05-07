package FourCats.InterfaceAdapters;

import FourCats.DataStructure.WordCounter;
import FourCats.Entities.Bdl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class DataPresenterTest {

    @InjectMocks
    DataPresenter dataPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetMessage() {
        assertEquals("",dataPresenter.getMessage());
    }

    @Test
    public void testGetBdlNouns() {
        assertTrue(dataPresenter.getBdlNouns()!=null);
    }

    @Test
    public void testGetBdlVerbs() {
        assertTrue(dataPresenter.getBdlVerbs()!=null);
    }

    @Test
    public void testGetBdlPredicates() {
        assertTrue(dataPresenter.getBdlPredicates()!=null);
    }

    @Test
    public void testShowCreateBdlOutput() {
        Bdl bdlMock = Mockito.mock(Bdl.class);
        when(bdlMock.getName()).thenReturn("bdl_name");
        dataPresenter.showCreateBdlOutput(bdlMock);

        String expected="BDL \"bdl_name\" generated successfully. You can find the CSV files in the chosen directory";
        assertEquals(expected,dataPresenter.getMessage());
    }

    @Test
    public void testShowError() {
        dataPresenter.showError("message");

        String expected="Error: message";
        assertEquals(expected,dataPresenter.getMessage());
    }

    @Test
    public void testShowWarning() {
        dataPresenter.showWarning("message");

        String expected="Warning: message";
        assertEquals(expected,dataPresenter.getMessage());
    }

    @Test
    public void testShowAddDocumentsOutput() {
        Bdl bdlMock = Mockito.mock(Bdl.class);
        dataPresenter.showAddDocumentsOutput(bdlMock);

        String expected="Selected documents have been added successfully";
        assertEquals(expected,dataPresenter.getMessage());
    }

    @Test
    public void testShowRemoveDocumentOutputPort() {
        Bdl bdlMock = Mockito.mock(Bdl.class);
        dataPresenter.showRemoveDocumentOutputPort(bdlMock);

        String expected="Selected documents have been removed successfully";
        assertEquals(expected,dataPresenter.getMessage());
    }

    @Test
    public void testShowViewBdlOutput() {
        Bdl bdlMock = Mockito.mock(Bdl.class);
        WordCounter wcMock = Mockito.mock(WordCounter.class);
        LinkedList<WordCounter> list = new LinkedList<>();
        list.add(wcMock);
        when(bdlMock.getNouns()).thenReturn(list);
        when(bdlMock.getVerbs()).thenReturn(list);
        when(bdlMock.getPredicates()).thenReturn(list);
        when(bdlMock.getName()).thenReturn("bdl_name");
        when(wcMock.getWord()).thenReturn("word");
        when(wcMock.getCount()).thenReturn(1);

        dataPresenter.showViewBdlOutput(bdlMock);

        assertEquals("BDL \"bdl_name\" visualized correctly",dataPresenter.getMessage());
    }
}