package FourCats.Port;

import FourCats.Entities.Scenario;

import java.util.Map;

public interface GenerateBALSuggestionsOutputPort {

    void showSuggestionsForScenario(Map<Integer,Scenario> mScenarios);

    void showErrorFileLoad();
}


