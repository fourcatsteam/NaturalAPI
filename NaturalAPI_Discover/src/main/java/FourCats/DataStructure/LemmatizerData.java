package FourCats.DataStructure;

import java.util.LinkedList;
import java.util.List;

public class LemmatizerData {
    private LinkedList<WordTag> lemmatizationResult;

    public LemmatizerData() {
        lemmatizationResult = new LinkedList<>();
    }

    public List<WordTag> getList() {
        return lemmatizationResult;
    };

    public void addElement(String word, String tag, String lemma) {
        lemmatizationResult.add(new WordTag(word,tag,lemma));
    }

    public String toString(){
        String t ="-- Lemmatize Data -- \n";
        for(WordTag tag : lemmatizationResult){
            t = t + tag.toString()+ "\n";
        }
        return t;
    }



}
