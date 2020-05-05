package fourcats.port;

import java.util.List;

public interface GenerateBalSuggestionsInputPort {
    void generateSuggestions(List<String> lFeatureFilePaths, boolean isForNewBal);
}
