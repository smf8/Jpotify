package Model;

import java.net.URI;
import java.util.ArrayList;

public class User {
    private String username;
    private ArrayList<Album> albums = new ArrayList<>();
    private ArrayList<Playlist> playlists = new ArrayList<>();
    private ArrayList<Song> songs = new ArrayList<>();
    private ArrayList<Song> likedSongs = new ArrayList<>();
    private Song currentSong;
    private boolean isOnline;
    private String password;
    private URI profileImage;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, Song currentSong, String password) {
        this.username = username;
        this.currentSong = currentSong;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public ArrayList<Song> getLikedSongs() {
        return likedSongs;
    }

    public void setLikedSongs(ArrayList<Song> likedSongs) {
        this.likedSongs = likedSongs;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public URI getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(URI profileImage) {
        this.profileImage = profileImage;
    }
}
