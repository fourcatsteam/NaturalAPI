package fourcats.entities;


import java.util.ArrayList;
import java.util.List;

public class Action {
    private String name;
    private List<ObjectParam> param;
    private Type type;

    public Action(String actionName) {
        this.name = actionName;
        this.param = new ArrayList<>();
        this.type = null;
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

    public String getName() {
        return name;
    }

    public void addObjectParam(ObjectParam objectParam) {
        this.param.add(objectParam);
    }


    public void updateObjectParamType(String paramName, String newType) {
        for (ObjectParam p : param) {
            if (p.getName().equals(paramName)) {
                p.setType(newType);
                return;
            }
        }
    }

    public void updateIsObjectParamRequired(String paramName, boolean isRequired) {
        for (ObjectParam p : param) {
            if (p.getName().equals(paramName)) {
                p.setRequired(isRequired);
                return;
            }
        }
    }

    public List<ObjectParam> getObjectParams() {
        return param;
    }

    public void setType(String actionType) {
        if (this.type == null)
            this.type = new Type(actionType);
        else
            this.type.setName(actionType);
    }
    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void removeObjectParam(int idObjectParam){
        param.remove(idObjectParam);
    }

    @Override
    public String toString() {
        String ActionParams = "";
        boolean firstParam = true;
        for (ObjectParam par : param) {
            if (firstParam) {
                ActionParams+=par.getType()+ " " + par.getName();
                firstParam = false;
            }
            else {
                ActionParams+=", " + par.getType()+ " " + par.getName();
            }
        }
        return this.type + " " + this.name + "(" + ActionParams + ")";
    }
}
