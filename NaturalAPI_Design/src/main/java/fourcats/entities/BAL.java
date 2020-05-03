package fourcats.entities;

import java.util.List;

public class BAL{
    private List<Actor> lActors;

    public BAL(List<Actor> ActorsList) {
        this.lActors = ActorsList;
    }

    public void addUserToBAL(Actor userToAdd) {
        boolean present = false;
        for(Actor a : lActors) {
            if(a.getName().equals(userToAdd.getName())) {
                present = true;
                break;
            }
        }
        if(!present) {
            lActors.add(userToAdd);
        }
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

