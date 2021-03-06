package utils;

import utils.IO.FileIO;
import Model.Song;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;

public class TagReader {

    private String title;
    private String album;
    private String artist;
    private int durationInMiliSeconds;
    private String releaseDate;
    private String imageName;
    private Song currSong;
    /**
     * method retrieves mp3 file's tags from it's last 128 bytes
     *
     * @param fileUrl - a url to the file on disk.
     * @return returns an array of strings in the following format <br />
     * [0] : Title <br />
     * [1] : Artist <br/>
     * [2] : Album <br/>
     * [3] : Release date
     */
    public static void getSimpleTags(URL fileUrl) throws URISyntaxException {
        String[] result = new String[4];
        File file = new File(String.valueOf(new File(fileUrl.toURI())));
        try (FileInputStream inputStream = new FileInputStream(file)) {
            inputStream.skip(file.length() - 128);
            byte[] b = new byte[128];
            inputStream.read(b);
            String id3 = new String(b);
            System.out.println(id3);
            result[0] = id3.substring(3, 33);
            result[1] = id3.substring(33, 63);
            result[2] = id3.substring(63, 93);
            result[3] = id3.substring(93, 97);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return result;
    }

    public static void main(String[] args){
        TagReader reader = new TagReader();
        File file = new File("player/src/resources/test");
        String[] musics = file.list();
        Thread t1 = new Thread(() -> {
            for (String s : musics) {
                Path filePath = FileSystems.getDefault().getPath(FileIO.RESOURCES_RELATIVE + "test").normalize().toAbsolutePath().resolve(s);
                try {
                    reader.getAdvancedTags(filePath.toUri().toURL());
//                    reader.getSimpleTags(filePath.toUri().toURL());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
                }
            }
        });
        t1.start();
    }

    /**
     *
     * @param fileurl a URL to the mp3file to be used for extraction
     *
     * uses mp3agic library to extract id3v2 information from mp3 file
     * saves tag data to TagReader fields : title - album - artist - releaseDate - imageName
     */
    public void getAdvancedTags(URL fileurl) {
        try {
            Mp3File mp3File = new Mp3File(new File(fileurl.toURI()));
            if (mp3File.hasId3v2Tag()) {
                ID3v2 id3v2 = mp3File.getId3v2Tag();
                album = id3v2.getAlbum();
                if(album == null)
                    album = "albumless";
                this.artist = id3v2.getArtist();
                if (artist == null){
                    artist = "artistless";
                }
                this.title = id3v2.getTitle();
                if (title == null) {
                    String[] s = mp3File.getFilename().split("\\\\");
                    s[s.length -1] = s[s.length-1].replace(".mp3", "");
                    title = s[s.length-1];
                }
                this.durationInMiliSeconds= (int) mp3File.getLengthInMilliseconds();
                mp3File.getLengthInMilliseconds();
                this.releaseDate = id3v2.getYear();
                // calculate hash for searching image cache
                String hashString = FileIO.MD5(this.title + "-" + this.artist);
                String mimeType = id3v2.getAlbumImageMimeType();
                boolean hasImage = true;
                if (mimeType == null)
                    hasImage = false;
                String imgAddress = "";
                if (hasImage) {
                    // checking mime type of artwork
                    switch (mimeType) {
                        case "image/jpeg":
                            imgAddress = FileIO.RESOURCES_RELATIVE + "cache" + File.separator + "img" + File.separator + hashString + ".jpg";
                            break;
                        case "image/png":
                            imgAddress = FileIO.RESOURCES_RELATIVE + "cache" + File.separator + "img" + File.separator + hashString + ".png";
                            break;
                    }
                    if (!FileIO.checkIfFileExists(imgAddress)) {

                        // try and copy and extract artwork from mp3 file to cache/img/[image-hash]

                        String finalImgAddress = imgAddress;
                        BufferedOutputStream out = null;

                        try {
                            byte[] imgBytes = id3v2.getAlbumImage();
                            out = new BufferedOutputStream(new FileOutputStream(finalImgAddress));
                            out.write(imgBytes);
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // sets imageName Field to hashString
                        this.imageName = hashString;
                    }
                }
                int rel = 0;
                if (getReleaseDate() != null && !getReleaseDate().equals("")){
                    rel  = Integer.valueOf(getReleaseDate());
                }

                // Creating a new song object from Tag data
//                System.out.println(title + " - " + fileurl.toString());
                URI imageURI = Paths.get(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "no-artwork.jpg").toAbsolutePath().toUri();
                if (!imgAddress.equals("")) {
                    Path p = Paths.get(imgAddress);
                    imageURI = p.toAbsolutePath().toUri();
                }
                currSong = new Song(title, artist,album, durationInMiliSeconds, 0, new Date().getTime(), fileurl.toURI(), false,false, rel, imageURI);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public int getDurationInSeconds() {
        return durationInMiliSeconds;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getImageName() {
        return imageName;
    }
    public Song getSong(){
        return currSong;
    }
}
