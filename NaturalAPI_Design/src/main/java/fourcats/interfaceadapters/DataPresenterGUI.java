package fourcats.interfaceadapters;

import fourcats.suggestionbdlalgorithm.SuggestionFeedback;
import fourcats.suggestionbdlalgorithm.SuggestionFrequency;
import fourcats.datastructure.observer.Subject;
import fourcats.entities.*;
import fourcats.port.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataPresenterGUI extends Subject implements GenerateBalSuggestionsOutputPort, DeclineBalSuggestionOutputPort,
        ModifyBalSuggestionOutputPort, ShowTypesOutputPort, GenerateBalOutputPort, CreateCustomTypeOutputPort,
        AddBalSuggestionOutputPort, LoadBdlOutputPort, RemoveBdlOutputPort {

    private String message; //NOTE remember to always set to empty string ("") after notifyObservers
    private String featurePath;
    private String scenarioId;
    private String suggestionId;
    private String scenarioContent;
    private String actionType;
    private String actionName;
    private String actor;
    private final ArrayList<String> lTypes;
    private final ArrayList<String> lObjectId;
    private final ArrayList<String> lObjectTypes;
    private final ArrayList<String> lObjectNames;
    private boolean isSuggestionToAdd;
    private boolean isOkOperation;
    private boolean isSuggestionsRefreshNeeded;
    private boolean isBdlLoaded;
    static final String ERROR_MESSAGE = "Oh no! Something went wrong...";

    private SuggestionFeedback algorithm;
    private int frequencyInBdl;


    public DataPresenterGUI(){
        message = "";
        scenarioId = "" + (-1);
        isSuggestionToAdd = false;
        isOkOperation = false;
        isBdlLoaded = false;
        lTypes = new ArrayList<>();
        lObjectId = new ArrayList<>();
        lObjectTypes = new ArrayList<>();
        lObjectNames = new ArrayList<>();
        frequencyInBdl = 0;
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

    public boolean isSuggestionToAdd() {
        return isSuggestionToAdd;
    }

    public List<String> getlTypes() {
        return lTypes;
    }

    public List<String> getlObjectId() {
        return lObjectId;
    }

    public List<String> getlObjectTypes() {
        return lObjectTypes;
    }

    public List<String> getlObjectNames() {
        return lObjectNames;
    }

    public boolean isOkOperation() {
        return isOkOperation;
    }

    public boolean isSuggestionsRefreshNeeded() {
        return isSuggestionsRefreshNeeded;
    }

    public int getFrequencyInBdl(){ return frequencyInBdl; }


    public String getFeaturePath() {
        return featurePath;
    }

    @Override
    public void showSuggestionsForScenario(Map<Integer, Scenario> mScenarios) {
        showSuggestions(mScenarios);
    }

    @Override
    public void showErrorFileLoad(boolean isFileDuplicated) {
        if (isFileDuplicated){
            isOkOperation = true;
            message = ("Warning!\n" +
                    "One of the selected files is already present in the system or contains a duplicate scenario.");

        }
        else {
            isOkOperation = false;
            message = ("Error! \n" +
                    "Please make sure that the .feature file is inside the gherkin_documents folder and that the file name is correct.");
        }
        notifyObservers();
        message = "";
    }

    @Override
    public void showDeclinedSuggestion(Map<Integer, Scenario> mScenarios, boolean isOk) {
        if (isOk) {
            message = "Suggestion removed!";
            isOkOperation = true;
        }
        else {
            message = ERROR_MESSAGE;
            isOkOperation = false;
            notifyObservers();
        }
        message = "";
    }

    @Override
    public void showModifiedActionName(Map<Integer, Scenario> mScenarios, boolean isActionNameModified,String actionNameModified) {
        if (isActionNameModified){
            isOkOperation = true;
            if(isBdlLoaded) frequencyInBdl = algorithm.findActionInBdl(actionNameModified);
        }
        else{
            message = ERROR_MESSAGE + "\nThis name is probably already used. Change it!";
            isOkOperation = false;
        }
        notifyObservers();
        message = "";
    }

    @Override
    public void showModifiedActionType(Map<Integer, Scenario> mScenarios, boolean isActionTypeModified) {
        if (isActionTypeModified){
            message = "Action type successfully updated!";
            isOkOperation = true;
        }
        else{
            message = ERROR_MESSAGE;
            isOkOperation = false;
            notifyObservers();
        }
        message = "";

    }

    @Override
    public void showModifiedObjectName(Map<Integer, Scenario> mScenarios, boolean isObjectNameModified,String objectNameModified) {
        if (isObjectNameModified){
            isOkOperation = true;
            if(isBdlLoaded) this.frequencyInBdl = algorithm.findObjectInBdl(objectNameModified);
        }
        else {
            message = ERROR_MESSAGE;
            isOkOperation = false;
        }
        notifyObservers();
        message = "";
    }

    @Override
    public void showModifiedObjectType(Map<Integer, Scenario> mScenarios, boolean isObjectTypeModified) {
        if (isObjectTypeModified){
            message = "Parameter type of the suggestion successfully updated!";
            isOkOperation = true;
        }
        else{
            message = ERROR_MESSAGE;
            isOkOperation = false;
            notifyObservers();
        }
        message = "";
    }

    @Override
    public void showAddedObject(Map<Integer, Scenario> mScenarios, boolean isObjectAdded) {
        if (isObjectAdded){
            message = "Parameter successfully added!";
            isOkOperation = true;
        }
        else{
            message = ERROR_MESSAGE+
                    "\nCheck if the parameter is already defined: that could be the problem!";
            isOkOperation = false;
            notifyObservers();
        }
        message = "";
    }

    @Override
    public void showRemovedObject(Map<Integer, Scenario> mScenarios, boolean isObjectRemoved) {
        if (isObjectRemoved) {
            message = "Parameter successfully removed!";
            isOkOperation = true;
        } else {
            message = ERROR_MESSAGE+"while removing the parameter";
            isOkOperation = false;
            notifyObservers();
        }
        message = "";
    }

    @Override
    public void showCustomTypeCreationStatus(boolean isCreated) {
        if (isCreated){
            message = "Custom type successfully created!";
            isOkOperation = true;
        }
        else{
            message = ERROR_MESSAGE +
                    "\nCheck if the type is already defined: that could be the problem!";
            isOkOperation = false;
        }
        notifyObservers();
        message="";
    }

    @Override
    public void showGenerationStatus(boolean isBALGenerated) {
        if (isBALGenerated){
            message = "Hooray! The BAL was successfully created. You will find it in the folder you have selected";
            isOkOperation = true;
        }
        else{
            message = ERROR_MESSAGE;
            isOkOperation = false;
        }
        notifyObservers();
        message = "";
    }

    @Override
    public void showTypes(Map<Integer, Type> mTypes) {
        if (mTypes.size()!=0 && mTypes.size()!=lTypes.size()) {
            lTypes.clear();
            for (Map.Entry<Integer, Type> mTy : mTypes.entrySet()) {
                 if (!lTypes.contains(mTy.getValue().getName()))
                    lTypes.add(mTy.getValue().getName());
            }
        }
    }

    @Override
    public void showAddedSuggestion(Map<Integer, Scenario> mScenarios, boolean isSuggestionAdded) {
        if (isSuggestionAdded){
            showSuggestions(mScenarios);
        }
        else{
            message = ERROR_MESSAGE + "\nThe name is probably already used. No action added.";
            isOkOperation = false;
            notifyObservers();
        }
        message = "";
    }

    private void showSuggestions(Map<Integer,Scenario> mScenarios){
        //the following instruction (*) is needed for new suggestions generation after closing the SuggestionGenerated window
        //otherwise the GUI in a new initialization will read an old scenarioId value from the dataPresenter
        //and will fail to understand how to correctly initialize the widgets in initScenario()
        scenarioId = "" + (-1); //(*)
        isSuggestionsRefreshNeeded = true; //this will allows the GUI tu reset all the suggestions widgets
        notifyObservers();
        isSuggestionsRefreshNeeded = false;
        isSuggestionToAdd = true; //tell the GUI that it's time to create new suggestions widgets
        for (Map.Entry<Integer,Scenario> mSc : mScenarios.entrySet()) {
            featurePath = mSc.getValue().getFeatureName();
            scenarioContent = "Scenario:" + mSc.getValue().getContent();
            scenarioId = "" + mSc.getKey();
            actor = mSc.getValue().getActorName();
            if (mSc.getValue().getActionsMap().isEmpty()){ //scenario has no action
                actionName = null;
                notifyObservers();
            }
            else {
                getActionProperties(mSc);
            }
        }
        isSuggestionToAdd = false;
    }

    private void getActionProperties(Map.Entry<Integer, Scenario> mSc) {
        for (Map.Entry<Integer, Action> mAc : mSc.getValue().getActionsMap().entrySet()) {
            //do not print actions that have been removed or that do not have a name
            if (!mAc.getValue().getName().equals("")) {
                int idCurrentObject = 0;
                suggestionId = "" + mAc.getKey();
                actionName = mAc.getValue().getName();
                if (isBdlLoaded) this.frequencyInBdl = algorithm.findActionInBdl(actionName);
                actionType = "" + mAc.getValue().getType();
                for (ObjectParam op : mAc.getValue().getObjectParams()) {
                    lObjectId.add("" + idCurrentObject);
                    lObjectTypes.add("" + op.getType());
                    lObjectNames.add(op.getName());
                    idCurrentObject++;
                }
                notifyObservers();
                lObjectId.clear();
                lObjectTypes.clear();
                lObjectNames.clear();
            }
        }
    }

    public int getWordObjectFrequency(String s){
        return this.algorithm.findObjectInBdl(s);
    }

    public boolean isBdlLoaded(){
        return isBdlLoaded;
    }

    @Override
    public void showBDLOutput(Bdl bdl, boolean isLoaded) {
        if (isLoaded){
            algorithm = new SuggestionFrequency(bdl);
            message = "BDL Loaded successfully";
        }
        else{
            message = "Error while loading BDL. Check that the files belong to the same BDL";
        }
        isBdlLoaded = isLoaded;
        notifyObservers();
        message = "";
    }

    @Override
    public void showRemoveBdlStatus() {
        message = "Done! No BDL currently loaded.";
        isBdlLoaded = false;
        notifyObservers();
        message = "";
    }
}