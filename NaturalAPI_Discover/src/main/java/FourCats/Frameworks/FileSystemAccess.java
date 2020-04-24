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
    public Bdl loadBdl(String bdlName) {
        Bdl loadedBdl = new Bdl(bdlName);
        try{
            readBdlNouns(bdlName,loadedBdl);
            readBdlVerbs(bdlName,loadedBdl);
            readBdlPredicates(bdlName,loadedBdl);
        } catch(IOException e){
            return null;
        }

        return loadedBdl;
    }

    @Override
    public boolean saveBdl(Bdl bdl) {
        try{
            saveListToFile(bdl.getNouns(),bdl.getName()+".nouns.bdl.csv");
            saveListToFile(bdl.getVerbs(),bdl.getName()+".verbs.bdl.csv");
            saveListToFile(bdl.getPredicates(),bdl.getName()+".predicates.bdl.csv");
        } catch(IOException e){
            return false;
        }
        return true;
    }

    @Override
    public LinkedList<String> loadAssociation(String bdlName) {
        LinkedList<String> association = new LinkedList<>();

        String filepath = "system_files/"+bdlName+".json";

        JSONObject value;
        JSONParser parser = new JSONParser();
        try(BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            value = (JSONObject) parser.parse(reader);
            JSONArray entity = (JSONArray) value.get("FileNameList");

            for(int i=0;i<entity.size();i++){
                association.add( (String) entity.get(i) );
            }
        } catch (ParseException e) {
            return null;
        } catch (IOException e){
            return null;
        }
        return association;
    }

    @Override
    public boolean saveAssociation(String bdlName, List<String> titleList) {
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
            return true;
        }catch(IOException e){
            return false;
        }
    }

    //------------------------------//

    @Override
    public boolean addAssociation(String bdlName, List<String> titleList) {
        String filepath = "system_files/"+bdlName+".json";

        JSONObject value;
        JSONParser parser = new JSONParser();
        try(BufferedReader reader = new BufferedReader(new FileReader(filepath));) {
            value = (JSONObject) parser.parse(reader);
            JSONArray entity = (JSONArray) value.get("FileNameList");
            for(String s: titleList) {
                entity.add(s);
            }
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
                writer.write(value.toJSONString());
            } catch (IOException e){
                return false;
            }
        } catch (ParseException e) {
            return false;
        } catch (IOException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean removeAssociation(String bdlName, List<String> docToRemove) {
        String filepath = "system_files/"+bdlName+".json";

        JSONObject value;
        JSONParser parser = new JSONParser();
        try(BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            value = (JSONObject) parser.parse(reader);
            JSONArray entity = (JSONArray) value.get("FileNameList");

            for(String s: docToRemove) {
                entity.remove(s);
            }

            reader.close();
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
                writer.write(value.toJSONString());
            } catch(IOException e){
                return false;
            }
        } catch (ParseException e) {
            return false;
        } catch(IOException e){
            return false;
        }
        return true;

    }
}



