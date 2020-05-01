package fourcats.SuggestionBDLAlgorithm;

import fourcats.datastructure.WordCounter;
import fourcats.entities.Bdl;

public class SuggestionBDLAlgorithm implements StrategyAlgorithm{

    @Override
    public boolean findActionInBdl(String nameAction, Bdl bdl) {
        //Ricerca sui predicati
        if (bdl!=null){
            for(WordCounter wc : bdl.getPredicates()){
                if(wc.getWord().equalsIgnoreCase(nameAction.replaceAll("_"," "))){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean findObjectInBdl(String nameObject, Bdl bdl) {
        //Ricerca sui nomi
        if (bdl!=null) {
            for (WordCounter wc : bdl.getNouns()) {
                if (wc.getWord().equalsIgnoreCase(nameObject)) {
                    return true;
                }
            }
        }
        return false;
    }
}
