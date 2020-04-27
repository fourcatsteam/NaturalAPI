package fourcats.interfaceaccess;

import fourcats.entities.BAL;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;

public interface BalAnalyzer {
    String createJsonFromBAL(BAL bal) throws IOException;
}
