package FourCats.UseCaseInteractor;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.TextAnalyzer;
import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.Port.CreateBdlInputPort;
import FourCats.Port.CreateBdlOutputPort;
import FourCats.UseCaseUtilities.AnalyzeDocument;

import java.util.LinkedList;

public class CreateBdl implements CreateBdlInputPort {

    private RepositoryAccess repository;
    private AnalyzeDocument documentAnalyzer;
    private CreateBdlOutputPort output;

    public CreateBdl(RepositoryAccess ra, TextAnalyzer nlp, CreateBdlOutputPort p){
        repository = ra;
        documentAnalyzer = new AnalyzeDocument(nlp);
        output = p;
    }

    public void create(String nameBdl,LinkedList<String> titleList) {
        //create a new BDL
        Bdl bdl = repository.createBdl(nameBdl);

        //retrieve Documents from the repository
        LinkedList<Document> documents = new LinkedList<Document>();
        for(String title: titleList) {
            documents.add(repository.readDocument(title));
        }

        //add Documents analysis to BDL
        for(Document document: documents) {
            documentAnalyzer.addDocumentToBdl(bdl, document);
        }

        //update BDL and document association in the repository
        repository.updateBdl(bdl);
        repository.updateAssociation(nameBdl,titleList);

        //send results to output device
        output.showCreateBdlOuput("Bdl generata! Puoi trovare i file csv all'interno della cartella BDL");
    }
}
