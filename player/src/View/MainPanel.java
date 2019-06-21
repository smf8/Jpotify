package View;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class MainPanel extends JPanel {
    private JPanel mainOptionsPanel = new JPanel();
    private JLabel backGroundLabel = new JLabel();
    private ImageIcon backGroundImage;
    public MainPanel(){
        this.setLayout(new BorderLayout());
        // OptionsPanel
        mainOptionsPanel.setLayout(new GridBagLayout());
        OptionsPanel optionsPanel = new OptionsPanel();
        optionsPanel.showPlaylist();
        mainOptionsPanel.add(optionsPanel);
        GridBagConstraints frameConstraints = new GridBagConstraints();

        JScrollPane scrollPane = new JScrollPane(optionsPanel,   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(200, 500));
        frameConstraints.gridx = 2;
        frameConstraints.gridy = 2;
        frameConstraints.weighty =2;
        mainOptionsPanel.add(scrollPane,frameConstraints);
        mainOptionsPanel.add(scrollPane);
        this.add(mainOptionsPanel,BorderLayout.WEST);
        //
        //PlaybackControlPanel
        PlaybackControlPanel playbackControlPanel = new PlaybackControlPanel();
        this.add(playbackControlPanel,BorderLayout.SOUTH);
        //
        //FriendsActivityPanel
        FriendsActivityPanelsManager friendsActivityPanelsManager = new FriendsActivityPanelsManager();
        friendsActivityPanelsManager.showFriends();
        this.add(friendsActivityPanelsManager,BorderLayout.EAST);
        //
        //SearchAndProfilesPanel
        SearchAndProfilesPanel searchAndProfilesPanel = new SearchAndProfilesPanel();
        searchAndProfilesPanel.setProfileInformation("user");
        this.add(searchAndProfilesPanel,BorderLayout.NORTH);
        //
        //BackGroundPanel
        URL backGroundUrl = null;
        try {
            File backGroundFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "lanadelrey.png");
            backGroundUrl = backGroundFile.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        backGroundImage = new ImageIcon(new ImageIcon(backGroundUrl).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
        backGroundLabel.setIcon(backGroundImage);
        this.add(backGroundLabel,BorderLayout.CENTER);
        //
    }
}
