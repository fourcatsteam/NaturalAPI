package FourCats.UseCaseUtilities;

import FourCats.DataStructure.AnalyzedData;
import FourCats.DataStructure.Dependency;
import FourCats.DataStructure.WordTag;
import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.TextAnalyzer;

import java.util.HashSet;
import java.util.Set;

public class AnalyzeDocument {
    private TextAnalyzer analyzer;
    private Set<String> verbsBlacklist;

    public AnalyzeDocument(TextAnalyzer nlp){
        analyzer = nlp;
        verbsBlacklist = this.setUpBlacklist();
    }

    private Set<String> setUpBlacklist() {
        Set<String> blacklist = new HashSet<>();
        blacklist.add("be");
        blacklist.add("have");
        blacklist.add("do");
        return blacklist;
    }

    public void removeDocumentFromBdl(Bdl bdl, Document document) {
        AnalyzedData data = analyzer.parseDocumentContent(document.getContent());

        for(WordTag wordTag: data.getTaggedData()){
            if (wordTag.getTag().contains("NN")) {
                bdl.removeNoun(wordTag.getLemma());
            }
            if (wordTag.getTag().contains("VB")) {
                if(!verbsBlacklist.contains(wordTag.getLemma())){
                    bdl.removeVerb(wordTag.getLemma());
                }
            }
        }

        //find predicates from the analysis
        for(Dependency dep: data.getDependenciesList()){
            if(dep.getRelation().equalsIgnoreCase("dobj")){
                bdl.removePredicate(dep.getGov()+" "+dep.getDep());
            }
        }

        bdl.order();
    }

    public void addDocumentToBdl(Bdl bdl, Document document) {
        AnalyzedData data = analyzer.parseDocumentContent(document.getContent());

        //find names and verbs from the analysis composed by single word
        for(WordTag wordTag: data.getTaggedData()){
            if (wordTag.getTag().contains("NN")) {
                bdl.addNoun(wordTag.getLemma());
            }
            if (wordTag.getTag().contains("VB")) {
                if(!verbsBlacklist.contains(wordTag.getLemma())){
                    bdl.addVerb(wordTag.getLemma());
                }
            }
        }

        //find predicates from the analysis
        for(Dependency dep: data.getDependenciesList()){
            if(dep.getRelation().equalsIgnoreCase("dobj")){
                bdl.addPredicate(dep.getGov()+" "+dep.getDep());
            }
        }
        bdl.order();
    }
}
