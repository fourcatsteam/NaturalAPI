package fourcats.SuggestionBDLAlgorithm;

import fourcats.datastructure.WordCounter;
import fourcats.entities.Bdl;

public class SuggestionBDLAlgorithm implements StrategyAlgorithm{

    @Override
    public int findActionInBdl(String nameAction, Bdl bdl) {
        //Ricerca sui predicati

       if (bdl!=null){
            for(WordCounter wc : bdl.getPredicates()){
                if(wc.getWord().equalsIgnoreCase(nameAction.replaceAll("_"," "))){
                    return wc.getCount();
                }
            }
        }
        return 0;
    }

    @Override
    public int findObjectInBdl(String nameObject, Bdl bdl) {
        //Ricerca sui nomi
        if (bdl!=null) {
            for (WordCounter wc : bdl.getNouns()) {
                if (wc.getWord().equalsIgnoreCase(nameObject)) {
                    //return true;
                    return wc.getCount();
                }
            }
        }
        return 0;
    }
}
