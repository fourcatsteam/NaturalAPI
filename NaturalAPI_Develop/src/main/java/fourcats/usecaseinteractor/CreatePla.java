package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.CreatePlaInputPort;
import fourcats.port.CreatePlaOutputPort;

public class CreatePla implements CreatePlaInputPort {

    private RepositoryAccess repositoryAccess;
    private CreatePlaOutputPort createPlaOutputPort;

    public CreatePla(RepositoryAccess r,CreatePlaOutputPort createPlaOutputPort){
        this.repositoryAccess = r;
        this.createPlaOutputPort = createPlaOutputPort;
    }

    public void create(String path,String extension,String text){

        if(repositoryAccess.openFile(path + ".txt").exists()){
            createPlaOutputPort.showOutput("There is already a PLA with this name");
        }
        else{
            repositoryAccess.writePla(path + ".txt",extension + "\n" + text);
            createPlaOutputPort.showOutput("PLA created!");
        }
    }
}
