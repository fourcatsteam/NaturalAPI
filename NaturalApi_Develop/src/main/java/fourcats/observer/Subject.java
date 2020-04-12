package fourcats.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private final List<Observer> observers;

    protected Subject() {
        this.observers = new ArrayList<>();
    }

    public void attach(Observer observer) {
        this.observers.add(observer);
    }

    protected void notifyObservers() {
        for(Observer o: observers){
            o.update();
        }
    }
}