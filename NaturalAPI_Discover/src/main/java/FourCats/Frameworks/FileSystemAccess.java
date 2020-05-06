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
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;

public class FileSystemAccess implements PersistentMemoryAccess {
    private String bdlSourceFolder;
    private String saveFolder;
    private String txtSourceFolder;

    public FileSystemAccess() {
        bdlSourceFolder = "./BDL";
        saveFolder = "./BDL";
        txtSourceFolder = "./txt_documents";
    }

    public void setBdlSourceFolder(String bdlSourceFolder) {
        this.bdlSourceFolder = bdlSourceFolder;
    }

    public void setSaveFolder(String saveFolder) {
        this.saveFolder = saveFolder;
    }

    public void setTxtSourceFolder(String txtSourceFolder) {
        this.txtSourceFolder = txtSourceFolder;
    }

    private void readBdlNouns(String filename, Bdl loadbdl) throws IOException {
        String line = "";
        String filepath = bdlSourceFolder + "/" + filename + ".nouns.bdl.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            while ((line = br.readLine()) != null) {
                String[] bdl = line.split(",");
                loadbdl.storeBdlNoun(bdl[0], Integer.parseInt(bdl[1]));
            }
        }
    }

    private void readBdlVerbs(String filename,Bdl loadbdl) throws IOException {
        String line = "";
        String filepath = bdlSourceFolder + "/" + filename + ".verbs.bdl.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            while ((line = br.readLine()) != null) {
                String[] bdl = line.split(",");
                loadbdl.storeBdlVerb(bdl[0], Integer.parseInt(bdl[1]));
            }
        }
    }

    private void readBdlPredicates(String filename,Bdl loadbdl) throws IOException {
        String line = "";
        String filepath = bdlSourceFolder + "/" + filename + ".predicates.bdl.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            while ((line = br.readLine()) != null) {
                String[] bdl = line.split(",");
                loadbdl.storeBdlPredicate(bdl[0], Integer.parseInt(bdl[1]));
            }
        }
    }

    private void saveListToFile(List<WordCounter> list, String namefile) throws IOException {
            String filepath = saveFolder + "/" + namefile;
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
        String filepath = txtSourceFolder + "/" + documentTitle;
        try(BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            while((line = reader.readLine()) != null){
                //add a space to separate sentences
                fileContent += line + " ";
            }
        } catch(IOException e) {
            return null;
        }
        if(txtSourceFolder != saveFolder) {
            transferFile(filepath,saveFolder+"/"+documentTitle);
        }
        return new Document(documentTitle, fileContent);
    }

    private void transferFile(String sourcePath, String destPath) {
        try(FileInputStream inputStream = new FileInputStream(new File(sourcePath));
            FileOutputStream outputStream = new FileOutputStream(new File(destPath))) {
            FileChannel in = inputStream.getChannel();
            FileChannel out = outputStream.getChannel();

            in.transferTo(0,in.size(),out);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        String filepath = bdlSourceFolder + "/" + bdlName + ".json";

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
        String filepath = saveFolder + "/" + bdlName+".json";
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
}



