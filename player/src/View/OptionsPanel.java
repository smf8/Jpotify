package View;

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
    JLabel HOME = new JLabel("HOME");
    JLabel Search = new JLabel("SEARCH");
    JLabel yourLibrary = new JLabel("YOUR LIBRARY");
    JLabel recentlyPlayed = new JLabel("RECENTLY PLAYED");
    JLabel songs = new JLabel("SONGS");
    JLabel albums = new JLabel("ALBUMS");
    JLabel artists = new JLabel("ARTISTS");
    JLabel playLists = new JLabel("PLAYLISTS");
    private ArrayList<JLabel> playlistsArray;
    public OptionsPanel(){
        playlistsArray = new ArrayList<>();
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        URL homeUrl = null;
        URL searchUrl = null;
        URL libraryUrl = null;
        URL playlistUrl = null;
        try {
            File homeFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "home.png");
            homeUrl = homeFile.toURI().toURL();
            File libraryFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "audiobook.png");
            libraryUrl = libraryFile.toURI().toURL();
            File searchFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "search.png");
            searchUrl = searchFile.toURI().toURL();
            File playlistFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "playlist.png");
            playlistUrl = playlistFile.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageIcon home = new ImageIcon(new ImageIcon(homeUrl).getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
        ImageIcon search = new ImageIcon(new ImageIcon(searchUrl).getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
        ImageIcon library = new ImageIcon(new ImageIcon(libraryUrl).getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));
        ImageIcon playlist = new ImageIcon(new ImageIcon(playlistUrl).getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));HOME.setIcon(home);
        HOME.setText("HOME");
        HOME.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

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
        add(HOME);
        add(Box.createRigidArea(new Dimension(0,5)));
        Search.setIcon(search);
        Search.setText("SEARCH");
        Search.setFont(new Font("TimesRoman", Font.BOLD, 12));
        Search.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

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
        add(Search);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));
        yourLibrary.setText("YOUR LIBRARY ");
        yourLibrary.setFont(new Font("TimesRoman", Font.BOLD, 12));
        yourLibrary.setIcon(library);
        yourLibrary.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

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
        add(yourLibrary);
        add(Box.createRigidArea(new Dimension(0,5)));
        recentlyPlayed.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

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
        add(recentlyPlayed);
        add(Box.createRigidArea(new Dimension(0,5)));
        songs.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

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
        add(songs);
        add(Box.createRigidArea(new Dimension(0,5)));
        albums.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

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
        add(albums);
        add(Box.createRigidArea(new Dimension(0,5)));
        artists.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

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
        add(artists);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));
        playLists.setIcon(playlist);
        playLists.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

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
        add(playLists);
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
    public void showingPlaylist(){
        for(JLabel x: playlistsArray){
            add(x);
            add(Box.createRigidArea(new Dimension(0,5)));
        }
    }
}
