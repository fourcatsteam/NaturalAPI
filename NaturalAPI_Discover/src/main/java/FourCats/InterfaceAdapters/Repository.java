package FourCats.InterfaceAdapters;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.PersistentMemoryAccess;
import FourCats.InterfaceAccess.RepositoryAccess;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Repository implements RepositoryAccess {

    private PersistentMemoryAccess memoryAccess;

    public Repository(PersistentMemoryAccess persistentMemory) {
        this.memoryAccess = persistentMemory;
    }

    @Override
    public Bdl createBdl(String name) {
        //create a new Bdl and store it in the DataKeeper
        return new Bdl(name);

    }

    @Override
    public Bdl readBdl(String targetBdl) {
        //load the Bdl and the association list from memory and store it in the DataKeeper
        return this.memoryAccess.loadBdl(targetBdl);
    }

    @Override
    public boolean updateBdl(Bdl bdl) {
        return this.memoryAccess.saveBdl(bdl);
    }

    @Override
    public Document readDocument(String title) {
        //if the Document has already been loaded it's held by the DataKeeper
        return this.memoryAccess.loadDocument(title);
    }

    @Override
    public LinkedList<String> readAssociation(String referringBdl){
        return this.memoryAccess.loadAssociation(referringBdl);
    }

    @Override
    public boolean updateAssociation(String referringBdl, List<String> associateDocuments) {
        return this.memoryAccess.saveAssociation(referringBdl, associateDocuments);
    }
}
