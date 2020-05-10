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
            balAnalyzer.setBalFile(repositoryAccess.openFile(filenameBal));
            BAL bal = balAnalyzer.getBAL();
            PLA pla = new PLA(repositoryAccess.loadPLA(filenamePla));
            PLA testPla = new PLA(repositoryAccess.loadPLA("C:\\Users\\matte\\OneDrive\\Desktop\\NaturalAPI\\NaturalAPI_Develop\\PLA\\PlaPerTestJava.txt"));

            for(Actor actor : bal.getActors()){

                for(Action action : actor.getActions()){

                    String newApi = pla.getText();

                    int size = action.getObjectParams().size();
                    Pattern p = Pattern.compile("\"object_type\".*\"object_name\"");
                    Matcher m = p.matcher(newApi);
                    if(m.find()){
                        while(size != 1){
                            newApi = m.replaceFirst(m.group() + ", " + m.group());
                            size--;
                        }
                    }

                    if(action.getType().getAttributes().keySet().stream().findFirst().isPresent()) {
                        API api = createCustomClassForAction(pla, action);
                        if(repositoryAccess.isThisApiPresent(api) == false){
                            repositoryAccess.addApi(api);
                        }
                    }

                    for(ObjectParam objectParam : action.getObjectParams()) {

                        if(objectParam.getType().getAttributes().keySet().stream().findFirst().isPresent()){
                            API api = createCustomClassForObjectParam(pla,objectParam);
                            if(repositoryAccess.isThisApiPresent(api) == false){
                                repositoryAccess.addApi(api);
                            }
                        }

                        newApi = insertObjectType(newApi, objectParam.getType().getName());
                        newApi = insertObjectName(newApi, objectParam.getName());

                    }
                    String className = action.getName();
                    newApi = insertGroup(newApi,className);
                    newApi = insertActionType(newApi, action.getType().getName());
                    newApi = insertActionName(newApi, action.getName());
                    API api = new API();
                    api.setFilename("Api\\" + className.substring(0,1).toUpperCase() + className.substring(1) + pla.getExtension());
                    api.setText(newApi);
                    if(repositoryAccess.isThisApiPresent(api) == false){
                        int num = 0;
                        while(repositoryAccess.isThisClassNamePresent(api)){
                            num++;
                            String newClassName = className.concat("_" + num);
                            newClassName = newClassName.substring(0,1).toUpperCase() + newClassName.substring(1);
                            className = className.substring(0,1).toUpperCase() + className.substring(1);
                            api.setText(api.getText().replaceAll(className,newClassName));
                            api.setFilename(api.getFilename().replaceAll(className,newClassName));
                            className = newClassName;
                        }
                        repositoryAccess.addApi(api);
                    }

                    String step = action.getStep();
                    step = step.replaceAll(" ", "_");
                    step = step.toLowerCase();
                    step = step.replaceAll("\"","");
                    String testApi = testPla.getText();
                    String classTestName = step;
                    testApi = insertActionType(testApi,classTestName);
                    testApi = insertGroup(testApi,className);
                    testApi = insertActionName(testApi,action.getName());
                    API test = new API();
                    test.setFilename("Test\\" + classTestName + pla.getExtension());
                    test.setText(testApi);
                    if(repositoryAccess.isThisApiPresent(test) == false) {
                        repositoryAccess.addApi(test);
                    }
                }
            }
            repositoryAccess.addCoupleBalPla(filenameBal,filenamePla);
            apiOutputPort.showOutput(repositoryAccess.getApiMap());
        }
    }

    private String insertGroup(String text,String toReplace){
        //CamelCase
        toReplace = toReplace.substring(0,1).toUpperCase() + toReplace.substring(1);
        return text.replaceAll("\"group_action\"", toReplace);
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

    private API createCustomClassForAction(PLA pla, Action action) {

        API api = new API();
        String customApi = pla.getCustomClass();

        int numAttributes = action.getType().getAttributes().size();

        customApi = customApi.substring(0,customApi.lastIndexOf("}"));

        java.util.Iterator<String> iteratorKeys = action.getType().getAttributes().keySet().iterator();
        java.util.Iterator<String> iteratorValues = action.getType().getAttributes().values().iterator();
        while(numAttributes > 0) {

            customApi = customApi.replaceAll("\"attribute_name\"", iteratorKeys.next());
            customApi = customApi.replaceAll("\"attribute_type\"", iteratorValues.next());
            if(numAttributes > 1) {
                customApi = customApi.concat(pla.getCustomBody());
            }
            numAttributes--;
        }
        customApi = customApi.concat("}");

        String className = action.getType().getName();
        customApi = customApi.replaceAll("\"custom_class\"",className);

        api.setFilename("Custom classes\\" + className + pla.getExtension());
        api.setText(customApi);

        return api;
    }

    private API createCustomClassForObjectParam(PLA pla, ObjectParam objectParam) {

        API api = new API();
        String customApi = pla.getCustomClass();

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

        String className = objectParam.getType().getName();
        customApi = customApi.replaceAll("\"custom_class\"",className);

        api.setFilename("Custom classes\\" + className + pla.getExtension());
        api.setText(customApi);
        return api;
    }

}
