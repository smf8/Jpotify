package utils.IO;

import Model.Album;
import Model.Playlist;
import Model.Song;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class DatabaseTest {
    public static void main(String[] args) throws MalformedURLException, URISyntaxException {
        DatabaseConnection connection = new DatabaseConnection("test");
        connection.initMusicsTable();

        // initialize song table with songs inside a folder
        File dir = new File("player/src/resources");
        Song.saveSongs(Song.findSongsInFolder(dir), connection.getConnection());

        // initialize albums table with songs inside a folder
        Album.saveAlbums(Album.createAlbumFromFolder(dir), connection.getConnection());

        // retrieving a song from database by it's title
//        Song s1 = new DatabaseHelper(connection.getConnection()).getSongByTitle("Laid to Rest");
//        System.out.println(s1.getArtist());


        // create a sample playlist
            DatabaseHelper helper = new DatabaseHelper(connection.getConnection());
            ArrayList<Song> songs = new ArrayList<>();
            songs.add(helper.getSongByHash("2a4da2b7c03d7d0892d40c7349afa98a"));
            songs.add(helper.getSongByHash("a20f3f7fb574e64ee427e2c43a1c9374"));
            songs.add(helper.getSongByHash("599590dfc0ea763fdff2cf9ed077c4e7"));
            songs.add(helper.getSongByHash("1f68d0c84958eab210be87d89069ffd0"));
            songs.add(helper.getSongByHash("c937b8377a588dd5f70d25ef6e01e674"));
            songs.add(helper.getSongByHash("aa957bf4d028add75110e7a57db36a80"));

            Playlist playlist = new Playlist(0,"Zahra's playlist", "smf" , songs.get(0).getArtWork());
            playlist.setSongs(songs);
            helper.insertPlaylist(playlist);


//        ArrayList<Song> songs = helper.searchSong("go");
//        ArrayList<Album> albums = helper.searchAlbum("gojira");
//        ArrayList<Playlist> playlists = helper.searchPlaylist("smf");
//        for (Song song : songs){
//            System.out.println(song.getTitle());
//        }
//        System.out.println("-------------");
//        for (Album album : albums){
//            System.out.println(album.getTitle());
//        }
//        System.out.println("--------------");
//        for (Playlist playlist : playlists){
//            for (Song s : playlist.getSongs()){
//                System.out.println(s.getArtist());
//            }
//        }

//        Song s1 = helper.searchSong("low lands").get(0);
//        helper.removeSong(s1);
//        retrieving an album from database
//        Album album = new DatabaseHelper(connection.getConnection()).getAlbumByTitle("Magma");
//        for (Song s : album.getSongs()){
//            System.out.println(s.getTitle());
//        }

    }
}
