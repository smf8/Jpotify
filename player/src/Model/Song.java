package Model;

import IO.FileIO;

import java.net.URI;
import java.time.LocalDateTime;

public class Song {
    private LocalDateTime releasedDate;
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

    public Song(String title, String artist, String album, int length, int playCount, LocalDateTime playDate, URI location, boolean playing, boolean selected, LocalDateTime releasedDate) {
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
        hash = FileIO.MD5(artist + "-" + title);
    }


    public String getHash() {
        return hash;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getReleasedDate() {
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
}
