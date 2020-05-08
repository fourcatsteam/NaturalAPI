package fourcats.interfaceadapters;

import fourcats.port.*;

import java.util.List;
import java.util.Map;

public class Controller{
    private GenerateBalSuggestionsInputPort generateSuggestion;
    private DeclineBalSuggestionInputPort declineSuggestion;
    private GenerateBalInputPort generateBAL;
    private ModifyBalSuggestionInputPort modifySuggestion;
    private CreateCustomTypeInputPort createCustomType;
    private ShowTypesInputPort showTypes;
    private AddBalSuggestionInputPort addSuggestion;
    private LoadBdlInputPort loadBdl;
    private RemoveBdlInputPort removeBdl;

    public Controller(GenerateBalSuggestionsInputPort generateBALSugg, DeclineBalSuggestionInputPort declineBALSugg,
                      GenerateBalInputPort generateBal, ModifyBalSuggestionInputPort modifyBALSugg,
                      CreateCustomTypeInputPort createCustomType, ShowTypesInputPort showTypes,
                      AddBalSuggestionInputPort addSuggestion, LoadBdlInputPort loadBdl, RemoveBdlInputPort removeBdl){
            this.generateSuggestion = generateBALSugg;
            this.declineSuggestion = declineBALSugg;
            this.generateBAL = generateBal;
            this.modifySuggestion = modifyBALSugg;
            this.createCustomType = createCustomType;
            this.showTypes = showTypes;
            this.addSuggestion = addSuggestion;
            this.loadBdl = loadBdl;
            this.removeBdl = removeBdl;
    }

    public void generateSuggestions(List<String> lFeatureFilePaths, boolean isForNewBal) {
        generateSuggestion.generateSuggestions(lFeatureFilePaths, isForNewBal);
    }

    public void declineSuggestion(String idSuggestion, String idScenario) {
        declineSuggestion.declineSuggestion(Integer.parseInt(idSuggestion), Integer.parseInt(idScenario));
    }

    public void generateBAL(String filePath) {
        generateBAL.generateBAL(filePath);
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
    public void addSuggestion(String idScenario, String suggestionName, String suggestionType){
        addSuggestion.addSuggestion(Integer.parseInt(idScenario),suggestionName,suggestionType);
    }
    public void addSuggestionByIdType(String idScenario, String suggestionName, String idType){
        addSuggestion.addSuggestionByIdType(Integer.parseInt(idScenario),suggestionName,Integer.parseInt(idType));
    }

    public void loadBdl(String[] namebdl) {
        loadBdl.loadBdlFiles(namebdl);
    }

    public void removeBdl(){
        removeBdl.removeLoadedBdl();
    }


}
