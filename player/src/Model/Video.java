package Model;

import java.net.URI;

public class Video {
    private String title;
    private long length;
    private URI location;
    private boolean playing;

    public Video(URI location){
        this.location = location;
    }
    public String getTitle(){
        return title;
    }
    public URI getLocation(){
        return location;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public void setLocation(URI location) {
        this.location = location;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}
