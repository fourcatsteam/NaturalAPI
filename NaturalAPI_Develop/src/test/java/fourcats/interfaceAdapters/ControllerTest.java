package fourcats.interfaceAdapters;

import fourcats.interfaceadapters.Controller;
import fourcats.port.*;
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

    @Mock
    CreatePlaInputPort createPlaMock;

    @Mock
    ModifyPlaInputPort modifyPlaMock;

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
    public void testGenerateApi() throws Exception {

        controller.generateApi("path");

        verify(genMock).generate(any(String.class));
    }

    @Test
    public void testModifyApi(){
        int i = 1;
        String fileNameBal = "nameBal";
        String fileNamePla = "namePla";
        controller.modifyApi(1,2,fileNameBal,fileNamePla);

        verify(modMock).modify(1,2);
        verify(apiMock).create(fileNameBal,fileNamePla);
    }

    @Test
    public void testingModifyApiGui(){
        controller.modifyApiGui("","");
        verify(modMock).modifyGui(any(String.class),any(String.class));
    }

    @Test
    public void testingCreatePla(){
        controller.createPla("path","exts","pla");
        verify(createPlaMock).create("path","exts","pla");
    }

    @Test
    public void testLoadingPla() {
        controller.loadPlaToModify("filename");
        verify(modifyPlaMock).loadPlaToModify("filename");
    }

    @Test
    public void testModifyPla() {
        controller.modifyPla("filename","text");
        verify(modifyPlaMock).modify("filename","text");
    }





}
