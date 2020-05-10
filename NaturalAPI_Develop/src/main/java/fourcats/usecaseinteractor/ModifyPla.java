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

    public void loadPlaToModify(String filename){
        File file = repositoryAccess.openFile(filename);
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            String text = "";
            line = br.readLine(); //SERVE PER SALTARE LA PRIMA RIGA
            while((line = br.readLine()) != null){
                text = text.concat(line);
                text = text.concat("\n");
            }

            String[] split = text.split("\ncustom class\n");

            String[] splitTest = split[1].split("\ntest class\n");

            modifyPlaOutputPort.showLoadPla(split[0],splitTest[0],splitTest[1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modify(String filename,String text){
        String pla = repositoryAccess.loadPLA(filename);
        String extension = pla.split("\n",2)[0];
        repositoryAccess.writePla(filename,extension + "\n" + text);
        modifyPlaOutputPort.showModifyPla("Pla modified!");

    }
}