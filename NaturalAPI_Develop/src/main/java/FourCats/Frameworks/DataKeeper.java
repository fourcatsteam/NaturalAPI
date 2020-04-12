package fourcats.frameworks;

import fourcats.entity.API;

import java.util.HashMap;
import java.util.Map;

public class DataKeeper {

    private Map<Integer, API> mApi;

    public DataKeeper() {
        mApi = new HashMap<>();
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
}
