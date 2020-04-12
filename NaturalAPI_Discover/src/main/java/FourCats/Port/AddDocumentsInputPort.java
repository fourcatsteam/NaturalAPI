package FourCats.Port;

import java.io.IOException;
import java.util.LinkedList;

public interface AddDocumentsInputPort {
    void add(String targetBdl, LinkedList<String> docTitles) throws IOException;

}
