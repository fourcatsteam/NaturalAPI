package fourcats.interfaceadapters;

import fourcats.datastructure.observer.Subject;
import fourcats.entities.Action;
import fourcats.entities.ObjectParam;
import fourcats.entities.Scenario;
import fourcats.port.DeclineBALSuggestionOutputPort;
import fourcats.port.GenerateBALSuggestionsOutputPort;

import java.util.Map;

public class DataPresenterGUI extends Subject implements GenerateBALSuggestionsOutputPort, DeclineBALSuggestionOutputPort {
    String message = "";
    String scenarioId;
    String suggestionId;
    String scenarioContent;
    String actionId;
    String actionType;
    String actionName;
    String objectId;
    String objectType;
    String objectName;
    String actor;

    @Override
    public void showSuggestionsForScenario(Map<Integer, Scenario> mScenarios) {
        for (Map.Entry<Integer,Scenario> mSc : mScenarios.entrySet()) {
            scenarioContent = "Scenario:" + mSc.getValue().getContent().replace("  ","\n");
            scenarioId = "" + mSc.getKey();
            actor = mSc.getValue().getActorName();
            for (Map.Entry<Integer, Action> mAc : mSc.getValue().getActionsMap().entrySet()) {
                 int idCurrentObject=0;
                 actionId = "" + mAc.getKey();
                 actionName = mAc.getValue().getName();
                 actionType = "" + mAc.getValue().getType();
                 suggestionId = "" + mAc.getKey();
                 for (ObjectParam op : mAc.getValue().getObjectParams()){
                     objectId = "" + idCurrentObject;
                     objectType = "" + op.getType();
                     objectName = op.getName();
                     notifyObservers();
                     idCurrentObject++;
                 }
            }
        }
    }

    @Override
    public void showErrorFileLoad() {
        message = ("Error! \n" +
                "Please make sure that the .feature file is inside the gherkin_documents folder and that the file name is correct.");
        notifyObservers();
    }

    public String getActionId() {
        return actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public String getActionType() {
        return actionType;
    }

    public String getMessage() {
        return message;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getScenarioContent() {
        return scenarioContent;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public String getActor() {
        return actor;
    }

    public String getSuggestionId() {
        return suggestionId;
    }

    public String getObjectId() {
        return objectId;
    }

    @Override
    public void showDeclinedSuggestion(Map<Integer, Scenario> mScenarios, boolean isOk) {
        if (isOk) {
            message = "Suggestion removed! This is the updated list of suggestions";
            notifyObservers();
            //showSuggestionsForScenario(mScenarios);
        }
        else {
            message = "Oh no! Something went wrong, please check the id of the scenario and the id of the suggestion";
            notifyObservers();
        }
    }
}
