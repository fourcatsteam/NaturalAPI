package fourcats.frameworks;

import fourcats.entity.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataKeeper {

    private Map<Integer, API> mApi;
    private Map<String,List<String>> mBalPlaUsed;

    public DataKeeper() {
        mApi = new HashMap<>();
        mBalPlaUsed = new HashMap<>();
    }

    public void addApi(API api){
        int id = mApi.size();
        mApi.put(id,api);
    }

    public void addApiWithId(int id,API api) {
        mApi.put(id,api);
    }

    public void deleteApi(int id){
        mApi.remove(id);
    }

    public API getApi(int id){
        return mApi.get(id);
    }

    public Map<Integer,API> getApiMap(){
        return mApi;
    }

    public int getSize(){
        return  mApi.size();
    }

    public void updateApi(String oldApi,String newApi) {
        for(Map.Entry<Integer,API> api : mApi.entrySet()){
            for(Map.Entry<String,String> listApi : api.getValue().getListApi().entrySet()){
                if(listApi.getValue().equals(oldApi)){
                    listApi.setValue(newApi);
                }
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
}
