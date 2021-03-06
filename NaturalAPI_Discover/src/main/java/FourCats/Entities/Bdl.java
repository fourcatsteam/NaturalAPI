package FourCats.Entities;

import FourCats.DataStructure.WordCounter;

import java.util.*;
import java.util.function.Consumer;

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

    public Bdl(String name, List<WordCounter> n, List<WordCounter> v, List<WordCounter> p) {
        this.nameBdl = name;
        this.nouns = new LinkedList<>();
        if(n!=null) {
            n.stream().forEach(wordCounter -> nouns.add(wordCounter));
        }
        this.verbs = new LinkedList<>();
        if(v!=null) {
            v.stream().forEach(wordCounter -> verbs.add(wordCounter));
        }
        this.predicates = new LinkedList<>();
        if(p!=null) {
            p.stream().forEach(wordCounter -> predicates.add(wordCounter));
        }
    }

    public List<WordCounter> getNouns(){
        return nouns;
    }

    public List<WordCounter> getVerbs(){
        return verbs;
    }

    public List<WordCounter> getPredicates(){
        return predicates;
    }

    public void setPredicates(List<WordCounter> predicates) {

        this.predicates = new LinkedList<>(predicates);
    }

    public String getName() { return nameBdl; }

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

    //ORDER BDL
    public void order() {
        Collections.sort(nouns,Collections.reverseOrder());
        Collections.sort(verbs,Collections.reverseOrder());
        Collections.sort(predicates, Collections.reverseOrder());
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
        //Da rivedere perchè uso iteratori
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

    public String nounsToString(){
        String ret="-- NOUNS -- \n";
        for(WordCounter w: nouns){
            ret = ret + w.toString() +"\n";
        }
        return ret;
    }

    public String verbsToString(){
        String ret="-- VERBS -- \n";
        for(WordCounter w: verbs){
            ret = ret + w.toString() +"\n";
        }
        return ret;
    }

    public String predicatesToString(){
        String ret="-- PREDICATES -- \n";
        for(WordCounter w: predicates){
            ret = ret + w.toString() +"\n";
        }
        return ret;
    }
    @Override
    public String toString() {

        String ret="-- NOUNS -- \n";

        for(WordCounter w: nouns){
            ret = ret + w.toString() +"\n";
        }
        ret = ret + "-- VERBS -- \n";
        for(WordCounter w: verbs){
            ret = ret + w.toString() + "\n";
        }
        ret = ret + "-- PREDICATES -- \n";
        for(WordCounter w: predicates){
            ret = ret + w.toString() +"\n";
        }

        return ret;
    }

    private Integer getTotalListsOccurrencies(List<WordCounter> list) {
        Optional<Integer> sum = list.stream()
                .map(wordCounter -> wordCounter.getCount())
                .reduce((a, b) -> a + b);
        if(sum.isPresent()) {
            return sum.get();
        }
        return 0;
    }

    public Integer getTotalNounsOccurrences() {
        return getTotalListsOccurrencies(nouns);
    }

    public Integer getTotalVerbsOccurrences() {
        return getTotalListsOccurrencies(verbs);
    }

    public Integer getTotalPredicatesOccurrences() {
        return getTotalListsOccurrencies(predicates);
    }
}
