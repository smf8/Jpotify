package View;


import Model.Song;
import Model.User;
import utils.FontManager;
import utils.IO.DatabaseAlterListener;
import utils.IO.DatabaseConnection;
import utils.IO.DatabaseHandler;
import utils.IO.DatabaseHelper;
import utils.playback.PlaybackManager;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Main {
    static PlaybackManager playbackManager;
    public static DatabaseHandler usersHandler;
    public static DatabaseHandler databaseHandler;
    public static User user;
    private static JFrame frame;

    public static void closeFrame(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public static void main(String[] args) {
        //setting default font and color
        UIManager.put("Label.foreground", new ColorUIResource(255, 255, 255));
        UIManager.put("Label.font", new FontUIResource(FontManager.getUbuntu(18f)));
        UIManager.put("Button.font", new FontUIResource(FontManager.getUbuntuBold(20f)));
        UIManager.put("TextField.font", new FontUIResource(FontManager.getUbuntu(20f)));
        UIManager.put("PasswordField.font", new FontUIResource(FontManager.getUbuntu(20f)));

        // initializing databaseListeners
        DatabaseConnection connection = new DatabaseConnection("users");
        connection.initUserTable();
        usersHandler = new DatabaseHelper(connection.getConnection());

                frame = new JFrame();
                LoginPanel loginPanel = new LoginPanel();
                frame.setContentPane(loginPanel);
                frame.setMinimumSize(new Dimension(350,230));
                frame.setResizable(false);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

    }
}
