package utils.IO;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileIO {


    public static String RESOURCES_RELATIVE = "player" + File.separator + "src" + File.separator + "resources" + File.separator;
    /**
     * checks if the file in the given url exists
     * @param fileURL file url
     * @return whether the file exists or not
     */
    public static boolean checkIfFileExists(String fileURL){
        File file = new File(fileURL);
        if (file.exists() && !file.isDirectory()){
            return true;
        }
        else return false;
    }

    /**
     * uses Files find method to find all regular files in the given directory recursively
     * @param directory a file object to the wanted directory
     * @return a URI list of regular files found in the given folder and it's sub folders
     */
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

    /**
     * filters MP3 files among all the files
     * @param content URI list of files
     * @return a URI list MP3 files
     */
    public static ArrayList<URI> findMP3Files(ArrayList<URI> content){
        ArrayList<URI> res = new ArrayList<>();
        for (URI i : content){
            if (i.toString().contains(".mp3")){
                res.add(i);
            }
        }
        return res;
    }

    /**
     * this method shouldn't be here but it creates a MD5 hash
     * @param md5 the string we need the hash of
     * @return hashed MD5
     */
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
