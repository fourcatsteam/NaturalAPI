package fourcats.entities;

import fourcats.datastructure.WordCounter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Bdl {
    private String nameBdl;
    private LinkedList<WordCounter> nouns;
    private LinkedList<WordCounter> verbs;
    private LinkedList<WordCounter> predicates;

    public Bdl() {
        this("EmptyName");
    }

    public Bdl(String name){
        this.nameBdl = name;
        this.nouns=new LinkedList<>();
        this.verbs=new LinkedList<>();
        this.predicates=new LinkedList<>();
    }

    public List<WordCounter> getNouns() {
        return nouns;
    }

    public List<WordCounter> getVerbs() {
        return verbs;
    }

    public List<WordCounter> getPredicates() {
        return predicates;
    }

    public String getName() { return nameBdl; }

    public void addNoun(String noun) {
        boolean present = false;
        for (WordCounter w : nouns) {
            if(w.getWord().equalsIgnoreCase(noun)) {
                w.incrementCounter();
                present = true;
            }
        }
        if(!present) nouns.add(new WordCounter(noun));
    }

    public void addVerb(String verb) {
        boolean present = false;
        for (WordCounter w : verbs) {
            if(w.getWord().equalsIgnoreCase(verb)) {
                w.incrementCounter();
                present = true;
            }
        }
        if(!present) verbs.add(new WordCounter(verb));
    }

    public void addPredicate(String predicate) {
        boolean present = false;
        for (WordCounter w : predicates) {
            if(w.getWord().equalsIgnoreCase(predicate)) {
                w.incrementCounter();
                present = true;
            }
        }
        if(!present) predicates.add(new WordCounter(predicate));
    }

    public void storeBdlNoun(String s,Integer i){
        nouns.add(new WordCounter(s,i));
    }
    public void storeBdlVerb(String s,Integer i){
        verbs.add(new WordCounter(s,i));
    }
    public void storeBdlPredicate(String s,Integer i){
        predicates.add(new WordCounter(s,i));
    }

    public void removeNoun(String noun) {
        for (Iterator<WordCounter> iterator = nouns.iterator(); iterator.hasNext();) {
            WordCounter wc = iterator.next();
            if (wc.getWord().equalsIgnoreCase(noun)) {
                wc.decrementCounter();
                if(wc.getCount()==0){
                    iterator.remove();
                }
            }
        }
    }

    public void removeVerb(String verb) {
        for (Iterator<WordCounter> iterator = verbs.iterator(); iterator.hasNext();) {
            WordCounter wc = iterator.next();
            if (wc.getWord().equalsIgnoreCase(verb)) {
                wc.decrementCounter();
                if(wc.getCount()==0){
                    iterator.remove();
                }
            }
        }
    }

    public void removePredicate(String predicate) {
        for (Iterator<WordCounter> iterator = predicates.iterator(); iterator.hasNext();) {
            WordCounter wc = iterator.next();
            if (wc.getWord().equalsIgnoreCase(predicate)) {
                wc.decrementCounter();
                if(wc.getCount()==0){
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public String toString() {

        StringBuilder ret= new StringBuilder("-- NOUNS -- \n");

        for(WordCounter w: nouns){
            ret.append(w.toString()).append("\n");
        }
        ret.append("-- VERBS -- \n");
        for(WordCounter w: verbs){
            ret.append(w.toString()).append("\n");
        }
        ret.append("-- PREDICATES -- \n");
        for(WordCounter w: predicates){
            ret.append(w.toString()).append("\n");
        }

        return ret.toString();
    }
}
