package fourcats.port;

import fourcats.entities.Scenario;

import java.util.Map;

public interface GenerateBalSuggestionsOutputPort {

    void showSuggestionsForScenario(Map<Integer,Scenario> mScenarios);

    void showErrorFileLoad(boolean isFileDuplicated);
}


