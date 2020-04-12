package FourCats.InterfaceAccess;
import FourCats.Entities.Bdl;
import FourCats.Entities.Document;

import java.util.LinkedList;
import java.util.List;

public interface RepositoryAccess {
    //bdl CRUD methods
    Bdl createBdl(String name);
    Bdl readBdl(String targetBdl);
    void updateBdl(Bdl bdl);

    //document CRUD methods
    Document readDocument(String title);

    //association CRUD methoods
    LinkedList<String> readAssociation(String referringBdl);
    void addAssociation(String referringBdl, List<String> docToAdd);
    void removeAssociation(String referringBdl, List<String> docToRemove);
    void updateAssociation(String referringBdl, List<String> associateDocuments);


}
