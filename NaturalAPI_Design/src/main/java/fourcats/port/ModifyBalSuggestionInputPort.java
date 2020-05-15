package fourcats.port;

public interface ModifyBalSuggestionInputPort {
    void modifyActionType(int idAction, int idScenario, String newType);
    void modifyActionTypeById(int idAction, int idScenario, int idType);
    void modifyActionName(int idAction, int idScenario, String newName);
    void modifyObjectType(int idAction, int idScenario, int idObject, String newType);
    void modifyObjectTypeById(int idAction, int idScenario, int idObject, int idType);
    void modifyObjectName(int idAction, int idScenario, int idObject, String newName);
    void addObject (int idAction, int idScenario,String objectName, int idType);
    void removeObject(int idAction, int idScenario, int idObject);


}
