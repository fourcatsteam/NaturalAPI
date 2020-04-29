//package fourcats.view;
//
//import fourcats.interfaceadapters.Controller;
//import fourcats.interfaceadapters.DataPresenter;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.internal.util.MockUtil;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintStream;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class CLITest {
//
//    @Mock
//    Controller controllerMock;
//
//    @Mock
//    DataPresenter dataPresenterMock;
//
//    @Mock
//    BufferedReader bufferedReaderMock;
//
//    @InjectMocks
//    CLI cli;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testAskingForBal(){
//        PrintStream out = mock(PrintStream.class);
//        System.setOut(out);
//        cli.askBal();
//        verify(out).println("Write file BAL title. Type E to exit");
//    }
//
//    @Test
//    public void readingBalInput() throws IOException {
//        when(bufferedReaderMock.readLine()).thenReturn("nomebal");
//        cli.readBal();
//        verify(bufferedReaderMock,times(1)).readLine();
//    }
//
//    @Test
//    public void testAskingForPLA(){
//        PrintStream out = mock(PrintStream.class);
//        System.setOut(out);
//        cli.askPla();
//        verify(out).println("Write file PLA title. Type E to exit");
//    }
//
//    @Test
//    public void readingPLAInput() throws IOException {
//        when(bufferedReaderMock.readLine()).thenReturn("nomePLA");
//        cli.readPla();
//        verify(bufferedReaderMock,times(1)).readLine();
//    }
//
//    @Test
//    public void testAskingForSuggestionApi(){
//        PrintStream out = mock(PrintStream.class);
//        System.setOut(out);
//        cli.askSuggestApi();
//        verify(out).println("Do you want to create your APIs suggestion? Type y or n");
//    }
//
//    @Test
//    public void readingSuggestionAPIInput() throws IOException {
//        when(bufferedReaderMock.readLine()).thenReturn("y");
//        cli.readSuggestApi();
//        verify(controllerMock,times(1)).createApiSuggestion(any(String.class),any(String.class));
//    }
//
//    @Test
//    public void readingSuggestionAPIInputWithNoAnswer() throws IOException {
//        CLI spycli = Mockito.spy(cli);
//        when(bufferedReaderMock.readLine()).thenReturn("n","bal","pla","y");
//        spycli.readSuggestApi();
//
//        verify(spycli).askBal();
//        verify(spycli).readBal();
//        verify(spycli).askPla();
//        verify(spycli).readPla();
//        verify(spycli).askSuggestApi();
//        verify(spycli,atLeast(1)).readSuggestApi();
//    }
//
//    @Test
//    public void testAskingAnother(){
//        PrintStream out = mock(PrintStream.class);
//        System.setOut(out);
//        cli.askAnother();
//        verify(out).println("Do you want to add another BAL? Type y or n");
//    }
//
//    @Test
//    public void readingAnotherBal() throws IOException {
//        when(bufferedReaderMock.readLine()).thenReturn("y");
//        cli.readAnother();
//        verify(bufferedReaderMock,times(1)).readLine();
//    }
//
//    @Test
//    public void testAskingGenerateApi(){
//        PrintStream out = mock(PrintStream.class);
//        System.setOut(out);
//        cli.askGenerateApi();
//        verify(out).println("Do you want to generate your APIs? Type y or n");
//    }
//
//    @Test
//    public void readingGenerateApi() throws IOException {
//        when(bufferedReaderMock.readLine()).thenReturn("y");
//        cli.readGenerateApi();
//        verify(controllerMock,times(1)).generateApi();
//    }
//
//    @Test
//    public void readingNotGenerateApi() throws IOException {
//        CLI spycli = Mockito.spy(cli);
//        when(bufferedReaderMock.readLine()).thenReturn("n");
//        spycli.readGenerateApi();
//
//        verify(spycli).askModifyApi();
//    }
//
//    @Test
//    public void testAskingModifyApi() throws IOException {
//        CLI spycli = Mockito.spy(cli);
//        PrintStream out = mock(PrintStream.class);
//        System.setOut(out);
//        when(bufferedReaderMock.readLine()).thenReturn("n");
//        spycli.askModifyApi();
//        verify(out).println("Do you want to modify an API? Type y or n.");
//        verify(spycli).readModifyApi();
//    }
//
//    @Test
//    public void readingModifyApi() throws IOException {
//        CLI spycli = Mockito.spy(cli);
//        PrintStream out = mock(PrintStream.class);
//        System.setOut(out);
//        when(bufferedReaderMock.readLine()).thenReturn("y","1","nomebal","nomepla","y");
//        spycli.readModifyApi();
//        verify(out).println("Type the ID of the chosen API");
//        verify(spycli).askBal();
//        verify(spycli).readBal();
//        verify(spycli).askPla();
//        verify(spycli).readPla();
//        verify(controllerMock,times(1)).modifyApi(any(Integer.class),any(String.class),any(String.class));
//        verify(spycli).askGenerateApi();
//        verify(spycli).readGenerateApi();
//    }
//
//    @Test
//    public void testingShowOutput(){
//        cli.showOutput();
//        verify(dataPresenterMock).getStringToShow();
//    }
//
//    @Test
//    public void gettingCurrentAnswer(){
//        assertNotNull(cli.getCurrentAnswer());
//    }
//
//    @Test
//    public void testingUpdate(){
//        CLI spycli = Mockito.spy(cli);
//        spycli.update();
//        verify(spycli).showOutput();
//    }
//
//
//}
