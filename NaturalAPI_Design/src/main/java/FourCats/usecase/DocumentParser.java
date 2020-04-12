package FourCats.usecase;
        import FourCats.DataStructure.LemmatizerData;
        import FourCats.interfaceAccess.ParserAccess;

        import java.util.LinkedList;

public class DocumentParser {
    private ParserAccess pa;

    public DocumentParser(ParserAccess p){
        pa = p;
    }

    public LinkedList<String> parseSentence(String documentContent) {
        return pa.parseSentence(documentContent);
    }

    public LemmatizerData lemmatizeSentence(String documentContent) {
        return pa.lemmatizeSentence(documentContent);
    }
}
