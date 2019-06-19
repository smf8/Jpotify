package Model;

import IO.FileIO;
import utils.TagReader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;

/**
 * the class responsible for albums in program
 */
public class Album extends Playlist{
    private String artist;
    public Album(int id, String title, String artist) {
        super(id, title);
        this.artist = artist;
    }
    public Album(int id, String title,String artist, URI artWork){
        super(id, title, artWork);
        this.artist = artist;
    }

    /**
     * searches among the music files in the given folder and categorize them by their Album
     * @param folder the folder to search for songs in
     * @return an array list of Albums found in the folder
     */
    public static ArrayList<Album> createAlbumFromFolder(File folder){
        ArrayList<URI> files = FileIO.findMP3Files(FileIO.findFilesRecursive(folder));
        ArrayList<Song> songs = new ArrayList<>();
        ArrayList<Album> results = new ArrayList<>();
        // creating a list of songs
        for (URI i :files){
            TagReader musicFileReader = new TagReader();
            try {
                musicFileReader.getAdvancedTags(i.toURL());
                Song currSong = musicFileReader.getSong();
                songs.add(currSong);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i) == null)
                continue;
            Song currentSong = songs.get(i);
            Album currentAlbum = new Album(i, currentSong.getAlbum(), currentSong.getArtist());
            for (int j = i+1; j < songs.size(); j++){
                if (songs.get(j) == null)
                    continue;
                Song beingChecked = songs.get(j);
                Album beingCheckedAlbum = new Album(j, beingChecked.getAlbum(), beingChecked.getArtist());
                if (currentAlbum.equals(beingCheckedAlbum)){
                    currentAlbum.addSong(beingChecked);
                    songs.set(j, null);
                }
            }
            results.add(currentAlbum);
        }
        return results;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Album)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Album album = (Album) obj;

        if (album.title.equals(this.title))
            return true;
        return false;
    }

    public String getArtist() {
        return artist;
    }
}
