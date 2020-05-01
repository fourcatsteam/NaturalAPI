package fourcats.interfaceaccess;
import fourcats.entities.Bdl;
import fourcats.entities.Scenario;
import fourcats.entities.Type;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface RepositoryAccess {
    String read(String titleFile) throws FileNotFoundException;
    //Ritorna la lista con i contenuti
    void createScenario(Scenario scenario);
    void deleteSuggestion(int idAction, int idScenario);
    Map<Integer, Scenario> readScenarios();
    Map<Integer, Type> readTypes();
    void deleteScenarios();
    void createBAL(String bal,String filePath) throws IOException;
    void updateActionName(int idAction, int idScenario, String newActionName);
    void updateActionType(int idAction, int idScenario, String newActionType);
    void updateObjectName(int idAction, int idScenario, int idObject, String newObjectName);
    void updateObjectType(int idAction, int idScenario, int idObject, String newObjectType);
    void createCustomType(String typeName, Map<String,String> mAttributes);
    void updateObjectTypeById(int idAction, int idScenario, int idObject, int idType);
    void updateActionTypeById(int idAction, int idScenario, int idType);
    void createObject(int idAction, int idScenario, String objectName, int idType);
    void deleteObject(int idAction, int idScenario, int idObject);

    void createSuggestion(int idScenario, String suggestionName, String suggestionType);
    void crateSuggestionByIdType(int idScenario, String suggestionName, int idType);

    Bdl readBdl(String[] name) throws IOException;

}
