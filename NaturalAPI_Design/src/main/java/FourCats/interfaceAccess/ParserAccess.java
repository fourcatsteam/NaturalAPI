package FourCats.interfaceAccess;
import FourCats.DataStructure.LemmatizerData;

import java.util.LinkedList;

public interface ParserAccess {
    public LinkedList<String> parseSentence(String sentence);
    public LemmatizerData lemmatizeSentence(String sentence);

}
