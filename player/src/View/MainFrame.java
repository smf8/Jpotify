package View;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class MainFrame extends JFrame {
    private JPanel mainOptionsPanel = new JPanel();
    private JPanel searchAndBackGroundPanel = new JPanel();
    private JLabel backGroundLabel = new JLabel();
    public MainFrame(){
        this.setLayout(new BorderLayout());
        // OptionsPanel
        mainOptionsPanel.setLayout(new GridBagLayout());
        OptionsPanel optionsPanel = new OptionsPanel();
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.addPlaylist("jkpk");
        optionsPanel.showPlaylist();
        searchAndBackGroundPanel.setLayout(new BorderLayout());
        mainOptionsPanel.add(optionsPanel);
        GridBagConstraints frameConstraints = new GridBagConstraints();

        JScrollPane scrollPane = new JScrollPane(optionsPanel,   ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
       scrollPane.setPreferredSize(new Dimension(250, 700));
        //frameConstraints.gridx = 2;
        //frameConstraints.gridy = 2;
        //frameConstraints.weighty =2;

       // mainOptionsPanel.add(scrollPane);
       // mainOptionsPanel.add(scrollPane,frameConstraints);
        this.add(scrollPane,BorderLayout.WEST);
        //
        //PlaybackControlPanel
        PlaybackControlPanel playbackControlPanel = new PlaybackControlPanel(Main.playbackManager);
        this.add(playbackControlPanel,BorderLayout.SOUTH);
        //FriendsActivityPanel
        FriendsActivityPanelsManager friendsActivityPanelsManager = new FriendsActivityPanelsManager();
        friendsActivityPanelsManager.showFriends();
        this.add(friendsActivityPanelsManager,BorderLayout.EAST);
        //
        //SearchAndProfilesPanel
        SearchAndProfilesPanel searchAndProfilesPanel = new SearchAndProfilesPanel();
        searchAndProfilesPanel.setProfileInformation("user");
        searchAndBackGroundPanel.add(searchAndProfilesPanel,BorderLayout.NORTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        //
        //BackGroundPanel
//        URL backGroundUrl = null;
//        try {
//            File backGroundFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "lanadelrey.png");
//            backGroundUrl = backGroundFile.toURI().toURL();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        backGroundImage = new ImageIcon(new ImageIcon(backGroundUrl).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
//        backGroundLabel.setIcon(backGroundImage);
   //     searchAndBackGroundPanel.add(new BackGroundPanel(),BorderLayout.CENTER);
        //SongsPanel
         SongsPanel songsPanel = new SongsPanel();

        JScrollPane scrollPane2 = new JScrollPane(songsPanel,   ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane2.setPreferredSize(new Dimension(250, 700));
        searchAndBackGroundPanel.add(scrollPane2,BorderLayout.CENTER);
       // searchAndBackGroundPanel.add(songsPanel);
        this.add(searchAndBackGroundPanel,BorderLayout.CENTER);
        //
        this.setVisible(true);
    }
}
