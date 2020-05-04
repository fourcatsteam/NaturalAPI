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
            br.readLine();
            while((line = br.readLine()) != null){
                text = text.concat(line);
                text = text.concat("\n");
            }

            String[] split = text.split("\ncustom class\n");

            modifyPlaOutputPort.showLoadPla(split[0],split[1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modify(String filename,String text){
        File file = repositoryAccess.openFile(filename);
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String title = file.getName();
            String extension = br.readLine();

            repositoryAccess.writePla("./PLA/" + title,extension + "\n" + text);
            modifyPlaOutputPort.showModifyPla("Pla modified!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
