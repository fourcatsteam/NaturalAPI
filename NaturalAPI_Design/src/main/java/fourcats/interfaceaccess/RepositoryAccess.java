package fourcats.interfaceaccess;
import fourcats.entities.Scenario;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface RepositoryAccess {
    String read(String titleFile) throws FileNotFoundException;
    //Ritorna la lista con i contenuti
    void createScenario(Scenario scenario);
    void deleteSuggestion(int idAction, int idScenario);
    Map<Integer, Scenario> readScenarios();
    void deleteScenarios();
    void createBAL(String bal,String filename) throws IOException;
    void updateActionName(int idAction, int idScenario, String newActionName);
    void updateActionType(int idAction, int idScenario, String newType);
    void updateObjectName(int idAction, int idScenario, int idObject, String newObjectName);
    void updateObjectType(int idAction, int idScenario, int idObject, String newObject);





}
