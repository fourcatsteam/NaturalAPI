package fourcats.interfaceaccess;

import fourcats.entity.BAL;
import com.fasterxml.jackson.databind.JsonNode;


import java.io.File;

public interface BalAnalyzer {

    void setBalFile(File file);
    BAL getBAL();
}
