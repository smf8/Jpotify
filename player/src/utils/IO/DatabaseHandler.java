package utils.IO;

import Model.Album;
import Model.Playlist;
import Model.Song;
import Model.User;

import java.util.ArrayList;

public interface DatabaseHandler {
    void removeSong(Song song);

    void removeSongFromAlbum(Song song, Album album);

    void removeSongFromPlaylist(Song song, Playlist playlist);

    void addSongToPlaylist(Song song, Playlist playlist);

    void insertSongs(ArrayList<Song> songs);

    Playlist getPlaylistByName(String title);

    Album getAlbumByTitle(String title);

    Song getSongByTitle(String title);

    Song getSongByHash(String hash);

    void insertAlbum(Album album);

    int insertPlaylist(Playlist playlist);

    ArrayList<Album> searchAlbum(String searchQuery);

    ArrayList<Playlist> searchPlaylist(String searchItem);

    ArrayList<Song> searchSong(String searchItem);

    boolean addUser(User user);
    void deepInsertSong(ArrayList<Song> songs);

    ArrayList<User> getUserByUsername(String username, DatabaseHandler handler);
    Album getAlbumByID(int ID);
    Playlist getPlaylistByID(int ID);
    void removeUser(String username);
    void updateSong(Song song);
    void close();
}
