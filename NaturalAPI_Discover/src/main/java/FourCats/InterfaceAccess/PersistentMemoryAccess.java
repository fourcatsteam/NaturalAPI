package FourCats.InterfaceAccess;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public interface PersistentMemoryAccess {
    Document loadDocument(String documentTitle);
    Bdl loadBdl(String bdlName) throws IOException;
    void saveBdl(Bdl bdl) throws IOException;
    LinkedList<String> loadAssociation(String bdlName) throws IOException;
    void addAssociation(String bdlName, LinkedList<String> titleList) throws IOException;
    void removeAssociation(String referringBdl, LinkedList<String> docToRemove) throws  IOException;
    void saveAssociation(String bdlName, LinkedList<String> titleList) throws IOException;


}
