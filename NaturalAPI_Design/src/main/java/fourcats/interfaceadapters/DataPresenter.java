package fourcats.interfaceadapters;

import fourcats.datastructure.observer.Subject;
import fourcats.entities.Action;
import fourcats.entities.Scenario;
import fourcats.entities.Type;
import fourcats.port.*;

import java.util.Map;

public class DataPresenter extends Subject implements GenerateBALSuggestionsOutputPort, DeclineBALSuggestionOutputPort,
        GenerateBALOutputPort, ModifyBALSuggestionOutputPort, CreateCustomTypeOutputPort, ShowTypesOutputPort,  AddBALSuggestionOutputPort {

    private String toShow;

    @Override
    public void showSuggestionsForScenario(Map<Integer,Scenario> mScenarios) {
        showSuggestions(mScenarios);
    }

    @Override
    public void showErrorFileLoad() {
        toShow = ("Error! \n" +
                "Please make sure that the .feature file is inside the gherkin_documents folder and that the file name is correct.");
    }


    @Override
    public void showDeclinedSuggestion(Map<Integer,Scenario> mScenarios, boolean isOk) {
        if (isOk) {
            toShow = "Suggestion removed! This is the updated list of suggestions";
            notifyObservers();
            showSuggestions(mScenarios);
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

    @Override
    public void showModifiedActionName(Map<Integer, Scenario> mScenarios, boolean isActionNameModified,String actionNameModified) {
        //Da implementare isBdlLoaded
        if (isActionNameModified){
            toShow = "Suggestion name successfully updated! This is the updated list of suggestions";
            notifyObservers();
            showSuggestions(mScenarios);
        }
        else{
            toShow = "Oh no! Something went wrong, please retry by checking the id of the scenario and the id of the suggestion for the suggestion you want to update.";
            notifyObservers();
        }

    }

    @Override
    public void showModifiedActionType(Map<Integer, Scenario> mScenarios, boolean isActionTypeModified) {
        if (isActionTypeModified){
            toShow = "Action type successfully updated! This is the updated list of suggestions";
            notifyObservers();
            showSuggestions(mScenarios);
        }
        else{
            toShow = "Oh no! Something went wrong, please retry by checking the id of the scenario, the id of the suggestion and the id of the parameter for the suggestion you want to update.";
            notifyObservers();
        }
    }


    @Override
    public void showModifiedObjectName(Map<Integer, Scenario> mScenarios, boolean isObjectNameModified, String objectNameModified) {
        //Da implementare isBdlLoaded
        if (isObjectNameModified){
            toShow = "Object name of the suggestion successfully updated! This is the updated list of suggestions";
            notifyObservers();
            showSuggestions(mScenarios);
        }
        else{
            toShow = "Oh no! Something went wrong, please retry by checking the id of the scenario, the id of the suggestion and the id of the object for the suggestion you want to update.";
            notifyObservers();
        }
    }

    @Override
    public void showModifiedObjectType(Map<Integer, Scenario> mScenarios, boolean isObjectTypeModified) {
        if (isObjectTypeModified){
            toShow = "Object type of the suggestion successfully updated! This is the updated list of suggestions";
            notifyObservers();
            showSuggestions(mScenarios);
        }
        else{
            toShow = "Oh no! Something went wrong, please retry by checking the id of the scenario, the id of the suggestion and the id of the object for the suggestion you want to update.";
            notifyObservers();
        }
    }

    @Override
    public void showAddedObject(Map<Integer, Scenario> mScenarios, boolean isObjectAdded) {
        if (isObjectAdded){
            toShow = "Object successfully added! This is the updated list of suggestions";
            notifyObservers();
            showSuggestions(mScenarios);
        }
        else{
            toShow = "Oh no! Something went wrong, please retry by checking the id of the scenario and the id of the suggestion for the suggestion you want to update." +
                    "\nCheck also if the object is already defined: that could be the problem!";
            notifyObservers();
        }
    }

    @Override
    public void showRemovedObject(Map<Integer, Scenario> mScenarios, boolean isObjectRemoved) {
        if (isObjectRemoved){
            toShow = "Object successfully removed! This is the updated list of suggestions";
            notifyObservers();
            showSuggestions(mScenarios);
        }
        else{
            toShow = "Oh no! Something went wrong, please retry by checking the id of the scenario, the id of the suggestion and the id of the object for the object you want to remove.";
            notifyObservers();
        }
    }

    @Override
    public void showTypes(Map<Integer, Type> mTypes) {
        if (mTypes.size()!=0) {
            for (Map.Entry<Integer, Type> mTy : mTypes.entrySet()) {
                toShow = "----TYPE: " + mTy.getKey() + ") " + mTy.getValue().getName() + "----";
                notifyObservers();
            }
        }
        else{
            toShow = "No types defined yet!";
            notifyObservers();
        }
    }

    @Override
    public void showCustomTypeCreationStatus(boolean isCreated) {
        if (isCreated) {
            toShow = "Custom type successfully created! This is the updated list of custom types";
        }
        else {
            toShow = "Error! Please check your input, probably the type is already defined: change the name or use the old one.";
        }
        notifyObservers();
    }

    @Override
    public void showAddedSuggestion(Map<Integer, Scenario> mScenarios, boolean isSuggestionAdded) {
        if (isSuggestionAdded) {
            toShow = "Suggestion successfully added! This is the updated list of suggestions";
            notifyObservers();
            showSuggestions(mScenarios);
        }
        else {
            toShow = "Oh no! Something went wrong, please retry by checking the id of the scenario or the id of the type for the suggestion you want to update.";
            notifyObservers();
        }
    }

    private void showSuggestions(Map<Integer,Scenario> mScenarios) {
        for (Map.Entry<Integer,Scenario> mSc : mScenarios.entrySet()) {
            toShow = "----SCENARIO: " + mSc.getKey() + ") " + mSc.getValue().getName() + "----";
            notifyObservers();
            for (Map.Entry<Integer,Action> mAc : mSc.getValue().getActionsMap().entrySet()) {
                toShow = mAc.getKey() + ") " + mAc.getValue().toString();
                notifyObservers();
            }
        }
    }
}
