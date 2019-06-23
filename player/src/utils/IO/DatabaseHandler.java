package utils.IO;

import Model.Album;
import Model.Playlist;
import Model.Song;

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

    void insertPlaylist(Playlist playlist);

    ArrayList<Album> searchAlbum(String searchQuery);

    ArrayList<Playlist> searchPlaylist(String searchItem);

    ArrayList<Song> searchSong(String searchItem);

    void close();
}
