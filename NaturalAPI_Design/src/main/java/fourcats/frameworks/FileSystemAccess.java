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
        String filepath = documentsPathToFolder + fileName;
        String s, fileContent = "";
        try(BufferedReader input = new BufferedReader(new FileReader(filepath))){
            while((s=input.readLine()) != null){
                fileContent += s + " ";
            }

        }catch(IOException e){
            //modificare gestione eccezioni
            throw new FileNotFoundException();
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
    public Bdl getBdl(String[] name) {
        return null;
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



