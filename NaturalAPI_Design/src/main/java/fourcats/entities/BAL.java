package fourcats.entities;

import java.util.ArrayList;
import java.util.List;

public class BAL{
    private List<Actor> lActors;

    public BAL() {
        lActors = new ArrayList<>();
    }
    public BAL(Actor actor){
        lActors = new ArrayList<>();
        lActors.add(actor);
    }
    public BAL(List<Actor> ActorsList) {
        this.lActors = ActorsList;
    }

    public void addUserToBAL(Actor userToAdd) {
        lActors.add(userToAdd);
    }

    public List<Actor> getActors() {
        return lActors;
    }

    @Override
    public String toString() {
        String BALStr = "";
        for (Actor f : lActors) {
            BALStr +=f.toString();
        }
        return BALStr;
    }
}

