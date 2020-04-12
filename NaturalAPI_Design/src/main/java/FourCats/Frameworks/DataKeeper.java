package FourCats.Frameworks;

import FourCats.Entities.Scenario;

import java.util.*;

public class DataKeeper {
    private Map<Integer, Scenario> mScenarios;


    public DataKeeper(){
        mScenarios = new HashMap<>();
    }

    public void createScenariosMap(Scenario scenario){
        mScenarios.put(mScenarios.size(),scenario);
    }

    public Map<Integer,Scenario> getScenarioMap(){
        return mScenarios;
    }


    public boolean removeAction(int idScenario, int idAction){
        try {
            mScenarios.get(idScenario).getActionsMap().remove(idAction);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


    public void clearScenariosMap(){
        mScenarios.clear();
    }



}
