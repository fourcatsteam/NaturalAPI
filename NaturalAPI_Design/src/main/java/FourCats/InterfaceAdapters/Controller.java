package FourCats.InterfaceAdapters;

import FourCats.Port.DeclineBALSuggestionInputPort;
import FourCats.Port.GenerateBALSuggestionsInputPort;
import FourCats.UseCaseInteractor.DeclineBALSuggestion;
import FourCats.UseCaseInteractor.GenerateBAL;
import FourCats.UseCaseInteractor.GenerateBALSuggestions;

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
