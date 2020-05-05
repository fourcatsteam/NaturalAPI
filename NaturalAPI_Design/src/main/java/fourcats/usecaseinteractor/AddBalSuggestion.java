package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.AddBalSuggestionInputPort;
import fourcats.port.AddBalSuggestionOutputPort;

public class AddBalSuggestion implements AddBalSuggestionInputPort {
    RepositoryAccess repo;
    AddBalSuggestionOutputPort out;

    public AddBalSuggestion(RepositoryAccess repositoryAccess, AddBalSuggestionOutputPort outputPort){
        this.repo = repositoryAccess;
        this.out = outputPort;
    }

    @Override
    public void addSuggestion(int idScenario, String suggestionName, String suggestionType) {
        try {
            repo.createSuggestion(idScenario, suggestionName, suggestionType);
            out.showAddedSuggestion(repo.readScenarios(), true);
        }
        catch(Exception e){
            out.showAddedSuggestion(repo.readScenarios(), false);
        }
    }

    @Override
    public void addSuggestionByIdType(int idScenario, String suggestionName, int idType) {
        try{
            repo.crateSuggestionByIdType(idScenario,suggestionName,idType);
            out.showAddedSuggestion(repo.readScenarios(),true);
        }
        catch (Exception e) {
            out.showAddedSuggestion(repo.readScenarios(),false);
        }

    }
}
