package InterfaceAccess;

import Entity.BAL;
import com.fasterxml.jackson.databind.JsonNode;
import Frameworks.BalAnalyzerImplementation;

import java.io.File;

public interface BalAnalyzer {

    void setBalFile(File file);
    JsonNode getJsonNode();
    BalAnalyzer get(String s);
    BAL getBAL();
}
