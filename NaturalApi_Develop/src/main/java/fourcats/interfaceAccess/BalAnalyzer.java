package fourcats.interfaceAccess;

import fourcats.entity.BAL;
import com.fasterxml.jackson.databind.JsonNode;


import java.io.File;

public interface BalAnalyzer {

    void setBalFile(File file);
    JsonNode getJsonNode();
    BalAnalyzer get(String s);
    BAL getBAL();
}
