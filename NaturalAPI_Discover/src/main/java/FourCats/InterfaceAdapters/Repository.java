package FourCats.InterfaceAdapters;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.PersistentMemoryAccess;
import FourCats.InterfaceAccess.RepositoryAccess;

import java.io.IOException;
import java.util.LinkedList;

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
        try{
            return this.memoryAccess.loadBdl(targetBdl);
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateBdl(Bdl bdl) {
        try{
            this.memoryAccess.saveBdl(bdl);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Document readDocument(String title) {
        //if the Document has already been loaded it's held by the DataKeeper
        return this.memoryAccess.loadDocument(title);
    }

    @Override
    public LinkedList<String> readAssociation(String referringBdl){
        try {
            return this.memoryAccess.loadAssociation(referringBdl);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addAssociation(String referringBdl, LinkedList<String> associateDocuments) {
        try{
            this.memoryAccess.addAssociation(referringBdl, associateDocuments);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeAssociation(String referringBdl, LinkedList<String> docToRemove) {
        try{
            this.memoryAccess.removeAssociation(referringBdl, docToRemove);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateAssociation(String referringBdl, LinkedList<String> associateDocuments) {
        try{
            this.memoryAccess.saveAssociation(referringBdl, associateDocuments);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
