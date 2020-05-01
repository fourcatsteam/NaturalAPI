package FourCats.InterfaceAdapters;

import FourCats.DataStructure.TableRow;
import FourCats.Entities.Bdl;
import FourCats.Observer.Subject;
import FourCats.Port.AddDocumentsOutputPort;
import FourCats.Port.CreateBdlOutputPort;
import FourCats.Port.RemoveDocumentsOutputPort;
import FourCats.Port.ViewBdlOutputPort;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DataPresenter_GUI extends Subject implements CreateBdlOutputPort, AddDocumentsOutputPort, RemoveDocumentsOutputPort, ViewBdlOutputPort {
    private String message;
    private LinkedList<TableRow> bdlNouns;
    private LinkedList<TableRow> bdlVerbs;
    private LinkedList<TableRow> bdlPredicates;

    public DataPresenter_GUI() {
        this.message = "";
        this.bdlNouns = new LinkedList<>();
        this.bdlVerbs = new LinkedList<>();
        this.bdlPredicates = new LinkedList<>();
    }

    public String getMessage() {return message;}
    public List<TableRow> getBdlNouns() {return bdlNouns;}
    public List<TableRow> getBdlVerbs() {return bdlVerbs;}
    public List<TableRow> getBdlPredicates() {return bdlPredicates;}

    private void clearData() {
        this.message = "";
        this.bdlNouns.clear();
        this.bdlVerbs.clear();
        this.bdlPredicates.clear();
    }

    @Override
    public void showCreateBdlOutput(Bdl bdl) {
        this.clearData();
        convertBdl(bdl);
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
    public void showAddDocumentsOutput(Bdl bdl) {
        this.clearData();
        convertBdl(bdl);
        message = "Documenti aggiunti al BDL con successo!";
        notifyObservers();
    }

    @Override
    public void showRemoveDocumentOutputPort(Bdl bdl) {
        this.clearData();
        convertBdl(bdl);
        message = "I Documenti da te selezionati sono stati rimossi con successo";
        notifyObservers();
    }

    @Override
    public void showViewBdlOutput(Bdl bdl) {
        this.clearData();
        this.message = "BDL visualized as requested";
        convertBdl(bdl);
        notifyObservers();
    }

    private void convertBdl(Bdl bdl) {
        this.bdlNouns.addAll(bdl.getNouns().stream()
                .map(w->new TableRow(w.getWord(),w.getCount().toString()))
                .collect(Collectors.toList()));
        this.bdlVerbs.addAll(bdl.getVerbs().stream()
                .map(w->new TableRow(w.getWord(),w.getCount().toString()))
                .collect(Collectors.toList()));
        this.bdlPredicates.addAll(bdl.getPredicates().stream()
                .map(w->new TableRow(w.getWord(),w.getCount().toString()))
                .collect(Collectors.toList()));
    }

}

