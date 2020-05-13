package fourcats.view.utilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ViewUtility {

    private ViewUtility(){
        throw new IllegalStateException("Utility class");
    }
    public static boolean isFeaturePathValid(String path){
        File file = new File(path);
        String[] splittedPath = path.split("\\.");
        String extension = splittedPath[splittedPath.length-1];
        return file.exists() && extension.equals("feature");
    }

    public static Image getLogo() {
        try {
            return ImageIO.read(new File("src/main/java/fourcats/logo.png"));
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(null,"Error while loading logo");
        }
        return null;
    }
}
