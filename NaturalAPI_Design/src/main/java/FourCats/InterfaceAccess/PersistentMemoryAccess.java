package FourCats.InterfaceAccess;

import FourCats.Entities.Bdl;

import java.io.FileNotFoundException;
import java.io.IOException;


public interface PersistentMemoryAccess {
    Bdl loadBdl(String bdlName) throws IOException;

    String readFile(String fileName) throws FileNotFoundException;
}
