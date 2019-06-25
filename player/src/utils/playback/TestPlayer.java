package utils.playback;

import utils.IO.DatabaseConnection;
import utils.IO.DatabaseHelper;
import Model.Playlist;

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
                    playbackManager.move(10000);
                    break;
                case "b":
                    for (int i = 0; i < 10; i++) {
//                        try {
//                            Thread.sleep(20);
                            playbackManager.move(playbackManager.getSec() + 4000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                    }
                    break;
                case "shuffle":
                    playbackManager.shuffle();
            }
        }


    }
}
