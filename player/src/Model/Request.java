package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Request implements Serializable {
    private int type;
    // request types :
    // 0 : connecting -> Only contains User object to alert other users to update their list
    //1 : receiving friend's current song
    //2 : Sending a song's file
    //3 : telling friends about my public playlist
    //4 : telling a friend hash of songs of a playlist
    //5 : answering 4 with hash of songs which are not in the friend's computer

    private byte[] dataToTransfer;
    private Playlist alteredPlaylist;
    private ArrayList<String> requestedStringData = new ArrayList<>();
    private Song song;
    private User user;

    public Request(int type, User user) {
        this.type = type;
        this.user = user;
    }

    public void addRequestedStringData(String string){
        requestedStringData.add(string);
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public byte[] getDataToTransfer() {
        return dataToTransfer;
    }

    public void setDataToTransfer(byte[] dataToTransfer) {
        this.dataToTransfer = dataToTransfer;
    }

    public Playlist getAlteredPlaylist() {
        return alteredPlaylist;
    }

    public void setAlteredPlaylist(Playlist alteredPlaylist) {
        this.alteredPlaylist = alteredPlaylist;
    }

    public ArrayList<String> getRequestedStringData() {
        return requestedStringData;
    }

    public void setRequestedStringData(ArrayList<String> requestedStringData) {
        this.requestedStringData = requestedStringData;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


