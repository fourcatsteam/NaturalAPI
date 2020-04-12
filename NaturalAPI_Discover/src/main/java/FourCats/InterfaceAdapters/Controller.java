package FourCats.InterfaceAdapters;

import FourCats.Port.AddDocumentsInputPort;
import FourCats.Port.CreateBdlInputPort;
import FourCats.Port.RemoveDocumentsInputPort;
import FourCats.UseCaseInteractor.AddDocuments;
import FourCats.UseCaseInteractor.CreateBdl;
import FourCats.UseCaseInteractor.RemoveDocuments;

import java.io.IOException;
import java.util.LinkedList;

public class Controller{

    private CreateBdlInputPort createBdl;
    private AddDocumentsInputPort addDocuments;
    private RemoveDocumentsInputPort removeDocuments;

    public Controller(CreateBdlInputPort c,AddDocumentsInputPort a,RemoveDocumentsInputPort r){
        this.createBdl = c;
        this.addDocuments = a;
        this.removeDocuments = r;
    }

    public void createBdl(String nameBdl,LinkedList<String> titleList) throws IOException {
        try {
            createBdl.create(nameBdl,titleList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDocument(String nameBdl,LinkedList<String> docToAdd) throws IOException {
        addDocuments.add(nameBdl,docToAdd);
    }

    public void removeDocument(String nameBdl, LinkedList<String> docToRemove) throws IOException{
        removeDocuments.remove(nameBdl,docToRemove);
    }


}
