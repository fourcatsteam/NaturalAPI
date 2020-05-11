package fourcats;

import fourcats.frameworks.BalAnalyzerImplementation;
import fourcats.frameworks.DataKeeper;
import fourcats.frameworks.FileSystem;
import fourcats.frameworks.Repository;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;
import fourcats.interfaceadapters.DataPresenterGui;
import fourcats.usecaseinteractor.*;
import fourcats.view.CLI;

public class AppCli {

    public static void main(String[] args) {

        Repository repo = new Repository(new DataKeeper(),new FileSystem());
        BalAnalyzerImplementation balAnalyzerImplementation = new BalAnalyzerImplementation();
        DataPresenter dataPresenter = new DataPresenter();
        SuggestApi suggestApi = new SuggestApi(balAnalyzerImplementation,repo,dataPresenter);
        GenerateApi generateApi = new GenerateApi(repo,dataPresenter);
        ModifyApi modifyApi = new ModifyApi(balAnalyzerImplementation,repo,dataPresenter);
        Controller controller = new Controller(suggestApi,generateApi,modifyApi,
                new CreatePla(repo,new DataPresenterGui()), new ModifyPla(repo,new DataPresenterGui()));

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
}
