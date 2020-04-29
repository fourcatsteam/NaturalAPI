package fourcats.Entity;

import fourcats.entity.ObjectParam;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectParamTest {

    private ObjectParam objectParam;

    @Before
    public void CreateValidObjectParam() {
        objectParam = new ObjectParam("Name", "Type");
    }

    @Test
    public void EmptyObjectParamConstructorCreation(){
        ObjectParam correct = new ObjectParam();
        assertNotNull(correct);
    }

    @Test
    public void OneParamObjectParamConstructorCreation(){
        ObjectParam correct = new ObjectParam("objectName");
        assertNotNull(correct);
    }


    @Test
    public void EmptyObjectParamCorrectCreation() {
        ObjectParam correct = new ObjectParam("", "");
        assertNotNull(correct);
    }

    @Test
    public void ObjectParamCorrectCreationWthName() {
        ObjectParam correct = new ObjectParam("correct", "object");
        assertNotNull(correct);
    }

    @Test
    public void ObjectParamSetNameCorrectly() {
        objectParam.setName("NewName");
        assertEquals("NewName", objectParam.getName());
    }

    @Test
    public void ObjectParamGetTypeCorrectly() {
        assertEquals("Type", objectParam.getType());
    }

    @Test
    public void ObjectParamSetTypeCorrectly() {
        objectParam.setType("newType");
        assertEquals("newType", objectParam.getType());
    }

    @Test
    public void ObjectParamSetRequiredCorrectly() {
        objectParam.setRequired(true);
        assertTrue(objectParam.isRequired());
    }

    @Test
    public void ObjectParamConvertedToStringCorrectly() {
        assertEquals("Name",objectParam.toString());
    }

}
