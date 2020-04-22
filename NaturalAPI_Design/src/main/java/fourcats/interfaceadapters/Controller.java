package fourcats.interfaceadapters;

import fourcats.port.DeclineBALSuggestionInputPort;
import fourcats.port.GenerateBALSuggestionsInputPort;
import fourcats.port.ModifyBALSuggestionInputPort;
import fourcats.usecaseinteractor.DeclineBALSuggestion;
import fourcats.usecaseinteractor.GenerateBAL;
import fourcats.usecaseinteractor.GenerateBALSuggestions;
import fourcats.usecaseinteractor.ModifyBALSuggestion;

import java.io.IOException;

public class Controller{
    private GenerateBALSuggestionsInputPort generateSuggestion;
    private DeclineBALSuggestionInputPort declineSuggestion;
    private GenerateBAL generateBAL;
    private ModifyBALSuggestionInputPort modifySuggestion;

    public Controller(GenerateBALSuggestions generateBALSugg, DeclineBALSuggestion declineBALSugg, GenerateBAL generateBal, ModifyBALSuggestion modifyBALSugg){
            this.generateSuggestion = generateBALSugg;
            this.declineSuggestion = declineBALSugg;
            this.generateBAL = generateBal;
            this.modifySuggestion = modifyBALSugg;
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
}
