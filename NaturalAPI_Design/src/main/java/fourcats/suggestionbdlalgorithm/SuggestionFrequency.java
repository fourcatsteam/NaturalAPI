package fourcats.suggestionbdlalgorithm;

import fourcats.datastructure.WordCounter;
import fourcats.entities.Bdl;

public class SuggestionFrequency implements SuggestionFeedback {

    private Bdl bdl;

    public SuggestionFrequency(Bdl bdl) {
        this.bdl = bdl;
    }

    @Override
    public int findActionInBdl(String nameAction) {
        //predicates research
       if (bdl!=null) {
            for(WordCounter wc : bdl.getPredicates()) {
                if(wc.getWord().equalsIgnoreCase(nameAction.replace("_"," "))) {
                    return wc.getCount();
                }
            }
        }
        return 0;
    }

    @Override
    public int findObjectInBdl(String nameObject) {
        //nouns research
        if (bdl!=null) {
            for (WordCounter wc : bdl.getNouns()) {
                if (wc.getWord().equalsIgnoreCase(nameObject)) {
                    return wc.getCount();
                }
            }
        }
        return 0;
    }
}
