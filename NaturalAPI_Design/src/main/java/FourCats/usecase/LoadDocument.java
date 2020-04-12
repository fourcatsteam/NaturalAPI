package FourCats.usecase;

import FourCats.entity.Document;
import FourCats.entity.Documents;
import FourCats.interfaceAccess.DirectoryAccess;

import java.io.FileNotFoundException;
import java.util.LinkedList;

/* Use case Interactor per il caricamento dei documenti dalla repository  */
public class LoadDocument{

    //Questo use case contine un riferimento alla lista dei documenti caricati dall'utente
    //e un riferimento all'interfaccia DirectoryAccess, per il metodo read
    private Documents list;
    private DirectoryAccess da;

    public LoadDocument(DirectoryAccess r){
        list = new Documents();
        da = r;
    }

    //Questa funzione richiama il metodo read() di DirectoryAccess
    //E per ogni Document da lui creato, viene aggiunto alla lista
    public void loadDocs() throws FileNotFoundException {
        for (Document dc:da.read()) {
            list.add(dc);
        };
    }

    //Semplice stampa dei contenuto dei vari documenti CARICATI
    public String readDocument(){
        return list.print();
    }

    public Documents getDocuments(){
        return list;
    }

    public LinkedList<Document> getList(){
        return list.getList();
    }


}
