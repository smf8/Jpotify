package View;

import IO.DatabaseConnection;
import IO.DatabaseHandler;
import IO.DatabaseHelper;
import Model.Song;
import playback.PlaybackManager;

import java.util.ArrayList;

public class Main {
    static  PlaybackManager playbackManager;
    public static void main(String[] args) {
        // Testing playback controlling

        DatabaseConnection connection = new DatabaseConnection("test");
        DatabaseHandler databaseHandler = new DatabaseHelper(connection.getConnection());
        ArrayList<Song> songsQueue  = databaseHandler.searchSong("gojira");
        playbackManager = new PlaybackManager(songsQueue);
        MainFrame mainFrame = new MainFrame();

    }
}
