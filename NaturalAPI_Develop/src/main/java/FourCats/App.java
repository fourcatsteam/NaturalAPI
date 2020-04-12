import Frameworks.BalAnalyzerImplementation;
import InterfaceAccess.BalAnalyzer;
import InterfaceAdapters.DataPresenter;
import UseCase.GenerateApi;
import UseCase.ModifyApi;
import UseCase.SuggestApi;
import View.CLI;
import Frameworks.Repository;
import InterfaceAdapters.Controller;

public class App {

    public static void main(String[] args) {

//        repositoryAccessImpl ral = new repositoryAccessImpl();
//        jacksonAccessImpl jal = new jacksonAccessImpl(ral.openFile("bal.json"));
//        BAL bal = jal.getBAL();
//        PLA pla = new PLA(ral.loadPLA("javaClassPLA.txt"));
//        API api = new API();
//
//        SuggestApi sa = new SuggestApi(jal,ral,api,pla,bal);
//        sa.create();
//        for (Map.Entry<String,String> map : sa.getApi().getListApi().entrySet()) {
//            System.out.println(map.getValue());
//        }

        Repository repo = new Repository();
        BalAnalyzerImplementation balAnalyzerImplementation = new BalAnalyzerImplementation();
        DataPresenter dataPresenter = new DataPresenter();
        SuggestApi suggestApi = new SuggestApi(balAnalyzerImplementation,repo,dataPresenter);
        GenerateApi generateApi = new GenerateApi(repo);
        ModifyApi modifyApi = new ModifyApi(balAnalyzerImplementation,repo,dataPresenter);
        Controller controller = new Controller(suggestApi,generateApi,modifyApi);
        CLI c = new CLI(controller,dataPresenter);
        do{
            c.askBal();
            c.readBal();
            c.askPla();
            c.readPla();
            c.askSuggestApi();
            c.readSuggestApi();
            c.askAnother();
            c.readAnother();
        }
        while(!c.getCurrentAnswer().equals(new String("n")));

        c.askGenerateApi();
        c.readGenerateApi();
    }
}
