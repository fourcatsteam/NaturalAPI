package fourcats.view;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;
import fourcats.observer.Observer;


import java.util.InputMismatchException;
import java.util.Scanner;

public class CLI implements Observer {

    private Controller controller;
    private DataPresenter dataPresenter;
    private String currentBal;
    private String currentPla;
    private String currentAnswer;
    private Scanner br;

    public CLI(Controller c,DataPresenter dp){
        dataPresenter = dp;
        dataPresenter.attach(this);
        controller = c;
        br = new Scanner(System.in);
        currentBal="";
        currentPla="";
        currentAnswer="";
    }

    public void askBal(){
        System.out.println("Write file BAL title. Type E to exit");
    }

    public void readBal(){
            currentBal = br.nextLine();
            if (currentBal.equals("E")){
                System.exit(0);
            }

    }

    public void askPla(){
        System.out.println("Write file PLA title. Type E to exit");
    }

    public void readPla(){
            currentPla = br.nextLine();
            if (currentPla.equals("E")){
                   System.exit(0);
            }
    }

    public void askSuggestApi(){
        System.out.println("Do you want to create your APIs suggestion? Type y or n");
    }

    public void readSuggestApi(){

            currentAnswer = br.nextLine();
            if (currentAnswer.equals("y")) {
                controller.createApiSuggestion(currentBal, currentPla);
            }
            else {
                askBal();
                readBal();
                askPla();
                readPla();
                askSuggestApi();
                readSuggestApi();
            }
    }

    public void askAnother(){
        System.out.println("Do you want to add another BAL? Type y or n");
    }

    public void readAnother(){
            currentAnswer = br.nextLine();
    }

    public void askGenerateApi(){
        System.out.println("Do you want to generate your APIs? Type y or n");
    }

    public void readGenerateApi(){
            currentAnswer = br.nextLine();
            switch (currentAnswer) {
                case "y":
                    controller.generateApi();
                    break;
                case "n":
                    askModifyApi();
                    break;
                default:
                    throw new InputMismatchException("Error: insert y or n");
            }
    }

    public void askModifyApi(){
        System.out.println("Do you want to modify an API? Type y or n.");
        readModifyApi();
    }

    public void readModifyApi(){
            String currentModify = br.nextLine();
            switch (currentModify) {
                case "y":
                    System.out.println("Type the ID of the chosen API");
                    currentModify = br.nextLine();
                    askBal();
                    readBal();
                    askPla();
                    readPla();
                    //Serve un controllo per l'id
                    controller.modifyApi(Integer.parseInt(currentModify),currentBal,currentPla);
                    askGenerateApi();
                    readGenerateApi();
                    break;
                case "n":
                    break;
                default:
                    throw new InputMismatchException("Error: insert y or n");
            }
    }

    public void showOutput(){
        System.out.println(dataPresenter.getStringToShow());
    }

    @Override
    public void update() {
        showOutput();
    }

    public String getCurrentAnswer() {
        return currentAnswer;
    }
}
