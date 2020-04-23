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
        AddDocuments addDocuments = new AddDocuments(repo,nlp,datapresenter);
        RemoveDocuments removeDocuments = new RemoveDocuments(repo,nlp,datapresenter);

        Controller controller = new Controller(createBdl,addDocuments,removeDocuments);

        CLI cli = new CLI(controller,datapresenter);

        Boolean start = true;
        while(start) {
            cli.askForUseCase();
            start = cli.readUseCase();
        }
    }
}
