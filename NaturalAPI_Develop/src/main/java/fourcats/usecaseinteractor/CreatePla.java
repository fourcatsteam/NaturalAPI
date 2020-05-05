package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.CreatePlaInputPort;
import fourcats.port.CreatePlaOutputPort;

public class CreatePla implements CreatePlaInputPort {

    private RepositoryAccess repositoryAccess;
    private CreatePlaOutputPort createPlaOutputPort;

    public CreatePla(RepositoryAccess r,CreatePlaOutputPort createPlaOutputPort){
        repositoryAccess = r;
        this.createPlaOutputPort = createPlaOutputPort;
    }

    public void create(String title, String extension, String text){

        if(title.isEmpty() || extension.isEmpty() || text.isEmpty()){
            createPlaOutputPort.showOutput("There is an empty field!");
        }
        else{
            if(repositoryAccess.openFile("./PLA/" + title).exists()){
                createPlaOutputPort.showOutput("There is already a PLA with this name");
            }
            else{
                repositoryAccess.writePla("./PLA/" + title,extension + "\n" + text);
                createPlaOutputPort.showOutput("PLA created!");
            }
        }
    }
}
