package fourcats.entity;

import java.util.ArrayList;
import java.util.List;

public class BAL{
    private List<Actor> lActors;

    public BAL() {
        lActors = new ArrayList<>();
    }

    public BAL(List<Actor> actorsList) {
        this.lActors = actorsList;
    }

    public void addUserToBAL(Actor userToAdd) {
        Boolean present = false;
        for(Actor a : lActors) {
            if(a.getName().equals(userToAdd.getName())) {
                present = true;
                break;
            }
        }
        if(Boolean.FALSE.equals(present)) {
            lActors.add(userToAdd);
        }
    }

    public List<Actor> getActors() {
        return lActors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Actor f : lActors) {
            sb.append(f.toString());
        }
        String balStr = sb.toString();
        return balStr;
    }
}
