package fourcats.interfaceadapters;

import fourcats.port.*;

import java.io.IOException;
import java.util.Map;

public class Controller{
    private GenerateBALSuggestionsInputPort generateSuggestion;
    private DeclineBALSuggestionInputPort declineSuggestion;
    private GenerateBALInputPort generateBAL;
    private ModifyBALSuggestionInputPort modifySuggestion;
    private CreateCustomTypeInputPort createCustomType;
    private ShowTypesInputPort showTypes;

    public Controller(GenerateBALSuggestionsInputPort generateBALSugg, DeclineBALSuggestionInputPort declineBALSugg,
                      GenerateBALInputPort generateBal, ModifyBALSuggestionInputPort modifyBALSugg,
                      CreateCustomTypeInputPort createCustomType, ShowTypesInputPort showTypes){
            this.generateSuggestion = generateBALSugg;
            this.declineSuggestion = declineBALSugg;
            this.generateBAL = generateBal;
            this.modifySuggestion = modifyBALSugg;
            this.createCustomType = createCustomType;
            this.showTypes = showTypes;
    }

    public void generateSuggestions(String featureFileName) {
        generateSuggestion.generateSuggestions(featureFileName);
    }

    public void declineSuggestion(String idSuggestion, String idScenario) {
        declineSuggestion.declineSuggestion(Integer.parseInt(idSuggestion), Integer.parseInt(idScenario));
    }

    public void generateBAL(String filename) throws IOException {
        generateBAL.generateBAL(filename);
    }
    public void modifyActionName(String idSuggestion, String idScenario, String newActionName){
        modifySuggestion.modifyActionName(Integer.parseInt(idSuggestion), Integer.parseInt(idScenario),newActionName);
    }
    public void modifyActionType(String idSuggestion, String idScenario, String newType) {
        modifySuggestion.modifyActionType(Integer.parseInt(idSuggestion), Integer.parseInt(idScenario), newType);
    }

    public void modifyObjectName(String idSuggestion, String idScenario, String idObject, String newObjectName){
        modifySuggestion.modifyObjectName(Integer.parseInt(idSuggestion), Integer.parseInt(idScenario), Integer.parseInt(idObject), newObjectName);
    }

    public void modifyObjectType(String idSuggestion, String idScenario, String idObject, String newType) {
        modifySuggestion.modifyObjectType(Integer.parseInt(idSuggestion), Integer.parseInt(idScenario), Integer.parseInt(idObject), newType);
    }

    public void modifyObjectTypeById(String idSuggestion, String idScenario, String idObject, String idType) {
        modifySuggestion.modifyObjectTypeById(Integer.parseInt(idSuggestion), Integer.parseInt(idScenario), Integer.parseInt(idObject), Integer.parseInt(idType));
    }
    public void createCustomType(String typeName, Map<String,String> mAttributes){
        createCustomType.createType(typeName,mAttributes);
    }

    public void showTypes(){
        showTypes.showTypes();
    }

    public void modifyActionTypeById(String idSuggestion, String idScenario, String idType) {
        modifySuggestion.modifyActionTypeById(Integer.parseInt(idSuggestion), Integer.parseInt(idScenario), Integer.parseInt(idType));
    }
    public void addObject(String idSuggestion, String idScenario, String objectName, String idType){
        modifySuggestion.addObject(Integer.parseInt(idSuggestion), Integer.parseInt(idScenario), objectName, Integer.parseInt(idType));
    }
    public void removeObject(String idSuggestion, String idScenario, String idObject){
        modifySuggestion.removeObject(Integer.parseInt(idSuggestion), Integer.parseInt(idScenario),Integer.parseInt(idObject));
    }
}
