package fourcats.Entity;

import fourcats.entity.Action;
import fourcats.entity.ObjectParam;
import fourcats.entity.Type;
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
    public void createActionWithNameAndTypeNotString(){
        Action act = new Action("NomeAction",new Type("Tipo particolare"));
        assertNotNull(act);
    }

    @Test
    public void createActionWithNameAndTypeNotStringAndScenarioAndStep(){
        Action act = new Action("NomeAction",new Type("Tipo particolare"),"scenario","step");
        assertNotNull(act);
    }

    @Test
    public void createActionWithNameAndTypeAndScenarioAndStep(){
        Action act = new Action("NomeAction","Tipo particolare","scenario","step");
        assertNotNull(act);
    }

    @Test
    public void createActionWithNameAndTypeNullAndScenarioAndStep(){
        Action act = new Action("NomeAction","","scenario","step");
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
        assertEquals("TYPE NAME (type nome)",act.toString());
    }
    @Test

    public void toStringActionWithTwoParametersCorrectly(){
        act.setName("NAME");
        act.setType("TYPE");
        ObjectParam o = new ObjectParam("nome","type");
        ObjectParam o2 = new ObjectParam("nome2","type2");
        act.addObjectParam(o);
        act.addObjectParam(o2);
        assertEquals("TYPE NAME (type nome, type2 nome2)",act.toString());
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

    @Test
    public void testAddObjectParamWithString(){
        act.addObjectParam("nome","tipo");
        assertEquals(1,act.getObjectParams().size());
    }

    @Test
    public void testRemoveObjectParamWithId(){
        ObjectParam obj = new ObjectParam("nome","tipo");
        act.addObjectParam(obj);
        act.removeObjectParam(act.getObjectParams().indexOf(obj));
        assertEquals(0,act.getObjectParams().size());
    }

    @Test
    public void setTypeCorrectly(){
        Type t1 = new Type("tipo1");
        act.setType(t1);
        assertEquals(t1,act.getType());
    }

    @Test
    public void getScenarioCorrectly(){
        Action act = new Action("NomeAction","","scenario","step");
        assertEquals("scenario",act.getScenario());
    }

    @Test
    public void getStepCorrectly(){
        Action act = new Action("NomeAction","","scenario","step");
        assertEquals("step",act.getStep());
    }


}
