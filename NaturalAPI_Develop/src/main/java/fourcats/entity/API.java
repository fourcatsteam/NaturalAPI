package fourcats.entity;

import java.util.HashMap;
import java.util.Map;

public class API {

    private Map<String,String> listApi;

    public API(){
        listApi = new HashMap<>();
    }

    public void addApi(String filename,String a){
        listApi.put(filename,a);
    }

    public Map<String,String> getListApi() {
        return listApi;
    }

}
