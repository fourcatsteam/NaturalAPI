package FourCats.UseCaseInteractor;

import FourCats.Entities.Actor;
import FourCats.Entities.BAL;
import FourCats.Entities.Scenario;
import FourCats.InterfaceAccess.BalAnalyzer;
import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.Port.GenerateBALInputPort;
import FourCats.Port.GenerateBALOutputPort;

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

    public void generateBAL(String filename) throws IOException {
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
                lActors.add(new Actor(scenario.getActorName(), scenario.getActions())); //non aggiunge attori correttamente
            }

        }
        BAL bal = new BAL(lActors);
        try{
            balAnalyzer.createJsonFromBAL(bal,filename+".json");
            repo.deleteScenarios(); //after generating the bal and saved it on the fileSystem we want to delete scenarios from repo
            out.showGenerationStatus(true);
        }
        catch (IOException e) {
            out.showGenerationStatus(false);
        }


    }

}
