package fourcats.interfaceAdapters;

import fourcats.interfaceadapters.Controller;
import fourcats.port.ApiInputPort;
import fourcats.port.GenerateInputPort;
import fourcats.port.ModifyInputPort;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    @Mock
    ApiInputPort apiMock;

    @Mock
    GenerateInputPort genMock;

    @Mock
    ModifyInputPort modMock;

    @InjectMocks
    Controller controller;

    @Test
    public void createControllerCorrectly(){
        assertNotNull(controller);
    }

    @Test
    public void testCreateApiSuggestion(){
        String fileNameBal = "nameBal";
        String fileNamePla = "namePla";
        controller.createApiSuggestion(fileNameBal,fileNamePla);

        verify(apiMock).create(any(String.class),any(String.class));
    }

    @Test
    public void testGenerateApi(){
        controller.generateApi();

        verify(genMock).generate();
    }

    @Test
    public void testModifyApi(){
        int i = 1;
        String fileNameBal = "nameBal";
        String fileNamePla = "namePla";
        controller.modifyApi(1,fileNameBal,fileNamePla);

        verify(modMock).modify(1,fileNameBal,fileNamePla);
    }




}
