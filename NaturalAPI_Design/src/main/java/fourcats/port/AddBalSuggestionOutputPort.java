package fourcats.port;

import fourcats.entities.Scenario;

import java.util.Map;

public interface AddBalSuggestionOutputPort {
    void showAddedSuggestion(Map<Integer, Scenario> mScenarios,boolean isSuggestionAdded);
}
