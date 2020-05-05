package fourcats.port;


import fourcats.entities.Scenario;

import java.util.Map;

public interface DeclineBalSuggestionOutputPort {
    void showDeclinedSuggestion(Map<Integer, Scenario> mScenarios, boolean isOk);
}
