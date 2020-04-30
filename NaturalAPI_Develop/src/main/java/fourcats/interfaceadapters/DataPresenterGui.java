package fourcats.interfaceadapters;

import fourcats.entity.API;
import fourcats.observer.Subject;
import fourcats.port.ApiOutputPort;
import fourcats.port.ModifyOutputPort;
import java.util.Map;

public class DataPresenterGui extends Subject implements ApiOutputPort, ModifyOutputPort {

    private String toShow;
    private String comboToShow;

    public DataPresenterGui(){
        toShow = "";
        comboToShow = "";
    }

    public String getStringToShow(){
        return toShow;
    }

    public String getComboToShow(){
        return comboToShow;
    }

    @Override
    public void showOutput(Map<Integer,API> mApi) {

        for (Map.Entry<Integer,API> mapApi : mApi.entrySet()) {

            for(Map.Entry<String,String> api : mapApi.getValue().getListApi().entrySet()){
                toShow = api.getValue();
                String[] split = api.getKey().split("/");
                comboToShow = split[split.length-1];
                notifyObservers();
            }
        }
    }
}