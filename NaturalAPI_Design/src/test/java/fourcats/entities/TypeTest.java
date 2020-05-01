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
        assertEquals(type.getName(), "Name");
        assertEquals(type.getAttributes(), map);
    }

    @Test
    public void TypeSetNameCorrectly() {
        type.setName("NewName");
        assertEquals(type.getName(), "NewName");
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
        assertEquals(type.toString(), "Name");
    }

}