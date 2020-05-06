package FourCats;

import FourCats.Frameworks.CLI;
import FourCats.Frameworks.FileSystemAccess;
import FourCats.Frameworks.StanfordNlp;
import FourCats.InterfaceAdapters.Controller;
import FourCats.InterfaceAdapters.DataPresenter;
import FourCats.InterfaceAdapters.Repository;
import FourCats.UseCaseInteractor.AddDocuments;
import FourCats.UseCaseInteractor.CreateBdl;
import FourCats.UseCaseInteractor.RemoveDocuments;
import FourCats.UseCaseInteractor.ViewBdl;
import FourCats.UseCaseUtilities.AnalyzeDocument;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CLI_Launcher {
    public static void main( String[] args ) {

        FileSystemAccess fs = new FileSystemAccess();
        Repository repo = new Repository(fs);
        StanfordNlp nlp = new StanfordNlp();
        DataPresenter datapresenter = new DataPresenter();

        CreateBdl createBdl = new CreateBdl(repo,new AnalyzeDocument(nlp),datapresenter);
        AddDocuments addDocuments = new AddDocuments(repo,new AnalyzeDocument(nlp),datapresenter);
        RemoveDocuments removeDocuments = new RemoveDocuments(repo,new AnalyzeDocument(nlp),datapresenter);
        ViewBdl viewBdl = new ViewBdl(repo,datapresenter);

        Controller controller = new Controller(createBdl,addDocuments,removeDocuments,viewBdl);

        System.out.println("Launching CLI");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        CLI cli = new CLI(controller,datapresenter,br);

        Boolean start = true;
        while(start) {
            cli.askForUseCase();
            start = cli.readUseCase();
        }

    }
}
