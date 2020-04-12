package FourCats.UseCaseInteractor;

import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.InterfaceAccess.TextAnalyzer;
import FourCats.Port.DeclineBALSuggestionInputPort;
import FourCats.Port.DeclineBALSuggestionOutputPort;
import FourCats.Port.GenerateBALSuggestionsOutputPort;

public class DeclineBALSuggestion implements DeclineBALSuggestionInputPort {
    RepositoryAccess repo;
    DeclineBALSuggestionOutputPort out;

    public DeclineBALSuggestion(RepositoryAccess repositoryAccess, DeclineBALSuggestionOutputPort outputPort){
        this.repo = repositoryAccess;
        this.out = outputPort;
    }
    public void declineSuggestion(int idAction, int idScenario){
        boolean isOk = repo.deleteSuggestion(idAction, idScenario);
        out.showDeclinedSuggestion(repo.readScenarios(),isOk);

    }
}
