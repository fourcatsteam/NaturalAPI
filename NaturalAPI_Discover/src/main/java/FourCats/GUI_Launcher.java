package FourCats;

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

public class GUI_Launcher {

    public static void main(String[] args) {
        FileSystemAccess fs = new FileSystemAccess();
        Repository repo = new Repository(fs);
        StanfordNlp nlp = new StanfordNlp();
        DataPresenter datapresenter = new DataPresenter();

        CreateBdl createBdl = new CreateBdl(repo,new AnalyzeDocument(nlp),datapresenter);
        AddDocuments addDocuments = new AddDocuments(repo,new AnalyzeDocument(nlp),datapresenter);
        RemoveDocuments removeDocuments = new RemoveDocuments(repo,new AnalyzeDocument(nlp),datapresenter);
        ViewBdl viewBdl = new ViewBdl(repo,datapresenter);

        Controller controller = new Controller(createBdl,addDocuments,removeDocuments,viewBdl);

        System.out.println("Launching GUI");

        GUI_Discover g = new GUI_Discover(controller,datapresenter,fs);
        g.showGUI();

    }
}
