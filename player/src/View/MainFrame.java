package View;

import Model.*;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;
import utils.FontManager;
import utils.IO.DatabaseAlterListener;
import utils.IO.FileIO;
import utils.IO.MyFileChooser;
import utils.TagReader;
import utils.net.Client;
import utils.playback.PlaybackManager;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class MainFrame extends JFrame {
    static PlaybackManager playbackManager;
    static DatabaseAlterListener listener;
    static AlbumsPanel albumsPanel = new AlbumsPanel();
    // song playback management
    public static ArrayList<Song> songsQueue;
    public static int queueIndex = 0;
    private AudioPlayerComponent mediaController = new AudioPlayerComponent();
    //
    private JPanel mainOptionsPanel = new JPanel();
    private static JPanel searchAndBackGroundPanel = new JPanel();
    private JLabel addNewPlaylistText = new JLabel("Add new playlist");
    private JLabel addNewPlaylistImageLabel = new JLabel();
    private static JScrollPane scrollPane2;
    private JPanel addNewPlaylistPanel = new JPanel();
    private JPanel mainMainOptionsPanel = new JPanel();
    private static MainFrame mainFrame;
    private static ArrayList<Song> allSongs;
    private static ArrayList<Album> allAlbums;
    private static ArrayList<Playlist> allPlaylists;
    private static ArrayList<Song> likedSongs = new ArrayList<>();
    private static ArrayList<Song> playedSongs = new ArrayList<>();
    public FriendsActivityPanelsManager friendsActivityPanelsManager;

    public static Client userClient;
    public JFrame playListFrame;

    private OptionsPanel optionsPanel;

    public MainFrame() {
        // init user login management
        initClient();
        this.setLayout(new BorderLayout());
        initializeSongs();
        initSidePanels();
        allSongs = Main.databaseHandler.searchSong("");
        allAlbums = Main.databaseHandler.searchAlbum("");
        allPlaylists = Main.databaseHandler.searchPlaylist("");

        // tell other users about our public play lists
        ArrayList<Playlist> publicPlaylists = new ArrayList<>();
        for (Playlist p : allPlaylists){
            if (p.isPublic()){
                publicPlaylists.add(p);
            }
        }
        if(publicPlaylists.size()>=1){
            new Thread(()->{
                for (Playlist p : publicPlaylists){
                    for (User friend : Main.user.getFriendsList()) {
                        if (friend.isOnline()) {
                            System.out.println("sending to " + friend.getUsername());
                                userClient.sendRequest(Client.tellSongsHash(p, friend));
                        }
                    }
                }
            }).start();
        }

        setupAlbumsPanel(allAlbums);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(MainFrame.this,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                            closeResources(); // inja goh hayi ke bayad bokhori ro benevis
                            System.exit(0);
                        }
            }
        });
        mainFrame = this;

    }
    private void initClient(){
        new Thread(()->{
            userClient = new Client("localhost", Main.user);
            userClient.sendRequest(new Request(0, Main.user));
        }).start();
    }
    public static void addSongToQueue(Song song) {
        if (!songsQueue.contains(song)) {
            songsQueue.add(song);
        } else {
            songsQueue.remove(song);
            songsQueue.add(0, song);
        }
    }
    public static void setupQueue(ArrayList<Song> newQueue){
        songsQueue = newQueue;
    }
    public static void addSongToPlay(Song song) {
        songsQueue.remove(song);
        songsQueue.add(0, song);
        playbackManager.resetQueue(songsQueue);
//        for (Song s : songsQueue){
//            System.out.println(s.getTitle());
//        }
        playbackManager.stop();
        playbackManager.play(song);
    }

    private void initSidePanels() {

        // init font and background defaults
        UIManager.put("Label.foreground", new ColorUIResource(255, 255, 255));
        UIManager.put("Label.font", new FontUIResource(FontManager.getUbuntuLight(20f)));

        //init left side menu
        mainMainOptionsPanel.setLayout(new BorderLayout());
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
        // mouse listener for playlist creation
        addNewPlaylistText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playListFrame = new JFrame();
                AddNewPlaylistPanel addNewPlaylistPanel = new AddNewPlaylistPanel();
                playListFrame.add(addNewPlaylistPanel);
                playListFrame.pack();
                playListFrame.setVisible(true);
            }
        });

        addNewPlaylistPanel.add(addNewPlaylistImageLabel, BorderLayout.WEST);
        addNewPlaylistPanel.add(addNewPlaylistText, BorderLayout.CENTER);
        mainOptionsPanel.setLayout(new GridBagLayout());
        optionsPanel = new OptionsPanel();
        updatePlaylists();
        searchAndBackGroundPanel.setLayout(new BorderLayout());
        mainOptionsPanel.add(optionsPanel);
        JScrollPane scrollPane = new JScrollPane(optionsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(210, 700));
        addNewPlaylistPanel.setBackground(new Color(22, 22, 22));
        addNewPlaylistText.setFont(FontManager.getUbuntu(18f));
        addNewPlaylistText.setForeground(Color.WHITE);
        mainMainOptionsPanel.add(scrollPane, BorderLayout.CENTER);
        mainMainOptionsPanel.add(addNewPlaylistPanel, BorderLayout.SOUTH);
        this.add(mainMainOptionsPanel, BorderLayout.WEST);


        // init bottom control panel
        playbackManager = new PlaybackManager(songsQueue, mediaController);
        PlaybackControlPanel playbackControlPanel = new PlaybackControlPanel(playbackManager);
        this.add(playbackControlPanel, BorderLayout.SOUTH);
        // add right side friends panel
        friendsActivityPanelsManager = new FriendsActivityPanelsManager();
        friendsActivityPanelsManager.updateFriendsList();
        JScrollPane scrollPanel3 = new JScrollPane(friendsActivityPanelsManager, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPanel3.setPreferredSize(new Dimension(280, 700));
        this.add(scrollPanel3, BorderLayout.EAST);
        // top side search bar
        SearchAndProfilesPanel searchAndProfilesPanel = new SearchAndProfilesPanel();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // center side init it inside another method
        searchAndBackGroundPanel.add(searchAndProfilesPanel, BorderLayout.NORTH);
        this.add(searchAndBackGroundPanel, BorderLayout.CENTER);
    }

    public void setupAlbumsPanel(ArrayList<Album> albums) {
        if (albumsPanel.getPanels().size() == 0) {
            for (Album album : albums) {
                albumsPanel.addAlbum(album);
            }
            albumsPanel.showAlbums();

            for (AlbumPanel p : albumsPanel.getPanels()) {
                p.setViewUpdateListener((parent, child) -> {
                    searchAndBackGroundPanel.remove(scrollPane2);
                    scrollPane2 = new JScrollPane(child, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                    searchAndBackGroundPanel.add(scrollPane2, BorderLayout.CENTER);
                    searchAndBackGroundPanel.validate();
                    searchAndBackGroundPanel.repaint();
                });
            }
        }
        if (scrollPane2 !=null) {
            searchAndBackGroundPanel.remove(scrollPane2);
            searchAndBackGroundPanel.validate();
            searchAndBackGroundPanel.repaint();
        }
        System.out.println(albumsPanel.getPanels().size());
        scrollPane2 = new JScrollPane(albumsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        searchAndBackGroundPanel.add(scrollPane2, BorderLayout.CENTER);
        searchAndBackGroundPanel.validate();
        searchAndBackGroundPanel.repaint();
    }
    public static void setContentPanel(JPanel panel){
        searchAndBackGroundPanel.remove(scrollPane2);
        scrollPane2 = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        searchAndBackGroundPanel.add(scrollPane2, BorderLayout.CENTER);
        searchAndBackGroundPanel.validate();
        searchAndBackGroundPanel.repaint();
    }
    private void initializeSongs() {
        // get all saved songs from database
        ArrayList<Song> songs = Main.databaseHandler.searchSong("");
        songsQueue = songs;
    }

    public static ArrayList<Song> getSongsQueue() {
        return songsQueue;
    }
    public static MainFrame getInstance(){
        if (mainFrame== null){
            return new MainFrame();
        }else{
            return mainFrame;
        }
    }
    public static ArrayList<Album> getAllAlbums(){
            return Main.databaseHandler.searchAlbum("");
    }
    public static ArrayList<Song> getAllSongs(){
        if (allSongs == null){
            return Main.databaseHandler.searchSong("");
        }else{
            return allSongs;
        }
    }

    public void updatePlaylists(){
        ArrayList<Playlist> playlists = Main.databaseHandler.searchPlaylist("");
        optionsPanel.removePlaylist("s");
        for (Playlist playlist : playlists){
            optionsPanel.addPlaylist(playlist);
        }
        optionsPanel.showPlaylist();
    }



    private void closeResources(){
        playbackManager.getMediaController().release();
        // releasing sockets and save data
        Main.user.setOnline(false);
        Main.user.setLastOnline(new Date().getTime());
        Main.usersHandler.removeUser(Main.user.getUsername());
        Main.usersHandler.addUser(Main.user);
        Main.databaseHandler.close();
        Main.usersHandler.close();
        userClient.sendRequest(new Request(-1, Main.user));
        userClient.closeConnection();
    }
}
