package fourcats.entity;

import java.util.HashMap;
import java.util.Map;

public class API {

    String filename;
    String text;

    //private Map<String,String> listApi;
    public API(){
        //listApi = new HashMap<>();
        filename = "";
        text = "";
    }

    //public void addApi(String filename,String a){
    //    listApi.put(filename,a);

    //}
    //public Map<String,String> getListApi() {
    //   return listApi;
//}

    public String getFilename() {
        return filename;
    }

    public String getText() {
        return text;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setText(String text) {
        this.text = text;
    }
}
