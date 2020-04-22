package fourcats.port;

import fourcats.entities.Scenario;

import java.util.Map;

public interface ModifyBALSuggestionOutputPort {
    void showModifiedAction(Map<Integer, Scenario> mScenarios, boolean isOk);
}
