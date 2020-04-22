package fourcats.frameworks;

import fourcats.entities.Scenario;

import java.util.*;

public class DataKeeper {
    private Map<Integer, Scenario> mScenarios;


    public DataKeeper(){
        mScenarios = new HashMap<>();
    }

    public void addScenarioToMap(Scenario scenario){
        mScenarios.put(mScenarios.size(),scenario);
    }

    public Map<Integer,Scenario> getScenarioMap(){
        return mScenarios;
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



}
