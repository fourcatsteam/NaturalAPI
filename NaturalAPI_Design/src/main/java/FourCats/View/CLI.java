package FourCats.View;

import FourCats.DataStructure.Observer.Observer;
import FourCats.InterfaceAdapters.Controller;
import FourCats.InterfaceAdapters.DataPresenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;



public class CLI implements Observer {
    private Controller contr;
   // private Repository repo;
    private String currentUseCase = "";
    private String userSelectedInput = "";
    private LinkedList<String> nameTitleList;
    final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private DataPresenter dataPresenter;

    public CLI(Controller controller, DataPresenter presenter){
        this.contr = controller;
        nameTitleList = new LinkedList<String>();
        this.dataPresenter = presenter;
        this.dataPresenter.attach(this);
    }

    public void askForUseCase(){
        System.out.println("\n-------NATURAL API DESIGN-------");
        System.out.println("\n1. Generate Suggestions");
        System.out.println("2. Load BAL (TODO)");
    }

    public void readUseCase(){
        try {
            currentUseCase = br.readLine();
            switch (currentUseCase) {
                case "1": {
                    generateSuggestion();
                    break;
                }

                case "2": {
                    System.out.println("Get out of here, This is a Work in progress!");
                    break;
                }
                default:{
                    System.out.println("Please insert a valid option.");
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String chooseFile() throws IOException {
        System.out.println("Choose a gherkin feature namefile, digit EXIT to exit.");
        String r = br.readLine();
        if(!r.equals("EXIT")){
            nameTitleList.add(r);
        }
        return r;
    }

    public void generateSuggestion() throws IOException {
        contr.generateSuggestions(chooseFile());
        String input = "";
        while(!input.equalsIgnoreCase("EXIT")) {
            System.out.println("\nDo you want to modify or delete one of them? 1. YES 2. NO. Digit EXIT to abort.");
            input = br.readLine();
            if (input.equals("1")) {
                System.out.println("1. Delete 2. Modify(TODO)");
                input = br.readLine();
                if (input.equals("1")) {
                    System.out.println("Please insert the id of the scenario for the suggestion you want to delete");
                    input = br.readLine();
                    System.out.println("Please insert the id of the suggestion you want to delete");
                    String newInput = br.readLine();
                    contr.declineSuggestion(newInput, input);
                }
            }
            if (input.equals("2")){
                System.out.println("1. Add new scenario 2. Generate BAL. Digit EXIT to abort");
                input = br.readLine();
                if(input.equals("1")){
                    contr.generateSuggestions(chooseFile());
                }
                if(input.equals("2")){
                    System.out.println("Please, insert the name for the BAL");
                    input = br.readLine();
                    contr.generateBAL(input);
                    return;
                }

            }
        }
    }


    @Override
    public void update() {
        System.out.println(dataPresenter.getDataToShow());
    }
}
