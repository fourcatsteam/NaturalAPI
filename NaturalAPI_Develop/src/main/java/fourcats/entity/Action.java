package fourcats.entity;

import java.util.ArrayList;
import java.util.List;

public class Action {
    private String name;
    private List<ObjectParam> param;
    private String type;

    public Action() {
        this.name = null;
        this.param = new ArrayList<>();
        this.type = null;
    }
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
            this.type = actionType;
    }

    public void setName(String actionName) {
        this.name = actionName;
    }

    public String getName() {
        return name;
    }

    public void addObjectName(String objectName) {
        ObjectParam par = new ObjectParam();
        par.setName(objectName);
        this.param.add(par);
    }

    public void addObjectParam(ObjectParam objectParam) {
        this.param.add(objectParam);
    }

    public void updateObjectParamName(String paramName, String newName) {
        for (ObjectParam p : param) {
            if (p.getName().equals(paramName)) {
                p.setName(newName);
                break;
            }
        }
    }

    public void updateObjectParamType(String paramName, String newType) {
        for (ObjectParam p : param) {
            if (p.getName().equals(paramName)) {
                p.setType(newType);
                break;
            }
        }
    }

    public void updateIsObjectParamRequired(String paramName, boolean isRequired) {
        for (ObjectParam p : param) {
            if (p.getName().equals(paramName)) {
                p.setRequired(isRequired);
                break;
            }
        }
    }

    public List<ObjectParam> getObjectParam() {
        return param;
    }

    public void setType(String actionType) {
        this.type = actionType;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean firstParam = true;
        for (ObjectParam par : param) {
            if (firstParam) {
                sb.append(par.getType() + " " + par.getName());
                firstParam = false;
            }
            else {
                sb.append(", " + par.getType() + " " + par.getName());
            }
        }
        String actionParams = sb.toString();
        return this.type + " " + this.name + "(" + actionParams + ")";
    }


}