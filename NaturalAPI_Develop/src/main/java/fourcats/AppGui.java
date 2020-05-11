package fourcats;

import fourcats.frameworks.BalAnalyzerImplementation;
import fourcats.frameworks.DataKeeper;
import fourcats.frameworks.FileSystem;
import fourcats.frameworks.Repository;
import fourcats.gui.Gui;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGui;
import fourcats.usecaseinteractor.*;

public class AppGui {

    public static void main(String[] args) {

        Repository repo = new Repository(new DataKeeper(),new FileSystem());
        BalAnalyzerImplementation balAnalyzerImplementation = new BalAnalyzerImplementation();

        DataPresenterGui dataPresenterGui = new DataPresenterGui();
        SuggestApi suggestApi = new SuggestApi(balAnalyzerImplementation,repo,dataPresenterGui);
        GenerateApi generateApi = new GenerateApi(repo,dataPresenterGui);
        ModifyApi modifyApi = new ModifyApi(balAnalyzerImplementation,repo,dataPresenterGui);
        CreatePla createPla = new CreatePla(repo,dataPresenterGui);
        ModifyPla modifyPla = new ModifyPla(repo,dataPresenterGui);
        Controller controller = new Controller(suggestApi,generateApi,modifyApi,modifyApi,createPla,modifyPla);
        Gui gui = new Gui(controller,dataPresenterGui);
        gui.showGui();

    }
}
