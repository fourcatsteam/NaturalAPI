package fourcats.frameworks;

import fourcats.entities.ObjectParam;
import fourcats.entities.Scenario;
import fourcats.entities.Type;
import org.apache.lucene.util.SetOnce;

import java.util.*;

public class DataKeeper {
    private List<String> lDeafultTypes = new ArrayList<>();
    private Map<Integer, Scenario> mScenarios;
    private Map<Integer, Type> mTypes;


    public DataKeeper(){
        mScenarios = new HashMap<>();
        mTypes = new HashMap<>();
        //init mTypes with default values
        lDeafultTypes.addAll(Arrays.asList("string","int","float","double","bool"));
        for (String s : lDeafultTypes){
            mTypes.put(mTypes.size(),new Type(s));
        }

    }

    public void addScenarioToMap(Scenario scenario){
        mScenarios.put(mScenarios.size(),scenario);
    }

    public Map<Integer,Scenario> getScenarioMap(){
        return mScenarios;
    }
    public Map<Integer,Type> getTypeMap(){
        return mTypes;
    }


    public void removeAction(int idScenario, int idAction){
        try {
            mScenarios.get(idScenario).getActionsMap().remove(idAction);
        }
        catch (NullPointerException e){
            throw e;
        }
    }

    public void clearScenariosMap(){
        mScenarios.clear();
    }

    public void updateActionName(int idScenario, int idAction, String newActionName){
        try{
            mScenarios.get(idScenario).getActionsMap().get(idAction).setName(newActionName);
        }
        catch (NullPointerException e){
            throw e;
        }
    }

    public void updateActionType(int idScenario, int idAction, String newActionType){
        for (Type ty : mTypes.values()){
            if (ty.getName().equals(newActionType)) {
                try {
                    mScenarios.get(idScenario).getActionsMap().get(idAction).setType(ty);
                    return;
                }
                catch (Exception e){
                    throw e;
                }
            }
        }
        try{
            mScenarios.get(idScenario).getActionsMap().get(idAction).setType(newActionType);
        }
        catch (Exception e){
            throw e;
        }
    }

    public void updateObjectName(int idScenario, int idAction, int idObject, String newObjectName){
        try{
            mScenarios.get(idScenario).getActionsMap().get(idAction).getObjectParams().get(idObject).setName(newObjectName);
        }
        catch (Exception e){
            throw e;
        }
    }

    public void updateObjectType(int idScenario, int idAction, int idObject, String newObjectType){
        for (Type ty : mTypes.values()){
            if (ty.getName().equals(newObjectType)) {
                try {
                    mScenarios.get(idScenario).getActionsMap().get(idAction).getObjectParams().get(idObject).setType(ty);
                    return;
                }
                catch (Exception e){
                    throw e;
                }
            }
        }
        try{
            mScenarios.get(idScenario).getActionsMap().get(idAction).getObjectParams().get(idObject).setType(newObjectType);
        }
        catch (Exception e){
            throw e;
        }
    }

    public void addCustomType(String typeName, Map<String, String> mAttributes) {
        for (Type ty : mTypes.values()){
            if (!ty.getName().equals(typeName)) {
                mTypes.put(mTypes.size(),new Type(typeName,mAttributes));
                return;
            }
            else{
                throw new SetOnce.AlreadySetException();
            }
        }
        mTypes.put(mTypes.size(),new Type(typeName,mAttributes));

    }

    public void updateObjectTypeById(int idScenario, int idAction, int idObject, int idType) {
        try {
            if(mTypes.get(idType)!=null) {
                mScenarios.get(idScenario).getActionsMap().get(idAction).getObjectParams().get(idObject).setType(mTypes.get(idType));
            }
            else {
                throw new NoSuchElementException();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateActionTypeById(int idScenario, int idAction, int idType) {
        try {
            if(mTypes.get(idType)!=null) {
                mScenarios.get(idScenario).getActionsMap().get(idAction).setType(mTypes.get(idType));
            }
            else {
                throw new NoSuchElementException();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void addObject(int idScenario, int idAction, String objectName, int idType) {
        for (ObjectParam op : mScenarios.get(idScenario).getActionsMap().get(idAction).getObjectParams()){
            if (op.getName().equals(objectName)){
                throw new SetOnce.AlreadySetException();
            }
        }
        try{
            mScenarios.get(idScenario).getActionsMap().get(idAction).addObjectParam(new ObjectParam(objectName, mTypes.get(idType)));
        }
        catch (Exception e){
            throw e;
        }
    }

    public void removeObject(int idScenario, int idAction, int idObject) {
        try {
            mScenarios.get(idScenario).getActionsMap().get(idAction).removeObjectParam(idObject);
        }
        catch (Exception e){
            throw e;
        }
    }
}