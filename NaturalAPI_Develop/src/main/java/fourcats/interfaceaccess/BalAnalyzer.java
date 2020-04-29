package fourcats.interfaceaccess;

import fourcats.entity.BAL;

import java.io.File;

public interface BalAnalyzer {

    void setBalFile(File file);
    BAL getBAL();
}
