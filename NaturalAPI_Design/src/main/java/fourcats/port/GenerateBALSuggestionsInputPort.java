package fourcats.port;

import java.util.List;

public interface GenerateBALSuggestionsInputPort {
    void generateSuggestions(List<String> lFeatureFilePaths, boolean isForNewBal);
}
