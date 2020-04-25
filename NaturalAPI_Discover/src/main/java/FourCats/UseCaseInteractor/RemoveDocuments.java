package FourCats.UseCaseInteractor;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.InterfaceAccess.TextAnalyzer;
import FourCats.Port.RemoveDocumenetsOutputPort;
import FourCats.Port.RemoveDocumentsInputPort;
import FourCats.UseCaseUtilities.AnalyzeDocument;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class RemoveDocuments implements RemoveDocumentsInputPort {

    private RepositoryAccess repository;
    private AnalyzeDocument documentAnalyzer;
    private RemoveDocumenetsOutputPort outputPort;

    public RemoveDocuments(RepositoryAccess repo, AnalyzeDocument analyzer, RemoveDocumenetsOutputPort port) {
        this.repository = repo;
        this.documentAnalyzer = analyzer;
        this.outputPort = port;
    }

    @Override
    public void remove(String targetBdl, List<String> docTitles) {
        //retrieve BDL
        Bdl bdl = this.repository.readBdl(targetBdl);

        //retrieve association
        LinkedList<String> association = this.repository.readAssociation(targetBdl);

        //retrieve Documents
        LinkedList<Document> documents = new LinkedList<>();
        for (String title: docTitles) {
            Document doc = this.repository.readDocument(title);
            if(doc != null) {   //Documento non presente
                documents.add(doc);
            }
        }

        //remove Documents analysis from BDL
        for (Document document: documents) {
            this.documentAnalyzer.removeDocumentFromBdl(bdl, document);
        }

        if(association!=null) {
            association.removeAll(docTitles);
        }
        //update Bdl in the repository

        repository.updateBdl(bdl);
        repository.updateAssociation(bdl.getName(),association);
        //send results to output device
        outputPort.showRemoveDocumentOutputPort("I Documenti da te selezionati sono stati rimossi con successo");
    }
}
