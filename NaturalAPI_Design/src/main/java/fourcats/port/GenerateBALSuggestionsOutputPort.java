package fourcats.port;

import fourcats.entities.Scenario;

import java.util.Map;

public interface GenerateBALSuggestionsOutputPort {

    void showSuggestionsForScenario(Map<Integer,Scenario> mScenarios);

    void showErrorFileLoad();
}


