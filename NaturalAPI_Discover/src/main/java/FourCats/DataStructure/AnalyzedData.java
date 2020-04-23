package FourCats.DataStructure;

import java.util.LinkedList;
import java.util.List;

public class AnalyzedData {
    private LemmatizerData data;
    private LinkedList<String> parserData;

    public AnalyzedData(){
        data = new LemmatizerData();
        parserData = new LinkedList<>();
    }

    public void addParseData(String s){
        parserData.add(s);
    }

    public void addLemmaData(String w,String t,String l){
        data.addElement(w,t,l);
    }

    public List<String> getParseList(){
        return parserData;
    }

    public List<WordTag> getTaggedData() { return data.getList(); }

    public String toString(){
        String s = data.toString();
        s = s + "-- Parser Data -- \n";
        for(String dat: parserData){
            s = s + dat;
        }
        return s;

    }

}
