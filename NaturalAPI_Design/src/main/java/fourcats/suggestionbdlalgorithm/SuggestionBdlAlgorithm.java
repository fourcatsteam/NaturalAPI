package fourcats.suggestionbdlalgorithm;

import fourcats.datastructure.WordCounter;
import fourcats.entities.Bdl;

public class SuggestionBdlAlgorithm implements StrategyAlgorithm{

    private Bdl bdl;

    public SuggestionBdlAlgorithm(Bdl bdl){
        this.bdl = bdl;
    }

    @Override
    public int findActionInBdl(String nameAction) {
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
    public int findObjectInBdl(String nameObject) {
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
