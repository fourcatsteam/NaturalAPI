package FourCats.Port;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public interface RemoveDocumentsInputPort {
    void remove(String targetBdl, LinkedList<String> docTitles) throws IOException;
}
