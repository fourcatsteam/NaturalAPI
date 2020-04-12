package FourCats.usecase;

import FourCats.entity.Bdl;

public class DataAnalyzer {

    public Bdl bdl;

    public DataAnalyzer(){
        bdl = new Bdl();
    }

    public Bdl getBdl(){
        return bdl;
    }

    public void receivePredicate(String s){
        bdl.addPredicate(s);
    }

    public void receiveNoun(String s){
        bdl.addNoun(s);
    }

    public void receiveVerb(String s){
        bdl.addVerb(s);
    }

    public void orderBdl(){
        bdl.order();
    }



}
