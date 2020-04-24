package FourCats.InterfaceAccess;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public interface PersistentMemoryAccess {
    Document loadDocument(String documentTitle);
    Bdl loadBdl(String bdlName);
    boolean saveBdl(Bdl bdl);
    LinkedList<String> loadAssociation(String bdlName);
    boolean addAssociation(String bdlName, List<String> titleList);
    boolean removeAssociation(String referringBdl, List<String> docToRemove);
    boolean saveAssociation(String bdlName, List<String> titleList);


}
