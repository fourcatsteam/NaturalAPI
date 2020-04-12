package FourCats.InterfaceAccess;
import FourCats.Entities.Bdl;
import FourCats.Entities.Document;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public interface RepositoryAccess {
    //bdl CRUD methods
    Bdl createBdl(String name);
    Bdl readBdl(String targetBdl);
    void updateBdl(Bdl bdl);

    //document CRUD methods
    Document readDocument(String title);

    //association CRUD methoods
    LinkedList<String> readAssociation(String referringBdl);
    void addAssociation(String referringBdl, LinkedList<String> docToAdd);
    void removeAssociation(String referringBdl, LinkedList<String> docToRemove);
    void updateAssociation(String referringBdl, LinkedList<String> associateDocuments);


}
