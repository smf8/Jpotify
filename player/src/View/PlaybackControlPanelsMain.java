package View;

import utils.IO.DatabaseConnection;
import utils.IO.DatabaseHelper;
import Model.Song;
import utils.playback.PlaybackManager;
import utils.playback.SimplePlaybackListener;

import javax.swing.*;
import java.util.ArrayList;

public class PlaybackControlPanelsMain {
    public static void main(String[] args) {
        // init player for test

        ArrayList<Song> songs = new DatabaseHelper(new DatabaseConnection("test").getConnection()).searchSong("love");
        JFrame frame = new JFrame();
//        PlaybackControlPanel playbackControlPanel = new PlaybackControlPanel(new PlaybackManager(songs));
//        frame.add(playbackControlPanel);
//        frame.pack();
        frame.setVisible(true);
    }
}
