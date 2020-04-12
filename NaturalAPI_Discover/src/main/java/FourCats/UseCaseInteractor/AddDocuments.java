package FourCats.UseCaseInteractor;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.TextAnalyzer;
import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.Port.AddDocumentsInputPort;
import FourCats.Port.AddDocumentsOutputPort;
import FourCats.UseCaseUtilities.AnalyzeDocument;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class AddDocuments implements AddDocumentsInputPort {

    private RepositoryAccess repository;
    private AnalyzeDocument documentAnalyzer;
    private AddDocumentsOutputPort outputPort;

    public AddDocuments(RepositoryAccess repo, TextAnalyzer analyzer, AddDocumentsOutputPort port) {
        this.repository = repo;
        this.documentAnalyzer = new AnalyzeDocument(analyzer);
        this.outputPort = port;
    }


    @Override
    public void add(String targetBdl, List<String> docTitles) throws IOException {
        //retrieve BDL from the repository
        Bdl bdl = this.repository.readBdl(targetBdl);

        //retrieve BDL-Documents association from the repository

        LinkedList<String> association = this.repository.readAssociation(targetBdl);
        try {
            //filter already used documents
            if (association != null) {
                docTitles.removeAll(association);
            }
            //retrieve Documents from the repository
            LinkedList<Document> documents = new LinkedList<>();
            for (String title : docTitles) {
                documents.add(this.repository.readDocument(title));
            }

            //add document analysis to BDL
            for (Document document : documents) {
                documentAnalyzer.addDocumentToBdl(bdl, document);
            }

            association.addAll(docTitles);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        //update Bdl and association in the repository
        repository.updateBdl(bdl);
        repository.updateAssociation(targetBdl, association);

        //send results to output device
        outputPort.showAddDocumentsOutput("Documenti aggiunti al BDL con successo!");
    }
}
