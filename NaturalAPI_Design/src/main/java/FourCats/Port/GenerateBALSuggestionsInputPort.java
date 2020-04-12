package FourCats.Port;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface GenerateBALSuggestionsInputPort {
    void generateSuggestions(String featureFileName) throws IOException;
}
