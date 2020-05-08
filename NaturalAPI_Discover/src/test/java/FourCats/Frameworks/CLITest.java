package FourCats.Frameworks;

import FourCats.InterfaceAdapters.Controller;
import FourCats.InterfaceAdapters.DataPresenter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CLITest {

    @Mock
    Controller controllerMock;

    @Mock
    DataPresenter dataPresenterMock;

    @Mock
    BufferedReader bufferedReaderMock;

    @InjectMocks
    CLI cli;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testReadUseCaseCreateBdl() throws IOException {
        when(bufferedReaderMock.readLine()).thenReturn("1","name","doc.txt","EXIT");

        cli.readUseCase();

        verify(controllerMock).createBdl(any(String.class),any(List.class));
    }

    @Test
    public void testReadUseCaseAddDocuments() throws IOException {
        when(bufferedReaderMock.readLine()).thenReturn("2","name","doc.txt","EXIT");

        cli.readUseCase();

        verify(controllerMock).addDocument(any(String.class),any(List.class));
    }
    @Test
    public void testReadUseCaseRemoveDocuments() throws IOException {
        when(bufferedReaderMock.readLine()).thenReturn("3","name","doc.txt","EXIT");

        cli.readUseCase();

        verify(controllerMock).removeDocument(any(String.class),any(List.class));
    }

    @Test
    public void testReadUseCaseExit() throws IOException {
        when(bufferedReaderMock.readLine()).thenReturn("E");

        Boolean result = cli.readUseCase();

        assertTrue(!result);
    }

    @Test
    public void testShowResult() {
        when(dataPresenterMock.getMessage()).thenReturn("message");

        cli.showResult();

        verify(dataPresenterMock).getMessage();
    }

    @Test
    public void update() {
        CLI spyCli = Mockito.spy(cli);
        doNothing().when(spyCli).showResult();
        spyCli.update();

        verify(spyCli).showResult();
    }
}