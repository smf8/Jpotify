package utils;

import java.io.File;

public class FileIO {
    public static boolean checkIfFileExists(String fileURL){
        File file = new File(fileURL);
        if (file.exists() && !file.isDirectory()){
            return true;
        }
        else return false;
    }
}
