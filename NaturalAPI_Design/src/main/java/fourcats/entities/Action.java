package fourcats.entities;


import java.util.ArrayList;
import java.util.List;

public class Action {
    private String name;
    private List<ObjectParam> param;
    private String type;

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

    public void addObjectParamName(String paramName) {
        ObjectParam par = new ObjectParam();
        par.setName(paramName);
        this.param.add(par);
    }

    public void addObjectParam(ObjectParam objectParam) {
        this.param.add(objectParam);
    }
    public void updateObjectParamName(String paramName, String newName) {
        for (ObjectParam p : param) {
            if (p.getName()==paramName) {
                p.setName(newName);
                return;
            }
        }
    }

    public void updateObjectParamType(String paramName, String newType) {
        for (ObjectParam p : param) {
            if (p.getName()==paramName) {
                p.setType(newType);
                return;
            }
        }
    }

    public void updateIsObjectParamRequired(String paramName, boolean isRequired) {
        for (ObjectParam p : param) {
            if (p.getName()==paramName) {
                p.setRequired(isRequired);
                return;
            }
        }
    }

    public List<ObjectParam> getObjectParams() {
        return param;
    }

    public void setType(String ActionType) {
        this.type = ActionType;
    }

    public String getType() {
        return type;
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
