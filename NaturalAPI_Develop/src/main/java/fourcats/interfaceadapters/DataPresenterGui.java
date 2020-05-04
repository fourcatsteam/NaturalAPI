package fourcats.interfaceadapters;

import fourcats.entity.API;
import fourcats.observer.Subject;
import fourcats.port.ApiOutputPort;
import fourcats.port.CreatePlaInputPort;
import fourcats.port.CreatePlaOutputPort;
import fourcats.port.ModifyOutputPort;
import java.util.Map;

public class DataPresenterGui extends Subject implements ApiOutputPort, ModifyOutputPort, CreatePlaOutputPort {

    private String toShow;
    private String comboToShow;
    private String messagePla;

    public DataPresenterGui(){
        toShow = "";
        comboToShow = "";
        messagePla = "";
    }

    public String getStringToShow(){
        return toShow;
    }

    public String getComboToShow(){
        return comboToShow;
    }

    public String getMessagePla() {
        return messagePla;
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

    public void showOutput(String message){
        messagePla = message;
        notifyObservers();
    }
}