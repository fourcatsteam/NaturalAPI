package fourcats.view.utilities;

import java.awt.*;
import java.io.File;
import java.net.URL;

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
        final URL url = Thread.currentThread().getContextClassLoader().getResource("images/logo.png");
        return Toolkit.getDefaultToolkit().getImage(url);
    }
}
