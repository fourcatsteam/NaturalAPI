package fourcats;

import fourcats.frameworks.DataKeeper;
import fourcats.frameworks.FileSystem;
import fourcats.gui.Gui;
import fourcats.frameworks.BalAnalyzerImplementation;
import fourcats.interfaceadapters.DataPresenter;
import fourcats.usecaseinteractor.GenerateApi;
import fourcats.usecaseinteractor.ModifyApi;
import fourcats.usecaseinteractor.SuggestApi;
import fourcats.view.CLI;
import fourcats.frameworks.Repository;
import fourcats.interfaceadapters.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class App {

    public static void main(String[] args) {


        Repository repo = new Repository(new DataKeeper(),new FileSystem());
        BalAnalyzerImplementation balAnalyzerImplementation = new BalAnalyzerImplementation();
        DataPresenter dataPresenter = new DataPresenter();
        SuggestApi suggestApi = new SuggestApi(balAnalyzerImplementation,repo,dataPresenter);
        GenerateApi generateApi = new GenerateApi(repo);
        ModifyApi modifyApi = new ModifyApi(balAnalyzerImplementation,repo,dataPresenter);
        Controller controller = new Controller(suggestApi,generateApi,modifyApi,modifyApi);

        Gui gui = new Gui(controller,dataPresenter);
        gui.showGui();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        CLI c = new CLI(controller,dataPresenter,br);

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
}
