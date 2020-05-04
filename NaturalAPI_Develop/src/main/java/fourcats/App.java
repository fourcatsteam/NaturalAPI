package fourcats;

import fourcats.frameworks.DataKeeper;
import fourcats.frameworks.FileSystem;
import fourcats.gui.Gui;
import fourcats.frameworks.BalAnalyzerImplementation;
import fourcats.interfaceadapters.DataPresenter;
import fourcats.interfaceadapters.DataPresenterGui;
import fourcats.usecaseinteractor.*;
import fourcats.view.CLI;
import fourcats.frameworks.Repository;
import fourcats.interfaceadapters.Controller;

import java.io.IOException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Repository repo = new Repository(new DataKeeper(),new FileSystem());
        BalAnalyzerImplementation balAnalyzerImplementation = new BalAnalyzerImplementation();

        System.out.println("Press 1 for CLI, 2 for GUI");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if(choice == 1){
            DataPresenter dataPresenter = new DataPresenter();
            SuggestApi suggestApi = new SuggestApi(balAnalyzerImplementation,repo,dataPresenter);
            GenerateApi generateApi = new GenerateApi(repo);
            ModifyApi modifyApi = new ModifyApi(balAnalyzerImplementation,repo,dataPresenter);
            Controller controller = new Controller(suggestApi,generateApi,modifyApi,modifyApi,new CreatePla(repo,new DataPresenterGui()),
                    new ModifyPla(repo,new DataPresenterGui()));
            CLI c = new CLI(controller,dataPresenter);

            do{
                c.askBal();
                c.readBal();
                c.askPla();
                c.readPla();
                c.askSuggestApi();
                c.readSuggestApi();
                c.askAnother();
                c.readAnother();
            }
            while(!c.getCurrentAnswer().equals("n"));

            c.askGenerateApi();
            c.readGenerateApi();
        }
        else if(choice == 2){
            DataPresenterGui dataPresenterGui = new DataPresenterGui();
            SuggestApi suggestApi = new SuggestApi(balAnalyzerImplementation,repo,dataPresenterGui);
            GenerateApi generateApi = new GenerateApi(repo);
            ModifyApi modifyApi = new ModifyApi(balAnalyzerImplementation,repo,dataPresenterGui);
            CreatePla createPla = new CreatePla(repo,dataPresenterGui);
            ModifyPla modifyPla = new ModifyPla(repo,dataPresenterGui);
            Controller controller = new Controller(suggestApi,generateApi,modifyApi,modifyApi,createPla,modifyPla);
            Gui gui = new Gui(controller,dataPresenterGui);
            gui.showGui();
        }
    }
}
