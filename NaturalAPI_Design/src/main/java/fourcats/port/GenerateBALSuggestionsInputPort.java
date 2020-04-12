package fourcats.port;

import java.io.IOException;

public interface GenerateBALSuggestionsInputPort {
    void generateSuggestions(String featureFileName) throws IOException;
}
