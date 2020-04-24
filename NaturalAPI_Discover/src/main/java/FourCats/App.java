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
import FourCats.UseCaseUtilities.AnalyzeDocument;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

        Repository repo = new Repository(new FileSystemAccess());
        StanfordNlp nlp = new StanfordNlp();
        DataPresenter datapresenter = new DataPresenter();

        CreateBdl createBdl = new CreateBdl(repo,new AnalyzeDocument(nlp),datapresenter);
        AddDocuments addDocuments = new AddDocuments(repo,new AnalyzeDocument(nlp),datapresenter);
        RemoveDocuments removeDocuments = new RemoveDocuments(repo,new AnalyzeDocument(nlp),datapresenter);

        Controller controller = new Controller(createBdl,addDocuments,removeDocuments);

        System.out.println("Choose CLI(1) or GUI(2)?");
        /* Codice per scegliere CLI o GUI*/
        /*CLI cli = new CLI(controller,datapresenter);

        Boolean start = true;
        while(start) {
            cli.askForUseCase();
            start = cli.readUseCase();
        }*/
        GUI_Discover g = new GUI_Discover(controller,datapresenter);
        g.createAndShowGUI();


    }
}
