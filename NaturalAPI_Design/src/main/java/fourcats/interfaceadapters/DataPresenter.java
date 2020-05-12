package fourcats.interfaceadapters;

import fourcats.datastructure.observer.Subject;
import fourcats.entities.*;
import fourcats.port.*;
import fourcats.suggestionbdlalgorithm.SuggestionFeedback;
import fourcats.suggestionbdlalgorithm.SuggestionFrequency;

import java.util.Map;

public class DataPresenter extends Subject implements GenerateBalSuggestionsOutputPort, DeclineBalSuggestionOutputPort,
        GenerateBalOutputPort, ModifyBalSuggestionOutputPort, CreateCustomTypeOutputPort, ShowTypesOutputPort,
        AddBalSuggestionOutputPort, LoadBdlOutputPort, RemoveBdlOutputPort {

    private String toShow;
    private SuggestionFeedback algorithm;
    private boolean isBdlLoaded;

    @Override
    public void showSuggestionsForScenario(Map<Integer,Scenario> mScenarios) {
        showSuggestions(mScenarios);
    }

    @Override
    public void showErrorFileLoad(boolean isFileDuplicated) {
        if (isFileDuplicated){
            toShow = ("Warning!\n" +
                    "One of the selected files is already present in the system or contains a duplicate scenario.");
        }
        else {
            toShow = ("Error! \n" +
                    "Please make sure that the path you specified for the .feature file is correct.");
        }
        notifyObservers();
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
            toShow = "Hooray! The BAL was successfully created. You will find it in the folder you specified";
        }
        else{
            toShow = "Oh no! Something went wrong...";
        }
        notifyObservers();
    }

    @Override
    public void showModifiedActionName(Map<Integer, Scenario> mScenarios, boolean isActionNameModified,String actionNameModified) {
        if (isActionNameModified){
            toShow = "Suggestion name successfully updated! This is the updated list of suggestions";
            notifyObservers();
            showSuggestions(mScenarios);
        }
        else{
            toShow = "Oh no! Something went wrong, please retry by checking the id of the scenario and the id of the suggestion for the suggestion you want to update." +
                    "\nIt's also possible that the name has already been used. No changes have been made";
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
            toShow = "Oh no! Something went wrong, please retry by checking the id of the scenario or the id of the type for the suggestion you want to update."+
                    "\nIt's also possible that the name has already been used. No changes have been made";
            notifyObservers();
        }
    }

    private void showSuggestions(Map<Integer,Scenario> mScenarios) {
        for (Map.Entry<Integer,Scenario> mSc : mScenarios.entrySet()) {
            toShow = "----SCENARIO: " + mSc.getKey() + ") " + mSc.getValue().getName() + "----";
            notifyObservers();
            if (mSc.getValue().getActionsMap().isEmpty()){
                toShow = "No additional suggestion found";
                notifyObservers();
            }
            else {
                if (isBdlLoaded) showActionObjectsWithFrequency(mSc);
                else {
                    for (Map.Entry<Integer, Action> mAc : mSc.getValue().getActionsMap().entrySet()) {
                        //do not print actions that have been removed or that do not have a name
                        if (!mAc.getValue().getName().equals("")) {
                            if(mAc.getValue().getName().startsWith("@"))
                                toShow = mAc.getKey() + ") " + mAc.getValue().getName();
                            else
                                toShow = mAc.getKey() + ") " + mAc.getValue().toString();
                            notifyObservers();
                        }
                    }
                }
            }
        }
    }

    private void showActionObjectsWithFrequency(Map.Entry<Integer,Scenario> mScenario) {
        //for each action
        String action = "";
        StringBuilder objects = new StringBuilder();
        for (Map.Entry<Integer,Action> mAc : mScenario.getValue().getActionsMap().entrySet()) {
            //do not print actions that have been removed or that do not have a name
            if (!mAc.getValue().getName().equals("")) {
                //build action string
                if(mAc.getValue().getName().startsWith("@")) { //-->no suggestion found for the step
                    toShow = mAc.getKey() + ") " + mAc.getValue().getName();
                }
                else {
                    action = mAc.getKey() + ") " + mAc.getValue().getType() + " " + mAc.getValue().getName() +
                            "[" + algorithm.findActionInBdl(mAc.getValue().getName()) + "] ";
                    //build object string
                    objects.setLength(0); //clear the stringBuilder
                    boolean isFirstObject = true;
                    for (ObjectParam obj : mAc.getValue().getObjectParams()) {
                        if (!isFirstObject) {
                            objects.append(", "); //add a comma before new object
                        }
                        objects.append(obj.getType() + " " + obj.getName() + "[" +
                                algorithm.findObjectInBdl(obj.getName()) + "]");
                        isFirstObject = false;
                    }
                    toShow = action + "(" + objects + ")"; //build combined string (action + objects)
                }
                notifyObservers();
            }
        }

    }

    @Override
    public void showBDLOutput(Bdl bdl, boolean isLoaded) {
        if (isLoaded){
            algorithm = new SuggestionFrequency(bdl);
            toShow = "BDL Loaded successfully";
        }
        else{
            toShow = "Error while loading BDL."+
                    "\nPlease check the path you entered and that the three files that make up the BDL are in the same dir.";
        }
        isBdlLoaded = isLoaded;
        notifyObservers();
    }

    @Override
    public void showRemoveBdlStatus() {
        toShow = "Done! No BDL currently loaded.";
        isBdlLoaded = false;
        notifyObservers();
        toShow = "";
    }

    public boolean isBdlLoaded() {
        return isBdlLoaded;
    }
}
