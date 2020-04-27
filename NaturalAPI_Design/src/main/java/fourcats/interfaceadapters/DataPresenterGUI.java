package fourcats.interfaceadapters;

import fourcats.datastructure.observer.Subject;
import fourcats.entities.Action;
import fourcats.entities.ObjectParam;
import fourcats.entities.Scenario;
import fourcats.entities.Type;
import fourcats.port.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataPresenterGUI extends Subject implements GenerateBALSuggestionsOutputPort, DeclineBALSuggestionOutputPort,
        ModifyBALSuggestionOutputPort, ShowTypesOutputPort, GenerateBALOutputPort, CreateCustomTypeOutputPort {
    String message;
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
    List<String> lTypes;
    boolean isSuggestionToAdd;
    static final String ERROR_MESSAGE = "Oh no! Something went wrong...";

    public DataPresenterGUI(){
        message = "";
        isSuggestionToAdd = false;
        lTypes = new ArrayList<>();
    }

    @Override
    public void showSuggestionsForScenario(Map<Integer, Scenario> mScenarios) {
        isSuggestionToAdd = true;
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
        isSuggestionToAdd = false;
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

    public boolean isSuggestionToAdd() {
        return isSuggestionToAdd;
    }

    public List<String> getlTypes() {
        return lTypes;
    }

    @Override
    public void showDeclinedSuggestion(Map<Integer, Scenario> mScenarios, boolean isOk) {
        if (isOk) {
            message = "Suggestion removed!";
        }
        else {
            message = ERROR_MESSAGE;
        }
        notifyObservers();
    }

    @Override
    public void showModifiedActionName(Map<Integer, Scenario> mScenarios, boolean isActionNameModified) {
        if (isActionNameModified){
            message = "Suggestion name successfully updated!";
            notifyObservers();
        }
        else{
            message = ERROR_MESSAGE;
            notifyObservers();
        }

    }

    @Override
    public void showModifiedActionType(Map<Integer, Scenario> mScenarios, boolean isActionTypeModified) {
        if (isActionTypeModified){
            message = "Action type successfully updated!";
            notifyObservers();
        }
        else{
            message = ERROR_MESSAGE;
        }
    }

    @Override
    public void showModifiedObjectName(Map<Integer, Scenario> mScenarios, boolean isObjectNameModified) {
        if (isObjectNameModified){
            message = "Object name of the suggestion successfully updated!";
        }
        else{
            message = ERROR_MESSAGE;
        }
        notifyObservers();
    }

    @Override
    public void showModifiedObjectType(Map<Integer, Scenario> mScenarios, boolean isObjectTypeModified) {
        if (isObjectTypeModified){
            message = "Object type of the suggestion successfully updated!";
        }
        else{
            message = ERROR_MESSAGE;
        }
        notifyObservers();
    }

    @Override
    public void showAddedObject(Map<Integer, Scenario> mScenarios, boolean isObjectAdded) {
        if (isObjectAdded){
            message = "Object successfully added!";
        }
        else{
            message = ERROR_MESSAGE+
                    "\nCheck if the object is already defined: that could be the problem!";
        }
        notifyObservers();
    }

    @Override
    public void showRemovedObject(Map<Integer, Scenario> mScenarios, boolean isObjectRemoved) {
        if (isObjectRemoved) {
            message = "Object successfully removed!";
        } else {
            message = ERROR_MESSAGE;
            notifyObservers();
        }
    }

    @Override
    public void showCustomTypeCreationStatus(boolean isCreated) {
        //TODO
    }

    @Override
    public void showGenerationStatus(boolean isBALGenerated) {
        if (isBALGenerated){
            message = "Hooray! The BAL was successfully created. You will find it in the BAL folder";
        }
        else{
            message = "Oh no! Something went wrong...";
        }
        notifyObservers();
    }

    @Override
    public void showTypes(Map<Integer, Type> mTypes) {
        if (mTypes.size()!=0 && mTypes.size()!=lTypes.size()) {
            for (Map.Entry<Integer, Type> mTy : mTypes.entrySet()) {
                 if (!lTypes.contains(mTy.getValue().getName()))
                    lTypes.add(mTy.getValue().getName());
            }
            //notifyObservers();
        }
        else{
            message = "No types defined yet!";
        }

    }
}
