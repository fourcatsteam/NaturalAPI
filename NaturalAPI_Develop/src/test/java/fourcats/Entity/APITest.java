package fourcats.Entity;

import fourcats.entity.API;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = fourcats.AppConfig.class)
public class APITest {
    API api = null;

    @Before
    public void before() {
        api = new API();
    }

    @Test
    public void testConstructor() {
        assertTrue(api!=null);
    }

    @Test
    public void testAddApi(){
        api.addApi("nomefile","api");
        assertEquals(1,api.getListApi().size());
    }
}
