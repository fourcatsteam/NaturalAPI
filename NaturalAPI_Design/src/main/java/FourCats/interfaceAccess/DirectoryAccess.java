package FourCats.interfaceAccess;
import FourCats.entity.Document;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public interface DirectoryAccess {
    public LinkedList<Document> read() throws FileNotFoundException;
}
