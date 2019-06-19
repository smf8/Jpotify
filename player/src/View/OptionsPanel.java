package View;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
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
    public OptionsPanel(){
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        URL homeUrl = null;
        URL searchUrl = null;
        URL libraryUrl = null;
        try {
            File homeFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "home.png");
            homeUrl = homeFile.toURI().toURL();
            File libraryFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "audiobook.png");
            libraryUrl = libraryFile.toURI().toURL();
            File searchFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "search.png");
            searchUrl = searchFile.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageIcon home = new ImageIcon(new ImageIcon(homeUrl).getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
        HOME.setIcon(home);
        HOME.setText("HOME");
        add(HOME);
        add(Box.createRigidArea(new Dimension(0,5)));
        ImageIcon search = new ImageIcon(new ImageIcon(searchUrl).getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
        ImageIcon library = new ImageIcon(new ImageIcon(libraryUrl).getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));
        Search.setIcon(search);
        Search.setText("SEARCH");
        Search.setFont(new Font("TimesRoman", Font.BOLD, 12));
        add(Search);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));
        yourLibrary.setText("YOUR LIBRARY ");
        yourLibrary.setFont(new Font("TimesRoman", Font.BOLD, 12));
        yourLibrary.setIcon(library);
        add(yourLibrary);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(recentlyPlayed);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(songs);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(albums);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(artists);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));
        add(playLists);
        add(Box.createRigidArea(new Dimension(0,5)));
    }
}
