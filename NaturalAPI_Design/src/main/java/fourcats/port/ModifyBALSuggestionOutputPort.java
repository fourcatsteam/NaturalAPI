package fourcats.port;

import fourcats.entities.Scenario;

import java.util.Map;

public interface ModifyBALSuggestionOutputPort {
    void showModifiedActionName(Map<Integer, Scenario> mScenarios, boolean isActionNameModified,String actionNameModified,boolean isBdlLoaded);
    void showModifiedActionType(Map<Integer, Scenario> mScenarios, boolean isActionTypeModified);
    void showModifiedObjectName(Map<Integer, Scenario> mScenarios, boolean isObjectNameModified, String objectNameModified,boolean isBdlLoaded);
    void showModifiedObjectType(Map<Integer, Scenario> mScenarios, boolean isObjectTypeModified);
    void showAddedObject(Map<Integer, Scenario> mScenarios, boolean isObjectAdded);
    void showRemovedObject(Map<Integer, Scenario> mScenarios, boolean isObjectRemoved);
}
