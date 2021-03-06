package fourcats.entities;


import java.util.ArrayList;
import java.util.List;

public class Action {
    private String name;
    private List<ObjectParam> param;
    private Type type;
    private String scenario;
    private String step;

    public Action(String actionName, Type actionType) {
        this.name = actionName;
        this.param = new ArrayList<>();
        this.type = actionType;
        this.scenario = null;
        this.step = null;
    }

    public Action(String actionName, String actionType) {
        this.name = actionName;
        this.param = new ArrayList<>();
        this.scenario = null;
        this.step = null;
        if(actionType.equals(""))
            this.type = null;
        else
            this.type = new Type(actionType);
    }

    public Action(String actionName, String actionType, String scenario, String step){
        this.name = actionName;
        this.param = new ArrayList<>();
        this.scenario = scenario;
        this.step = step;
        if(actionType.equals(""))
            this.type = null;
        else
            this.type = new Type(actionType);
    }

    public void setName(String actionName) {
        this.name = actionName;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public String getName() { return name; }
    public Type getType() {
        return type;
    }
    public List<ObjectParam> getObjectParams() {
        return param;
    }
    public void setType(String actionType) {
        this.type = new Type(actionType);
    }
    public String getScenario() {
        return scenario;
    }
    public String getStep() {
        return step;
    }

    public void addObjectParam(ObjectParam objectParam) {
        this.param.add(objectParam);
    }

    public void addObjectParam(String name, String type) {param.add(new ObjectParam(name, type));}

    public void removeObjectParam(int idObjectParam){
        param.remove(idObjectParam);
    }

    @Override
    public String toString() {
        StringBuilder actionParams = new StringBuilder();
        boolean isFirstParam = true;
        for (ObjectParam par : param) {
            if (isFirstParam) {
                actionParams.append(par.getType()).append(" ").append(par.getName());
                isFirstParam = false;
            }
            else {
                actionParams.append(", ").append(par.getType()).append(" ").append(par.getName());
            }
        }
        return this.type + " " + this.name + " (" + actionParams + ")";
    }
}
