package fourcats.frameworks;


import fourcats.entities.Bdl;
import fourcats.interfaceaccess.PersistentMemoryAccess;

import java.io.*;

public class FileSystemAccess implements PersistentMemoryAccess {

    // documentsPathToFolder = "gherkin_documents/"
    private String documentsPathToFolder;

    // balPathToFolder = "BAL/"
    private String balPathToFolder;

    // bdlPathToFolder = "BDL/"
    private String bdlPathToFolder;

    public FileSystemAccess(String documentsPath, String balPath, String bdlPath) {
        documentsPathToFolder = documentsPath;
        balPathToFolder = balPath;
        bdlPathToFolder = bdlPath;
    }

    @Override
    public String readFile(String fileName) throws FileNotFoundException {
        String filepathRelative = documentsPathToFolder + fileName;
        String filepathAbsolute = fileName;
        String s, fileContent = "";
        //first try with relative path, than, if exception is throw, try with absolute one
        try(BufferedReader input = new BufferedReader(new FileReader(filepathRelative))){
            while((s=input.readLine()) != null){
                fileContent += s + " ";
            }

        }catch(IOException e){
            //try with absolute path
            try(BufferedReader input = new BufferedReader(new FileReader(filepathAbsolute))){
                while((s=input.readLine()) != null){
                    fileContent += s + " ";
                }
            }
            catch (IOException exception){
                throw new FileNotFoundException();
            }
            //modificare gestione eccezioni
        }
        return fileContent;
    }

    @Override
    public void writeFile(String content, String filename) throws IOException {
        try{
            File file = new File(balPathToFolder + filename + ".json");
            file.getParentFile().mkdirs();
            try(FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public Bdl getBdl(String[] name) throws IOException {
        String bdlNames[] = new String[3];
        int i = 0;
        for(String s:name){
           String[] splitting = s.split("\\."); //Il punto è un carattere special
           bdlNames[i] = splitting[0];
           i++;
        }
        if(!(bdlNames[0].equals(bdlNames[1]) && bdlNames[0].equals(bdlNames[2]))){ //Hai selezionato dei file che si riferiscono a BDL diverse
            return null;
        }
        return loadBdl(bdlNames[0]);
    }

    //NOTE
    //The following functions are not used now, they will be useful for the integration of the bdl file

    @Override
    public Bdl loadBdl(String bdlName) throws IOException {
        Bdl loadedBdl = new Bdl(bdlName);
        readBdlNouns(bdlName,loadedBdl);
        readBdlVerbs(bdlName,loadedBdl);
        readBdlPredicates(bdlName,loadedBdl);

        return loadedBdl;
    }

    private void readBdlNouns(String filename,Bdl loadbdl) throws IOException {
        String line = "";
        String filepath = bdlPathToFolder + filename + ".nouns.bdl.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            while ((line = br.readLine()) != null) {
                String[] bdl = line.split(",");
                loadbdl.storeBdlNoun(bdl[0], Integer.parseInt(bdl[1]));
            }
        }
    }

    private void readBdlVerbs(String filename,Bdl loadbdl) throws IOException {
        String line = "";
        String filepath = bdlPathToFolder + filename + ".verbs.bdl.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            while ((line = br.readLine()) != null) {
                String[] bdl = line.split(",");
                loadbdl.storeBdlVerb(bdl[0], Integer.parseInt(bdl[1]));
            }
        }
    }

    private void readBdlPredicates(String filename,Bdl loadbdl) throws IOException {
        String line = "";
        String filepath = bdlPathToFolder + filename + ".predicates.bdl.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            while ((line = br.readLine()) != null) {
                String[] bdl = line.split(",");
                loadbdl.storeBdlPredicate(bdl[0], Integer.parseInt(bdl[1]));
            }
        }
    }

}



