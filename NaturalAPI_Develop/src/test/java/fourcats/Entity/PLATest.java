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
    public void before(){ pla = new PLA(".java\npublic class Classe{ }\ncustom class\npublic class\n{\n}");}

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
        assertEquals("public class Classe{ }",pla.getText());
    }

    @Test
    public void testSettingTextPLA(){
        pla.setText("another class");
        assertEquals("another class",pla.getText());
    }



}
