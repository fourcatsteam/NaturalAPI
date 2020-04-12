package FourCats.DataStructure;

import java.util.LinkedList;

public class LemmatizerData {
    /*public class WordTag{
        private String value;
        private String tag;
        private String lemma;

        public WordTag(String w, String t, String l) {
            value=w;
            tag=t;
            lemma=l;
        }

        public String getValue() {return value;}
        public String getTag() {return tag;}
        public String getLemma() {return lemma;}

        public String toString(){
            return "["+value+","+tag+","+lemma+"]";
        }
    }*/

    private LinkedList<WordTag> lemmatizationResult;

    public LemmatizerData() {
        lemmatizationResult = new LinkedList<WordTag>();
    }

    public LinkedList<WordTag> getList() {
        return lemmatizationResult;
    };

    public void addElement(String word, String tag, String lemma) {
        lemmatizationResult.add(new WordTag(word,tag,lemma));
    }

    public LinkedList<String> getNouns(){
        LinkedList<String> nouns = new LinkedList<String>();
        for(WordTag tag : lemmatizationResult) {
            if (tag.getTag().contains("NN")) {
                nouns.add(tag.getLemma());
            }
        }
        return nouns;
    }

    public LinkedList<String> getVerbs(){
        LinkedList<String> verbs = new LinkedList<String>();
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
