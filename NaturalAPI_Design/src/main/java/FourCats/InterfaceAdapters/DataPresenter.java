package FourCats.InterfaceAdapters;

import FourCats.DataStructure.Observer.Subject;
import FourCats.Entities.Action;
import FourCats.Entities.Scenario;
import FourCats.Port.DeclineBALSuggestionOutputPort;
import FourCats.Port.GenerateBALOutputPort;
import FourCats.Port.GenerateBALSuggestionsOutputPort;

import java.util.List;
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
    public void showDeclinedSuggestion(Map<Integer,Scenario> mScenarios, boolean isOk) {
        if (isOk) {
            toShow = "Suggestion removed! This is the updated list of suggestions";
            notifyObservers();
            showSuggestionsForScenario(mScenarios);
        }
        else {
            toShow = "Oh no! Something went wrong, please check the id of the scenario and the id of the suggestion";
        }
        notifyObservers();

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
