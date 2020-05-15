package fourcats.view;

import fourcats.datastructure.observer.Observer;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;
import fourcats.view.utilities.ViewUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Cli implements Observer {
    private Controller contr;
    final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private DataPresenter dataPresenter;
    private boolean isBdlLoaded = false;


    public Cli(Controller controller, DataPresenter presenter) {
        this.contr = controller;
        this.dataPresenter = presenter;
        this.dataPresenter.attach(this);
    }

    public void askForUseCase() {
        print("\n-------NATURAL API DESIGN-------");
        print("\n1. Generate Suggestions");
        if (!isBdlLoaded)
            print("2. Load BDL");
        else
            print("2. Remove loaded BDL");
        print("EXIT. Quit Application");
    }

    public boolean readUseCase() {
        String input = "";
        boolean shouldContinue = true;
        try {
            input = br.readLine();
            switch (input) {
                case "1":
                    generateSuggestion();
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
                    print("Please insert a valid option. Digit EXIT to exit.");
                    break;
            }

        } catch (IOException e) {
            shouldContinue = false;
        }
        return shouldContinue;
    }

    private void loadBdl() throws IOException {
        String[] bdlFiles = chooseBdlFiles();
        if (bdlFiles != null) {
            contr.loadBdl(bdlFiles);
        }
    }

    private String[] chooseBdlFiles() throws IOException {
        String[] bdlFiles = new String[3];
        String input = "";
        print("Enter the path of one of the BDL file\n" +
                "Notice: all the 3 files (.nouns, .verbs, .predicates) must be in the same dir\n" +
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
        String input = "";
        while (!input.equals("EXIT")) {
            print("Enter the path of the gherkin feature file, digit EXIT when you're done.");
            input = br.readLine();
            if (!input.equals("EXIT")) {
                if (ViewUtility.isFeaturePathValid(input)) lFiles.add(input);
                else print("\nInvalid file: this is not a .feature file!\n");
            }
        }
        return lFiles;
    }

    private void askForOperationOnSuggestion() throws IOException {
        String input = "";
        while (!input.equalsIgnoreCase("EXIT")) {
            print("\nWhat do you want to do?\n1. Modify suggestion\n2. Delete suggestion\n3. Add suggestion" +
                    "\n4. Add new feature\n5. Generate BAL\nDigit EXIT to abort and go back to the main menu.");
            input = br.readLine();
            switch (input) {
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
                    contr.generateSuggestions(chooseFeatureFile(), false);
                    break;
                case "5":
                    print("Please, enter the path including the name for the BAL");
                    String balPath = br.readLine();
                    contr.generateBAL(balPath);
                    break;
                default:
                    print("Please choose a valid option. Digit EXIT to exit.");
                    break;
            }
        }
    }

    private void modifySuggestion() throws IOException {
        String idScenario = askForIdScenarioToModify();
        String idSuggestion = askForIdSuggestionToModify();
        print("1. Modify action name\n2. Modify action type\n3. Modify parameter name\n4. Modify parameter type\n5. Add parameter\n6. Remove parameter");
        String input = br.readLine();
        switch (input) {
            case "1":
                modifyActionName(idScenario, idSuggestion);
                break;
            case "2":
                modifyActionType(idScenario, idSuggestion);
                break;
            case "3":
                modifyObjectName(idScenario, idSuggestion);
                break;
            case "4":
                modifyObjectType(idScenario, idSuggestion);
                break;
            case "5":
                addObject(idScenario, idSuggestion);
                break;
            case "6":
                removeObject(idScenario, idSuggestion);
                break;
            default:
                print("Error! Please insert a valid option");
                break;
        }
    }

    private void addSuggestion() throws IOException {
        String idScenario = askForIdScenarioToModify();
        print("Insert the name for the new Action");
        String actionName = br.readLine();
        if (actionName.equals("") || isNumeric(actionName)) {
            print("Abort: invalid name.");
            return;
        }
        String idType = askForIdType();
        contr.addSuggestionByIdType(idScenario, actionName, idType);
    }

    private void createCustomType() throws IOException {
        Map<String, String> mAttributes = new HashMap<>();
        print("Insert the name for the custom type");
        String customTypeName = br.readLine().trim().replace(' ','_');
        if (!customTypeName.equals("") && !isNumeric(customTypeName)) {
            boolean isDone = false;
            print("Do you want to add an attribute for the type '" + customTypeName + "'? 1. Yes 2. No");
            String input = br.readLine();
            if (input.equals("2")) isDone = true;
            else if (!input.equals("1")) {
                print("Invalid option. Abort.");
                return;
            }
            while (!isDone) {
                askForAttribute(mAttributes,customTypeName);
                print("\nDo you want to add another attribute? 1. Yes 2. No");
                if (br.readLine().equals("2")) {
                    isDone = true;
                }
            }
            contr.createCustomType(customTypeName, mAttributes);
        }
    }

    private void askForAttribute(Map<String,String> mAttributes, String customTypeName) throws IOException {
        print("Insert the name of the attribute for the type '" + customTypeName + "'");
        print("NOTICE: All attribute names must be unique (otherwise only the last one entered will be retained).");
        String attributeName = br.readLine().trim().replace(' ','_');
        if (!attributeName.equals("") && !isNumeric(attributeName)) {
            print("Insert the type for the attribute '" + attributeName + "'");
            String attributeType = askForDefinedTypeId();
            if (attributeType!=null) {
                mAttributes.put(attributeName, attributeType);
                print("Here the attribute you defined: " + attributeType + " " + attributeName);
            }
            else {
                print("Invalid input. Abort.");
            }
        }
    }


    private String askForDefinedTypeId() throws IOException {
        String idType = "";
        while(true) {
            contr.showTypes();
            print("Insert the id for the desired type.");
            idType = br.readLine();
            if (!isNumeric(idType) || Integer.parseInt(idType)>=dataPresenter.getTypes().size()){
                print("Invalid input.");
            }
            else{
                return dataPresenter.getTypes().get(Integer.parseInt(idType));
            }
        }
    }


    private void deleteSuggestion() throws IOException {
        print("Please insert the id of the scenario for the suggestion you want to delete");
        String idScenario = br.readLine();
        if (!isNumeric(idScenario)) return;
        print("Please insert the id of the suggestion you want to delete");
        String idSuggestion = br.readLine();
        if (!isNumeric(idSuggestion)) return;
        contr.declineSuggestion(idSuggestion, idScenario);
    }

    public void generateSuggestion() throws IOException {
        List<String> lFilesPath = chooseFeatureFile();
        if (!lFilesPath.isEmpty()) {
            contr.generateSuggestions(lFilesPath, true);
            askForOperationOnSuggestion();
        }
    }

    private void modifyActionName(String idScenario, String idSuggestion) throws IOException {
        String actionName = "";
        while (actionName.equals("") || isNumeric(actionName)){
            print("Please insert the new name of the action");
            actionName = br.readLine();
        }
        contr.modifyActionName(idSuggestion, idScenario, actionName);
    }

    private void modifyActionType(String idScenario, String idSuggestion) throws IOException {
        print("1. Restore action to default type (void) 2. Show other types");
        String input = br.readLine();
        if (input.equals("1")) {
            contr.modifyActionType(idSuggestion, idScenario, "void");
        } else if (input.equals("2")) {
            String idType = askForIdType();
            contr.modifyActionTypeById(idSuggestion, idScenario, idType);
        } else {
            print("Error: invalid option");
        }
    }

    private void modifyObjectName(String idScenario, String idSuggestion) throws IOException {
        String idObject = askForIdObjectToModify();
        String objectName = "";
        while (objectName.equals("") || isNumeric(objectName)) {
            print("Please insert the new name for the parameter");
            objectName = br.readLine();
        }
        contr.modifyObjectName(idSuggestion, idScenario, idObject, objectName);
    }

    private void modifyObjectType(String idScenario, String idSuggestion) throws IOException {
        String idObject = askForIdObjectToModify();
        String idType = askForIdType();
        contr.modifyObjectTypeById(idSuggestion, idScenario, idObject, idType);
    }

    private void addObject(String idScenario, String idSuggestion) throws IOException {
        print("Insert new parameter name");
        String objectName = br.readLine();
        if(!objectName.equals("") && !isNumeric(objectName)) {
            String idType = askForIdType();
            contr.addObject(idSuggestion, idScenario, objectName, idType);
        }
        else print("Abort: invalid name.");

    }

    private void removeObject(String idScenario, String idSuggestion) throws IOException {
        String idObject = "";
        while (!isNumeric(idObject)) {
            print("Please insert the id of the parameter you want to remove: 0 first, 1 second, 2 third...");
            idObject = br.readLine();
        }
        contr.removeObject(idSuggestion, idScenario, idObject);
    }

    private String askForIdScenarioToModify() throws IOException {
        String idScenario = "";
        while (!isNumeric(idScenario)) {
            print("Please insert the id of the scenario you want to modify");
            idScenario = br.readLine();
        }
        return idScenario;
    }

    private String askForIdSuggestionToModify() throws IOException {
        String idSuggestion = "";
        while (!isNumeric(idSuggestion)) {
            print("Please insert the id of the suggestion you want to modify");
            idSuggestion = br.readLine();
        }
        return idSuggestion;
    }

    private String askForIdObjectToModify() throws IOException {
        String idObject = "";
        while (!isNumeric(idObject)) {
            print("Please insert the id of the parameter you want to modify: 0 first, 1 second, 2 third...");
            idObject = br.readLine();
        }
        return idObject;
    }

    private String askForIdType() throws IOException {
        contr.showTypes();
        print("Insert the id for the desired type.\nDigit CREATE to create a custom one.");
        String input = br.readLine();
        while (!isNumeric(input)) {
            if (input.equals("CREATE")) createCustomType();
            contr.showTypes();
            print("Insert the id for the desired type.\nDigit CREATE to create a custom one.");
            input = br.readLine();
        }
        return input;
    }

    @Override
    public void update() {
        print(dataPresenter.getDataToShow());
        isBdlLoaded = dataPresenter.isBdlLoaded();
    }

    private void print(String message) {
        System.out.println(message);
    }

    public static boolean isNumeric(final String str) {
        // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
