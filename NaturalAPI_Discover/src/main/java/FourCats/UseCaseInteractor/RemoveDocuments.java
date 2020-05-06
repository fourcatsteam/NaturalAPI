package FourCats.UseCaseInteractor;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.Port.RemoveDocumentsOutputPort;
import FourCats.Port.RemoveDocumentsInputPort;
import FourCats.UseCaseUtilities.AnalyzeDocument;

import java.util.LinkedList;
import java.util.List;

public class RemoveDocuments implements RemoveDocumentsInputPort {

    private RepositoryAccess repository;
    private AnalyzeDocument documentAnalyzer;
    private RemoveDocumentsOutputPort outputPort;

    public RemoveDocuments(RepositoryAccess repo, AnalyzeDocument analyzer, RemoveDocumentsOutputPort port) {
        this.repository = repo;
        this.documentAnalyzer = analyzer;
        this.outputPort = port;
    }

    @Override
    public void remove(String targetBdl, List<String> docTitles) {
        //retrieve BDL
        Bdl bdl = this.repository.readBdl(targetBdl);

        if(bdl==null) {
            outputPort.showError("BDL \""+targetBdl+"\" not found");
        } else {
            //retrieve association
            LinkedList<String> association = this.repository.readAssociation(targetBdl);

            if(association==null) {
                outputPort.showError("BDL-Document association file missing, removing documents is not possible");
            } else {
                for (String title: docTitles) {
                    if(!association.contains(title)) {
                        outputPort.showWarning("Document "+title+" is not associated to the BDL. This document will be discarded");
                        docTitles.remove(title);
                    }
                }
                //retrieve Documents
                LinkedList<Document> documents = new LinkedList<>();
                for (String title: docTitles) {
                    Document doc = this.repository.readDocument(title);
                    if(doc != null) {
                        documents.add(doc);
                    } else {
                        outputPort.showWarning("Document "+title+" not found. The removal will continue with the other documents");
                    }
                }

                //remove Documents analysis from BDL
                for (Document document: documents) {
                    this.documentAnalyzer.removeDocumentFromBdl(bdl, document);
                }

                association.removeAll(docTitles);

                //update Bdl in the repository
                repository.updateBdl(bdl);
                repository.updateAssociation(bdl.getName(),association);

                //send results to output device
                outputPort.showRemoveDocumentOutputPort(bdl);
            }
        }
    }
}
