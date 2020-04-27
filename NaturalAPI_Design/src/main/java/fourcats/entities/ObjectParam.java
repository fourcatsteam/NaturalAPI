package fourcats.entities;


public class ObjectParam {
    private String name;
    private Type type;
    private boolean required;

    public ObjectParam(String objectName, String objectType) {
        this.name = objectName;
        this.type = new Type(objectType);
        this.required = true;
    }

    public ObjectParam(String objectName, Type type){
        this.name = objectName;
        this.type = type;
        this.required = true;
    }

    public void setName(String objectName) {
        this.name = objectName;
    }

    public String getName() {
        return name;
    }

    public void setType(String objectType) {
        this.type.setName(objectType);
    }
    public void setType(Type type){
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setRequired(boolean isParamRequired) {
        this.required = isParamRequired;
    }

    public boolean isRequired() {
        return required;
    }

    @Override public String toString() {
        return name; //"name: " + name + ", type: " + type + ", required: " + required + "\n";
    }
}
