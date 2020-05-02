package fourcats.entity;

import java.util.ArrayList;
import java.util.List;

public class Action {
    private String name;
    private List<ObjectParam> param;
    private Type type;

    public Action(String actionName, Type actionType) {
        this.name = actionName;
        this.param = new ArrayList<>();
        this.type = actionType;
    }

    public Action(String actionName, String actionType) {
        this.name = actionName;
        this.param = new ArrayList<>();
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
    public void setType(String actionType) {
        if (this.type == null)
            this.type = new Type(actionType);
        else
            this.type.setName(actionType);
    }
    public void addObjectParam(ObjectParam objectParam) {
        this.param.add(objectParam);
    }

    public List<ObjectParam> getObjectParams() {
        return param;
    }



    public void removeObjectParam(int idObjectParam){
        param.remove(idObjectParam);
    }

    @Override
    public String toString() {
        String actionParams = "";
        boolean firstParam = true;
        for (ObjectParam par : param) {
            if (firstParam) {
                actionParams+=par.getType()+ " " + par.getName();
                firstParam = false;
            }
            else {
                actionParams+=", " + par.getType()+ " " + par.getName();
            }
        }
        return this.type + " " + this.name + "(" + actionParams + ")";
    }
}