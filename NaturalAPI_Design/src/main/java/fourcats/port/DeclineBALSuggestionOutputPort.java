package fourcats.port;


import fourcats.entities.Scenario;

import java.util.Map;

public interface DeclineBALSuggestionOutputPort {
    void showDeclinedSuggestion(Map<Integer, Scenario> mScenarios, boolean isOk);
}
