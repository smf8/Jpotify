package View;

import org.w3c.dom.html.HTMLObjectElement;

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
    private JLabel homeLabel = new JLabel("PROFILE");
    private JLabel searchLabel = new JLabel("SEARCH");
    private JLabel yourLibraryLabel = new JLabel("YOUR LIBRARY");
    private JLabel recentlyPlayedLabel = new JLabel("RECENTLY PLAYED");
    private JLabel albumsLabel = new JLabel("ALBUMS");
    private JLabel artistsLabel = new JLabel("ARTISTS");
    private JLabel playListsLabel = new JLabel("PLAYLISTS");
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
            File homeFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "o.png");
            homeUrl = homeFile.toURI().toURL();
            File libraryFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "book.png");
            libraryUrl = libraryFile.toURI().toURL();
            File playlistFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "g.png");
            playlistUrl = playlistFile.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ImageIcon homeIcon = new ImageIcon(new ImageIcon(homeUrl).getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH));
        ImageIcon libraryIcon = new ImageIcon(new ImageIcon(libraryUrl).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
        ImageIcon playlistIcon = new ImageIcon(new ImageIcon(playlistUrl).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));

        homeLabel.setIcon(homeIcon);
        yourLibraryLabel.setText("LIBRARY ");
        yourLibraryLabel.setIcon(libraryIcon);
        playListsLabel.setIcon(playlistIcon);


        //Adding elements to panel
        add(homeLabel);
        add(Box.createRigidArea(new Dimension(0,5)));

        add(yourLibraryLabel);
        add(Box.createRigidArea(new Dimension(0,10)));

        add(recentlyPlayedLabel);
        add(Box.createRigidArea(new Dimension(0,5)));

        add(albumsLabel);
        add(Box.createRigidArea(new Dimension(0,5)));

        add(artistsLabel);
        add(Box.createRigidArea(new Dimension(0,15)));


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
