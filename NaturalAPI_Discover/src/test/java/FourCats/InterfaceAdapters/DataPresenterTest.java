package FourCats.InterfaceAdapters;

import FourCats.Entities.Bdl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
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
        dataPresenter.showCreateBdlOutput(bdlMock);
        when(bdlMock.getName()).thenReturn("bdl_name");

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

        String expected="Documenti aggiunti al BDL con successo!";
        assertEquals(expected,dataPresenter.getMessage());
    }

    @Test
    public void testShowRemoveDocumentOutputPort() {
        Bdl bdlMock = Mockito.mock(Bdl.class);
        dataPresenter.showRemoveDocumentOutputPort(bdlMock);

        String expected="I Documenti da te selezionati sono stati rimossi con successo";
        assertEquals(expected,dataPresenter.getMessage());
    }

    @Test
    public void testShowViewBdlOutput() {
        Bdl bdlMock = Mockito.mock(Bdl.class);
        when(bdlMock.nounsToString()).thenReturn("nouns");
        when(bdlMock.verbsToString()).thenReturn("verbs");
        when(bdlMock.predicatesToString()).thenReturn("predicates");

        dataPresenter.showViewBdlOutput(bdlMock);

        assertEquals("nouns",dataPresenter.getBdlNouns());
        assertEquals("verbs",dataPresenter.getBdlVerbs());
        assertEquals("predicates",dataPresenter.getBdlPredicates());
    }
}