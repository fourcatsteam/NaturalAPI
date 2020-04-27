package FourCats.UseCaseInteractor;

import FourCats.Entities.Bdl;
import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.Port.ViewBdlOutputPort;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ViewBdlTest {

    @Mock
    private RepositoryAccess repoMock;

    @Mock
    private ViewBdlOutputPort outputMock;

    @InjectMocks
    private ViewBdl interactor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testViewExistingBdl() {
        when(repoMock.readBdl(any(String.class))).thenReturn(new Bdl());
        interactor.view("name");

        verify(outputMock).showViewBdlOutput(any(Bdl.class));
        verify(outputMock,never()).showError(any(String.class));
    }

    @Test
    public void testViewNotExistingBdl() {
        when(repoMock.readBdl(any(String.class))).thenReturn(null);
        interactor.view("name");

        verify(outputMock).showError(any(String.class));
        verify(outputMock,never()).showViewBdlOutput(any(Bdl.class));
    }
}