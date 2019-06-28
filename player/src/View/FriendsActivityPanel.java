package View;

import Model.Album;
import Model.Playlist;
import Model.Song;
import Model.User;
import utils.FontManager;
import utils.IO.FileIO;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FriendsActivityPanel extends JPanel {
    private JLabel friendsNameLabel = new JLabel();
   private JLabel friendsProfPicLabel = new JLabel();
    private JLabel lastPlayedSongLabel = new JLabel();
    private JLabel lastSongsArtistLabel = new JLabel();
    private JLabel isOnlineLabel = new JLabel();
    private JPanel friendsInformationPanel = new JPanel();
    private JPanel friendsProfPicPanel = new JPanel();
    private JPanel isOnlinePanel = new JPanel();

    public FriendsActivityPanel(User user) {
        friendsNameLabel.setFont(FontManager.getUbuntuBold(16f));
        friendsNameLabel.setText(user.getUsername());
        if (user.getSongs().size()<1) {
            lastPlayedSongLabel.setText("Listened to nothing");
            lastSongsArtistLabel.setText("Nobody :(");
        }else{
            lastPlayedSongLabel.setText(user.getSongs().get(0).getTitle());
            lastSongsArtistLabel.setText(user.getSongs().get(0).getArtist());
        }
        //
        friendsInformationPanel.setLayout(new BoxLayout(friendsInformationPanel,BoxLayout.PAGE_AXIS));
        friendsInformationPanel.setBackground(new Color(22,22,22));
        friendsInformationPanel.add(friendsNameLabel);
        friendsInformationPanel.add(Box.createRigidArea(new Dimension(0,5)));
        friendsInformationPanel.add(lastPlayedSongLabel);
        friendsInformationPanel.add(Box.createRigidArea(new Dimension(0,5)));
        friendsInformationPanel.add(lastSongsArtistLabel);
        friendsInformationPanel.add(Box.createRigidArea(new Dimension(0,5)));
        isOnlinePanel.setBackground(new Color(22,22,22));
        friendsProfPicPanel.setBackground(new Color(22,22,22));

        try {
            ImageIcon profileImage = new ImageIcon(new ImageIcon(user.getProfileImage().toURL()).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            friendsProfPicLabel.setIcon(profileImage);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (user.isOnline()){
            isOnlineLabel.setText("Online");
        }else{
            isOnlineLabel.setFont(FontManager.getUbuntuLight(14f));
            new Thread(()->{
                while (true) {
                    long diff = new Date().getTime() - user.getLastOnline();
                    ;
                    int day = (int) TimeUnit.MILLISECONDS.toDays(diff);
                    int hour = (int) ((int) TimeUnit.MILLISECONDS.toHours(diff) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(diff)));
                    int min = (int) (TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diff)));
                    isOnlineLabel.setText(day + "  " + hour + ":" + min + " ago");
                    FriendsActivityPanel.this.validate();
                    FriendsActivityPanel.this.repaint();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        //

        friendsProfPicPanel.add(friendsProfPicLabel);
        isOnlinePanel.add(isOnlineLabel);

        this.setLayout(new BorderLayout());
        this.add(friendsInformationPanel,BorderLayout.CENTER);
        this.add(friendsProfPicPanel,BorderLayout.WEST);
        this.add(isOnlinePanel,BorderLayout.EAST);
        this.setMaximumSize(new Dimension(250,100));
        this.setMinimumSize(new Dimension(250,100));
        this.setPreferredSize(new Dimension(250,100));
    }
}
