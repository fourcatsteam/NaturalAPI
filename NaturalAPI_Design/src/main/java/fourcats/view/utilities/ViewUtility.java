package fourcats.view.utilities;

import java.io.File;

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
}
