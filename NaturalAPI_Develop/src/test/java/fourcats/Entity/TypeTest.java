package fourcats.Entity;

import fourcats.entity.Type;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = fourcats.AppConfig.class)
public class TypeTest {
    Type type;

    @Test
    public void testConstructorStringCorrectly(){
        type = new Type("tipo");
        assertNotNull(type);
    }

    @Test
    public void testConstructorStringAndMapCorrectly(){
        type = new Type("tipo",null);
        assertNotNull(type);
    }

    @Test
    public void gettingNameCorrectly(){
        type = new Type("tipo");
        assertEquals("tipo",type.getName());
    }

    @Test
    public void settingNameCorrectly(){
        type = new Type("tipo");
        type.setName("tipo2");
        assertEquals("tipo2",type.getName());
    }

    @Test
    public void testGettingAttributesCorrectly(){
        type = new Type("tipo",new HashMap<>());
        assertNotNull(type.getAttributes());
    }

    @Test
    public void addingAttributeCorrectly(){
        type = new Type("tipo");
        type.addAttribute("NomeAttributo","TipoAttributo");
        assertEquals(1,type.getAttributes().size());
    }

    @Test
    public void testingToStringCorrectly(){
        type = new Type("tipo");
        assertEquals("tipo",type.toString());
    }

}
