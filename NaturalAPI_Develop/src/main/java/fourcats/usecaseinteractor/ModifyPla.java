package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.ModifyPlaInputPort;
import fourcats.port.ModifyPlaOutputPort;

import java.io.*;

public class ModifyPla implements ModifyPlaInputPort {

    private RepositoryAccess repositoryAccess;
    private ModifyPlaOutputPort modifyPlaOutputPort;

    public ModifyPla(RepositoryAccess repositoryAccess,ModifyPlaOutputPort modifyPlaOutputPort){
        this.repositoryAccess = repositoryAccess;
        this.modifyPlaOutputPort = modifyPlaOutputPort;
    }

    public void modify(String filename){
        File file = repositoryAccess.openFile(filename);
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String title = file.getName();
            String extension = br.readLine();
            String line;
            String text = "";
            while((line = br.readLine()) != null){
                text = text.concat(line);
                text = text.concat("\n");
            }

            modifyPlaOutputPort.showModifyPla(title,extension,text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
