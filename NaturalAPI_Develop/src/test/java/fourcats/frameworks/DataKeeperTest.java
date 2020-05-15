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
        API api = new API();
        api.setFilename("filename");
        api.setText("text");
        data.addApiWithId(1,api);
        data.updateApi("text","ciao");

       assertEquals("ciao",data.getApi(1).getText());
    }

    @Test
    public void updatingAPIWithSame(){
        API api = new API();
        api.setFilename("filename");
        api.setText("ciao");
        data.addApiWithId(1,api);
        data.updateApi("text","ciao");

        assertEquals("ciao",data.getApi(1).getText());
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
        data.deleteApi(1);
        assertEquals(0,data.getSize());
    }

    @Test
    public void isCoupleBalPlaPresentTest(){
        data.addCoupleBalPla("bal","pla");
        assertTrue(data.isCoupleBalPlaPresent("bal","pla"));
    }

    @Test
    public void isCoupleBalPlaNOTPresentTest(){
        data.addCoupleBalPla("bal","pla");
        assertFalse(data.isCoupleBalPlaPresent("bal1","pla1"));
    }

    @Test
    public void addCoupleBlaPlaTest(){
        data.addCoupleBalPla("bal","pla");
        assertTrue(data.isCoupleBalPlaPresent("bal","pla"));
    }

    @Test
    public void addCoupleBlaPlaTestWithSameBal(){
        data.addCoupleBalPla("bal","pla");
        data.addCoupleBalPla("bal","pla2");
        assertTrue(data.isCoupleBalPlaPresent("bal","pla2"));
    }

    @Test
    public void isAPIPresentTest(){
        API api = new API();
        api.setText("Mia api");
        data.addApi(api);
        assertTrue(data.isThisApiPresent(api));
    }

    @Test
    public void isAPINotPresentTest(){
        API api = new API();
        api.setText("Mia api");
        assertFalse(data.isThisApiPresent(api));
    }

    @Test
    public void isClassNamePresentTest(){
        API api = new API();
        api.setFilename("Mia api");
        data.addApi(api);
        assertTrue(data.isThisClassNamePresent(api));
    }

    @Test
    public void isClassNameNotPresentTest(){
        API api = new API();
        api.setFilename("Mia api");
        assertFalse(data.isThisClassNamePresent(api));
    }






}
