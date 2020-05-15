package fourcats.interfaceadapters;

import fourcats.entity.API;
import fourcats.observer.Subject;
import fourcats.port.*;

import java.util.Map;

public class DataPresenterGui extends Subject implements ApiOutputPort, ModifyOutputPort,
        CreatePlaOutputPort, ModifyPlaOutputPort, GenerateOutputPort {

    private String toShow;
    private String comboToShow;
    private String message;
    private String modifyApiPla;
    private String modifyCustomPla;
    private String modifyTestPla;

    public DataPresenterGui(){
        toShow = "";
        comboToShow = "";
        modifyApiPla = "";
        modifyCustomPla = "";
        modifyTestPla = "";
        message = "";
    }

    public String getStringToShow(){
        return toShow;
    }

    public String getComboToShow(){
        return comboToShow;
    }

    public String getModifyApiPla() {
        return modifyApiPla;
    }

    public String getModifyCustomPla() {
        return modifyCustomPla;
    }

    public String getModifyTestPla() {
        return modifyTestPla;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void showOutput(Map<Integer, API> mApi) {

        for (Map.Entry<Integer, API> mapApi : mApi.entrySet()) {

            toShow = mapApi.getValue().getText();
            String[] split = mapApi.getValue().getFilename().split("/");
            comboToShow = split[split.length-1];
            notifyObservers();
        }
    }

    public void showLoadPla(String api,String custom,String test){
        modifyApiPla = api;
        modifyCustomPla = custom;
        modifyTestPla = test;
        notifyObservers();
    }

    public void showMessage(String m) {
        message = m;
        notifyObservers();
    }
}