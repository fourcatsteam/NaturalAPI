package FourCats.InterfaceAccess;

import FourCats.Entities.BAL;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;

public interface BalAnalyzer {

    void setBalFile(File file);
    JsonNode getJsonNode();
    BalAnalyzer get(String s);
    BAL getBAL();
    String createJsonFromBAL(BAL bal) throws IOException;
}
