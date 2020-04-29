package fourcats.frameworks;

import fourcats.entity.API;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataKeeperTest {

    DataKeeper data;

    @Before
    public void before(){
        data = new DataKeeper();
    }

    @Test
    public void createDatakeeperCorrectly(){
        assertNotNull(data);
    }

    @Test
    public void addingAPIWithIdCorrectlyWithoutDuplicate(){
        API api1= new API();
        data.addApiWithId(1,api1);
        data.addApiWithId(1,api1);
        assertEquals(1,data.getSize());

    }

    @Test
    public void addingAPIWithoutIdCorrectly(){
        API api1 = new API();
        data.addApi(api1);
        assertEquals(1,data.getSize());
    }

    @Test
    public void gettingAPIWithId(){
        API api1 = new API();
        data.addApiWithId(1,api1);
        assertEquals(api1,data.getApi(1));
    }

    @Test
    public void gettingAPIMapCorrectly() {
        assertNotNull(data.getApiMap());
    }

    @Test
    public void gettingSizeOfAPIMap(){
        data.addApi(new API());
        assertEquals(1,data.getSize());
    }

    @Test
    public void updatingAPICorrectly(){
        API apis = new API();
        apis.addApi("api1","a");

        data.addApiWithId(1,apis);
        data.updateApi("a","ciao");

        assertEquals("ciao",data.getApi(1).getListApi().get("api1"));
    }

    @Test
    public void removingAPIPresent(){
        API api = new API();
        data.addApiWithId(1,api);
        data.deleteApi(1);

        assertEquals(0,data.getSize());
    }

    @Test
    public void removingAPINotPresent(){
        API api = new API();
        data.deleteApi(1);
        assertEquals(0,data.getSize());
    }





}
