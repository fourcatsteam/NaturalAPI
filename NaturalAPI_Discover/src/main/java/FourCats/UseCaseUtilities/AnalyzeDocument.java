package FourCats.UseCaseUtilities;

import FourCats.DataStructure.AnalyzedData;
import FourCats.DataStructure.WordTag;
import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.TextAnalyzer;

import java.util.LinkedList;

public class AnalyzeDocument {
    private TextAnalyzer analyzer;

    public AnalyzeDocument(TextAnalyzer nlp){
        analyzer = nlp;
    }

    public AnalyzedData parseDocuments(LinkedList<Document> docs) {
        AnalyzedData dataToPass = null;
        for(Document doc : docs){
            dataToPass = analyzer.parseDocumentContent(doc.getContent());
        }
        return dataToPass;
    }

    public void removeDocumentFromBdl(Bdl bdl, Document document) {
        AnalyzedData data = analyzer.parseDocumentContent(document.getContent());

        for(WordTag wordTag: data.getTaggedData()){
            if (wordTag.getTag().contains("NN")) {
                bdl.removeNoun(wordTag.getLemma());
            }
            if (wordTag.getTag().contains("VB")) {
                bdl.removeVerb(wordTag.getLemma());
            }
        }

        //find predicates from the analysis
        for(String s: data.getParseList()){
            bdl.removePredicate(s);
        }

    }

    public void addDocumentToBdl(Bdl bdl, Document document) {
        AnalyzedData data = analyzer.parseDocumentContent(document.getContent());

        //find names and verbs from the analysis composed by single word
        for(WordTag wordTag: data.getTaggedData()){
            if (wordTag.getTag().contains("NN")) {
                bdl.addNoun(wordTag.getLemma());
            }
            if (wordTag.getTag().contains("VB")) {
                bdl.addVerb(wordTag.getLemma());
            }
        }

        //find predicates from the analysis
        for(String s: data.getParseList()){
            bdl.addPredicate(s);
        }

    }
}
