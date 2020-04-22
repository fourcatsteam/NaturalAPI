package fourcats.view;

import fourcats.datastructure.observer.Observer;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;



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
                System.out.println("1. Delete\n2. Modify suggestion name\n3. Modify parameter name\n");
                input = br.readLine();
                if (input.equals("1")) {
                    deleteSuggestion();
                }
                else if(input.equals("2")){
                    modifyActionName();
                }
                else if(input.equals("3")){
                    modifyObjectName();
                }
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

    private void modifyActionName() throws IOException {
        System.out.println("Please insert the id of the scenario for the suggestion you want to modify");
        String idScenario = br.readLine();
        System.out.println("Please insert the id of the suggestion you want to modify");
        String idSuggestion = br.readLine();
        System.out.println("Please insert the new name of the action");
        String actionName = br.readLine();
        contr.modifyActionName(idSuggestion,idScenario,actionName);
    }

    private void modifyObjectName() throws IOException {
        System.out.println("Please insert the id of the scenario for the suggestion you want to modify");
        String idScenario = br.readLine();
        System.out.println("Please insert the id of the suggestion you want to modify");
        String idSuggestion = br.readLine();
        System.out.println("Please insert the id of the parameter");
        String idObject = br.readLine();
        System.out.println("Please insert the new name of the parameter");
        String objectName = br.readLine();
        contr.modifyObjectName(idSuggestion,idScenario,idObject,objectName);
    }


    @Override
    public void update() {
        System.out.println(dataPresenter.getDataToShow());
    }
}
