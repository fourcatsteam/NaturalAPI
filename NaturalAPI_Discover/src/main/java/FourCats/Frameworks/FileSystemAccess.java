package FourCats.Frameworks;

import FourCats.DataStructure.WordCounter;
import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.PersistentMemoryAccess;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileSystemAccess implements PersistentMemoryAccess {

    /*public LinkedList<String> read(LinkedList<String> titleDoc) throws FileNotFoundException {
        LinkedList<String> ls = new LinkedList<String>();
        for(int i=0;i<titleDoc.size();i++){
            ls.add(read(titleDoc.get(i)));
        }
        return ls;
    }*/

    /*private String read(String filename){
        String filepath = "txt_documents/" + filename;
        String s, fileContent = new String();
        try{
            BufferedReader input = new BufferedReader(new FileReader(filepath));
            while((s=input.readLine()) != null){
                fileContent += s + " ";
            }
            input.close();

        }catch(IOException e){
            //modificare gestione eccezioni
            fileContent="file non trovato";
        }
        return fileContent;
    }*/

    /*public void writeBdl(String nameBdl,Bdl bdl) throws IOException {
        saveListToFile(bdl.getNouns(),nameBdl+".nouns.bdl.csv");
        saveListToFile(bdl.getVerbs(),nameBdl+".verbs.bdl.csv");
        saveListToFile(bdl.getPredicates(),nameBdl+".predicates.bdl.csv");
    }*/

    /*public void writeBdlReference(String nameBdl, LinkedList<String> titleList) throws IOException{
        String filepath = "system_files/"+nameBdl;
        JSONObject obj = new JSONObject();
        JSONArray namefiles = new JSONArray();
        for(String title : titleList) {
            namefiles.add("FileName: "+title);
        }
        obj.put("FileNameList",namefiles);
        obj.put("BdlName", nameBdl);
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        writer.write(obj.toJSONString());
        writer.flush();
        writer.close();
    }*/

    /*public Bdl readBdl(String targetBdl) throws IOException {
        Bdl loadedBdl = new Bdl();
        readBdlNouns(targetBdl,loadedBdl);
        readBdlVerbs(targetBdl,loadedBdl);
        readBdlPredicates(targetBdl,loadedBdl);

        return loadedBdl;
    }*/


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

    //Sostanzialmente uguale a read, ma ritorna un documento

    /*public Document readDocument(String title) {
        String line, fileContent = "", filepath = "txt_documents/" + title;
        try(BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            while((line = reader.readLine()) != null){
                //add a space to separate sentences
                fileContent += line + " ";
            }
        } catch(IOException e) {
            return null;
        }
        return new Document(title, fileContent);
    }*/

    private void saveListToFile(List<WordCounter> list, String namefile) throws IOException {
            String filepath = "BDL/"+namefile;
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
                StringBuilder sb = new StringBuilder();
                for (WordCounter w : list) {
                    sb.append(w.getWord());
                    sb.append(",");
                    sb.append(w.getCount());
                    sb.append('\n');
                }
                writer.write(sb.toString());
            }
    }

    //------------------------------------------------------------------------------------

    @Override
    public Document loadDocument(String documentTitle) {
        String line = "";
        String fileContent = "";
        String filepath = "txt_documents/" + documentTitle;
        try(BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            while((line = reader.readLine()) != null){
                //add a space to separate sentences
                fileContent += line + " ";
            }
        } catch(IOException e) {
            return null;
        }
        return new Document(documentTitle, fileContent);
    }

    @Override
    public Bdl loadBdl(String bdlName) throws IOException {
        Bdl loadedBdl = new Bdl(bdlName);
        readBdlNouns(bdlName,loadedBdl);
        readBdlVerbs(bdlName,loadedBdl);
        readBdlPredicates(bdlName,loadedBdl);

        return loadedBdl;
    }

    @Override
    public void saveBdl(Bdl bdl) throws IOException {
        saveListToFile(bdl.getNouns(),bdl.getName()+".nouns.bdl.csv");
        saveListToFile(bdl.getVerbs(),bdl.getName()+".verbs.bdl.csv");
        saveListToFile(bdl.getPredicates(),bdl.getName()+".predicates.bdl.csv");
    }

    @Override
    public LinkedList<String> loadAssociation(String bdlName) throws IOException {
        LinkedList<String> association = new LinkedList<>();

        String filepath = "system_files/"+bdlName+".json";
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        JSONObject value;
        JSONParser parser = new JSONParser();
        try {
            value = (JSONObject) parser.parse(reader);
            JSONArray entity = (JSONArray) value.get("FileNameList");

            for(int i=0;i<entity.size();i++){
                association.add( (String) entity.get(i) );
            }

            reader.close();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return association;
    }

    @Override
    public void saveAssociation(String bdlName, LinkedList<String> titleList) throws IOException {
        String filepath = "system_files/" + bdlName+".json";
        JSONObject obj = new JSONObject();
        JSONArray namefiles = new JSONArray();
        for(String title : titleList) {
            namefiles.add(title);
        }
        obj.put("FileNameList",namefiles);
        obj.put("BdlName", bdlName);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            writer.write(obj.toJSONString());
            writer.flush();
        }
    }

    //------------------------------//

    @Override
    public void addAssociation(String bdlName, LinkedList<String> titleList) throws IOException {
        String filepath = "system_files/"+bdlName+".json";
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        JSONObject value;
        JSONParser parser = new JSONParser();
        try {
            value = (JSONObject) parser.parse(reader);
            JSONArray entity = (JSONArray) value.get("FileNameList");
            for(String s: titleList) {
                entity.add(s);
            }
            reader.close();
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
                writer.write(value.toJSONString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAssociation(String bdlName, LinkedList<String> docToRemove) throws IOException {
        String filepath = "system_files/"+bdlName+".json";
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        JSONObject value;
        JSONParser parser = new JSONParser();
        try {
            value = (JSONObject) parser.parse(reader);
            JSONArray entity = (JSONArray) value.get("FileNameList");

            for(String s: docToRemove) {
                entity.remove(s);
            }

            reader.close();
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
                writer.write(value.toJSONString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}



