package fourcats.frameworks;

import fourcats.entities.Bal;
import fourcats.interfaceaccess.BalAnalyzer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class BalAnalyzerImplementation implements BalAnalyzer {


    @Override
    public String createJsonFromBAL(Bal bal) throws IOException {
        //Creating the ObjectMapper object
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //Converting the Object to JSONString and saving it on file
        String balJson = "";
        try {
            balJson = mapper.writeValueAsString(bal);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return balJson;
    }
}
