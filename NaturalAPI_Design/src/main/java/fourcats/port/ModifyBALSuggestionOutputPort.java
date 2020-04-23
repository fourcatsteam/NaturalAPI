package fourcats.port;

import fourcats.entities.Scenario;

import java.util.Map;

public interface ModifyBALSuggestionOutputPort {
    void showModifiedActionName(Map<Integer, Scenario> mScenarios, boolean isActionNameModified);
    void showModifiedObjectName(Map<Integer, Scenario> mScenarios, boolean isObjectNameModified);
    void showModifiedObjectType(Map<Integer, Scenario> mScenarios, boolean isObjectTypeModified);

}
