package fourcats.entity;

import java.util.HashMap;
import java.util.Map;

public class Type {
    private String name;
    private Map<String, String> mAttributes;

    public Type(String name){
        this.name = name;
        mAttributes = new HashMap<>();
    }
    public Type(String name, Map<String,String> mAttributes){
        this.name = name;
        this.mAttributes = mAttributes;
    }
    public void setName(String name){
        this.name = name;
    }
    public void addAttribute(String attributeName, String attributeType){
        if (mAttributes==null) {
            this.mAttributes = new HashMap<>();
        }
        this.mAttributes.put(attributeName, attributeType);
    }
    public String getName(){
        return this.name;
    }
    public Map<String,String> getAttributes(){
        return this.mAttributes;
    }
    @Override
    public String toString() {
        return name;
    }
}