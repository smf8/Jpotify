package View;

import Model.Song;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FriendsActivityPanel extends JPanel {
    private JLabel friendsNameLabel = new JLabel();
   private JLabel friendsProfPicLabel = new JLabel();
    private JLabel lastPlayedSongLabel = new JLabel();
    private JLabel lastSongsArtistLabel = new JLabel();
    private JLabel isOnlineLabel = new JLabel();
    private JPanel friendsInformationPanel = new JPanel();
    private JPanel friendsProfPicPanel = new JPanel();
    private JPanel isOnlinePanel = new JPanel();

    public FriendsActivityPanel(String userName) {
        friendsNameLabel.setText(userName);
        lastPlayedSongLabel.setText("dohdyfoifuy");
        lastSongsArtistLabel.setText("reijgwpir");
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

        //for test
        try {
            URL playUrl = null;
            File playFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "play.png");
            playUrl = playFile.toURI().toURL();
            ImageIcon playIcon = new ImageIcon(new ImageIcon(playUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            friendsProfPicLabel.setIcon(playIcon);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        isOnlineLabel.setText("Online");

        //

        friendsProfPicPanel.add(friendsProfPicLabel);
        isOnlinePanel.add(isOnlineLabel);

        this.setLayout(new BorderLayout());
        this.add(friendsInformationPanel,BorderLayout.CENTER);
        this.add(friendsProfPicPanel,BorderLayout.WEST);
        this.add(isOnlinePanel,BorderLayout.EAST);
    }
}
