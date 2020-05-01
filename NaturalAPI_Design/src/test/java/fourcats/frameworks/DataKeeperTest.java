package fourcats.frameworks;

import fourcats.entities.Scenario;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DataKeeperTest {

    DataKeeper dataKeeper;

    @Mock
    Scenario scenario;

    @Before
    public void CreateDataKeeper() {
        dataKeeper = new DataKeeper();
    }

    @Test
    public void DataKeeperAddScenarioCorrectly() {
        dataKeeper.addScenarioToMap(scenario);
    }


}