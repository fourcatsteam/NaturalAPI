package FourCats.Frameworks;


import FourCats.Entities.Bdl;
import FourCats.InterfaceAccess.PersistentMemoryAccess;

import java.io.*;

public class FileSystemAccess implements PersistentMemoryAccess {


    @Override
    public String readFile(String fileName) throws FileNotFoundException {
        String filepath = "gherkin_documents/" + fileName;
        String s, fileContent = new String();
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
    public Bdl loadBdl(String bdlName) throws IOException {
        Bdl loadedBdl = new Bdl(bdlName);
        readBdlNouns(bdlName,loadedBdl);
        readBdlVerbs(bdlName,loadedBdl);
        readBdlPredicates(bdlName,loadedBdl);

        return loadedBdl;
    }

    private void readBdlNouns(String filename,Bdl loadbdl) throws IOException {
        String line = "";
        String filepath = "BDL/" + filename + ".nouns.bdl.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            while ((line = br.readLine()) != null) {
                String[] bdl = line.split(",");
                loadbdl.storeBdlNoun(bdl[0], Integer.parseInt(bdl[1]));
            }
        }
    }

    private void readBdlVerbs(String filename,Bdl loadbdl) throws IOException {
        String line = "";
        String filepath = "BDL/" + filename + ".verbs.bdl.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            while ((line = br.readLine()) != null) {
                String[] bdl = line.split(",");
                loadbdl.storeBdlVerb(bdl[0], Integer.parseInt(bdl[1]));
            }
        }
    }

    private void readBdlPredicates(String filename,Bdl loadbdl) throws IOException {
        String line = "";
        String filepath = "BDL/" + filename + ".predicates.bdl.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            while ((line = br.readLine()) != null) {
                String[] bdl = line.split(",");
                loadbdl.storeBdlPredicate(bdl[0], Integer.parseInt(bdl[1]));
            }
        }
    }

}



