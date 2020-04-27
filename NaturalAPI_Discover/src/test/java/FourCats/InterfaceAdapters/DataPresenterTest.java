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
        assertEquals("",dataPresenter.getBdlNouns());
    }

    @Test
    public void testGetBdlVerbs() {
        assertEquals("",dataPresenter.getBdlVerbs());
    }

    @Test
    public void testGetBdlPredicates() {
        assertEquals("",dataPresenter.getBdlPredicates());
    }

    @Test
    public void testShowCreateBdlOutput() {
        dataPresenter.showCreateBdlOutput();

        String expected="BDL generata! Puoi trovare i file csv all'interno della cartella BDL";
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
        dataPresenter.showAddDocumentsOutput();

        String expected="Documenti aggiunti al BDL con successo!";
        assertEquals(expected,dataPresenter.getMessage());
    }

    @Test
    public void testShowRemoveDocumentOutputPort() {
        dataPresenter.showRemoveDocumentOutputPort();

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