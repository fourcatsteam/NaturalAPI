package fourcats.port;

import fourcats.entities.Scenario;

import java.util.Map;

public interface AddBALSuggestionOutputPort {
    void showAddedSuggestion(Map<Integer, Scenario> mScenarios,boolean isSuggestionAdded);
}
