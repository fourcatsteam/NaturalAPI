package fourcats.usecaseinteractor;

import fourcats.port.ApiInputPort;
import fourcats.entity.*;
import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.ApiOutputPort;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SuggestApi implements ApiInputPort {

    BalAnalyzer balAnalyzer;
    RepositoryAccess repositoryAccess;
    ApiOutputPort apiOutputPort;

    public SuggestApi(BalAnalyzer b,RepositoryAccess r,ApiOutputPort apiOutputPort) {
        balAnalyzer = b;
        repositoryAccess = r;
        this.apiOutputPort = apiOutputPort;
    }

    public void create(String filenameBal,String filenamePla){

        if(repositoryAccess.isCoupleBalPlaPresent(filenameBal,filenamePla) == false){
            balAnalyzer.setBalFile(repositoryAccess.openFile("./BAL/" + filenameBal));
            BAL bal = balAnalyzer.getBAL();
            PLA pla = new PLA(repositoryAccess.loadPLA(filenamePla));

            API api = new API();
            for(Actor actor : bal.getActors()){

                for(Action action : actor.getActions()){

                    String newApi = pla.getText();
                    newApi = insertGroup(newApi, action.getName());
                    newApi = insertActionType(newApi, action.getType().getName());
                    newApi = insertActionName(newApi, action.getName());


                    int size = action.getObjectParams().size();
                    Pattern p = Pattern.compile("\"object_type\".*\"object_name\"");
                    Matcher m = p.matcher(newApi);
                    if(m.find()){
                        while(size != 1){
                            newApi = m.replaceFirst(m.group() + ", " + m.group());
                            size--;
                        }
                    }

                    for(ObjectParam objectParam : action.getObjectParams()) {

                        String customApi = pla.getCustomClass();
                        newApi = insertObjectType(newApi, objectParam.getType().getName());
                        newApi = insertObjectName(newApi, objectParam.getName());

                        if(objectParam.getType().getAttributes().keySet().stream().findFirst().isPresent()) {

                            int numAttributes = objectParam.getType().getAttributes().size();

                            customApi = customApi.substring(0,customApi.lastIndexOf("}"));

                            java.util.Iterator<String> iteratorKeys = objectParam.getType().getAttributes().keySet().iterator();
                            java.util.Iterator<String> iteratorValues = objectParam.getType().getAttributes().values().iterator();
                            while(numAttributes > 0) {

                                customApi = customApi.replaceAll("\"attribute_name\"", iteratorKeys.next());
                                customApi = customApi.replaceAll("\"attribute_type\"", iteratorValues.next());
                                if(numAttributes > 1) {
                                    customApi = customApi.concat(pla.getCustomBody());
                                }
                                numAttributes--;
                            }
                            customApi = customApi.concat("}");

                            customApi = customApi.replaceAll("\"custom_class\"",objectParam.getType().getName());
                            repositoryAccess.openFile("./API/" + actor.getName() + "/" +"/CustomClasses/").mkdir();
                            api.addApi("./API/" + actor.getName() + "/" +"/CustomClasses/"
                                    + objectParam.getType().getName() + "_" + repositoryAccess.getSize()
                                    + pla.getExtension(),customApi);
                        }
                    }
                    repositoryAccess.openFile("./API/" + actor.getName() + "/").mkdir();
                    repositoryAccess.openFile("./API/" + actor.getName() + "/" +"/CustomClasses/").mkdir();
                    api.addApi("./API/" + actor.getName() + "/" + action.getName().substring(0,1).toUpperCase() +
                            action.getName().substring(1) + "_" + repositoryAccess.getSize() + pla.getExtension(), newApi);
                }
            }
            repositoryAccess.addApi(api);
            repositoryAccess.addCoupleBalPla(filenameBal,filenamePla);
            apiOutputPort.showOutput(repositoryAccess.getApiMap());
        }
    }

    private String insertGroup(String text,String toReplace){
        //CamelCase
        toReplace = toReplace.substring(0,1).toUpperCase() + toReplace.substring(1);
        return text.replaceFirst("\"group_action\"", toReplace);
    }

    private String insertActionType (String text,String toReplace){
        return text.replaceFirst("\"action_type\"", toReplace);
    }

    private String insertActionName (String text,String toReplace){
        return text.replaceFirst("\"action_name\"", toReplace);
    }

    private String insertObjectType (String text,String toReplace){
        return text.replaceFirst("\"object_type\"", toReplace);
    }

    private String insertObjectName (String text,String toReplace){
        return text.replaceFirst("\"object_name\"", toReplace);
    }

}
