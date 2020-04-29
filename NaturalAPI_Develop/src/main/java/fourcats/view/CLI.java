package fourcats.view;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;
import fourcats.observer.Observer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;

public class CLI implements Observer {

    private Controller controller;
    private DataPresenter dataPresenter;
    private String currentBal;
    private String currentPla;
    private String currentAnswer;
    private BufferedReader br;

    public CLI(Controller c,DataPresenter dp){
        dataPresenter = dp;
        dataPresenter.attach(this);
        controller = c;
        br = new BufferedReader(new InputStreamReader(System.in));
        currentBal="";
        currentPla="";
        currentAnswer="";
    }

    public void askBal(){
        System.out.println("Write file BAL title. Type E to exit");
    }

    public void readBal(){
        try {
            currentBal = br.readLine();
            if (currentBal.equals("E")){
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void askPla(){
        System.out.println("Write file PLA title. Type E to exit");
    }

    public void readPla(){
        try {
            currentPla = br.readLine();
            if (currentPla.equals("E")){
                   System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void askSuggestApi(){
        System.out.println("Do you want to create your APIs suggestion? Type y or n");
    }

    public void readSuggestApi(){
        try {
            currentAnswer = br.readLine();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void askAnother(){
        System.out.println("Do you want to add another BAL? Type y or n");
    }

    public void readAnother(){
        try{
            currentAnswer = br.readLine();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void askGenerateApi(){
        System.out.println("Do you want to generate your APIs? Type y or n");
    }

    public void readGenerateApi(){
        try{
            currentAnswer = br.readLine();
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
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void askModifyApi(){
        System.out.println("Do you want to modify an API? Type y or n.");
        readModifyApi();
    }

    public void readModifyApi(){
        try{
            String currentModify = br.readLine();
            switch (currentModify) {
                case "y":
                    System.out.println("Type the ID of the chosen API");
                    currentModify = br.readLine();
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
        catch(IOException e){
            e.printStackTrace();
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
