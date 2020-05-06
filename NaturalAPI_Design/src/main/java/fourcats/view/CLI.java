package fourcats.view;

import fourcats.datastructure.observer.Observer;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class CLI implements Observer {
    private Controller contr;
    private String currentUseCase = "";
    final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private DataPresenter dataPresenter;
    private boolean isBdlLoaded = false;

    public CLI(Controller controller, DataPresenter presenter){
        this.contr = controller;
        this.dataPresenter = presenter;
        this.dataPresenter.attach(this);
    }

    public void askForUseCase(){
        System.out.println("\n-------NATURAL API DESIGN-------");
        System.out.println("\n1. Generate Suggestions");
        if (!isBdlLoaded)
            System.out.println("2. Load BDL");
        else
            System.out.println("2. Remove loaded BDL");
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
                    if (!isBdlLoaded)
                        loadBdl();
                    else
                        contr.removeBdl();
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

    private void loadBdl() throws IOException {
        String[] bdlFiles = chooseBdlFiles();
        if (bdlFiles!=null) {
            contr.loadBdl(bdlFiles);
        }
    }

    private String[] chooseBdlFiles() throws IOException {
        String[] bdlFiles = new String[3];
        String input="";
        System.out.println("Enter the path of one of the BDL file\n"+
                "Notice: all the 3 files (.nouns, .verbs, .predicates) must be in the same dir\n"+
                "Digit EXIT to abort.\n");
        input = br.readLine();
        if (input.equals("EXIT") || input.equals("")) {
            return null;
        }
        Arrays.fill(bdlFiles, input); //this is needed as the loadBdl file functions needs 3 paths..
        return bdlFiles;
    }

    private List<String> chooseFeatureFile() throws IOException {
        List<String> lFiles = new ArrayList<>();
        String input="";
        while(!input.equals("EXIT")){
            System.out.println("Enter the path of the gherkin feature file, digit EXIT when you're done.");
            input = br.readLine();
            if (!input.equals("EXIT")){
                lFiles.add(input);
            }
        }
        return lFiles;
    }

    private void askForOperationOnSuggestion() throws IOException {
        String input = "";
        while(!input.equalsIgnoreCase("EXIT")) {
            System.out.println("\nWhat do you want to do?\n1. Modify suggestion\n2. Delete suggestion\n3. Add suggestion" +
                    "\n4. Add new feature\n5. Generate BAL\nDigit EXIT to abort and go back to the main menu.");
            input = br.readLine();
            switch(input){
                case "1":
                    modifySuggestion();
                    break;
                case "2":
                    deleteSuggestion();
                    break;
                case "3":
                    addSuggestion();
                    break;
                case "4":
                    contr.generateSuggestions(chooseFeatureFile(),false);
                    break;
                case "5":
                    System.out.println("Please, enter the path including the name for the BAL");
                    String balPath = br.readLine();
                    contr.generateBAL(balPath);
                    break;
                default:
                    System.out.println("Please choose a valid option. Digit EXIT to exit.");
                    break;
            }
        }
    }

    private void modifySuggestion() throws IOException {
        String idScenario = askForIdScenarioToModify();
        String idSuggestion = askForIdSuggestionToModify();
        System.out.println("1. Modify action name\n2. Modify action type\n3. Modify object name\n4. Modify object type\n5. Add object\n6. Remove object");
        String input = br.readLine();
        switch(input){
            case "1":
                modifyActionName(idScenario,idSuggestion);
                break;
            case "2":
                modifyActionType(idScenario,idSuggestion);
                break;
            case "3":
                modifyObjectName(idScenario,idSuggestion);
                break;
            case "4":
                modifyObjectType(idScenario,idSuggestion);
                break;
            case "5":
                addObject(idScenario,idSuggestion);
                break;
            case "6":
                removeObject(idScenario,idSuggestion);
                break;
            default:
                System.out.println("Error! Please insert a valid option");
                break;
        }
    }

    private void addSuggestion() throws IOException {
        String idScenario = askForIdScenarioToModify();
        System.out.println("Insert the name for the new Action");
        String actionName = br.readLine();
        String idType = askForIdType();
        contr.addSuggestionByIdType(idScenario,actionName,idType);
    }

    private String createCustomType() throws IOException {
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
        List<String> lFilesPath = chooseFeatureFile();
        contr.generateSuggestions(lFilesPath,true);
    }

    private void modifyActionName(String idScenario, String idSuggestion) throws IOException {
        System.out.println("Please insert the new name of the action");
        String actionName = br.readLine();
        contr.modifyActionName(idSuggestion,idScenario,actionName);
    }

    private void modifyActionType(String idScenario, String idSuggestion) throws IOException {
        System.out.println("1. Restore action to default type (void) 2. Show other types");
        String input = br.readLine();
        if (input.equals("1")){
            contr.modifyActionType(idSuggestion, idScenario, "void");
        }
        else if (input.equals("2")){
            String idType = askForIdType();
            contr.modifyActionTypeById(idSuggestion,idScenario,idType);
        }
        else{
            System.out.println("Error: invalid option");
        }
    }
    private void modifyObjectName(String idScenario, String idSuggestion) throws IOException {
        String idObject = askForIdObjectToModify();
        System.out.println("Please insert the new name for the object");
        String objectName = br.readLine();
        contr.modifyObjectName(idSuggestion,idScenario,idObject,objectName);
    }

    private void modifyObjectType(String idScenario, String idSuggestion) throws IOException {
        String idObject = askForIdObjectToModify();
        String idType = askForIdType();
        contr.modifyObjectTypeById(idSuggestion,idScenario,idObject,idType);
    }

    private void addObject(String idScenario, String idSuggestion) throws IOException {
        System.out.println("Insert new object name");
        String objectName = br.readLine();
        String idType = askForIdType();
        contr.addObject(idSuggestion,idScenario,objectName,idType);
    }

    private void removeObject(String idScenario, String idSuggestion) throws IOException {
        System.out.println("Please insert the id of the object you want to remove: 0 first, 1 second, 2 third...");
        String idObject = br.readLine();
        contr.removeObject(idSuggestion,idScenario,idObject);
    }

    private String askForIdScenarioToModify() throws IOException {
        System.out.println("Please insert the id of the scenario you want to modify");
        return br.readLine();
    }
    private String askForIdSuggestionToModify() throws IOException {
        System.out.println("Please insert the id of the suggestion you want to modify");
        return br.readLine();
    }
    private String askForIdObjectToModify() throws IOException {
        System.out.println("Please insert the id of the object you want to modify: 0 first, 1 second, 2 third...");
        return br.readLine();
    }
    private String askForIdType() throws IOException {
        contr.showTypes();
        System.out.println("Insert the id for the desired type.\nDigit CREATE to create a custom one.");
        String input = br.readLine();
        while (input.equals("CREATE")){
            createCustomType();
            contr.showTypes();
            System.out.println("Insert the id for the desired type.\nDigit CREATE to create a custom one.");
            input = br.readLine();
        }
        return input;
    }

    @Override
    public void update() {
        System.out.println(dataPresenter.getDataToShow());
        isBdlLoaded = dataPresenter.isBdlLoaded();
    }
}
