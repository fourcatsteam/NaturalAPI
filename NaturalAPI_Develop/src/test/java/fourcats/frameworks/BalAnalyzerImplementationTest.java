package fourcats.frameworks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceadapters.Controller;
import fourcats.port.ModifyInputPort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BalAnalyzerImplementationTest {

    @Mock
    BalAnalyzer balAnalyzer;

    @InjectMocks
    BalAnalyzerImplementation analyzer;


    @Test
    public void createBalAnalyzerCorrectly(){
        assertNotNull(analyzer);
    }

    @Test
    public void correctlySettingBalFile(){
        String fileNameBal = "nameBal";
        File file = new File(fileNameBal);
        analyzer.setBalFile(file);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            assertEquals(balAnalyzer,objectMapper.readValue(file, BalAnalyzer.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void gettingBalCorrectly(){
        analyzer.setBalFile(new File("./BAL/TestFiles/balAtm.json"));
        assertNotNull(analyzer.getBAL());
    }



}
