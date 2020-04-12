package Entity;

import java.util.ArrayList;
import java.util.List;

public class Action {
    private String name;
    private List<ObjectParam> param;
    private String type;

    public Action() {
        this.name = null;
        this.param = new ArrayList<ObjectParam>();
        this.type = null;
    }
    public Action(String actionName) {
        this.name = actionName;
        this.param = new ArrayList<ObjectParam>();
        this.type = null;
    }

    public Action(String actionName, String actionType) {
        this.name = actionName;
        this.param = new ArrayList<ObjectParam>();
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