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

    public AddDocuments(RepositoryAccess repo, AnalyzeDocument analyzer, AddDocumentsOutputPort port) {
        this.repository = repo;
        this.documentAnalyzer = analyzer;
        this.outputPort = port;
    }

    @Override
    public void add(String targetBdl, List<String> docTitles) {
        //retrieve BDL from the repository
        Bdl bdl = this.repository.readBdl(targetBdl);

        if(bdl==null) {
            outputPort.showError("BDL not found");
        } else {
            //retrieve BDL-Documents association from the repository
            LinkedList<String> association = this.repository.readAssociation(targetBdl);

            //filter already used documents
            if (association == null) {
                outputPort.showError("BDL-Document association file missing, adding documents is not possible");
            } else {
                docTitles.removeAll(association);

                //retrieve Documents from the repository
                LinkedList<Document> documents = new LinkedList<>();
                for (String title : docTitles) {
                    Document doc = repository.readDocument(title);
                    if(doc!=null) {
                        documents.add(doc);
                    } else {
                        outputPort.showWarning("Document "+title+" not found");
                    }
                }

                //add document analysis to BDL
                for (Document document : documents) {
                    documentAnalyzer.addDocumentToBdl(bdl, document);
                }

                association.addAll(docTitles);

                //update Bdl and association in the repository
                repository.updateBdl(bdl);
                repository.updateAssociation(targetBdl, association);

                //send results to output device
                outputPort.showAddDocumentsOutput(bdl);
            }
        }
    }
}
