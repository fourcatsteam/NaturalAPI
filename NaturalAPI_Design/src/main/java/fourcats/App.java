package fourcats;

import fourcats.frameworks.*;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;
import fourcats.usecaseinteractor.*;
import fourcats.view.CLI;
import fourcats.view.GUI_Design;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
        Repository repo = new Repository(new DataKeeper(), new FileSystemAccess());
       // StanfordNlp nlp = new StanfordNlp();
        StanfordNlp nlp = null;
        DataPresenter dataPresenter = new DataPresenter();
        BalAnalyzerImplementation balAnalyzer = new BalAnalyzerImplementation();

        GenerateBAL generateBAL = new GenerateBAL(repo,dataPresenter,balAnalyzer);
        GenerateBALSuggestions generateBALSugg = new GenerateBALSuggestions(repo,nlp,dataPresenter);
        DeclineBALSuggestion declineBALSuggestion = new DeclineBALSuggestion(repo,dataPresenter);
        ModifyBALSuggestion modifyBALSuggestion = new ModifyBALSuggestion(repo,dataPresenter);
        CreateCustomType createCustomType = new CreateCustomType(repo,dataPresenter);
        ShowTypes showTypes = new ShowTypes(repo,dataPresenter);

        Controller controller = new Controller(generateBALSugg, declineBALSuggestion,
                generateBAL ,modifyBALSuggestion, createCustomType, showTypes);

        GUI_Design g = new GUI_Design(controller,dataPresenter);
        g.createAndShowGUI();

      /*  CLI cli = new CLI(controller,dataPresenter);


        boolean shouldContinue = true;
        while(shouldContinue) {
            cli.askForUseCase();
            shouldContinue = cli.readUseCase();
        }*/

    }
}
