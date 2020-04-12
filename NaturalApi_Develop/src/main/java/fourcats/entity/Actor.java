package fourcats.entity;

import java.util.ArrayList;
import java.util.List;

public class Actor {
    private String name;
    private List<Action> lAction;

    public Actor(){
        this.name = null;
        this.lAction = new ArrayList<>();
    }

    public Actor(String userName){
        this.name = userName;
        this.lAction = new ArrayList<>();
    }

    public void setName(String userName) {
        this.name = userName;
    }

    public String getName() {
        return name;
    }

    public void addAction(Action actionToAdd) {
        this.lAction.add(actionToAdd);
    }

    public void addActions(List<Action> actionsToAdd) {
        this.lAction.addAll(actionsToAdd);
    }

    public List<Action> getActions() {
        return lAction;
    }

    public List<Action> getActionsByName(String actionName) {
        List<Action> lSameNameActions = new ArrayList<Action>();
        for (Action op : lAction) {
            if (op.getName().equals(actionName))
                lSameNameActions.add(op);
        }
        return lSameNameActions;
    }

    @Override
    public String toString() {
        String actionStr = "";
        for (Action s : lAction) {
            actionStr += s.toString();
        }
        return actionStr;
    }

}