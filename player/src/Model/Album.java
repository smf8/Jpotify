package Model;

import utils.IO.DatabaseHelper;

import java.io.File;
import java.net.URI;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * the class responsible for albums in program
 */
public class Album extends Playlist {
    private String artist;

    public Album(int id, String title, String artist) {
        super(id, title);
        this.artist = artist;
        super.editable = false;
        super.isPublic = false;
    }

    public Album(int id, String title, String artist, URI artWork) {
        super(id, title, artWork);
        this.artist = artist;
        super.editable = false;
        super.isPublic = false;
    }

    /**
     * searches among the music files in the given folder and categorize them by their Album
     *
     * @param folder the folder to search for songs in
     * @return an array list of Albums found in the folder
     */
    public static ArrayList<Album> createAlbumFromFolder(File folder) {
        ArrayList<Song> songs;
        ArrayList<Album> results = new ArrayList<>();
        songs = Song.findSongsInFolder(folder);
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i) == null)
                continue;
            Song currentSong = songs.get(i);
            boolean hasCover = false;
            Album currentAlbum = new Album(i, currentSong.getAlbum(), currentSong.getArtist());
            for (int j = i; j < songs.size(); j++) {
                if (songs.get(j) == null)
                    continue;
                Song beingChecked = songs.get(j);
                Album beingCheckedAlbum = new Album(j, beingChecked.getAlbum(), beingChecked.getArtist());
                if (currentAlbum.equals(beingCheckedAlbum)) {
                    if (currentSong.getArtWork() != null) {
                        currentAlbum.setImageURI(currentSong.getArtWork());
                    }
                    currentAlbum.addSong(beingChecked);
                    songs.set(j, null);
                }
            }
            results.add(currentAlbum);
        }
        return results;
    }

    public static void saveAlbums(ArrayList<Album> albums, Connection databaseConnection) {
        DatabaseHelper helper = new DatabaseHelper(databaseConnection);
        new Thread(() -> {
            for (Album a : albums) {
                helper.insertAlbum(a);
            }
        }).start();
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

        return album.title.equals(this.title);
    }

    public String getArtist() {
        return artist;
    }


}
