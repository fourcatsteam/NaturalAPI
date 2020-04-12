package FourCats.Port;


import FourCats.Entities.Scenario;

import java.util.Map;

public interface DeclineBALSuggestionOutputPort {
    void showDeclinedSuggestion(Map<Integer, Scenario> mScenarios, boolean isOk);
}
