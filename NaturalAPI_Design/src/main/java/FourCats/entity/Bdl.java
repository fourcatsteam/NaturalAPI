package FourCats.entity;

import FourCats.DataStructure.WordCounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;

public class Bdl {
    private LinkedList<WordCounter> nouns;
    private LinkedList<WordCounter> verbs;
    private LinkedList<WordCounter> predicates;

    public Bdl() {
        nouns=new LinkedList<WordCounter>();
        verbs=new LinkedList<WordCounter>();
        predicates=new LinkedList<WordCounter>();
    }

    public void addNoun(String noun) {
        Boolean present = false;
        for (WordCounter w : nouns) {
            if(w.getWord().equalsIgnoreCase(noun)) {
                w.incrementCounter();
                present = true;
            }
        }
        if(!present) nouns.add(new WordCounter(noun));
    }

    public void addVerb(String verb) {
        Boolean present = false;
        for (WordCounter w : verbs) {
            if(w.getWord().equalsIgnoreCase(verb)) {
                w.incrementCounter();
                present = true;
            }
        }
        if(!present) verbs.add(new WordCounter(verb));
    }

    public void addPredicate(String predicate) {
        Boolean present = false;
        for (WordCounter w : predicates) {
            if(w.getWord().equalsIgnoreCase(predicate)) {
                w.incrementCounter();
                present = true;
            }
        }
        if(!present) predicates.add(new WordCounter(predicate));
    }
    //Funzioni di salvataggio da mettere qui o su BdlGenerator?
    public void saveToFile(String filename) throws FileNotFoundException {
        saveListToFile(nouns,filename+".nouns.bdl.csv");
        saveListToFile(verbs,filename+".verbs.bdl.csv");
        saveListToFile(predicates,filename+".predicates.bdl.csv");
    }

    private void saveListToFile(LinkedList<WordCounter> list,String namefile) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(namefile));
        StringBuilder sb = new StringBuilder();
        for(WordCounter w : list) {
            sb.append(w.getWord());
            sb.append(",");
            sb.append(w.getCount());
            sb.append('\n');
        }
        writer.write(sb.toString());
        writer.close();
    }

    //ORDER BDL
    public void order() {
        Collections.sort(nouns,Collections.reverseOrder());
        Collections.sort(verbs,Collections.reverseOrder());
        Collections.sort(predicates, Collections.reverseOrder());
    }

    @Override
    public String toString() {

        String ret="-- NOUNS -- \n";

        for(WordCounter w: nouns){
            ret = ret + w.toString() +"\n";
        }
        ret = ret + "-- VERBS -- \n";
        for(WordCounter w: verbs){
            ret = ret + w.toString() +"\n";
        }
        ret = ret + "-- PREDICATES -- \n";
        for(WordCounter w: predicates){
            ret = ret + w.toString() +"\n";
        }

        return ret;
    }
}
