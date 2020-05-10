package fourcats.Entity;

import fourcats.entity.API;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

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
        assertNotNull(api);
    }

    @Test
    public void getFileNameCorrectly(){
        assertEquals("",api.getFilename());
    }

    @Test
    public void getTextCorrectly(){
        assertEquals("",api.getText());
    }

    @Test
    public void setFileNameCorrectly(){
        api.setFilename("filename");
        assertEquals("filename",api.getFilename());
    }

    @Test
    public void setTextCorrectly(){
        api.setText("text");
        assertEquals("text",api.getText());
    }
}
