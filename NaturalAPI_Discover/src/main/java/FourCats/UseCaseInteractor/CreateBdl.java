package FourCats.UseCaseInteractor;

import FourCats.DataStructure.WordCounter;
import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.Port.CreateBdlInputPort;
import FourCats.Port.CreateBdlOutputPort;
import FourCats.UseCaseUtilities.AnalyzeDocument;
import smile.nlp.collocation.NGram;
import smile.nlp.keyword.CooccurrenceKeywords;

import java.util.ArrayList;
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
                output.showWarning("Document "+title+" not found. The creation will continue with the other documents.");
            }
        }

        //add Documents analysis to BDL
        for(Document document: documents) {
            documentAnalyzer.addDocumentToBdl(bdl, document);
        }

        // SMILE for keywords

         //Get String with all documents
        StringBuilder documentsString = new StringBuilder();
        for(Document document: documents) {
            documentsString.append(document.getContent());
        }

        // Ottiene le keyword
        NGram[] keyWords = CooccurrenceKeywords.of(documentsString.toString());

        // Trasforma le keyword in una lista di stringhe
        List<String> keyWordList = new ArrayList<>();
        for (int i = 0; i < keyWords.length; i++) {
            String [] wordArray = keyWords[i].words;
            String wordString = String.join(" ",wordArray);
            keyWordList.add(wordString);
        }

        List<WordCounter> predicateList = bdl.getPredicates();
//
//
//        // Ciclo for su tutti i predicati
        for (WordCounter predicate: predicateList) {
//
//            // Non so ancora nulla sul predicato, do per scontato che non sia una keyword
            predicate.setKeyValue(false);
//
//            // Estrai il noun dal predicato
            String noun = predicate.getWord();
            noun = noun.split(" ")[1];

            noun = noun.toLowerCase();

            for(String keyword: keyWordList){
                if(keyword.contains(noun)) {
                    predicate.setKeyValue(true);
                    break;
                }
            }
//
        }
//
//        // Update predicates
        bdl.setPredicates(predicateList);

        //update BDL and document association in the repository
        repository.updateBdl(bdl);
        repository.updateAssociation(nameBdl,titleList);

        //send results to output device
        output.showCreateBdlOutput(bdl);
    }
}
