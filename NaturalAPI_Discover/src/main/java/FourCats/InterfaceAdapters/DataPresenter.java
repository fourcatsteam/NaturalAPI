package FourCats.InterfaceAdapters;

import FourCats.Observer.Subject;
import FourCats.Port.AddDocumentsOutputPort;
import FourCats.Port.CreateBdlOutputPort;
import FourCats.Port.RemoveDocumenetsOutputPort;

public class DataPresenter extends Subject implements CreateBdlOutputPort, AddDocumentsOutputPort, RemoveDocumenetsOutputPort {
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
}
