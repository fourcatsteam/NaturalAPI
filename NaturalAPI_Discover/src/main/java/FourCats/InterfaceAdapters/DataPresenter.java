package FourCats.InterfaceAdapters;

import FourCats.Entities.Bdl;
import FourCats.Observer.Subject;
import FourCats.Port.AddDocumentsOutputPort;
import FourCats.Port.CreateBdlOutputPort;
import FourCats.Port.RemoveDocumentsOutputPort;
import FourCats.Port.ViewBdlOutputPort;

public class DataPresenter extends Subject implements CreateBdlOutputPort, AddDocumentsOutputPort, RemoveDocumentsOutputPort, ViewBdlOutputPort {
    private String message;
    private String bdlNouns;
    private String bdlVerbs;
    private String bdlPredicates;

    public String getMessage() {return message;}
    public String getBdlNouns() {return bdlNouns;}
    public String getBdlVerbs() {return bdlVerbs;}
    public String getBdlPredicates() {return bdlPredicates;}

    private void clearData() {
        this.message = "";
        this.bdlNouns = "";
        this.bdlVerbs = "";
        this.bdlPredicates = "";
    }

    @Override
    public void showCreateBdlOutput() {
        this.clearData();
        message = "BDL generata! Puoi trovare i file csv all'interno della cartella BDL";
        notifyObservers();
    }

    @Override
    public void showError(String msg) {
        this.clearData();
        message = "Error: "+msg;
        notifyObservers();
    }

    @Override
    public void showWarning(String msg) {
        this.clearData();
        message = "Warning: "+msg;
        notifyObservers();
    }

    @Override
    public void showAddDocumentsOutput() {
        this.clearData();
        message = "Documenti aggiunti al BDL con successo!";
        notifyObservers();
    }

    @Override
    public void showRemoveDocumentOutputPort() {
        this.clearData();
        message = "I Documenti da te selezionati sono stati rimossi con successo";
        notifyObservers();
    }

    @Override
    public void showViewBdlOutput(Bdl b) {
        this.clearData();
        this.bdlNouns = b.nounsToString();
        this.bdlVerbs = b.verbsToString();
        this.bdlPredicates = b.predicatesToString();
        notifyObservers();
    }
}
