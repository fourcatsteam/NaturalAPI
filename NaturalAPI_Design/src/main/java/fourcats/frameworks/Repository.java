package fourcats.frameworks;

import fourcats.entities.Bdl;
import fourcats.entities.Scenario;
import fourcats.entities.Type;
import fourcats.interfaceaccess.PersistentMemoryAccess;
import fourcats.interfaceaccess.RepositoryAccess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class Repository implements RepositoryAccess  {
    DataKeeper dataKeeper;
    PersistentMemoryAccess memoryAccess;

    public Repository(DataKeeper dataKeeper, PersistentMemoryAccess memoryAccess){
        this.dataKeeper = dataKeeper;
        this.memoryAccess = memoryAccess;
    }


    @Override
    public String read(String filePath) throws FileNotFoundException {
        return memoryAccess.readFile(filePath);
    }

    @Override
    public void createScenario(Scenario scenario) {
        dataKeeper.addScenarioToMap(scenario);
    }


    @Override
    public void deleteSuggestion(int idAction, int idScenario) {
        dataKeeper.removeAction(idScenario,idAction);
    }

    @Override
    public Map<Integer, Scenario> readScenarios() {
        return dataKeeper.getScenarioMap();
    }

    @Override
    public Map<Integer, Type> readTypes() {
        return dataKeeper.getTypeMap();
    }

    @Override
    public void deleteScenarios() {
        dataKeeper.clearScenariosMap();
    }

    @Override
    public void createBAL(String bal, String filename) throws IOException {
        memoryAccess.writeFile(bal,filename);
    }

    @Override
    public void updateActionName(int idAction, int idScenario, String newActionName) {
        dataKeeper.updateActionName(idScenario, idAction, newActionName);
    }

    @Override
    public void updateActionType(int idAction, int idScenario, String newActionType) {
        dataKeeper.updateActionType(idScenario,idAction,newActionType);
    }

    @Override
    public void updateObjectName(int idAction, int idScenario, int idObject, String newObjectName) {
        dataKeeper.updateObjectName(idScenario,idAction,idObject,newObjectName);
    }

    @Override
    public void updateObjectType(int idAction, int idScenario, int idObject, String newObjectType) {
        dataKeeper.updateObjectType(idScenario,idAction,idObject,newObjectType);
    }

    @Override
    public void createCustomType(String typeName, Map<String, String> mAttributes) {
        dataKeeper.addCustomType(typeName,mAttributes);
    }

    @Override
    public void updateObjectTypeById(int idAction, int idScenario, int idObject, int idType) {
        dataKeeper.updateObjectTypeById(idScenario,idAction,idObject,idType);
    }

    @Override
    public void updateActionTypeById(int idAction, int idScenario, int idType) {
        dataKeeper.updateActionTypeById(idScenario,idAction,idType);
    }

    @Override
    public void createObject(int idAction, int idScenario, String objectName, int idType) {
        dataKeeper.addObject(idScenario,idAction, objectName, idType);
    }

    @Override
    public void deleteObject(int idAction, int idScenario, int idObject) {
        dataKeeper.removeObject(idScenario,idAction,idObject);
    }

    @Override
    public void createSuggestion(int idScenario, String suggestionName, String suggestionType) {
        dataKeeper.addSuggestion(idScenario,suggestionName,suggestionType);
    }

    @Override
    public void crateSuggestionByIdType(int idScenario, String suggestionName, int idType) {
        dataKeeper.addSuggestionByIdType(idScenario,suggestionName,idType);
    }

    @Override
    public Bdl readBdl(String[] name) throws IOException {
        return memoryAccess.getBdl(name);
    }


}
