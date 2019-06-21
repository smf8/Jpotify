package View;

import Model.Song;

import javax.swing.*;
import java.awt.*;
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

    public FriendsActivityPanel(Song song,String userName){
        friendsNameLabel.setText(userName);
        lastPlayedSongLabel.setText(song.getTitle());
        lastSongsArtistLabel.setText(song.getArtist());
        //
        friendsInformationPanel.setLayout(new BoxLayout(friendsInformationPanel,BoxLayout.PAGE_AXIS));
        friendsInformationPanel.add(friendsNameLabel);
        friendsInformationPanel.add(Box.createRigidArea(new Dimension(0,5)));
        friendsInformationPanel.add(lastPlayedSongLabel);
        friendsInformationPanel.add(Box.createRigidArea(new Dimension(0,5)));
        friendsInformationPanel.add(lastSongsArtistLabel);
        friendsInformationPanel.add(Box.createRigidArea(new Dimension(0,5)));

        friendsProfPicPanel.add(friendsProfPicLabel);
        isOnlinePanel.add(isOnlinePanel);

        this.setLayout(new BorderLayout());
        this.add(friendsInformationPanel,BorderLayout.CENTER);
        this.add(friendsProfPicPanel,BorderLayout.WEST);
        this.add(isOnlinePanel,BorderLayout.EAST);
    }
}
