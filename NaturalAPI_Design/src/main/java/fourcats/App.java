package fourcats;

import fourcats.frameworks.*;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;
import fourcats.usecaseinteractor.*;
import fourcats.view.gui.GUI_Design;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
        Repository repo = new Repository(new DataKeeper(), new FileSystemAccess());
        StanfordNlp nlp = new StanfordNlp();
        //DataPresenter dataPresenter = new DataPresenter();
        DataPresenterGUI dataPresenter = new DataPresenterGUI();
        BalAnalyzerImplementation balAnalyzer = new BalAnalyzerImplementation();

        GenerateBAL generateBAL = new GenerateBAL(repo,dataPresenter,balAnalyzer);
        GenerateBALSuggestions generateBALSugg = new GenerateBALSuggestions(repo,nlp,dataPresenter);
        DeclineBALSuggestion declineBALSuggestion = new DeclineBALSuggestion(repo,dataPresenter);
        ModifyBALSuggestion modifyBALSuggestion = new ModifyBALSuggestion(repo,dataPresenter);
        CreateCustomType createCustomType = new CreateCustomType(repo,dataPresenter);
        ShowTypes showTypes = new ShowTypes(repo,dataPresenter);
        AddBALSuggestion addBALSuggestion = new AddBALSuggestion(repo,dataPresenter);
        LoadBdl loadBdl = new LoadBdl(repo,dataPresenter);

        Controller controller = new Controller(generateBALSugg, declineBALSuggestion,
                generateBAL ,modifyBALSuggestion, createCustomType, showTypes, addBALSuggestion,loadBdl);


        GUI_Design gui = new GUI_Design(controller,dataPresenter);
        gui.createAndShowGUI();

        /*CLI cli = new CLI(controller,dataPresenter);


        boolean shouldContinue = true;
        while(shouldContinue) {
            cli.askForUseCase();
            shouldContinue = cli.readUseCase();
        }*/

    }
}
