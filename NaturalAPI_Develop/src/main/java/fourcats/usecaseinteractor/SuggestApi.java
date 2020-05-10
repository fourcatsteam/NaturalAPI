package fourcats.usecaseinteractor;

import fourcats.observer.Subject;
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

            for(Actor actor : bal.getActors()){

                for(Action action : actor.getActions()){

                    String newApi = pla.getText();

                    int size = action.getObjectParams().size();
                    Pattern p = Pattern.compile("\"object_type\".*\"object_name\"");
                    Matcher m = p.matcher(newApi);
                    if(m.find()){
                        while(size > 1){
                            newApi = m.replaceFirst(m.group() + ", " + m.group());
                            size--;
                        }
                    }

                    if(!action.getType().getAttributes().isEmpty()) {
                        createCustomClassForAction(pla,action,actor.getName());
                    }

                    if(action.getObjectParams().isEmpty()){
                        newApi = insertObjectType(newApi, "");
                        newApi = insertObjectName(newApi, "");
                    }
                    else {
                        for (ObjectParam objectParam : action.getObjectParams()) {

                            if (!objectParam.getType().getAttributes().isEmpty()) {
                                createCustomClassForObjectParam(pla, objectParam, actor.getName());
                            }

                            newApi = insertObjectType(newApi, objectParam.getType().getName());
                            newApi = insertObjectName(newApi, objectParam.getName());

                        }
                    }

                    String className = action.getName();
                    newApi = insertGroup(newApi,className);
                    newApi = insertActionType(newApi, action.getType().getName());
                    newApi = insertActionName(newApi, action.getName());
                    API api = new API();
                    api.setFilename("Api\\" + actor.getName() + "\\" + className.substring(0,1).toUpperCase() + className.substring(1) + pla.getExtension());
                    api.setText(newApi);

                    if(repositoryAccess.isThisApiPresent(api) == false){
                        int num = 0;
                        String holdClassName = className;
                        while(repositoryAccess.isThisClassNamePresent(api)){
                            num++;
                            String newClassName = holdClassName.concat("__" + num);
                            newClassName = newClassName.substring(0,1).toUpperCase() + newClassName.substring(1);
                            className = className.substring(0,1).toUpperCase() + className.substring(1);
                            api.setText(api.getText().replace(className,newClassName));
                            api.setFilename(api.getFilename().replace(className,newClassName));
                            className = newClassName;
                        }
                        repositoryAccess.addApi(api);
                    }

                    if(!action.getStep().equals("null")) {
                        String step = action.getStep();

                        String[] splitKeyword = step.split(" ");
                        String keyword = splitKeyword[0];

                        step = "";
                        for(int i = 1; i < splitKeyword.length-1; i++){
                            step = step.concat(splitKeyword[i]);
                            step = step.concat(" ");
                        }
                        step = step.concat(splitKeyword[splitKeyword.length-1]);

                        step = step.replace(" ", "_");
                        step = step.toLowerCase();
                        step = step.replace("\"", "");
                        String testApi = pla.getTestClass();
                        String classTestName = step;
                        testApi = insertKeyword(testApi,keyword);
                        testApi = insertTestStub(testApi, classTestName);
                        testApi = insertGroup(testApi, className);
                        testApi = insertActionName(testApi, action.getName());
                        API test = new API();
                        test.setFilename("Test\\" + actor.getName() + "\\" + classTestName + pla.getExtension());
                        test.setText(testApi);
                        if (repositoryAccess.isThisApiPresent(test) == false) {
                            int num = 0;
                            String holdClassTestName = classTestName;
                            while (repositoryAccess.isThisClassNamePresent(test)) {
                                num++;
                                String newClassTestName = holdClassTestName.concat("__" + num);
                                test.setText(test.getText().replace(classTestName, newClassTestName));
                                test.setFilename(test.getFilename().replace(classTestName, newClassTestName));
                                classTestName = newClassTestName;
                            }
                            repositoryAccess.addApi(test);
                        }
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
        return text.replace("\"group_action\"", toReplace);
    }

    private String insertActionType (String text,String toReplace){
        return text.replace("\"action_type\"", toReplace);
    }

    private String insertActionName (String text,String toReplace){
        return text.replace("\"action_name\"", toReplace);
    }

    private String insertObjectType (String text,String toReplace){
        return text.replace("\"object_type\"", toReplace);
    }

    private String insertObjectName (String text,String toReplace){
        return text.replace("\"object_name\"", toReplace);
    }

    private String insertTestStub(String text,String toReplace) {
        return text.replace("\"test_stub\"", toReplace);
    }

    private String insertKeyword(String text,String toReplace) {
        return text.replace("\"keyword\"", toReplace);
    }


    private void createCustomClassForAction(PLA pla, Action action, String actor) {

        API api = new API();
        String customApi = pla.getCustomClass();

        int numAttributes = action.getType().getAttributes().size();

        customApi = customApi.substring(0,customApi.lastIndexOf("}"));

        java.util.Iterator<String> iteratorKeys = action.getType().getAttributes().keySet().iterator();
        java.util.Iterator<String> iteratorValues = action.getType().getAttributes().values().iterator();
        while(numAttributes > 0) {

            customApi = customApi.replace("\"attribute_name\"", iteratorKeys.next());
            customApi = customApi.replace("\"attribute_type\"", iteratorValues.next());
            if(numAttributes > 1) {
                customApi = customApi.concat(pla.getCustomBody());
            }
            numAttributes--;
        }
        customApi = customApi.concat("}");

        String className = action.getType().getName();
        customApi = customApi.replace("\"custom_class\"",className);

        api.setFilename("Custom classes\\" + actor + "\\" + className + pla.getExtension());
        api.setText(customApi);

        if(repositoryAccess.isThisApiPresent(api) == false){
            int num = 0;
            String holdClassName = className;
            while(repositoryAccess.isThisClassNamePresent(api)){
                num++;
                String newClassName = holdClassName.concat("__" + num);
                newClassName = newClassName.substring(0,1).toUpperCase() + newClassName.substring(1);
                className = className.substring(0,1).toUpperCase() + className.substring(1);
                api.setText(api.getText().replace(className,newClassName));
                api.setFilename(api.getFilename().replace(className,newClassName));
                className = newClassName;
            }
            repositoryAccess.addApi(api);
        }
    }

    private void createCustomClassForObjectParam(PLA pla, ObjectParam objectParam, String actor) {

        API api = new API();
        String customApi = pla.getCustomClass();

        int numAttributes = objectParam.getType().getAttributes().size();

        customApi = customApi.substring(0,customApi.lastIndexOf("}"));

        java.util.Iterator<String> iteratorKeys = objectParam.getType().getAttributes().keySet().iterator();
        java.util.Iterator<String> iteratorValues = objectParam.getType().getAttributes().values().iterator();
        while(numAttributes > 0) {

            customApi = customApi.replace("\"attribute_name\"", iteratorKeys.next());
            customApi = customApi.replace("\"attribute_type\"", iteratorValues.next());
            if(numAttributes > 1) {
                customApi = customApi.concat(pla.getCustomBody());
            }
            numAttributes--;
        }
        customApi = customApi.concat("}");

        String className = objectParam.getType().getName();
        customApi = customApi.replace("\"custom_class\"",className);

        api.setFilename("Custom classes\\" + actor + "\\" + className + pla.getExtension());
        api.setText(customApi);

        if(repositoryAccess.isThisApiPresent(api) == false){
            int num = 0;
            String holdClassName = className;
            while(repositoryAccess.isThisClassNamePresent(api)){
                num++;
                String newClassName = holdClassName.concat("__" + num);
                newClassName = newClassName.substring(0,1).toUpperCase() + newClassName.substring(1);
                className = className.substring(0,1).toUpperCase() + className.substring(1);
                api.setText(api.getText().replace(className,newClassName));
                api.setFilename(api.getFilename().replace(className,newClassName));
                className = newClassName;
            }
            repositoryAccess.addApi(api);
        }
    }

}
