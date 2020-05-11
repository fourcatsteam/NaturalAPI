package integration.suggestAPI;

import fourcats.entity.*;
import fourcats.frameworks.Repository;
import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceadapters.DataPresenterGui;
import fourcats.usecaseinteractor.GenerateApi;
import fourcats.usecaseinteractor.SuggestApi;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SuggestAPIAdaptersIT {

    @Mock
    RepositoryAccess repo = Mockito.mock(RepositoryAccess.class);
    @Mock
    BalAnalyzer analyzerMock = Mockito.mock(BalAnalyzer.class);

    DataPresenterGui data;

    @InjectMocks
    SuggestApi suggestAPI;

    @Before
    public void before(){
        data = new DataPresenterGui();
        suggestAPI = new SuggestApi(analyzerMock,repo,data);
    }

    @Test
    public void verifyLinkFromDataPresenterGuiAndSuggestAPI() throws Exception {
        String filenamebal = ".\\BAL\\TestFiles\\balAtm.json";
        String filenamepla = ".\\PLA\\javaClassPLA.txt";

        List<Actor> l = new LinkedList<>();
        Actor act = new Actor("Actor1");
        Type tipo = new Type("tipo");
        tipo.addAttribute("nomeattributo1","tipoattributo1");
        tipo.addAttribute("nomeattributo2","tipoattributo2");
        Action action = new Action("gioca",tipo,"scenario","step");

        ObjectParam op = new ObjectParam("object","tipoObj");
        op.getType().addAttribute("attribute","attributetype");

        action.addObjectParam(op);
        act.addAction(action);
        l.add(act);
        BAL bal = new BAL(l);
        when(analyzerMock.getBAL()).thenReturn(bal);

        when(repo.isCoupleBalPlaPresent(filenamebal,filenamebal)).thenReturn(false);

        when(repo.loadPLA(filenamepla)).thenReturn(".java\n" +
                "public class \"group_action\" {\n" +
                "\n" +
                "\tpublic \"action_type\" \"action_name\" (\"object_type\" \"object_name\"){\n" +
                "\n" +
                "\t}\n" +
                "}\n" +
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
                "}\n" +
                "test class\n" +
                "@Test\n" +
                "public class \"test_stub\" {\n" +
                "\n" +
                "\t\"group_action\" object = new \"group_action\"();\n" +
                "\tobject.\"action_name\"();\n" +
                "}");
        when(repo.openFile(any(String.class))).thenReturn(new File(""));
        API api1 = new API();
        api1.setFilename("api1");
        api1.setText("APIIII");

        Map<Integer, API> mappa = new HashMap<>();
        mappa.put(1,api1);

        when(repo.getApiMap()).thenReturn(mappa);

        suggestAPI.create(filenamebal,filenamepla);

        verify(repo,times(1)).openFile(any(String.class));
        verify(repo,times(4)).addApi(any(API.class));


        assertEquals(mappa.get(1).getText(),data.getStringToShow());
        assertEquals(mappa.get(1).getFilename(),data.getComboToShow());


    }

}
