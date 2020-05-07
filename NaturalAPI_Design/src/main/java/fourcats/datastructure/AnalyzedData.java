package fourcats.datastructure;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AnalyzedData {
    private LinkedList<WordTag> tokens;
    private LinkedList<Dependency> dependencies;

    public AnalyzedData(){
        tokens = new LinkedList<>();
        dependencies = new LinkedList<>();
    }

    public void addDependency(String g, String d, String r){
        dependencies.add(new Dependency(g,d,r));
    }

    public void addLemmaData(String w,String t,String l){
        tokens.add(new WordTag(w,t,l));
    }

    public List<Dependency> getDependenciesList(){
        return dependencies;
    }

    public List<String> getPredicates(){
        List<String> predicates =  dependencies
                                   .stream()
                                   .filter(d->(d.getRelation().equals("dobj")))
                                   .map(d-> d.getGov()+" "+d.getDep())
                                   .collect(Collectors.toList());
        return predicates;

    }


    public List<WordTag> getTaggedData() { return tokens; }

    public String toString(){
        String s ="-- Token and lemma Data -- \n";
        for(WordTag wtag : tokens){
            s = s + wtag.toString()+ "\n";
        }
        s = s + "-- Dependencies Data -- \n";
        for(Dependency dat: dependencies){
            s = s + dat.getGov() + " " + dat.getDep() + " " + dat.getRelation() + "\n";
        }
        return s;

    }

}