package View;

import org.w3c.dom.html.HTMLObjectElement;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Flow;

public class OptionsPanel extends JPanel {
    JLabel homeLabel = new JLabel("HOME");
    JLabel searchLabel = new JLabel("SEARCH");
    JLabel yourLibraryLabel = new JLabel("YOUR LIBRARY");
    JLabel recentlyPlayedLabel = new JLabel("RECENTLY PLAYED");
    JButton songsButton = new JButton("SONGS");
    JLabel albumsLabel = new JLabel("ALBUMS");
    JLabel artistsLabel = new JLabel("ARTISTS");
    JLabel playListsLabel = new JLabel("PLAYLISTS");
    private ArrayList<JLabel> playlistsArray;

    public OptionsPanel(){
        playlistsArray = new ArrayList<>();
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        URL homeUrl = null;
        URL libraryUrl = null;
        URL playlistUrl = null;
        try {
            File homeFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "home.png");
            homeUrl = homeFile.toURI().toURL();
            File libraryFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "audiobook.png");
            libraryUrl = libraryFile.toURI().toURL();
            File playlistFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "playlist.png");
            playlistUrl = playlistFile.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        songsButton.setOpaque(false);
        songsButton.setContentAreaFilled(false);
        songsButton.setBorderPainted(false);
        songsButton.setMaximumSize(new Dimension(100 , 25));
        songsButton.setToolTipText("songs");

        ImageIcon homeIcon = new ImageIcon(new ImageIcon(homeUrl).getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH));
        ImageIcon libraryIcon = new ImageIcon(new ImageIcon(libraryUrl).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
        ImageIcon playlistIcon = new ImageIcon(new ImageIcon(playlistUrl).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));

        homeLabel.setIcon(homeIcon);
        homeLabel.setText("HOME");
        yourLibraryLabel.setText("YOUR LIBRARY ");
        yourLibraryLabel.setFont(new Font("TimesRoman", Font.BOLD, 12));
        yourLibraryLabel.setIcon(libraryIcon);
        playListsLabel.setIcon(playlistIcon);
        //Adding elements to panel
        add(homeLabel);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));

        add(yourLibraryLabel);
        add(Box.createRigidArea(new Dimension(0,5)));


        add(recentlyPlayedLabel);
        add(Box.createRigidArea(new Dimension(0,5)));

        add(songsButton);
        add(Box.createRigidArea(new Dimension(0,5)));

        add(albumsLabel);
        add(Box.createRigidArea(new Dimension(0,5)));

        add(artistsLabel);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));


        add(playListsLabel);
        add(Box.createRigidArea(new Dimension(0,5)));
    }

    public void addPlaylist(String playlistsName){
        JLabel newPlaylist = new JLabel(playlistsName);
        playlistsArray.add(newPlaylist);
    }

    public void removePlaylist(String playlistsName){
        for(int i=0;i<playlistsArray.size() ;i++){
            if(playlistsArray.get(i).getText().equals( playlistsName)){
                this.remove(playlistsArray.get(i));
                playlistsArray.remove(i);
            }
        }
    }

    public void showPlaylist(){
        for(JLabel x: playlistsArray){
            add(x);
            add(Box.createRigidArea(new Dimension(0,5)));
        }
    }
}