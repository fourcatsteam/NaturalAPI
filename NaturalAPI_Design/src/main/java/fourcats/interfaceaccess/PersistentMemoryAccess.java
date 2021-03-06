package fourcats.interfaceaccess;

import fourcats.entities.Bdl;

import java.io.FileNotFoundException;
import java.io.IOException;


public interface PersistentMemoryAccess {
    Bdl loadBdl(String bdlName) throws IOException;
    String readFile(String fileName) throws FileNotFoundException;
    void writeFile(String toWrite, String filename) throws IOException;
    Bdl getBdl(String[] name) throws IOException;
}
