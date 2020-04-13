package FourCats.InterfaceAccess;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public interface PersistentMemoryAccess {
    Document loadDocument(String documentTitle);
    Bdl loadBdl(String bdlName) throws IOException;
    void saveBdl(Bdl bdl) throws IOException;
    LinkedList<String> loadAssociation(String bdlName) throws IOException;
    void addAssociation(String bdlName, List<String> titleList) throws IOException;
    void removeAssociation(String referringBdl, List<String> docToRemove) throws  IOException;
    void saveAssociation(String bdlName, List<String> titleList) throws IOException;


}
