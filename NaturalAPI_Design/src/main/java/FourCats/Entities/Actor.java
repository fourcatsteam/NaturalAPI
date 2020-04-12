package FourCats.Entities;

import java.util.ArrayList;
import java.util.List;

public class Actor {
    private String name;
    private List<Action> lAction;

    public Actor(){
        this.name = null;
        this.lAction = new ArrayList<>();
    }

    public Actor(String actorName){
        this.name = actorName;
        this.lAction = new ArrayList<>();
    }

    public Actor(String userName, List<Action> lAction){
        this.name = userName;
        this.lAction = lAction;
    }

    public void setName(String userName) {
        this.name = userName;
    }

    public String getName() {
        return name;
    }

    public void addAction(Action operationToAdd) {
        this.lAction.add(operationToAdd);
    }

    public void addActions(List<Action> operationsToAdd) {
        this.lAction.addAll(operationsToAdd);
    }

    public List<Action> getActions() {
        return lAction;
    }

    public List<Action> getActionsByName(String operationName) {
        List<Action> lSameNameActions = new ArrayList<Action>();
        for (Action op : lAction) {
            if (op.getName().equals(operationName))
                lSameNameActions.add(op);
        }
        return lSameNameActions;
    }

    @Override
    public String toString() {
        String ActionStr = "";
        for (Action s : lAction) {
            ActionStr += s.toString();
        }
        return ActionStr;
    }

}