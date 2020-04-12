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
        try{
            repo.deleteSuggestion(idAction, idScenario);
            out.showDeclinedSuggestion(repo.readScenarios(),true);
        }
        catch (NullPointerException e){
            out.showDeclinedSuggestion(repo.readScenarios(),false); //SE VIENE CANCELLATO ULTIMO ELEMENTO DI SCENARIO SI SDOPPIA
        }
    }
}
