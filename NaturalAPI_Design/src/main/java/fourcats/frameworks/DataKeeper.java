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



}
