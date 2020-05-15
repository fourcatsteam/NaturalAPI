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
        return dependencies.stream()
                           .filter(d->(d.getRelation().equals("dobj")))
                           .map(d-> d.getGov()+" "+d.getDep())
                           .collect(Collectors.toList());
    }


    public List<WordTag> getTaggedData() { return tokens; }

    public String toString(){
        StringBuilder s = new StringBuilder("-- Token and lemma Data -- \n");
        for(WordTag wtag : tokens){
            s.append(wtag.toString()).append("\n");
        }
        s.append("-- Dependencies Data -- \n");
        for(Dependency dat: dependencies){
            s.append(dat.getGov()).append(" ").append(dat.getDep()).append(" ").append(dat.getRelation()).append("\n");
        }
        return s.toString();

    }

}