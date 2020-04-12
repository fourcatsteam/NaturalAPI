package InterfaceAdapters;

import Entity.API;
import Observer.Subject;
import Port.ApiOutputPort;
import Port.ModifyOutputPort;

import java.util.Map;

public class DataPresenter extends Subject implements ApiOutputPort, ModifyOutputPort {

    private String toShow;

    public String getStringToShow(){
        return toShow;
    }

    @Override
    public void showOutput(Map<Integer,API> mApi) {
        toShow = "";
        for (Map.Entry<Integer,API> mapApi : mApi.entrySet()) {

            toShow = "-----------API ID : " + mapApi.getKey() + "-----------";
            notifyObservers();
            for(Map.Entry<String,String> api : mapApi.getValue().getListApi().entrySet()){
                toShow = api.getValue();
                notifyObservers();
            }
        }
    }
}
