package fourcats.interfaceadapters;

import fourcats.entity.API;
import fourcats.observer.Subject;
import fourcats.port.ApiOutputPort;
import fourcats.port.ModifyOutputPort;

import javax.xml.crypto.Data;
import java.util.Map;

public class DataPresenter extends Subject implements ApiOutputPort, ModifyOutputPort {

    private String toShow;

    public DataPresenter(){
        this.toShow = "";
    }

    public String getStringToShow(){
        return toShow;
    }

    @Override
    public void showOutput(Map<Integer,API> mApi) {
        toShow = "";
        for (Map.Entry<Integer,API> mapApi : mApi.entrySet()) {

            toShow = "-----------API ID : " + mapApi.getKey() + "-----------\n";
            notifyObservers();
            for(Map.Entry<String,String> api : mapApi.getValue().getListApi().entrySet()){
                toShow = toShow + api.getValue();
                notifyObservers();
            }
        }
    }
}
