package fourcats.interfaceadapters;

import fourcats.port.*;
import fourcats.usecaseinteractor.*;

import java.io.IOException;
import java.util.Map;

public class Controller{
    private GenerateBALSuggestionsInputPort generateSuggestion;
    private DeclineBALSuggestionInputPort declineSuggestion;
    private GenerateBALInputPort generateBAL;
    private ModifyBALSuggestionInputPort modifySuggestion;
    private CreateCustomTypeInputPort createCustomType;

    public Controller(GenerateBALSuggestionsInputPort generateBALSugg, DeclineBALSuggestionInputPort declineBALSugg,
                      GenerateBALInputPort generateBal, ModifyBALSuggestionInputPort modifyBALSugg,
                      CreateCustomTypeInputPort createCustomType){
            this.generateSuggestion = generateBALSugg;
            this.declineSuggestion = declineBALSugg;
            this.generateBAL = generateBal;
            this.modifySuggestion = modifyBALSugg;
            this.createCustomType = createCustomType;
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

    public void modifyObjectName(String idSuggestion, String idScenario, String idObject, String newObjectName){
        modifySuggestion.modifyObjectName(Integer.parseInt(idSuggestion), Integer.parseInt(idScenario), Integer.parseInt(idObject), newObjectName);
    }

    public void modifyObjectType(String idSuggestion, String idScenario, String idObject, String newType) {
        modifySuggestion.modifyObjectType(Integer.parseInt(idSuggestion), Integer.parseInt(idScenario), Integer.parseInt(idObject), newType);
    }
    public void createCustomType(String typeName, Map<String,String> mAttributes){
        createCustomType.createType(typeName,mAttributes);
    }
}
