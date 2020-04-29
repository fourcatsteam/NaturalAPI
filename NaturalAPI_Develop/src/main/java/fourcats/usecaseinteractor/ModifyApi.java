package fourcats.usecaseinteractor;

import fourcats.entity.*;
import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.ModifyGuiInputPort;
import fourcats.port.ModifyInputPort;
import fourcats.port.ModifyOutputPort;


public class ModifyApi implements ModifyInputPort, ModifyGuiInputPort {

    BalAnalyzer balAnalyzer;
    RepositoryAccess repositoryAccess;
    ModifyOutputPort modifyOutputPort;

    public ModifyApi(BalAnalyzer b,RepositoryAccess r,ModifyOutputPort m){
        balAnalyzer = b;
        repositoryAccess = r;
        modifyOutputPort = m;
    }

    public void modifyGui(String oldApi,String newApi){
        repositoryAccess.updateApi(oldApi,newApi);
    }

    public void modify(int id, String filenameBal, String filenamePla){

        repositoryAccess.deleteApi(id);
        balAnalyzer.setBalFile(repositoryAccess.openFile("./BAL/" + filenameBal));
        BAL bal = balAnalyzer.getBAL();
        PLA pla = new PLA(repositoryAccess.loadPLA(filenamePla));

        API api = new API();
        for(Actor actors : bal.getActors()){

            for(Action action : actors.getActions()){

                String newApi = pla.getText();
                newApi = insertGroup(newApi, action.getName());
                newApi = insertActionType(newApi, action.getType());
                newApi = insertActionName(newApi, action.getName());
                for(ObjectParam objectParam : action.getObjectParam()) {

                    newApi = insertObjectType(newApi, objectParam.getType());
                    newApi = insertObjectName(newApi, objectParam.getName());
                }
                api.addApi("./API/" + action.getName().substring(0,1).toUpperCase() +
                        action.getName().substring(1) + "_" + id + pla.getExtension(), newApi);
            }
        }
        repositoryAccess.addApiWithId(id,api);
        modifyOutputPort.showOutput(repositoryAccess.getApiMap());
    }

    private String insertGroup(String text,String toReplace){
        //CamelCase
        toReplace = toReplace.substring(0,1).toUpperCase() + toReplace.substring(1);
        return text.replaceAll("\"group_action\"", toReplace);
    }

    private String insertActionType (String text,String toReplace){
        return text.replaceAll("\"action_type\"", toReplace);
    }

    private String insertActionName (String text,String toReplace){
        return text.replaceAll("\"action_name\"", toReplace);
    }

    private String insertObjectType (String text,String toReplace){
        return text.replaceAll("\"object_type\"", toReplace);
    }

    private String insertObjectName (String text,String toReplace){
        return text.replaceAll("\"object_name\"", toReplace);
    }
}
