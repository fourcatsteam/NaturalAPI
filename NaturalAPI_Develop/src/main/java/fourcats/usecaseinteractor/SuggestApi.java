package fourcats.usecaseinteractor;

import fourcats.port.ApiInputPort;
import fourcats.entity.*;
import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.ApiOutputPort;

import java.io.FileNotFoundException;
import java.io.IOException;
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

    public void create(String filenameBal,String filenamePla) throws IOException {

        if(!repositoryAccess.isCoupleBalPlaPresent(filenameBal,filenamePla)){
            balAnalyzer.setBalFile(repositoryAccess.openFile(filenameBal));
            BAL bal = balAnalyzer.getBAL();
            PLA pla = new PLA(repositoryAccess.loadPLA(filenamePla));

            for(Actor actor : bal.getActors()){

                iterateActions(pla,actor);
            }

            API test = new API();
            String[] splitTest = pla.getText().split("\n");
            splitTest[0] = insertGroup(splitTest[0],"StepDefinitions");
            test.setFilename("test\\StepDefinitions" + pla.getExtension());
            test.setText(splitTest[0] + "\n\n" + repositoryAccess.getAllTests() + splitTest[splitTest.length-1]);
            repositoryAccess.addApi(test);

            repositoryAccess.addCoupleBalPla(filenameBal,filenamePla);
            apiOutputPort.showOutput(repositoryAccess.getApiMap());
            apiOutputPort.showMessage("Suggestions created!");
        }
        else {
            apiOutputPort.showMessage("This couple of BAL and PLA is already generated!");
        }
    }

    private void iterateActions(PLA pla,Actor actor) {
        for(Action action : actor.getActions()) {

            String parameters = "";
            if (!action.getName().startsWith("@") && !action.getName().isEmpty()) {

                String newApi = pla.getText();

                int size = action.getObjectParams().size();

                newApi = numberOfParameters(newApi,size);

                if (!(action.getType().getName().equals("void") ||
                        action.getType().getName().equals("string") ||
                        action.getType().getName().equals("int") ||
                        action.getType().getName().equals("float") ||
                        action.getType().getName().equals("double") ||
                        action.getType().getName().equals("bool"))
                ) {
                    createCustomClassForAction(pla, action, actor.getName());
                }

                if (action.getObjectParams().isEmpty()) {
                    newApi = insertObjectType(newApi, "");
                    newApi = insertObjectName(newApi, "");
                } else {

                    String[] split = iterateObjectParams(pla,actor,action,newApi,parameters).split("PARAMETERS");
                    newApi = split[0];
                    parameters = split[1];
                }

                newApi = insertGroup(newApi, action.getName());
                newApi = insertActionType(newApi, action.getType().getName());
                newApi = insertActionName(newApi, action.getName());

                API api = new API();
                api.setFilename("api\\" + actor.getName().toLowerCase().replace(" ","_") + "\\" + action.getName().substring(0, 1).toUpperCase() + action.getName().substring(1) + pla.getExtension());
                api.setText(newApi);

                if (!repositoryAccess.isThisApiPresent(api)) {
                    repositoryAccess.addApi(api);
                }
            }

            createTest(pla,action,parameters);
        }
    }

    private String iterateObjectParams(PLA pla,Actor actor,Action action,String newApi,String parameters) {
        int size = action.getObjectParams().size();
        for (ObjectParam objectParam : action.getObjectParams()) {

            if (!(objectParam.getType().getName().equals("void") ||
                    objectParam.getType().getName().equals("string") ||
                    objectParam.getType().getName().equals("int") ||
                    objectParam.getType().getName().equals("float") ||
                    objectParam.getType().getName().equals("double") ||
                    objectParam.getType().getName().equals("bool"))
            ) {
                createCustomClassForObjectParam(pla, objectParam, actor.getName());
            }

            newApi = insertObjectType(newApi, objectParam.getType().getName());
            newApi = insertObjectName(newApi, objectParam.getName());
            parameters = parameters.concat(objectParam.getType().getName() + " " + objectParam.getName());
            if (size > 1) {
                parameters = parameters.concat(", ");
            }
            size--;
        }
        return newApi.concat("PARAMETERS" + parameters);
    }

    private void createTest(PLA pla,Action action,String parameters){
        String step = action.getStep();

        String[] splitKeyword = step.split(" ");
        String keyword = splitKeyword[0];

        step = "";
        for (int i = 1; i < splitKeyword.length - 1; i++) {
            step = step.concat(splitKeyword[i]);
            step = step.concat(" ");
        }
        step = step.concat(splitKeyword[splitKeyword.length - 1]);
        step = step.replace("\"", "");
        String testApi = pla.getTestClass();
        testApi = insertKeyword(testApi, keyword);
        testApi = insertTestStub(testApi, step);
        testApi = insertTestName(testApi, step.replace(" ", "_"));
        if (!action.getName().isEmpty() && !action.getName().startsWith("@")) {
            testApi = insertGroup(testApi, action.getName());
            testApi = insertActionName(testApi, action.getName());
            testApi = insertObjectName(testApi, parameters);
        } else {
            String[] splitTest = testApi.split("\n");
            testApi = "".concat(splitTest[0] + "\n" + splitTest[1] + "\n\n" + splitTest[splitTest.length - 1] + "\n");
        }

        if (!repositoryAccess.isThisTestPresent(testApi)) {
            repositoryAccess.addTest(testApi);
        }
    }

    private String numberOfParameters(String newApi,int size) {
        Pattern p = Pattern.compile("\"object_type\".*\"object_name\"");
        Matcher m = p.matcher(newApi);
        if (m.find()) {
            while (size > 1) {
                newApi = m.replaceFirst(m.group() + ", " + m.group());
                size--;
            }
        }
        return newApi;
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
        return text.replaceFirst("\"object_type\"", toReplace);
    }

    private String insertObjectName (String text,String toReplace){
        return text.replaceFirst("\"object_name\"", toReplace);
    }

    private String insertTestStub(String text,String toReplace) {
        return text.replace("\"test_stub\"", toReplace);
    }

    private String insertTestName(String text,String toReplace) {
        return text.replace("\"test_name\"", toReplace);
    }

    private String insertKeyword(String text,String toReplace) {
        return text.replace("\"keyword\"", toReplace);
    }


    private void createCustomClassForAction(PLA pla, Action action, String actor) {

        API api = new API();
        String customApi = pla.getCustomClass();

        int numAttributes = action.getType().getAttributes().size();

        customApi = customApi.substring(0,customApi.lastIndexOf('}'));

        java.util.Iterator<String> iteratorKeys = action.getType().getAttributes().keySet().iterator();
        java.util.Iterator<String> iteratorValues = action.getType().getAttributes().values().iterator();
        if(numAttributes > 0) {
            while(numAttributes > 0) {

                customApi = customApi.replace("\"attribute_name\"", iteratorKeys.next());
                customApi = customApi.replace("\"attribute_type\"", iteratorValues.next());
                if(numAttributes > 1) {
                    customApi = customApi.concat(pla.getCustomBody());
                }
                numAttributes--;
            }
            customApi = customApi.concat("}");
        }
        else {
            customApi = customApi.split("\n")[0].concat("\n\n}");
        }

        customApi = customApi.replace("\"custom_class\"",action.getType().getName());

        api.setFilename("custom_classes\\" + actor.toLowerCase().replace(" ","_") + "\\" + action.getType().getName() + pla.getExtension());
        api.setText(customApi);

        if(!repositoryAccess.isThisApiPresent(api)){
            repositoryAccess.addApi(api);
        }
    }

    private void createCustomClassForObjectParam(PLA pla, ObjectParam objectParam, String actor) {

        API api = new API();
        String customApi = pla.getCustomClass();

        int numAttributes = objectParam.getType().getAttributes().size();

        customApi = customApi.substring(0,customApi.lastIndexOf('}'));

        java.util.Iterator<String> iteratorKeys = objectParam.getType().getAttributes().keySet().iterator();
        java.util.Iterator<String> iteratorValues = objectParam.getType().getAttributes().values().iterator();
        if(numAttributes > 0) {
            while (numAttributes > 0) {

                customApi = customApi.replace("\"attribute_name\"", iteratorKeys.next());
                customApi = customApi.replace("\"attribute_type\"", iteratorValues.next());
                if (numAttributes > 1) {
                    customApi = customApi.concat(pla.getCustomBody());
                }
                numAttributes--;
            }
            customApi = customApi.concat("}");
        }
        else {
            customApi = customApi.split("\n")[0].concat("\n\n}");
        }

        customApi = customApi.replace("\"custom_class\"",objectParam.getType().getName());

        api.setFilename("custom_classes\\" + actor.toLowerCase().replace(" ","_") + "\\" + objectParam.getType().getName() + pla.getExtension());
        api.setText(customApi);

        if(!repositoryAccess.isThisApiPresent(api)){
            repositoryAccess.addApi(api);
        }
    }
}
