package fourcats.frameworks;

import fourcats.entity.API;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class FileSystem {

    public File openFile(String filename) {
        return new File(filename);
    }

    public String loadPLA(String filename) {
        StringBuilder sb = new StringBuilder();
        File file = openFile(filename);
        try (Scanner scanner = new Scanner(file)){

            while(scanner.hasNext()){
                sb.append(scanner.nextLine());
                sb.append("\n");
            }
        } catch (FileNotFoundException e) {
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "File not found", e);
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
            logger.log(Level.SEVERE, "File not found", e);
        }
    }

    public void writePla(String file,String pla) {
        File f = new File(file);
        try(FileWriter fw = new FileWriter(f)){
            fw.write(pla);
        } catch (IOException ex) {
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "File not found", ex);
        }
    }
}
