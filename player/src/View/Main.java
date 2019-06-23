package View;


import Model.Song;
import utils.IO.DatabaseConnection;
import utils.IO.DatabaseHandler;
import utils.IO.DatabaseHelper;
import utils.playback.PlaybackManager;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Main {
    static PlaybackManager playbackManager;
    public static void main(String[] args) throws MalformedURLException {
        // Testing playback controlling

        DatabaseConnection connection = new DatabaseConnection("test");
        DatabaseHandler databaseHandler = new DatabaseHelper(connection.getConnection());
        ArrayList<Song> songsQueue  = databaseHandler.searchSong("gojira");
        playbackManager = new PlaybackManager(songsQueue);
        MainFrame mainFrame = new MainFrame();
         //       SongPanel songPanel = new SongPanel();

           //     frame.add(songPanel,BorderLayout.CENTER);
//                frame.pack();
//                frame.setVisible(true);

    }
}
