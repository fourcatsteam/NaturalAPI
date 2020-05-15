package fourcats.entities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TypeTest {

    Type type;

    @Mock
    Map<String, String> map;

    @Before public void CreateType() {
        type = new Type("Name");
    }

    @Test
    public void TypeCreateFromMap() {
        type = new Type("Name", map);
        assertEquals("Name",type.getName() );
        assertEquals(map, type.getAttributes());
    }

    @Test
    public void TypeSetNameCorrectly() {
        type.setName("NewName");
        assertEquals("NewName",type.getName());
    }

    @Test
    public void TypeAddAttributesNotEmpty() {
        type.addAttribute("Actor", "Astor");
        assertTrue(type.getAttributes().containsKey("Actor"));
        assertTrue(type.getAttributes().containsValue("Astor"));
    }

    @Test
    public void TypeAddAttributesEmpty() {
        type.addAttribute("", "");
        assertTrue(type.getAttributes().isEmpty());
    }

    @Test
    public void TypeToString() {
        assertEquals("Name",type.toString());
    }

}