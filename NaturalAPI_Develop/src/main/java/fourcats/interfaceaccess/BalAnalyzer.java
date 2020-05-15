package fourcats.interfaceaccess;

import fourcats.entity.BAL;

import java.io.File;
import java.io.IOException;

public interface BalAnalyzer {

    void setBalFile(File file) throws IOException;
    BAL getBAL();
}
