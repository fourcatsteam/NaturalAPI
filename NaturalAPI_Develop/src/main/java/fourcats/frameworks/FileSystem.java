package fourcats.frameworks;

import fourcats.entity.API;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class FileSystem {

    public File openFile(String filename) {
        return new File(filename);
    }

    public String loadPLA(String filename) {
        String toReturn = "";
        File file = openFile("./PLA/" +filename);
        try (Scanner scanner = new Scanner(file)){

            while(scanner.hasNext()){
                toReturn += scanner.nextLine();
                toReturn += "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public void writeApi(API api) {
        for (Map.Entry<String,String> map : api.getListApi().entrySet()){
            File file = new File(map.getKey());
            try (FileWriter fileWriter = new FileWriter(file)){
                fileWriter.write(map.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}