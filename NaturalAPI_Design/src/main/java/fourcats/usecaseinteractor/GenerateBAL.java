package fourcats.usecaseinteractor;

import fourcats.entities.Actor;
import fourcats.entities.BAL;
import fourcats.entities.Scenario;
import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.GenerateBALInputPort;
import fourcats.port.GenerateBALOutputPort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateBAL implements GenerateBALInputPort {
    RepositoryAccess repo;
    GenerateBALOutputPort out;
    BalAnalyzer balAnalyzer;

    public GenerateBAL(RepositoryAccess repositoryAccess, GenerateBALOutputPort outputPort, BalAnalyzer balAnalyzer){
        this.repo = repositoryAccess;
        this.out = outputPort;
        this.balAnalyzer = balAnalyzer;
    }

    public void generateBAL(String filePath) {
        List<Actor> lActors = new ArrayList<>();
        for (Scenario scenario : repo.readScenarios().values()){
            int index = 0;
            boolean isActorInList = false;
            for (Actor actor : lActors) {
                if (actor.getName().equals(scenario.getActorName())) {
                    actor.addActions(scenario.getActions());
                    lActors.set(index,actor);
                    isActorInList = true;
                }
                index++;
            }
            if (!isActorInList){
                lActors.add(new Actor(scenario.getActorName(), scenario.getActions()));
            }

        }
        BAL bal = new BAL(lActors);
        String jsonBal;
        try{
            jsonBal = balAnalyzer.createJsonFromBAL(bal);
            repo.createBAL(jsonBal, filePath);
            out.showGenerationStatus(true);
        }
        catch (IOException e) {
            out.showGenerationStatus(false);
        }


    }

}
