package FourCats.Port;

import java.io.IOException;
import java.util.List;

public interface RemoveDocumentsInputPort {
    void remove(String targetBdl, List<String> docTitles);
}
