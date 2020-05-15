package fourcats.view;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;
import fourcats.observer.Observer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "IOException", e);
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
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "IOException", e);
        }
    }

    public void askSuggestApi(){
        System.out.println("Do you want to create your APIs suggestion? 1: YES or 2: NO");
    }

    public void readSuggestApi(){
        try {
            currentAnswer = br.readLine();
            switch (currentAnswer) {
                case "1":
                    controller.createApiSuggestion(currentBal, currentPla);
                    break;
                case "2":
                    askBal();
                    readBal();
                    askPla();
                    readPla();
                    askSuggestApi();
                    readSuggestApi();
                    break;
                default:
                    System.out.println("Error: insert 1 or 2");
                    askSuggestApi();
                    readSuggestApi();
                    break;
            }
        } catch (IOException e) {
            System.out.println("Error: bal or pla selected doesn't exist");
            askBal();
            readBal();
            askPla();
            readPla();
            askSuggestApi();
            readSuggestApi();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Pla isn't correctly formatted");
            askBal();
            readBal();
            askPla();
            readPla();
            askSuggestApi();
            readSuggestApi();
        }
    }

    public void askAnother(){
        System.out.println("Do you want to add another BAL? 1: YES or 2: NO");
    }

    public void readAnother(){
        try{
            currentAnswer = br.readLine();
            switch (currentAnswer) {
                case "1":
                    startCli();
                    break;
                case "2":
                    askGenerateApi();
                    readGenerateApi();
                    break;
                default:
                    System.out.println("Error: insert 1 or 2");
                    askAnother();
                    readAnother();
                    break;
            }
        }
        catch(IOException e){
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "IOException", e);
        }
    }

    public void askGenerateApi(){
        System.out.println("Do you want to generate your APIs? 1: YES or 2: NO");
    }

    public void askPathApi() {
        System.out.println("Type the path where APIs will be created.");
    }

    public void readGenerateApi(){
        try{
            currentAnswer = br.readLine();
            switch (currentAnswer) {
                case "1":
                    askPathApi();
                    currentAnswer = br.readLine();
                    controller.generateApi(currentAnswer);
                    System.out.println("Termine applicazione");
                    break;
                case "2":
                    askModifyApi();
                    break;
                default:
                    System.out.println("Error: insert 1 or 2");
                    askGenerateApi();
                    readGenerateApi();
                    break;
            }
        }
        catch (Exception e){
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "IOException", e);
        }
    }

    public void askModifyApi(){
        System.out.println("Do you want to modify an API? 1: YES or 2: NO");
        readModifyApi();
    }

    public void readModifyApi(){
        try{
            String currentModify = br.readLine();
            switch (currentModify) {
                case "1":
                    System.out.println("Type the ID of the first API to replace");
                    String first = br.readLine();
                    System.out.println("Type the ID of the last API to replace");
                    String second = br.readLine();
                    askPla();
                    readPla();
                    if(Integer.parseInt(first) < 0) {
                        System.out.println("Invalid ids");
                        askModifyApi();
                        readModifyApi();
                    }
                    else {
                        controller.modifyApi(Integer.parseInt(first),Integer.parseInt(second),currentBal,currentPla);
                        askGenerateApi();
                        readGenerateApi();
                    }
                    break;
                case "2":
                    System.out.println("Termine applicazione");
                    break;
                default:
                    System.out.println("Error: insert 1 or 2");
                    askModifyApi();
                    readModifyApi();
                    break;
            }
        }
        catch (IOException e) {
            System.out.println("Pla selected doesn't exist");
            askModifyApi();
            readModifyApi();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Pla isn't correctly formatted");
            askModifyApi();
            readModifyApi();
        }
    }

    public void startCli(){
        askBal();
        readBal();
        askPla();
        readPla();
        askSuggestApi();
        readSuggestApi();
        askAnother();
        readAnother();
    }

    public void showOutput(){
        System.out.println(dataPresenter.getStringToShow());
        System.out.println(dataPresenter.getMessage());
    }

    @Override
    public void update() {
        showOutput();
    }

    public String getCurrentAnswer() {
        return currentAnswer;
    }
}
