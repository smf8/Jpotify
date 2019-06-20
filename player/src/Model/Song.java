package Model;

import IO.DatabaseHelper;
import IO.FileIO;
import utils.TagReader;

import java.io.File;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URI;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Song {
    private int releasedDate;
    private String hash;
    private String title;
    private String artist;
    private String album;
    private int length;
    private int playCount;
    private LocalDateTime playDate;
    private URI location;
    private boolean playing;
    private boolean selected;

    public Song(String title, String artist, String album, int length, int playCount, LocalDateTime playDate, URI location, boolean playing, boolean selected, int releasedDate) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.length = length;
        this.playCount = playCount;
        this.playDate = playDate;
        this.location = location;
        this.playing = playing;
        this.selected = selected;
        this.releasedDate = releasedDate;
        hash = FileIO.MD5(title + "-" + artist);
    }


    public String getHash() {
        return hash;
    }

    public String getTitle() {
        return title;
    }

    public int getReleasedDate() {
        return releasedDate;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public int getLength() {
        return length;
    }

    public int getPlayCount() {
        return playCount;
    }

    public LocalDateTime getPlayDate() {
        return playDate;
    }

    public URI getLocation() {
        return location;
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isSelected() {
        return selected;
    }


    public static  ArrayList<Song> findSongsInFolder(File directory){
        ArrayList<URI> files = FileIO.findMP3Files(FileIO.findFilesRecursive(directory));
        // creating a list of songs
        ArrayList<Song> songs = new ArrayList<>();
        for (URI i :files){
            TagReader musicFileReader = new TagReader();
            try {
                musicFileReader.getAdvancedTags(i.toURL());
                Song currSong = musicFileReader.getSong();
                if (currSong!= null)
                songs.add(currSong);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return songs;
    }
    public static void saveSongs(ArrayList<Song> songs , Connection databaseConnection){
        DatabaseHelper helper = new DatabaseHelper(databaseConnection);
        System.out.println("Start inserting songs to database");
        new Thread(() -> {
                helper.insertSongs(songs);
                helper.close();
        }).start();
    }
}
