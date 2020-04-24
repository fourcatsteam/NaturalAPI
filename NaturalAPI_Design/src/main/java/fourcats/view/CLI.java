package fourcats.view;

import fourcats.datastructure.observer.Observer;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class CLI implements Observer {
    private Controller contr;
   // private Repository repo;
    private String currentUseCase = "";
    private LinkedList<String> nameTitleList;
    final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private DataPresenter dataPresenter;

    public CLI(Controller controller, DataPresenter presenter){
        this.contr = controller;
        nameTitleList = new LinkedList<>();
        this.dataPresenter = presenter;
        this.dataPresenter.attach(this);
    }

    public void askForUseCase(){
        System.out.println("\n-------NATURAL API DESIGN-------");
        System.out.println("\n1. Generate Suggestions");
        System.out.println("2. Load BAL (TODO)");
        System.out.println("EXIT. Quit Application");
    }

    public boolean readUseCase(){
        boolean shouldContinue = true;
        try {
            currentUseCase = br.readLine();
            switch (currentUseCase) {
                case "1":
                    generateSuggestion();
                    askForOperationOnSuggestion();
                    break;
                case "2":
                    System.out.println("Get out of here, This is a Work in progress!");
                    break;
                case "EXIT":
                    shouldContinue = false;
                    break;
                default:
                    System.out.println("Please insert a valid option. Digit EXIT to exit.");
                    break;

            }

        } catch (IOException e) {
            e.printStackTrace();
            shouldContinue = false;
        }
        return shouldContinue;
    }

    private String chooseFile() throws IOException {
        System.out.println("Choose a gherkin feature namefile, digit EXIT to exit.");
        String r = br.readLine();
        if(!r.equals("EXIT")){
            nameTitleList.add(r);
        }
        return r;
    }

    private void askForOperationOnSuggestion() throws IOException {
        String input = "";
        while(!input.equalsIgnoreCase("EXIT")) {
            System.out.println("\nDo you want to modify or delete a suggestion? 1. YES 2. NO. Digit EXIT to abort.");
            input = br.readLine();
            if (input.equals("1")) {
                modifySuggestion();
            }
            else if (input.equals("2")){
                System.out.println("1. Add new scenario 2. Generate BAL. Digit EXIT to abort");
                input = br.readLine();
                if(input.equals("1")){
                    contr.generateSuggestions(chooseFile());
                }
                else if(input.equals("2")){
                    System.out.println("Please, insert the name for the BAL");
                    input = br.readLine();
                    contr.generateBAL(input);
                    return;
                }
            }
        }
    }

    private void modifySuggestion() throws IOException {
        System.out.println("1. Delete 2. Modify suggestion");
        String input = br.readLine();
        if (input.equals("1")) {
            deleteSuggestion();
        }
        else if(input.equals("2")){
            String idScenario = askForIdScenarioToModify();
            String idSuggestion = askForIdSuggestionToModify();
            String idParameter = "";
            System.out.println("1. Modify suggestion name\n2. Modify parameter name\n3. Modify parameter type");
            input = br.readLine();
            if(input.equals("1")) {
                modifyActionName(idScenario,idSuggestion);
            }
            else if(input.equals("2")){
                idParameter = askForIdParameterToModify();
                modifyObjectName(idScenario,idSuggestion,idParameter);
            }
            else if(input.equals("3")){
                idParameter = askForIdParameterToModify();
                System.out.println("1. Simple type, 2. Complex type");
                input = br.readLine();
                String type = "";
                if (input.equals("1")) {
                    type = askForSimpleTypeOption();
                    contr.modifyObjectType(idSuggestion,idScenario,idParameter,type);
                }
                else if(input.equals("2")){
                    askForComplexTypeOption(idSuggestion,idScenario,idParameter);
                }
            }
        }
    }

    private void askForComplexTypeOption(String idSuggestion,String idScenario,String idParameter) throws IOException {
        System.out.println("1. Create complex type, 2. Show already defined ones");
        String input = br.readLine();
        if (input.equals("1")) {
            String customType = createCustomObjectType();
            contr.modifyObjectType(idSuggestion, idScenario, idParameter, customType);
        }
        else if (input.equals("2")){
            contr.showCustomTypes();
            System.out.println("Choose the id for the corresponding type or digit EXIT to quit");
            input = br.readLine();
            if(!input.equals("EXIT")){
                contr.modifyObjectTypeById(idSuggestion,idScenario,idParameter,input);
            }
        }
    }

    private String createCustomObjectType() throws IOException {
        Map<String, String> mAttributes = new HashMap<>();
        System.out.println("Insert the name for the custom type");
        String customTypeName = br.readLine();
        boolean isDone = false;
        while(!isDone) {
            System.out.println("Insert the name of the attribute for the type '" + customTypeName + "'");
            String attributeName = br.readLine();
            System.out.println("Insert the type for the attribute '" + attributeName + "'");
            String attributeType = askForSimpleTypeOption();
            mAttributes.put(attributeName,attributeType);
            System.out.println("Here the attribute you defined: " + attributeType + " " + attributeName);
            System.out.println("\nDo you want to add another attribute? 1. Yes 2. No");
            if (br.readLine().equals("2")) {
                isDone = true;
            }
        }
        contr.createCustomType(customTypeName,mAttributes);
        return customTypeName;
    }


    private String askForSimpleTypeOption() throws IOException {
        System.out.println("1. string, 2. int, 3. float, 4. double, 5. bool");
        String input = br.readLine();
        switch (input) {
            case "1":
                return "string";
            case "2":
                return "int";
            case "3":
                return "float";
            case "4":
                return "double";
            case "5":
                return "bool";
            default:
                System.out.println("Error. Please insert a valid option");
                return null;
        }
    }


    private void deleteSuggestion() throws IOException {
        System.out.println("Please insert the id of the scenario for the suggestion you want to delete");
        String idScenario = br.readLine();
        System.out.println("Please insert the id of the suggestion you want to delete");
        String idSuggestion = br.readLine();
        contr.declineSuggestion(idSuggestion, idScenario);
    }

    public void generateSuggestion() throws IOException {
        String filename = "";
        while(!filename.equalsIgnoreCase("EXIT")) {
            filename = chooseFile();
            if (!filename.equalsIgnoreCase("EXIT"))
                contr.generateSuggestions(filename);
        }
    }

    private void modifyActionName(String idScenario, String idSuggestion) throws IOException {
        System.out.println("Please insert the new name of the action");
        String actionName = br.readLine();
        contr.modifyActionName(idSuggestion,idScenario,actionName);
    }

    private void modifyObjectName(String idScenario, String idSuggestion, String idParameter) throws IOException {
        System.out.println("Please insert the new name of the parameter");
        String objectName = br.readLine();
        contr.modifyObjectName(idSuggestion,idScenario,idParameter,objectName);
    }

    private String askForIdScenarioToModify() throws IOException {
        System.out.println("Please insert the id of the scenario for the suggestion you want to modify");
        return br.readLine();
    }
    private String askForIdSuggestionToModify() throws IOException {
        System.out.println("Please insert the id of the suggestion you want to modify");
        return br.readLine();
    }
    private String askForIdParameterToModify() throws IOException {
        System.out.println("Please insert the id of the parameter you want to modify: 0 first, 1 second, 2 third...");
        return br.readLine();
    }

    @Override
    public void update() {
        System.out.println(dataPresenter.getDataToShow());
    }
}
