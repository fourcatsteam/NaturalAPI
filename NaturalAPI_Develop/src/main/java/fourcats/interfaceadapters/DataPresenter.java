package fourcats.interfaceadapters;

import fourcats.entity.API;
import fourcats.observer.Subject;
import fourcats.port.ApiOutputPort;
import fourcats.port.GenerateOutputPort;
import fourcats.port.ModifyOutputPort;
import java.util.Map;

public class DataPresenter extends Subject implements ApiOutputPort, ModifyOutputPort, GenerateOutputPort {

    private String toShow;

    private String message;

    public DataPresenter(){
        toShow = "";
        message = "";
    }

    public String getStringToShow(){
        return toShow;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void showOutput(Map<Integer, API> mApi) {
        toShow = "";
        for (Map.Entry<Integer, API> mapApi : mApi.entrySet()) {

            toShow = "-----------API ID : " + mapApi.getKey() + "-----------\n";
            notifyObservers();
            toShow = mapApi.getValue().getText();
            notifyObservers();
        }
        toShow = "";
    }

    @Override
    public void showMessage(String m){
        message = m;
        notifyObservers();
        message = "";
    }
}
