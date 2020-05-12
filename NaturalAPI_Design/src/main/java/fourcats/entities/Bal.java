package fourcats.entities;

import fourcats.entities.Actor;

import java.util.List;

public class Bal {
    private List<Actor> lActors;

    public Bal(List<Actor> actorsList) {
        this.lActors = actorsList;
    }

    public List<Actor> getActors() {
        return lActors;
    }

    @Override
    public String toString() {
        StringBuilder balStr = new StringBuilder();
        for (Actor f : lActors) {
            balStr.append(f.toString());
        }
        return balStr.toString();
    }
}


