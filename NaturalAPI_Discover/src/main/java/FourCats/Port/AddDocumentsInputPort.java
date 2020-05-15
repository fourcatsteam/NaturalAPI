package FourCats.Port;

import java.io.IOException;
import java.util.List;

public interface AddDocumentsInputPort {
    void add(String targetBdl, List<String> docTitles);

}
