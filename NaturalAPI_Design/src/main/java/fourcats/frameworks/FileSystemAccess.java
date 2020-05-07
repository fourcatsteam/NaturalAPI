package fourcats.frameworks;


import fourcats.entities.Bdl;
import fourcats.interfaceaccess.PersistentMemoryAccess;

import java.io.*;

public class FileSystemAccess implements PersistentMemoryAccess {

    @Override
    public String readFile(String filePath) throws FileNotFoundException {
        String s;
        String fileContent = "";
        try(BufferedReader input = new BufferedReader(new FileReader(filePath))){
            while((s=input.readLine()) != null){
                fileContent += s + "\n";
            }
        }catch(IOException e){
            throw new FileNotFoundException();
        }
        return fileContent;
    }

    @Override
    public void writeFile(String content, String filePath) throws IOException {
        File file = new File(filePath + ".json");
        try(FileWriter writer = new FileWriter(file)){
            if (file.getParentFile()!=null){
                file.getParentFile().mkdirs();
            }
            writer.write(content);
        }
        catch (Exception e){
            throw e;
        }

    }

    @Override
    public Bdl getBdl(String[] name) throws IOException {
        String bdlNames[] = new String[3];
        int i = 0;
        for(String s:name){
           String[] splitting = s.split("\\."); //period is a special character
           bdlNames[i] = splitting[0];
           i++;
        }
        if(!(bdlNames[0].equals(bdlNames[1]) && bdlNames[0].equals(bdlNames[2]))){ //Files are from different BDL
            return null;
        }
        return loadBdl(bdlNames[0]);
    }


    @Override
    public Bdl loadBdl(String bdlName) throws IOException {
        Bdl loadedBdl = new Bdl(bdlName);
        readBdlNouns(bdlName,loadedBdl);
        readBdlVerbs(bdlName,loadedBdl);
        readBdlPredicates(bdlName,loadedBdl);

        return loadedBdl;
    }

    private void readBdlNouns(String filePath,Bdl loadedBdl) throws IOException {
        String line = "";
        String filepath = filePath + ".nouns.bdl.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            while ((line = br.readLine()) != null) {
                String[] bdl = line.split(",");
                loadedBdl.storeBdlNoun(bdl[0], Integer.parseInt(bdl[1]));
            }
        }
    }

    private void readBdlVerbs(String filePath,Bdl loadedBdl) throws IOException {
        String line = "";
        String filepath = filePath + ".verbs.bdl.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            while ((line = br.readLine()) != null) {
                String[] bdl = line.split(",");
                loadedBdl.storeBdlVerb(bdl[0], Integer.parseInt(bdl[1]));
            }
        }
    }

    private void readBdlPredicates(String filePath,Bdl loadedBdl) throws IOException {
        String line = "";
        String filepath = filePath + ".predicates.bdl.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            while ((line = br.readLine()) != null) {
                String[] bdl = line.split(",");
                loadedBdl.storeBdlPredicate(bdl[0], Integer.parseInt(bdl[1]));
            }
        }
    }

}



