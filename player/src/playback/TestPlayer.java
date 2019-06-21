package playback;

import IO.DatabaseConnection;
import IO.DatabaseHelper;
import Model.Album;
import Model.Playlist;
import javazoom.jl.decoder.JavaLayerException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

public class TestPlayer {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String com;
        Playlist zahra = new DatabaseHelper(new DatabaseConnection("test").getConnection()).searchPlaylist("smf").get(0);
        PlaybackManager playbackManager = new PlaybackManager(zahra.getSongs());
        float volume = 0;
        while(!(com = sc.next()).equals("exit")){
            switch (com){
                case "play":
                    playbackManager.play();
                    break;
                case "next":
                    playbackManager.next();
                    break;
                case "prev":
                    playbackManager.previous();
                    break;
                case "pause":
                    playbackManager.pause();
                    break;
                case "down":
                    playbackManager.changeVolume(volume-=10);
                    break;
                case "up":
                    playbackManager.changeVolume(volume+=10);
                    break;
                case "forward":
                    playbackManager.move(350000);
                    break;
                case "backward":
                    playbackManager.move(-10000);
                    break;
                case "shuffle":
                    playbackManager.shuffle();
            }
        }


    }
}
