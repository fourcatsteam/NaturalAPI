package fourcats.datastructure.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private List<Observer> lObservers;

    protected Subject(){
        lObservers = new ArrayList<>();
    }
    public void attach(Observer observer){
        lObservers.add(observer);
    }
    public void detach(Observer observer){lObservers.remove(observer);}
    protected void notifyObservers(){
        for (Observer o: lObservers){
            o.update();
        }
    }

}
