package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.DeclineBALSuggestionInputPort;
import fourcats.port.DeclineBALSuggestionOutputPort;

public class DeclineBALSuggestion implements DeclineBALSuggestionInputPort {
    RepositoryAccess repo;
    DeclineBALSuggestionOutputPort out;

    public DeclineBALSuggestion(RepositoryAccess repositoryAccess, DeclineBALSuggestionOutputPort outputPort){
        this.repo = repositoryAccess;
        this.out = outputPort;
    }
    public void declineSuggestion(int idAction, int idScenario){
        try{
            repo.deleteSuggestion(idAction, idScenario);
            out.showDeclinedSuggestion(repo.readScenarios(),true);
        }
        catch (NullPointerException e){
            out.showDeclinedSuggestion(repo.readScenarios(),false);
        }
    }
}
