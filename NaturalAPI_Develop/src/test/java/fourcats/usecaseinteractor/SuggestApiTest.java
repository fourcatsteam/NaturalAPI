package fourcats.usecaseinteractor;

import fourcats.entity.*;
import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.ApiOutputPort;
import org.junit.Test;
import org.mockito.*;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

public class SuggestApiTest {

    BalAnalyzer analyzerMock = Mockito.mock(BalAnalyzer.class);
    RepositoryAccess repositoryMock = Mockito.mock(RepositoryAccess.class);
    ApiOutputPort outputMock = Mockito.mock(ApiOutputPort.class);

    @InjectMocks
    SuggestApi suggestAPI = new SuggestApi(analyzerMock, repositoryMock, outputMock);

    @Test
    public void SuggestAPITestCreationFromWrongInput() {

        try{
            suggestAPI.create("", "");
            fail();
        }
        catch (Throwable e)
        {
            assertTrue(true);
        }

    }

    @Test
    public void correctlyCreatingSuggestApi(){
        assertNotNull(suggestAPI);
    }

 /*   @Test
    public void correctlysuggestionApiCreated(){
        String filebal = "bal.json";
        String filepla = "pla";

        List<Actor> l = new LinkedList<>();
        Actor act = new Actor("Actor1");
        Action action = new Action("gioca","tipo");

        ObjectParam op = new ObjectParam("object","tipoObj");
        op.getType().addAttribute("attribute","attributetype");

        action.addObjectParam(op);
        act.addAction(action);
        l.add(act);
        BAL bal = new BAL(l);

        when(analyzerMock.getBAL()).thenReturn(bal);
        when(repositoryMock.loadPLA(filepla)).thenReturn(".java\n" +
                "public class \"group_action\" {\n" +
                "\n" +
                "\tpublic \"action_type\" \"action_name\" (\"object_type\" \"object_name\"){\n" +
                "\n" +
                "\t}\n" +
                "}\n" +
                "\n" +
                "custom class\n" +
                "public class \"custom_class\" {\n" +
                "\t\n" +
                "\tprivate \"attribute_type\" \"attribute_name\";\n" +
                "\n" +
                "\tpublic void set\"attribute_name\"(\"attribute_type\" \"attribute_name\") {\n" +
                "\t\tthis.\"attribute_name\" = \"attribute_name\";\n" +
                "\t}\n" +
                "\n" +
                "\tpublic \"attribute_type\" get\"attribute_name\"() {\n" +
                "\t\treturn \"attribute_name\";\n" +
                "\t}\n" +
                "\n" +
                "}");
        when(repositoryMock.openFile(any(String.class))).thenReturn(new File(""));

        suggestAPI.create(filebal,filepla);

        verify(repositoryMock,times(1)).openFile(any(String.class));
        verify(repositoryMock).addApi(any(API.class));
        verify(outputMock).showOutput(anyMap());
    }*/

}