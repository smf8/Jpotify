package Model;

import java.net.URI;
import java.util.ArrayList;

public class Playlist {
    protected int id;
    protected String title;
    private String creator;
    protected boolean editable;
    protected boolean isPublic;
    protected URI imageURI;
    protected ArrayList<Song> songs;

    public Playlist(int id, String title) {
        this.id = id;
        this.title = title;
        this.isPublic = false;
        this.editable = false;
        songs = new ArrayList<>();
    }
    public Playlist(int id, String title, String creator){
        this.id = id;
        this.title = title;
        this.creator = creator;
    }
    public Playlist(int id, String title, URI imageURI) {
        this.id = id;
        this.title = title;
        this.isPublic = false;
        this.editable = false;
        this.imageURI = imageURI;
        songs = new ArrayList<>();
    }
    public void addSong(Song s){
        songs.add(s);
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isEditable() {
        return editable;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }
    public Song getSong(int pos){
        return songs.get(pos);
    }

    public int getId() {
        return id;
    }

    public String getCreator() {
        return creator;
    }

    public String getTitle() {
        return title;
    }

    public URI getImageURI() {
        return imageURI;
    }

    public void setImageURI(URI imageURI) {
        this.imageURI = imageURI;
    }
}
