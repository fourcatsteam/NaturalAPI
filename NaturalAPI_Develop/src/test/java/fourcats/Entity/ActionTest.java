package fourcats.Entity;

import fourcats.entity.Action;
import fourcats.entity.ObjectParam;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ActionTest {

    Action act;

    @Before
    public void before(){
        act = new Action("name","type");
    }

    @Test
    public void createActionWithNameAndType(){
        Action act = new Action("NomeAction","TipoAction");
        assertNotNull(act);
    }

    @Test
    public void createActionWithNameAndNullType(){
        Action act = new Action("NomeAction","");
        assertNotNull(act);
    }

    @Test
    public void addObjectParamCorrectly(){
        ObjectParam o = new ObjectParam("nome","Tipo");
        act.addObjectParam(o);
        assertEquals(1,act.getObjectParams().size());
    }

    @Test
    public void toStringActionCorrectly(){
        act.setName("NAME");
        act.setType("TYPE");
        ObjectParam o = new ObjectParam("nome","type");
        act.addObjectParam(o);
        assertEquals("TYPE NAME(type nome)",act.toString());
    }
    @Test

    public void toStringActionWithTwoParametersCorrectly(){
        act.setName("NAME");
        act.setType("TYPE");
        ObjectParam o = new ObjectParam("nome","type");
        ObjectParam o2 = new ObjectParam("nome2","type2");
        act.addObjectParam(o);
        act.addObjectParam(o2);
        assertEquals("TYPE NAME(type nome, type2 nome2)",act.toString());
    }

    @Test
    public void testGettingNameCorrectly(){
        assertEquals("name",act.getName());
    }

    @Test
    public void testAddObjectName(){
        act.addObjectParam(new ObjectParam("nome","tipo"));
        assertEquals(1,act.getObjectParams().size());
    }

    @Test
    public void testGettingTypeNameCorrectly(){
         assertEquals("type",act.getType().getName());
    }




}
