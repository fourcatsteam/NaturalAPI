package fourcats.interfaceadapters;

import fourcats.datastructure.observer.Subject;
import fourcats.entities.Action;
import fourcats.entities.Scenario;
import fourcats.port.DeclineBALSuggestionOutputPort;
import fourcats.port.GenerateBALOutputPort;
import fourcats.port.GenerateBALSuggestionsOutputPort;

import java.util.Map;

public class DataPresenter extends Subject implements GenerateBALSuggestionsOutputPort, DeclineBALSuggestionOutputPort, GenerateBALOutputPort {

    private String toShow;

    @Override
    public void showSuggestionsForScenario(Map<Integer,Scenario> mScenarios) {
        for (Map.Entry<Integer,Scenario> mSc : mScenarios.entrySet()) {
            toShow = "----SCENARIO: " + mSc.getKey() + ") " + mSc.getValue().getName() + "----";
            notifyObservers();
            for (Map.Entry<Integer,Action> mAc : mSc.getValue().getActionsMap().entrySet()) {
                toShow = mAc.getKey() + ") " + mAc.getValue().toString();
                notifyObservers();
            }
        }
    }

    @Override
    public void showErrorFileLoad() {
        System.out.println("Error! \n" +
                "Please make sure that the .feature file is inside the gherkin_documents folder and that the file name is correct.");
    }


    @Override
    public void showDeclinedSuggestion(Map<Integer,Scenario> mScenarios, boolean isOk) {
        if (isOk) {
            toShow = "Suggestion removed! This is the updated list of suggestions";
            notifyObservers();
            showSuggestionsForScenario(mScenarios);
        }
        else {
            toShow = "Oh no! Something went wrong, please check the id of the scenario and the id of the suggestion";
            notifyObservers();
        }

    }

    public String getDataToShow(){
        return toShow;
    }

    @Override
    public void showGenerationStatus(boolean isBALGenerated) {
        if (isBALGenerated){
            toShow = "Hooray! The BAL was successfully created. You will find it in the BAL folder";
        }
        else{
            toShow = "Oh no! Something went wrong...";
        }
        notifyObservers();
    }
}