package fourcats;

import fourcats.frameworks.*;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;
import fourcats.usecaseinteractor.*;
import fourcats.view.gui.GuiDesign;

import java.io.IOException;
import java.util.Locale;

/**
 * Hello world!
 *
 */
public class GuiLauncher {
    public static void main( String[] args ) throws IOException {
        Repository repo = new Repository(new DataKeeper(), new FileSystemAccess());
        StanfordNlp nlp = new StanfordNlp();
        DataPresenterGUI dataPresenter = new DataPresenterGUI();
        BalAnalyzerImplementation balAnalyzer = new BalAnalyzerImplementation();

        GenerateBal generateBAL = new GenerateBal(repo, dataPresenter, balAnalyzer);
        GenerateBalSuggestions generateBALSugg = new GenerateBalSuggestions(repo, nlp, dataPresenter);
        DeclineBalSuggestion declineBALSuggestion = new DeclineBalSuggestion(repo, dataPresenter);
        ModifyBalSuggestion modifyBALSuggestion = new ModifyBalSuggestion(repo, dataPresenter);
        CreateCustomType createCustomType = new CreateCustomType(repo, dataPresenter);
        ShowTypes showTypes = new ShowTypes(repo, dataPresenter);
        AddBalSuggestion addBALSuggestion = new AddBalSuggestion(repo, dataPresenter);
        LoadBdl loadBdl = new LoadBdl(repo, dataPresenter);
        RemoveBdl removeBdl = new RemoveBdl(repo, dataPresenter);

        Controller controller = new Controller(generateBALSugg, declineBALSuggestion,
                generateBAL, modifyBALSuggestion, createCustomType, showTypes, addBALSuggestion, loadBdl, removeBdl);


        Locale.setDefault(Locale.ENGLISH); //set language of GUI components to english
        GuiDesign gui = new GuiDesign(controller, dataPresenter);
        gui.createAndShowGUI();
    }
}
