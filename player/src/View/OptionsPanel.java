package View;

import Model.Playlist;
import Model.Song;
import org.w3c.dom.html.HTMLObjectElement;
import utils.IO.FileIO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Flow;

public class OptionsPanel extends JPanel {
    private JLabel profileLabel = new JLabel("Profile");
    private JLabel yourLibraryLabel = new JLabel("Your");
    private JLabel recentlyPlayedLabel = new JLabel("Recently played");
    private JLabel albumsLabel = new JLabel("Albums");
    private JLabel artistsLabel = new JLabel("Liked Songs");
    private JLabel playListsLabel = new JLabel("Playlists");
    private ArrayList<JLabel> playlistsArray;

    public OptionsPanel(){
        this.setBackground(new Color(22,22,22));
        this.setBorder(new EmptyBorder(10,10,0,0));
        playlistsArray = new ArrayList<>();
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        URL homeUrl = null;
        URL libraryUrl = null;
        URL playlistUrl = null;
        try {
            File homeFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "o.png");
            homeUrl = homeFile.toURI().toURL();
            File libraryFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "book.png");
            libraryUrl = libraryFile.toURI().toURL();
            File playlistFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "g.png");
            playlistUrl = playlistFile.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ImageIcon homeIcon = new ImageIcon(new ImageIcon(homeUrl).getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH));
        ImageIcon libraryIcon = new ImageIcon(new ImageIcon(libraryUrl).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
        ImageIcon playlistIcon = new ImageIcon(new ImageIcon(playlistUrl).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));

        profileLabel.setIcon(homeIcon);
        yourLibraryLabel.setText("LIBRARY ");
        yourLibraryLabel.setIcon(libraryIcon);
        playListsLabel.setIcon(playlistIcon);


        //Adding elements to panel
        profileLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ProfileFrame profileFrame = new ProfileFrame();
            }
        });
        add(profileLabel);
        add(Box.createRigidArea(new Dimension(0,5)));

        add(yourLibraryLabel);
        add(Box.createRigidArea(new Dimension(0,10)));
        recentlyPlayedLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SongPanel songPanel = new SongPanel(Main.user.getSongs(), 3, null);
                // setup database listener because users must be able to remove songs from here
                MainFrame.setContentPanel(songPanel);
            }
        });
        add(recentlyPlayedLabel);
        add(Box.createRigidArea(new Dimension(0,5)));
        albumsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.getInstance().setupAlbumsPanel(MainFrame.getAllAlbums());
            }
        });
        add(albumsLabel);
        add(Box.createRigidArea(new Dimension(0,5)));
        artistsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.setContentPanel(new SongPanel(Main.user.getLikedSongs(),3,null));
            }
        });
        add(artistsLabel);
        add(Box.createRigidArea(new Dimension(0,15)));


        add(playListsLabel);
        add(Box.createRigidArea(new Dimension(0,5)));


    }

    public void addPlaylist(Playlist playlist){
        JLabel newPlaylist = new JLabel(playlist.getTitle());
        newPlaylist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Playlist playlistfd = Main.databaseHandler.getPlaylistByID(playlist.getId());
                PlayPanel playPanel = new PlayPanel(playlistfd);
                SongPanel songPanel = new SongPanel(playlistfd.getSongs(), 2,playlistfd);
                playPanel.addSongs(songPanel);
                MainFrame.setContentPanel(playPanel);
             }
        });
        playlistsArray.add(newPlaylist);
    }

    public void removePlaylist(String playlistsName){
        for (JLabel label : playlistsArray){
            remove(label);
        }
        playlistsArray.clear();
        validate();
    }

    public void showPlaylist(){

        for(JLabel x: playlistsArray){
            add(x);
            add(Box.createRigidArea(new Dimension(0,5)));
        }
        validate();
        repaint();
    }
}
