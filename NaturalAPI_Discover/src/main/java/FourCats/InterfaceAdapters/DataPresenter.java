package FourCats.InterfaceAdapters;

import FourCats.Entities.Bdl;
import FourCats.Observer.Subject;
import FourCats.Port.AddDocumentsOutputPort;
import FourCats.Port.CreateBdlOutputPort;
import FourCats.Port.RemoveDocumentsOutputPort;
import FourCats.Port.ViewBdlOutputPort;

public class DataPresenter extends Subject implements CreateBdlOutputPort, AddDocumentsOutputPort, RemoveDocumentsOutputPort, ViewBdlOutputPort {
    private String dataToView;
    private String message;

    public String getData(){
        return dataToView;
    }

    public String getMessage() {return message;}

    @Override
    public void showCreateBdlOutput() {
        message = "BDL generata! Puoi trovare i file csv all'interno della cartella BDL";
        dataToView = "";
        notifyObservers();
    }

    @Override
    public void showError(String msg) {
        message = "Error: "+msg;
        notifyObservers();
    }

    @Override
    public void showWarning(String msg) {
        message = "Warning: "+msg;
        notifyObservers();
    }

    @Override
    public void showAddDocumentsOutput() {
        message = "Documenti aggiunti al BDL con successo!";
        dataToView = "";
        notifyObservers();
    }

    @Override
    public void showRemoveDocumentOutputPort() {
        message = "I Documenti da te selezionati sono stati rimossi con successo";
        dataToView = "";
        notifyObservers();
    }

    @Override
    public void showViewBdlOutput(Bdl b) {

        this.dataToView = b.nounsToString();
        notifyObservers();
        this.dataToView = b.verbsToString();
        notifyObservers();
        this.dataToView = b.predicatesToString();
        notifyObservers();
    }
}
