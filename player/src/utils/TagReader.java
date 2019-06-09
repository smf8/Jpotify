package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class TagReader {

    /**
     * @param fileUrl - a url to the file on disk.
     * @return returns an array of strings in the following format <br />
     * [0] : Title <br />
     * [1] : Artist <br/>
     * [2] : Album <br/>
     * [3] : Release date
     */
    public static String[] getSimpleTags(URL fileUrl) {
        String[] result = new String[4];
        File file = new File(fileUrl.getFile());
        try (FileInputStream inputStream = new FileInputStream(file)) {
            inputStream.skip(file.length() - 128);
            byte[] b = new byte[128];
            inputStream.read(b);
            String id3 = new String(b);
            System.out.println(id3);
            result[0] = id3.substring(3, 32);
            result[1] = id3.substring(33, 62);
            result[2] = id3.substring(63, 92);
            result[3] = id3.substring(93, 96);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
