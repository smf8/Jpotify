package IO;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class FileIO {
    public static boolean checkIfFileExists(String fileURL){
        File file = new File(fileURL);
        if (file.exists() && !file.isDirectory()){
            return true;
        }
        else return false;
    }
    public static ArrayList<URI> findFilesRecursive(File directory){
        File[] files = directory.listFiles();
        ArrayList<URI> result = new ArrayList<>();
        try {
            Files.find(directory.toPath(), 1000, (path, basicFileAttributes) -> basicFileAttributes.isRegularFile()).forEach(path -> result.add(path.toUri()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static ArrayList<URI> findMP3Files(ArrayList<URI> content){
        ArrayList<URI> res = new ArrayList<>();
        for (URI i : content){
            if (i.toString().contains(".mp3")){
                res.add(i);
            }
        }
        return res;
    }
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
