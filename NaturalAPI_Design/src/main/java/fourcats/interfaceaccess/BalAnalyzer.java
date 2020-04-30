package fourcats.interfaceaccess;

import fourcats.entities.BAL;
import java.io.IOException;

public interface BalAnalyzer {
    String createJsonFromBAL(BAL bal) throws IOException;
}
