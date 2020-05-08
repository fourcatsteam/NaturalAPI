package fourcats.interfaceadapters;

import fourcats.entity.API;
import fourcats.observer.Subject;
import fourcats.port.*;

import java.util.Map;

public class DataPresenterGui extends Subject implements ApiOutputPort, ModifyOutputPort,
        CreatePlaOutputPort, ModifyPlaOutputPort, GenerateOutputPort {

    private String toShow;
    private String comboToShow;
    private String messagePla;

    private String message;

    private String modifyApiPla;
    private String modifyCustomPla;

    public DataPresenterGui(){
        toShow = "";
        comboToShow = "";
        messagePla = "";
        modifyApiPla = "";
        modifyCustomPla = "";
        message = "";
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

    public String getModifyApiPla() {
        return modifyApiPla;
    }

    public String getModifyCustomPla() {
        return modifyCustomPla;
    }

    public String getMessage() {
        return message;
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

    public void showLoadPla(String api,String custom){
        modifyApiPla = api;
        modifyCustomPla = custom;
        notifyObservers();
    }

    public void showModifyPla(String message){
        messagePla = message;
        notifyObservers();
    }

    public void showGenerationMessage(String m) {
        message = m;
        notifyObservers();
    }
}