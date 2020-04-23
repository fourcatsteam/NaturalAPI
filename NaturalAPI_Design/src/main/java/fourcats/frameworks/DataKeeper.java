package fourcats.frameworks;

import fourcats.entities.Scenario;
import fourcats.entities.Type;

import java.util.*;

public class DataKeeper {
    private Map<Integer, Scenario> mScenarios;
    private Map<Integer, Type> mType;


    public DataKeeper(){
        mScenarios = new HashMap<>();
        mType = new HashMap<>();
    }

    public void addScenarioToMap(Scenario scenario){
        mScenarios.put(mScenarios.size(),scenario);
    }

    public Map<Integer,Scenario> getScenarioMap(){
        return mScenarios;
    }
    public Map<Integer,Type> getTypeMap(){
        return mType;
    }


    public void removeAction(int idScenario, int idAction){
        try {
            mScenarios.get(idScenario).getActionsMap().remove(idAction);
        }
        catch (NullPointerException e){
            throw e;
        }
    }

    public void clearScenariosMap(){
        mScenarios.clear();
    }

    public void updateActionName(int idScenario, int idAction, String newActionName){
        try{
            mScenarios.get(idScenario).getActionsMap().get(idAction).setName(newActionName);
        }
        catch (NullPointerException e){
            throw e;
        }
    }

    public void updateObjectName(int idScenario, int idAction, int idObject, String newObjectName){
        try{
            mScenarios.get(idScenario).getActionsMap().get(idAction).getObjectParams().get(idObject).setName(newObjectName);
        }
        catch (Exception e){
            throw e;
        }
    }

    public void updateObjectType(int idScenario, int idAction, int idObject, String newObjectType){
        for (Type ty : mType.values()){
            if (ty.getName().equals(newObjectType)) {
                try {
                    mScenarios.get(idScenario).getActionsMap().get(idAction).getObjectParams().get(idObject).setType(ty);
                    return;
                }
                catch (Exception e){
                    throw e;
                }
            }
        }
        try{
            mScenarios.get(idScenario).getActionsMap().get(idAction).getObjectParams().get(idObject).setType(newObjectType);
        }
        catch (Exception e){
            throw e;
        }
    }

    public void addCustomType(String typeName, Map<String, String> mAttributes) {
        if (!mType.containsKey(typeName))
            mType.put(mType.size(),new Type(typeName,mAttributes));
    }
}
