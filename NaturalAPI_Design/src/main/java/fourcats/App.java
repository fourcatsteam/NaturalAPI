package fourcats;

import fourcats.frameworks.*;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;
import fourcats.usecaseinteractor.DeclineBALSuggestion;
import fourcats.usecaseinteractor.GenerateBAL;
import fourcats.usecaseinteractor.GenerateBALSuggestions;
import fourcats.view.CLI;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
        Repository repo = new Repository(new DataKeeper(), new FileSystemAccess());
        StanfordNlp nlp = new StanfordNlp();
        DataPresenter dataPresenter = new DataPresenter();
        BalAnalyzerImplementation balAnalyzer = new BalAnalyzerImplementation();


        GenerateBALSuggestions generateBALSugg = new GenerateBALSuggestions(repo,nlp,dataPresenter);
        DeclineBALSuggestion declineBALSuggestion = new DeclineBALSuggestion(repo,dataPresenter);
        GenerateBAL generateBAL = new GenerateBAL(repo,dataPresenter,balAnalyzer);
        Controller controller = new Controller(generateBALSugg, declineBALSuggestion, generateBAL);


        CLI cli = new CLI(controller,dataPresenter);



        boolean shouldContinue = true;
        while(shouldContinue) {
            cli.askForUseCase();
            shouldContinue = cli.readUseCase();
        }

    }
}
