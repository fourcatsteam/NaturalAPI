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
        try {
            repo.updateActionType(idAction, idScenario, newType);
            out.showModifiedActionType(repo.readScenarios(), true);
        }
        catch (Exception e){
            out.showModifiedActionType(repo.readScenarios(), false);
        }
    }

    @Override
    public void modifyActionTypeById(int idAction, int idScenario, int idType) {
        try{
            repo.updateActionTypeById(idAction, idScenario, idType);
            out.showModifiedActionType(repo.readScenarios(),true);
        }
        catch (Exception e) {
            out.showModifiedActionType(repo.readScenarios(),false);
        }
    }

    @Override
    public void modifyActionName(int idAction, int idScenario, String newName,boolean isBdlLoaded) {
        try {
            repo.updateActionName(idAction, idScenario, newName);
            out.showModifiedActionName(repo.readScenarios(), true,newName,isBdlLoaded);
        }
        catch (NullPointerException e){
            //Modifica non effettuata
            out.showModifiedActionName(repo.readScenarios(), false,"",isBdlLoaded);
        }
    }

    @Override
    public void modifyObjectType(int idAction, int idScenario, int idObject, String newType) {
        try{
            repo.updateObjectType(idAction, idScenario, idObject, newType);
            out.showModifiedObjectType(repo.readScenarios(),true);
        }
        catch (Exception e) {
            out.showModifiedObjectType(repo.readScenarios(),false);
        }

    }

    @Override
    public void modifyObjectTypeById(int idAction, int idScenario, int idObject, int idType) {
        try{
            repo.updateObjectTypeById(idAction, idScenario, idObject, idType);
            out.showModifiedObjectType(repo.readScenarios(),true);
        }
        catch (Exception e) {
            out.showModifiedObjectType(repo.readScenarios(),false);
        }
    }

    @Override
    public void modifyObjectName(int idAction, int idScenario, int idObject, String newName,boolean isBdlLoaded) {
        try {
            repo.updateObjectName(idAction, idScenario, idObject, newName);
            out.showModifiedObjectName(repo.readScenarios(),true,newName,isBdlLoaded);
        }
        catch (Exception e){
            out.showModifiedObjectName(repo.readScenarios(),false,"",isBdlLoaded);
        }
    }

    @Override
    public void addObject(int idAction, int idScenario, String objectName, int idType) {
        try {
            repo.createObject(idAction,idScenario,objectName,idType);
            out.showAddedObject(repo.readScenarios(),true);
        }
        catch (Exception e){
            out.showAddedObject(repo.readScenarios(),false);
        }
    }

    @Override
    public void removeObject(int idAction, int idScenario, int idObject) {
        try {
            repo.deleteObject(idAction,idScenario,idObject);
            out.showRemovedObject(repo.readScenarios(),true);
        }
        catch (Exception e){
            out.showRemovedObject(repo.readScenarios(),false);
        }
    }
}
