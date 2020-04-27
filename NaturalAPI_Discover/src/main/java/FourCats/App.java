package FourCats;

import FourCats.Frameworks.CLI;
import FourCats.Frameworks.FileSystemAccess;
import FourCats.Frameworks.StanfordNlp;
import FourCats.GUI.GUI_Discover;
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

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

        Repository repo = new Repository(new FileSystemAccess());
        StanfordNlp nlp = new StanfordNlp();
        //StanfordNlp nlp = null;
        DataPresenter datapresenter = new DataPresenter();

        CreateBdl createBdl = new CreateBdl(repo,new AnalyzeDocument(nlp),datapresenter);
        AddDocuments addDocuments = new AddDocuments(repo,new AnalyzeDocument(nlp),datapresenter);
        RemoveDocuments removeDocuments = new RemoveDocuments(repo,new AnalyzeDocument(nlp),datapresenter);
        ViewBdl viewBdl = new ViewBdl(repo,datapresenter);

        Controller controller = new Controller(createBdl,addDocuments,removeDocuments,viewBdl);

        System.out.println("Choose CLI(1) or GUI(2)?");
        /* Codice per scegliere CLI o GUI*/
        /*BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        CLI cli = new CLI(controller,datapresenter,br);

        Boolean start = true;
        while(start) {
            cli.askForUseCase();
            start = cli.readUseCase();
        }*/
        GUI_Discover g = new GUI_Discover(controller,datapresenter);
        g.createAndShowGUI();


    }
}
