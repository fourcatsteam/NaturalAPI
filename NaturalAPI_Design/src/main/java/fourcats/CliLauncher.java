package fourcats;

import fourcats.frameworks.*;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;
import fourcats.usecaseinteractor.*;
import fourcats.view.Cli;

public class CliLauncher {
    public static void main(String[] args) {
        Repository repo = new Repository(new DataKeeper(), new FileSystemAccess());
        StanfordNlp nlp = new StanfordNlp();
        DataPresenter dataPresenter = new DataPresenter();
        BalAnalyzerImplementation balAnalyzer = new BalAnalyzerImplementation();

        GenerateBal generateBAL = new GenerateBal(repo,dataPresenter,balAnalyzer);
        GenerateBalSuggestions generateBALSugg = new GenerateBalSuggestions(repo,nlp,dataPresenter);
        DeclineBalSuggestion declineBALSuggestion = new DeclineBalSuggestion(repo,dataPresenter);
        ModifyBalSuggestion modifyBALSuggestion = new ModifyBalSuggestion(repo,dataPresenter);
        CreateCustomType createCustomType = new CreateCustomType(repo,dataPresenter);
        ShowTypes showTypes = new ShowTypes(repo,dataPresenter);
        AddBalSuggestion addBALSuggestion = new AddBalSuggestion(repo,dataPresenter);
        LoadBdl loadBdl = new LoadBdl(repo,dataPresenter);
        RemoveBdl removeBdl = new RemoveBdl(repo,dataPresenter);

        Controller controller = new Controller(generateBALSugg, declineBALSuggestion,
                generateBAL ,modifyBALSuggestion, createCustomType, showTypes, addBALSuggestion,loadBdl,removeBdl);

        Cli cli = new Cli(controller,dataPresenter);

        boolean shouldContinue = true;
        while(shouldContinue) {
            cli.askForUseCase();
            shouldContinue = cli.readUseCase();
        }
    }

}
