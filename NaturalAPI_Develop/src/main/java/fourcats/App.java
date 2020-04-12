package fourcats;

import fourcats.frameworks.BalAnalyzerImplementation;
import fourcats.interfaceadapters.DataPresenter;
import fourcats.usecase.GenerateApi;
import fourcats.usecase.ModifyApi;
import fourcats.usecase.SuggestApi;
import fourcats.view.CLI;
import fourcats.frameworks.Repository;
import fourcats.interfaceadapters.Controller;

public class App {

    public static void main(String[] args) {


        Repository repo = new Repository();
        BalAnalyzerImplementation balAnalyzerImplementation = new BalAnalyzerImplementation();
        DataPresenter dataPresenter = new DataPresenter();
        SuggestApi suggestApi = new SuggestApi(balAnalyzerImplementation,repo,dataPresenter);
        GenerateApi generateApi = new GenerateApi(repo);
        ModifyApi modifyApi = new ModifyApi(balAnalyzerImplementation,repo,dataPresenter);
        Controller controller = new Controller(suggestApi,generateApi,modifyApi);
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
