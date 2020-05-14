package fourcats.frameworks;

import fourcats.entity.API;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class FileSystem {

    public File openFile(String filename) {
        return new File(filename);
    }

    public String loadPLA(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        File file = openFile(filename);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String line;
        while((line = bufferedReader.readLine()) != null){
            sb.append(line + "\n");
        }
        return sb.toString();
    }

    public void writeApi(String path, API api) {

        File file = new File(path + api.getFilename());
        file.getParentFile().getParentFile().mkdir();
        file.getParentFile().mkdir();
        try (FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write(api.getText());
        } catch (IOException e) {
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "IOException", e);
        }
    }

    public void writePla(String file,String pla) {
        File f = new File(file);
        try(FileWriter fw = new FileWriter(f)){
            fw.write(pla);
        } catch (IOException ex) {
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "IOException", ex);
        }
    }
}
