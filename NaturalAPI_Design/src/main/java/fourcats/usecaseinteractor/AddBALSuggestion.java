package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.AddBALSuggestionInputPort;
import fourcats.port.AddBALSuggestionOutputPort;

public class AddBALSuggestion implements AddBALSuggestionInputPort {
    RepositoryAccess repo;
    AddBALSuggestionOutputPort out;

    public AddBALSuggestion(RepositoryAccess repositoryAccess, AddBALSuggestionOutputPort outputPort){
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
