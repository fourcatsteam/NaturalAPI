package fourcats.interfaceadapters;

import fourcats.entity.API;
import fourcats.observer.Subject;
import fourcats.port.*;

import java.util.Map;

public class DataPresenterGui extends Subject implements ApiOutputPort, ModifyOutputPort, CreatePlaOutputPort, ModifyPlaOutputPort {

    private String toShow;
    private String comboToShow;
    private String messagePla;
    private String modifyTitlePla;
    private String modifyExtensionPla;
    private String modifyTextPla;

    public DataPresenterGui(){
        toShow = "";
        comboToShow = "";
        messagePla = "";
        modifyTitlePla = "";
        modifyExtensionPla = "";
        modifyTextPla = "";
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

    public String getModifyTitlePla() {
        return modifyTitlePla;
    }

    public String getModifyExtensionPla() {
        return modifyExtensionPla;
    }

    public String getModifyTextPla() {
        return modifyTextPla;
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

    public void showModifyPla(String title,String extension,String text){
        modifyTitlePla = title;
        modifyExtensionPla = extension;
        modifyTextPla = text;
        notifyObservers();
    }
}