package View;

import utils.FontManager;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
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
        UIManager.put("Label.foreground", new ColorUIResource(255,255,255));
        mainOptionsPanel.setLayout(new GridBagLayout());
        UIManager.put("Label.font", new FontUIResource(FontManager.getUbuntuLight(20f)));
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

        JScrollPane scrollPane = new JScrollPane(optionsPanel,   ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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
//        this.setPreferredSize(new Dimension(1600, 1000));
        this.pack();
        //
        //BackGroundPanel
   //     searchAndBackGroundPanel.add(new BackGroundPanel(),BorderLayout.CENTER);
        //AlbumsPanel
         AlbumsPanel songsPanel = new AlbumsPanel();

        JScrollPane scrollPane2 = new JScrollPane(songsPanel,   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane2.setPreferredSize(new Dimension(250, 700));
        searchAndBackGroundPanel.add(scrollPane2);
//        searchAndBackGroundPanel.setMinimumSize(new Dimension(300, 800));
        this.add(searchAndBackGroundPanel,BorderLayout.CENTER);
    //
        this.setVisible(true);
}
}
