package FourCats.Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Scenario {
    private String name;
    private String content;
    private Map<Integer,Action> mAction; //suggestions for the scenario
    private String actorName;

    public Scenario(String scenarioName, Map<Integer,Action> mAction, String content, String actorName) {
        this.name = scenarioName;
        this.mAction = mAction;
        this.content = content;
        this.actorName = actorName;
    }

    public String getName() {
        return name;
    }

    public Map<Integer,Action> getActionsMap() {
        return mAction;
    }

    public List<Action> getActions(){
        List<Action> lAction = new ArrayList<>();
        for(Action action : mAction.values()){
            lAction.add(action);
        }
        return lAction;
    }

    public String getContent() {
        return content;
    }

    public String getActorName(){ return actorName; }

}
