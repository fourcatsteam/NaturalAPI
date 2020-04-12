package fourcats.datastructure;

import java.util.LinkedList;

public class AnalyzedData {
    private LemmatizerData data;
    private LinkedList<String> parserData;

    public AnalyzedData(){
        data = new LemmatizerData();
        parserData = new LinkedList<String>();
    }

    public void addParseData(String s){
        parserData.add(s);
    }

    public void addLemmaData(String w,String t,String l){
        data.addElement(w,t,l);
    }

    public LinkedList<String> getParseList(){
        return parserData;
    }

    public LinkedList<String> getNouns(){
        return data.getNouns();
    }

    public LinkedList<String> getVerbs(){
        return data.getVerbs();
    }

    public LinkedList<WordTag> getTaggedData() { return data.getList(); }

    public String toString(){
        String s = data.toString();
        s = s + "-- Parser Data -- \n";
        for(String dat: parserData){
            s = s + dat;
        }
        return s;

    }

}
