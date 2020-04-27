package FourCats.Frameworks;
 
import FourCats.InterfaceAdapters.Controller;
import FourCats.InterfaceAdapters.DataPresenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import FourCats.Observer.Observer;

public class CLI implements Observer {
    private Controller controller;
    private DataPresenter datapresenter; //Concrete Subject
    private String currentUseCase;
    private LinkedList<String> nameTitleList;
    private BufferedReader br;
            //= new BufferedReader(new InputStreamReader(System.in));

    public CLI(Controller controller,DataPresenter datapresenter,BufferedReader bufferedReader){
        this.controller = controller;
        this.datapresenter = datapresenter;
        datapresenter.attach(this);
        this.nameTitleList = new LinkedList<>();
        this.br = bufferedReader;
    }

    public void askForUseCase(){
        System.out.println("1. Create a new BDL");
        System.out.println("2. Add documents to a BDL");
        System.out.println("3. Remove document from a BDL");
    }

    public Boolean readUseCase(){
        String name="";
        String r = "";
        Boolean result = true;
        try {
            currentUseCase = br.readLine();
            switch (currentUseCase) {
                case "1":
                    name = chooseBdlName();
                    System.out.println("Inserimento dei documenti:");
                    while(!r.equals("EXIT")){
                        r=chooseFile();
                    }
                    controller.createBdl(name,nameTitleList);
                    nameTitleList.clear();
                    break;

                case "2":
                    name = chooseBdlName();
                    System.out.println("Inserimento dei documenti che vuoi aggiungere:");
                    while(!r.equals("EXIT")){
                        r=chooseFile();
                    }
                    controller.addDocument(name,nameTitleList);
                    nameTitleList.clear();
                    break;
                case "3":
                    name = chooseBdlName();
                    System.out.println("Inserimento dei documenti che vuoi ELIMINARE dalla BDL:");
                    while(!r.equals("EXIT")){
                        r=chooseFile();
                    }
                    controller.removeDocument(name,nameTitleList);
                    nameTitleList.clear();
                    break;
                case "E":
                    result = false;
                default:
                    break;
            }
            //notifyObservers();
        } catch (IOException e) {
            System.out.println("Sorry, a problem occurred while reading the input.");
        }
        return result;

    }

    private String chooseBdlName() throws IOException{
        System.out.println("Inserisci il nome della Bdl");
        return br.readLine();
    }



    private String chooseFile() throws IOException {
        System.out.println("Choose a namefile, digit EXIT to exit");
        String r = br.readLine();
        if(!r.equals("EXIT")){
            nameTitleList.add(r);
        }
        return r;
    }
    public void showResult(){
        System.out.println(datapresenter.getMessage());
    }

    @Override
    public void update() {
        showResult();
    }
}
