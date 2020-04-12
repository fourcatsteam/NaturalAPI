package fourcats.interfaceadapters;

import fourcats.port.DeclineBALSuggestionInputPort;
import fourcats.port.GenerateBALSuggestionsInputPort;
import fourcats.usecaseinteractor.DeclineBALSuggestion;
import fourcats.usecaseinteractor.GenerateBAL;
import fourcats.usecaseinteractor.GenerateBALSuggestions;

import java.io.IOException;

public class Controller{
    private GenerateBALSuggestionsInputPort generateSuggestion;
    private DeclineBALSuggestionInputPort declineSuggestion;
    private GenerateBAL generateBAL;

    public Controller(GenerateBALSuggestions generateBALSugg, DeclineBALSuggestion declineBALSugg, GenerateBAL generateBal){
            this.generateSuggestion = generateBALSugg;
            this.declineSuggestion = declineBALSugg;
            this.generateBAL = generateBal;
    }

    public void generateSuggestions(String featureFileName) throws IOException {
        generateSuggestion.generateSuggestions(featureFileName);
    }

    public void declineSuggestion(String idSuggestion, String idScenario) {
        declineSuggestion.declineSuggestion(Integer.parseInt(idSuggestion), Integer.parseInt(idScenario));
    }

    public void generateBAL(String filename) throws IOException {
        generateBAL.generateBAL(filename);
    }
}
