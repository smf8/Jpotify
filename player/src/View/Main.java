package View;


import Model.Song;
import utils.IO.DatabaseAlterListener;
import utils.IO.DatabaseConnection;
import utils.IO.DatabaseHandler;
import utils.IO.DatabaseHelper;
import utils.playback.PlaybackManager;

import javax.swing.*;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Main {
    static PlaybackManager playbackManager;

    //    public static void main(String[] args) {
//        UIDefaults defaults = UIManager.getDefaults();
//        System.out.println(defaults.size()+ " properties defined !");
//        String[ ] colName = {"Key", "Value"};
//        String[ ][ ] rowData = new String[ defaults.size() ][ 2 ];
//        int i = 0;
//        for(Enumeration e = defaults.keys(); e.hasMoreElements(); i++){
//            Object key = e.nextElement();
//            if (key.toString().contains("Label")) {
//                rowData[i][0] = key.toString();
//                rowData[i][1] = "" + defaults.get(key);
//                System.out.println(rowData[i][0] + " ,, " + rowData[i][1]);
//            }
//        }
//        JFrame f = new JFrame("UIManager properties default values");
//        JTable t = new JTable(rowData, colName);
//        f.setContentPane(new JScrollPane(t));
//        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.pack();
//        f.setVisible(true);
//    }
    public static void main(String[] args) {

        // initializing databaseListeners
        DatabaseConnection connection = new DatabaseConnection("test");
        DatabaseHandler databaseHandler = new DatabaseHelper(connection.getConnection());
        DatabaseAlterListener listener = new DatabaseAlterListener() {
            @Override
            public void removeSong(Song song) {
                databaseHandler.removeSong(song);
            }
        };


        // Testing playback controlling


        ArrayList<Song> songsQueue = databaseHandler.searchSong("gojira");
        playbackManager = new PlaybackManager(songsQueue);
        MainFrame mainFrame = new MainFrame();
//        SignUpPanel signUpPanel = new SignUpPanel();
         //       AlbumPanel songPanel = new AlbumPanel();
     //           JFrame frame = new JFrame();
//                SongPanel sPanel = new SongPanel();
//                frame.add(sPanel);
//                frame.setResizable(false);
//                frame.add(,BorderLayout.CENTER);
//                frame.pack();

//                frame.setVisible(true);

    }
}
