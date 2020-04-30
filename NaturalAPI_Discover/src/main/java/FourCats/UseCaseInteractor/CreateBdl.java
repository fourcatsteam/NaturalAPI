package FourCats.UseCaseInteractor;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.Port.CreateBdlInputPort;
import FourCats.Port.CreateBdlOutputPort;
import FourCats.UseCaseUtilities.AnalyzeDocument;

import java.util.LinkedList;
import java.util.List;

public class CreateBdl implements CreateBdlInputPort {

    private RepositoryAccess repository;
    private AnalyzeDocument documentAnalyzer;
    private CreateBdlOutputPort output;

    public CreateBdl(RepositoryAccess ra, AnalyzeDocument an, CreateBdlOutputPort p){
        repository = ra;
        documentAnalyzer = an;
        output = p;
    }

    public void create(String nameBdl, List<String> titleList) {
        //create a new BDL
        Bdl bdl = repository.createBdl(nameBdl);

        //retrieve Documents from the repository
        LinkedList<Document> documents = new LinkedList<>();
        for(String title: titleList) {
            Document doc = repository.readDocument(title);
            if(doc!=null) {
                documents.add(doc);
            } else {
                output.showWarning("Document "+title+" not found");
            }
        }

        //add Documents analysis to BDL
        for(Document document: documents) {
            documentAnalyzer.addDocumentToBdl(bdl, document);
        }

        //update BDL and document association in the repository
        repository.updateBdl(bdl);
        repository.updateAssociation(nameBdl,titleList);

        //send results to output device
        output.showCreateBdlOutput(bdl);
    }
}
