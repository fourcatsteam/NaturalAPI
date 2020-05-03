package fourcats.interfaceadapters;

import fourcats.SuggestionBDLAlgorithm.StrategyAlgorithm;
import fourcats.SuggestionBDLAlgorithm.SuggestionBDLAlgorithm;
import fourcats.datastructure.observer.Subject;
import fourcats.entities.*;
import fourcats.port.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataPresenterGUI extends Subject implements GenerateBALSuggestionsOutputPort, DeclineBALSuggestionOutputPort,
        ModifyBALSuggestionOutputPort, ShowTypesOutputPort, GenerateBALOutputPort, CreateCustomTypeOutputPort, AddBALSuggestionOutputPort, LoadBDLOutputPort {

    String message; //NOTE remember to always set to empty string ("") after notifyObservers
    String featurePath;
    String scenarioId;
    String suggestionId;
    String scenarioContent;
    String actionType;
    String actionName;
    String actor;
    ArrayList<String> lTypes;
    ArrayList<String> lObjectId;
    ArrayList<String> lObjectTypes;
    ArrayList<String> lObjectNames;
    boolean isSuggestionToAdd;
    boolean isOkOperation;
    boolean isSuggestionsRefreshNeeded;
    static final String ERROR_MESSAGE = "Oh no! Something went wrong...";

    private StrategyAlgorithm algorithm;

    private Bdl bdlLoaded;

    private boolean isPresentInBdl;

    public DataPresenterGUI(){
        message = "";
        scenarioId = "" + (-1);
        isSuggestionToAdd = false;
        isOkOperation = false;
        lTypes = new ArrayList<>();
        lObjectId = new ArrayList<>();
        lObjectTypes = new ArrayList<>();
        lObjectNames = new ArrayList<>();
        algorithm = new SuggestionBDLAlgorithm();
        bdlLoaded = null;
        isPresentInBdl = false;
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

    public boolean isPresentInBdl(){ return isPresentInBdl; }

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
                    "One of the files you selected was already on the system. No action has been taken on it.");
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
            if(bdlLoaded!=null) this.isPresentInBdl = algorithm.findActionInBdl(actionNameModified,bdlLoaded);
        }
        else{
            message = ERROR_MESSAGE;
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
            if(bdlLoaded!=null) this.isPresentInBdl = algorithm.findObjectInBdl(objectNameModified,bdlLoaded);
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
            message = "Object type of the suggestion successfully updated!";
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
            message = "Object successfully added!";
            isOkOperation = true;
        }
        else{
            message = ERROR_MESSAGE+
                    "\nCheck if the object is already defined: that could be the problem!";
            isOkOperation = false;
            notifyObservers();
        }
        message = "";
    }

    @Override
    public void showRemovedObject(Map<Integer, Scenario> mScenarios, boolean isObjectRemoved) {
        if (isObjectRemoved) {
            message = "Object successfully removed!";
            isOkOperation = true;
        } else {
            message = ERROR_MESSAGE+"while removing the object";
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
            message = ERROR_MESSAGE;
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
            message = ERROR_MESSAGE;
            isOkOperation = false;
            notifyObservers();
        }
        message = "";
    }

    private void showSuggestions(Map<Integer,Scenario> mScenarios){
        isSuggestionsRefreshNeeded = true; //this will allows the GUI tu reset all the suggestions widgets
        notifyObservers();
        isSuggestionsRefreshNeeded = false;
        isSuggestionToAdd = true; //tell the GUI that it's time to create new suggestions widgets
        for (Map.Entry<Integer,Scenario> mSc : mScenarios.entrySet()) {
            featurePath = mSc.getValue().getFeatureName();
            scenarioContent = "Scenario:" + mSc.getValue().getContent().replace("  ","\n");
            scenarioId = "" + mSc.getKey();
            actor = mSc.getValue().getActorName();
            for (Map.Entry<Integer, Action> mAc : mSc.getValue().getActionsMap().entrySet()) {
                int idCurrentObject=0;
                suggestionId = "" + mAc.getKey();
                actionName = mAc.getValue().getName();
                actionType = "" + mAc.getValue().getType();
                for (ObjectParam op : mAc.getValue().getObjectParams()){
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
        isSuggestionToAdd = false;
        //the following instruction is needed for a new suggestions generation after closing the SuggestionGenerated window
        //otherwise the GUI in a new initialization will read an old scenarioId value from the dataPresenter
        //and will fail to understand how to correctly initialize the widgets in initScenario()
        scenarioId = "" + (-1);
    }

    public boolean isBdlLoaded(){
        if(bdlLoaded!=null) {
            return true;
        }
        return false;
    }

    @Override
    public void showBDLOutput(String s,Bdl b) {
        this.message = s;
        this.bdlLoaded = b;
        notifyObservers();
        this.message = "";
    }
}