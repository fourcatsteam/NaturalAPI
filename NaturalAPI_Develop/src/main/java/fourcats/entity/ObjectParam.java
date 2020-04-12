package fourcats.entity;

public class ObjectParam {
    private String name;
    private String type;
    private Boolean required;

    public ObjectParam() {
        name = null;
        type = null;
        required = true;
    }

    public ObjectParam(String objectName) {
        this.name = objectName;
        this.type = null;
        this.required = true;
    }

    public ObjectParam(String objectName, String objectType) {
        this.name = objectName;
        this.type = objectType;
        this.required = true;
    }

    public void setName(String objectName) {
        this.name = objectName;
    }

    public String getName() {
        return name;
    }

    public void setType(String objectType) {
        this.type = objectType;
    }

    public String getType() {
        return type;
    }

    public void setRequired(boolean isObjectRequired) {
        this.required = isObjectRequired;
    }

    public boolean isRequired() {
        return required;
    }

    @Override public String toString() {
        return name; //"name: " + name + ", type: " + type + ", required: " + required + "\n";
    }

}