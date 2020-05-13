package fourcats.view;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;
import fourcats.observer.Observer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
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
            logger.log(Level.SEVERE, "File not found", e);
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
            logger.log(Level.SEVERE, "File not found", e);
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
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "File not found", e);
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
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "File not found", e);
        }
    }

    public void askGenerateApi(){
        System.out.println("Do you want to generate your APIs? Type y or n");
    }

    public void askPathApi() {
        System.out.println("Type the path where APIs will be created.");
    }

    public void readGenerateApi(){
        try{
            currentAnswer = br.readLine();
            switch (currentAnswer) {
                case "y":
                    askPathApi();
                    currentAnswer = br.readLine();
                    controller.generateApi(currentAnswer);
                    break;
                case "n":
                    askModifyApi();
                    break;
                default:
                    throw new InputMismatchException("Error: insert y or n");
            }
        }
        catch (Exception e){
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "File not found", e);
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
                    System.out.println("Type the ID of the first API to replace");
                    String first = br.readLine();
                    System.out.println("Type the ID of the last API to replace");
                    String second = br.readLine();
                    askPla();
                    readPla();
                    //Serve un controllo per l'id
                    controller.modifyApi(Integer.parseInt(first),Integer.parseInt(second),currentBal,currentPla);
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
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "File not found", e);
        }
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
