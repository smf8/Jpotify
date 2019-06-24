package View;

import Model.Song;
import utils.FontManager;
import utils.IO.*;
import utils.playback.PlaybackManager;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    static PlaybackManager playbackManager;
    static DatabaseAlterListener listener;
    private static ArrayList<Song> songsQueue;
    private JPanel mainOptionsPanel = new JPanel();
    private JPanel searchAndBackGroundPanel = new JPanel();
    private JLabel addNewPlaylistText = new JLabel("Add new playlist");
    private JLabel addNewPlaylistImageLabel = new JLabel();
    private JPanel addNewPlaylistPanel = new JPanel();
    private JPanel mainMainOptionsPanel = new JPanel();
    //    private JLabel playingSondArtWorkLabel = new JLabel();
//    private JPanel addNewPlaylistAndSongArtWorkPanel = new JPanel();
    private JLabel backGroundLabel = new JLabel();

    public MainFrame() {
        mainMainOptionsPanel.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
        // OptionsPanea
        addNewPlaylistPanel.setLayout(new BorderLayout());
        URL addNewPlayListUrl = null;

        try {
            File addNewPlaylistFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "plus.png");
            addNewPlayListUrl = addNewPlaylistFile.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ImageIcon addNewPlaylistIcon = new ImageIcon(new ImageIcon(addNewPlayListUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        addNewPlaylistImageLabel.setIcon(addNewPlaylistIcon);
        //addNewPlaylist ActionListener
        addNewPlaylistText.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame frame = new JFrame();
                AddNewPlaylistPanel addNewPlaylistPanel = new AddNewPlaylistPanel();
                frame.add(addNewPlaylistPanel);
                frame.pack();
                frame.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        addNewPlaylistPanel.add(addNewPlaylistImageLabel, BorderLayout.WEST);
        addNewPlaylistPanel.add(addNewPlaylistText, BorderLayout.CENTER);

        UIManager.put("Label.foreground", new ColorUIResource(255, 255, 255));
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

        JScrollPane scrollPane = new JScrollPane(optionsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(210, 700));
        addNewPlaylistPanel.setBackground(new Color(22, 22, 22));
        addNewPlaylistText.setFont(FontManager.getUbuntu(18f));
        addNewPlaylistText.setForeground(Color.WHITE);
        mainMainOptionsPanel.add(scrollPane, BorderLayout.CENTER);
        mainMainOptionsPanel.add(addNewPlaylistPanel, BorderLayout.SOUTH);
        //frameConstraints.gridx = 2;
        //frameConstraints.gridy = 2;
        //frameConstraints.weighty =2;

        // mainOptionsPanel.add(scrollPane);
        // mainOptionsPanel.add(scrollPane,frameConstraints);
        this.add(mainMainOptionsPanel, BorderLayout.WEST);
        //
        //PlaybackControlPanel
        PlaybackControlPanel playbackControlPanel = new PlaybackControlPanel(playbackManager);
        this.add(playbackControlPanel, BorderLayout.SOUTH);
        //FriendsActivityPanel
        FriendsActivityPanelsManager friendsActivityPanelsManager = new FriendsActivityPanelsManager();
        friendsActivityPanelsManager.showFriends();
        JScrollPane scrollPanel3 = new JScrollPane(friendsActivityPanelsManager, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        scrollPanel3.setPreferredSize(new Dimension(280, 700));
        this.add(scrollPanel3, BorderLayout.EAST);
        //
        //SearchAndProfilesPanel
        SearchAndProfilesPanel searchAndProfilesPanel = new SearchAndProfilesPanel();
        searchAndBackGroundPanel.add(searchAndProfilesPanel, BorderLayout.NORTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        this.setPreferredSize(new Dimension(1600, 1000));
        //
        //BackGroundPanel
        //     searchAndBackGroundPanel.add(new BackGroundPanel(),BorderLayout.CENTER);
        //AlbumsPanel
        AlbumsPanel songsPanel = new AlbumsPanel();
        SongPanel songPanel = new SongPanel(songsQueue);
        songPanel.setDatabaseAlterListener(listener);
        JScrollPane scrollPane2 = new JScrollPane(songPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //        scrollPane2.setPreferredSize(new Dimension(250, 700));
        searchAndBackGroundPanel.add(scrollPane2, BorderLayout.CENTER);
        searchAndBackGroundPanel.add(searchAndProfilesPanel, BorderLayout.NORTH);
        //        searchAndBackGroundPanel.setMinimumSize(new Dimension(300, 800));
//        this.add(scrollPane2,BorderLayout.CENTER);
        this.add(searchAndBackGroundPanel, BorderLayout.CENTER);
        //
        this.pack();
        this.setVisible(true);
    }

    public static void addSongToQueue(Song song) {
        if (!songsQueue.contains(song)) {
            songsQueue.add(song);
        } else {
            songsQueue.remove(song);
            songsQueue.add(0, song);
        }
    }

    public static void main(String[] args) {

        // initializing databaseListeners
        DatabaseConnection connection = new DatabaseConnection("test");
        DatabaseHandler databaseHandler = new DatabaseHelper(connection.getConnection());
        listener = new DatabaseAlterListener() {
            @Override
            public void removeSong(Song song) {
                new Thread(() -> {
                    databaseHandler.removeSong(song);
                }).start();
            }

            @Override
            public void saveSong(Song song) {
                new Thread(() -> {
                    ArrayList<Song> s = new ArrayList<>();
                    s.add(song);
                    databaseHandler.insertSongs(s);
                });
            }
        };


        // Testing playback controlling
        songsQueue = databaseHandler.searchSong("go");
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

    public ArrayList<Song> getSongsQueue() {
        return songsQueue;
    }
    public static void addSongToPlay(Song song){
        if (songsQueue.contains(song)) {
            songsQueue.remove(song);
        }
        songsQueue.add(0, song);
        playbackManager.resetQueue(songsQueue);
//        for (Song s : songsQueue){
//            System.out.println(s.getTitle());
//        }
        playbackManager.play();
    }
}
