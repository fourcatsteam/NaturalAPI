package fourcats.interfaceaccess;

import fourcats.entities.Bal;
import java.io.IOException;

public interface BalAnalyzer {
    String createJsonFromBAL(Bal bal) throws IOException;
}
