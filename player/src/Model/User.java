package Model;

import utils.IO.DatabaseConnection;
import utils.IO.DatabaseHandler;

import java.lang.reflect.Array;
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
    private ArrayList<User> friendsList;
    private String friends;
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

    public ArrayList<User> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(ArrayList<User> friendsList) {
        this.friendsList = friendsList;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public void addPlaylist(Playlist playlist){
        if (playlists == null){
            playlists = new ArrayList<>();
        }
        playlists.add(playlist);
    }
    public void addAlbum(Album album){
        if (albums == null){
            albums = new ArrayList<>();
        }
        albums.add(album);
    }
    public void likeSong(Song song){
        if (likedSongs == null){
            likedSongs = new ArrayList<>();
        }
        likedSongs.add(song);
    }
    public void dislikeSong(Song song){
        likedSongs.remove(song);
    }
    public void listened(Song song){
        if (songs.contains(song)){
            songs.remove(song);
            songs.add(0, song);
        }else{
            songs.add(0, song);
        }
    }
}
