package fourcats.frameworks;

import fourcats.entity.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataKeeper {

    private Map<Integer, API> mApi;
    private Map<String,List<String>> mBalPlaUsed;
    private List<String> lTests;

    public DataKeeper() {
        mApi = new HashMap<>();
        mBalPlaUsed = new HashMap<>();
        lTests = new ArrayList<>();
    }

    public void addApi(API api){
        int id = mApi.size();
        mApi.put(id,api);
    }

    public void addApiWithId(int id, API api) {
        mApi.put(id,api);
    }

    public void deleteApi(int id){
        mApi.remove(id);
    }

    public API getApi(int id){
        return mApi.get(id);
    }

    public Map<Integer, API> getApiMap(){
        return mApi;
    }

    public int getSize(){
        return  mApi.size();
    }

    public void addTest(String s) {
        lTests.add(s);
    }

    public String getAllTests() {
        String toReturn = "";
        for(String s : lTests) {
            toReturn = toReturn.concat(s + "\n");
        }
        return toReturn;
    }

    public void updateApi(String oldApi,String newApi) {
        for(Map.Entry<Integer, API> api : mApi.entrySet()){

            if(api.getValue().getText().equals(oldApi)){
                api.getValue().setText(newApi);
            }
        }
    }

    public void addCoupleBalPla(String bal,String pla){

        if(mBalPlaUsed.containsKey(bal)){
            mBalPlaUsed.get(bal).add(pla);
        }
        else{
            List<String> l = new ArrayList<>();
            l.add(pla);
            mBalPlaUsed.put(bal,l);
        }
    }

    public boolean isCoupleBalPlaPresent(String bal,String pla){
        for(Map.Entry<String,List<String>> map : mBalPlaUsed.entrySet()){
            if(map.getKey().equals(bal) && map.getValue().contains(pla)){
                return true;
            }
        }
        return false;
    }

    public boolean isThisApiPresent(API a){
        for(Map.Entry<Integer, API> api : mApi.entrySet()) {
            if(api.getValue().getText().equals(a.getText())){
                return true;
            }
        }
        return false;
    }

    public boolean isThisClassNamePresent(API a){
        for(Map.Entry<Integer, API> api : mApi.entrySet()) {
            if(api.getValue().getFilename().equals(a.getFilename())){
                return true;
            }
        }
        return false;
    }

    public boolean isThisTestPresent(String test) {
        for(String s : lTests){
            if(s.equals(test)){
                return true;
            }
        }
        return false;
    }
}
