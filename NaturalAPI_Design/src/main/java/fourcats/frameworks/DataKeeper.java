package fourcats.frameworks;

import fourcats.entities.*;
import org.apache.lucene.util.SetOnce;

import java.util.*;

public class DataKeeper {
    private final List<String> lDefaultTypes = Arrays.asList("string","int","float","double","bool");
    private Map<Integer, Scenario> mScenarios;
    private Map<Integer, Type> mTypes;
    private Bdl bdl;

    public DataKeeper(){
        initTypesMap();
    }

    private void initTypesMap(){
        mScenarios = new HashMap<>();
        mTypes = new HashMap<>();
        for(int i = 0; i < lDefaultTypes.size(); i++ ){
            mTypes.put(i, new Type(lDefaultTypes.get(i)));
        }
    }

    public void addScenarioToMap(Scenario scenario){
        //first check if the scenario is already in the map by checking the scenario content
        for (Map.Entry<Integer, Scenario> mSc : mScenarios.entrySet()) {
            if (mSc.getValue().getContent().equals(scenario.getContent())) {
                throw new SetOnce.AlreadySetException();
            }
        }
        //check if action name or step already exists. If so, remove it from scenario
        List<Integer> actionKeysToRemove = new ArrayList<>();
        for (Map.Entry<Integer,Action> acEntry : scenario.getActionsMap().entrySet()){
            if (isActionNamePresent(acEntry.getValue().getName()) || isStepPresent(acEntry.getValue().getStep()))
                actionKeysToRemove.add(acEntry.getKey());
        }
        actionKeysToRemove.forEach(key->scenario.getActionsMap().remove(key));

        mScenarios.put(mScenarios.size(), scenario);
    }

    public Map<Integer,Scenario> getScenarioMap(){
        return mScenarios;
    }
    public Map<Integer,Type> getTypeMap(){
        return mTypes;
    }

    public void addBdl(Bdl bdl){
        this.bdl = bdl;
    }

    public void removeBdl() {
        this.bdl = null;
    }

    public Bdl getBdl(){
        return bdl;
    }

    public void removeAction(int idScenario, int idAction){
        //only deletes the name and objectParams of the action so that the step will remain present in the BAL
        mScenarios.get(idScenario).getActionsMap().get(idAction).setName("");
        mScenarios.get(idScenario).getActionsMap().get(idAction).getObjectParams().clear();

    }

    public void clearScenariosMap(){
        mScenarios.clear();
        initTypesMap();
    }

    public void updateActionName(int idScenario, int idAction, String newActionName){
        if (!isActionNamePresent(newActionName))
            mScenarios.get(idScenario).getActionsMap().get(idAction).setName(newActionName);
        else
            throw new SetOnce.AlreadySetException();
    }

    public void updateActionType(int idScenario, int idAction, String newActionType){
        for (Type ty : mTypes.values()){
            if (ty.getName().equals(newActionType)) {
                mScenarios.get(idScenario).getActionsMap().get(idAction).setType(ty);
                return;
            }
        }

        mScenarios.get(idScenario).getActionsMap().get(idAction).setType(newActionType);
    }

    public void updateObjectName(int idScenario, int idAction, int idObject, String newObjectName){
        mScenarios.get(idScenario).getActionsMap().get(idAction).getObjectParams().get(idObject).setName(newObjectName);
    }

    public void updateObjectType(int idScenario, int idAction, int idObject, String newObjectType){
        for (Type ty : mTypes.values()){
            if (ty.getName().equals(newObjectType)) {
                mScenarios.get(idScenario).getActionsMap().get(idAction).getObjectParams().get(idObject).setType(ty);
                return;
            }
        }
        mScenarios.get(idScenario).getActionsMap().get(idAction).getObjectParams().get(idObject).setType(newObjectType);
    }

    public void addCustomType(String typeName, Map<String, String> mAttributes) {
        for (Type ty : mTypes.values()){
            if (ty.getName().equals(typeName)) {
                throw new SetOnce.AlreadySetException();
            }
        }
        mTypes.put(mTypes.size(),new Type(typeName,mAttributes));

    }

    public void updateObjectTypeById(int idScenario, int idAction, int idObject, int idType) {
        if(mTypes.get(idType)!=null) {
            mScenarios.get(idScenario).getActionsMap().get(idAction).getObjectParams().get(idObject).setType(mTypes.get(idType));
        }
        else {
            throw new NoSuchElementException();
        }
    }

    public void updateActionTypeById(int idScenario, int idAction, int idType) {
        if(mTypes.get(idType)!=null) {
            mScenarios.get(idScenario).getActionsMap().get(idAction).setType(mTypes.get(idType));
        }
        else {
            throw new NoSuchElementException();
        }
    }

    public void addObject(int idScenario, int idAction, String objectName, int idType) {
        for (ObjectParam op : mScenarios.get(idScenario).getActionsMap().get(idAction).getObjectParams()){
            if (op.getName().equals(objectName)){
                throw new SetOnce.AlreadySetException();
            }
        }
        mScenarios.get(idScenario).getActionsMap().get(idAction).addObjectParam(new ObjectParam(objectName, mTypes.get(idType)));
    }

    public void removeObject(int idScenario, int idAction, int idObject) {
        mScenarios.get(idScenario).getActionsMap().get(idAction).removeObjectParam(idObject);
    }

    public void addSuggestion(int idScenario, String suggestionName, String suggestionType) {
        if (!isActionNamePresent(suggestionName)) {
            int idSuggestion = getMaxKeyInActionMap(mScenarios.get(idScenario).getActionsMap()) + 1;
            for (Type ty : mTypes.values()) {
                if (ty.getName().equals(suggestionType)) {
                    mScenarios.get(idScenario).getActionsMap().put(idSuggestion, new Action(suggestionName, ty));
                    return;
                }
            }
            mScenarios.get(idScenario).getActionsMap().put(idSuggestion, new Action(suggestionName, suggestionType));
        }
        else
            throw new SetOnce.AlreadySetException();
    }

    public void addSuggestionByIdType(int idScenario, String suggestionName, int idType) {
        if(!isActionNamePresent(suggestionName)) {
            if (mTypes.get(idType) != null) {
                int idSuggestion = getMaxKeyInActionMap(mScenarios.get(idScenario).getActionsMap()) + 1;
                mScenarios.get(idScenario).getActionsMap().put(idSuggestion, new Action(suggestionName, mTypes.get(idType)));
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    private int getMaxKeyInActionMap(Map<Integer,Action> mActions){
        int maxKeyInMap = 0;
        // Iterate through hashmap to find the maxKey in the map
        for (Map.Entry<Integer, Action> entry : mActions.entrySet()) {
            if (maxKeyInMap<entry.getKey()) {
                maxKeyInMap = entry.getKey();
            }
        }
        return maxKeyInMap;
    }

    private boolean isActionNamePresent(String actionName) {
        if (!actionName.equals("")) {
            for (Map.Entry<Integer, Scenario> mSc : mScenarios.entrySet()) {
                for (Action ac : mSc.getValue().getActions()) {
                    if (ac.getName().equals(actionName))
                        return true;
                }
            }
        }
        return false;
    }
    private boolean isStepPresent(String step){
        for (Map.Entry<Integer, Scenario> mSc : mScenarios.entrySet()) {
            for (Action ac : mSc.getValue().getActions()) {
                if (ac.getStep().equals(step))
                    return true;
            }
        }
        return false;
    }
}
