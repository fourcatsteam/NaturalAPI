package fourcats.datastructure;


import java.util.LinkedList;
import java.util.List;

public class LemmatizerData {

    private LinkedList<WordTag> lemmatizationResult;

    public LemmatizerData() {
        lemmatizationResult = new LinkedList<>();
    }

    public LinkedList<WordTag> getList() {
        return lemmatizationResult;
    };

    public void addElement(String word, String tag, String lemma) {
        lemmatizationResult.add(new WordTag(word,tag,lemma));
    }

    public List<String> getNouns(){
        LinkedList<String> nouns = new LinkedList<>();
        for(WordTag tag : lemmatizationResult) {
            if (tag.getTag().contains("NN")) {
                nouns.add(tag.getLemma());
            }
        }
        return nouns;
    }

    public List<String> getVerbs(){
        LinkedList<String> verbs = new LinkedList<>();
        for(WordTag tag : lemmatizationResult) {
            if (tag.getTag().contains("VB")) {
                verbs.add(tag.getLemma());
            }
        }
        return verbs;
    }

    public String toString(){
        String t ="-- Lemmatize Data -- \n";
        for(WordTag tag : lemmatizationResult){
            t = t + tag.toString()+ "\n";
        }
        return t;
    }



}
