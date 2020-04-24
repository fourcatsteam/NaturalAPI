package FourCats.InterfaceAdapters;

import FourCats.Entities.Bdl;
import FourCats.Observer.Subject;
import FourCats.Port.AddDocumentsOutputPort;
import FourCats.Port.CreateBdlOutputPort;
import FourCats.Port.RemoveDocumenetsOutputPort;
import FourCats.Port.ViewBdlOutputPort;

public class DataPresenter extends Subject implements CreateBdlOutputPort, AddDocumentsOutputPort, RemoveDocumenetsOutputPort, ViewBdlOutputPort {
    private String dataToView;

    public String getData(){
        return dataToView;
    }

    @Override
    public void showCreateBdlOuput(String s) {
        dataToView = s;
        notifyObservers();
    }

    @Override
    public void showAddDocumentsOutput(String s) {
        dataToView = s;
        notifyObservers();
    }

    @Override
    public void showRemoveDocumentOutputPort(String s) {
        dataToView = s;
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
