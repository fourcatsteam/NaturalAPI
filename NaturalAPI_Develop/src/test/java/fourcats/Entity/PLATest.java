package fourcats.Entity;

import fourcats.entity.PLA;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = fourcats.AppConfig.class)
public class PLATest {
    PLA pla;

    @Before
    public void before(){ pla = new PLA(".java\n" +
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
            "}");}

    @Test
    public void testConstructor(){
        assertTrue(pla!=null);
    }

    @Test
    public void testExtensionFromPLA(){
        assertEquals(".java",pla.getExtension());
    }

    @Test
    public void testTextFromPLA(){
        assertEquals("public class \"group_action\" {\n" +
                "\n" +
                "\tpublic \"action_type\" \"action_name\" (\"object_type\" \"object_name\"){\n" +
                "\n" +
                "\t}\n" +
                "}",pla.getText());
    }

    @Test
    public void testSettingTextPLA(){
        pla.setText("another class");
        assertEquals("another class",pla.getText());
    }

    @Test
    public void getCustomClassCorrectly(){
        assertEquals("public class \"custom_class\" {\n" +
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
                "}",pla.getCustomClass());
    }

    @Test
    public void getCustomBodyCorrectly(){
        assertEquals("\t\n" +
                "\tprivate \"attribute_type\" \"attribute_name\";\n" +
                "\n" +
                "\tpublic void set\"attribute_name\"(\"attribute_type\" \"attribute_name\") {\n" +
                "\t\tthis.\"attribute_name\" = \"attribute_name\";\n" +
                "\t}\n" +
                "\n" +
                "\tpublic \"attribute_type\" get\"attribute_name\"() {\n" +
                "\t\treturn \"attribute_name\";\n" +
                "\t}\n" +
                "\n",pla.getCustomBody());
    }

    @Test
    public void getTestClassCorrectly(){
        assertEquals("@Test\n" +
                "public class \"test_stub\" {\n" +
                "\n" +
                "\t\"group_action\" object = new \"group_action\"();\n" +
                "\tobject.\"action_name\"();\n" +
                "}",pla.getTestClass());
    }



}
