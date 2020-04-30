package fourcats.gui;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;
import fourcats.view.CLI;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GuiTest {
    @Mock
    DataPresenter data;

    @Mock
    Controller controller;

    @InjectMocks
    Gui gui;

    @Before
    public void before(){
        gui = new Gui(controller,data);
    }

    @Test
    public void testingUpdateCallShowOuput(){
        Gui spygui = Mockito.spy(gui);
        spygui.update();
        verify(spygui).showOutput();
    }

    @Test
    public void testingShowOuputCallDataPresenter(){
        gui.showOutput();
        verify(data).getStringToShow();
    }


}
