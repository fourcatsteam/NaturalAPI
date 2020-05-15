package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.DeclineBalSuggestionInputPort;
import fourcats.port.DeclineBalSuggestionOutputPort;

public class DeclineBalSuggestion implements DeclineBalSuggestionInputPort {
    RepositoryAccess repo;
    DeclineBalSuggestionOutputPort out;

    public DeclineBalSuggestion(RepositoryAccess repositoryAccess, DeclineBalSuggestionOutputPort outputPort){
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
