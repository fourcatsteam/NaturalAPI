package fourcats.port;

public interface ModifyBALSuggestionInputPort {
    void modifyActionType(int idAction, int idScenario, String newType);
    void modifyActionName(int idAction, int idScenario, String newName);
    void modifyObjectType(int idAction, int idScenario, int idObject, String newType);
    void modifyObjectName(int idAction, int idScenario, int idObject, String newName);
}
