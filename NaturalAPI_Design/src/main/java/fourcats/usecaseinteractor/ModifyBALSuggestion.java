package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.ModifyBALSuggestionInputPort;
import fourcats.port.ModifyBALSuggestionOutputPort;

public class ModifyBALSuggestion implements ModifyBALSuggestionInputPort {
    RepositoryAccess repo;
    ModifyBALSuggestionOutputPort out;

    public ModifyBALSuggestion(RepositoryAccess repositoryAccess, ModifyBALSuggestionOutputPort outputPort){
        this.repo = repositoryAccess;
        this.out = outputPort;
    }
    @Override
    public void modifyActionType(int idAction, int idScenario, String newType) {
        repo.updateActionType(idAction, idScenario, newType); //TODO
    }

    @Override
    public void modifyActionName(int idAction, int idScenario, String newName) {
        try {
            repo.updateActionName(idAction, idScenario, newName);
            out.showModifiedAction(repo.readScenarios(), true);
        }
        catch (NullPointerException e){
            out.showModifiedAction(repo.readScenarios(), false);
        }
    }

    @Override
    public void modifyObjectType(int idAction, int idScenario, int idObject, String newType) {
        repo.updateObjectType(idAction, idScenario, idObject, newType); //TODO
    }

    @Override
    public void modifyObjectName(int idAction, int idScenario, int idObject, String newName) {
        repo.updateObjectName(idAction, idScenario, idObject, newName); //TODO
    }
}
