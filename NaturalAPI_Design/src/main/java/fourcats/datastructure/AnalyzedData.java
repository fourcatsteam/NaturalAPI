package fourcats.datastructure;

import java.util.LinkedList;
import java.util.List;

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