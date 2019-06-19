package Model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class Song {
    private int id;
    private String title;
    private String artist;
    private String album;
    private int length;
    private long lengthInSeconds;
    private int trackNumber;
    private int discNumber;
    private int playCount;
    private LocalDateTime playDate;
    private String location;
    private boolean playing;
    private boolean selected;
    String hash;
    public Song(int id, String title, String artist, String album, int length, long lengthInSeconds, int trackNumber, int discNumber, int playCount, LocalDateTime playDate, String location, boolean playing, boolean selected) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.length = length;
        this.lengthInSeconds = lengthInSeconds;
        this.trackNumber = trackNumber;
        this.discNumber = discNumber;
        this.playCount = playCount;
        this.playDate = playDate;
        this.location = location;
        this.playing = playing;
        this.selected = selected;
        try {
            hash = new String(MessageDigest.getInstance("MD5").digest((title + "-" + artist).getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }

    public String getTitle() {
        return title;
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

    public long getLengthInSeconds() {
        return lengthInSeconds;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public int getDiscNumber() {
        return discNumber;
    }

    public int getPlayCount() {
        return playCount;
    }

    public LocalDateTime getPlayDate() {
        return playDate;
    }

    public String getLocation() {
        return location;
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isSelected() {
        return selected;
    }
}
