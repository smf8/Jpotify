package View;

import Model.Song;
import org.w3c.dom.html.HTMLObjectElement;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class SongPanel extends JPanel {
    private JLabel imageLabel = new JLabel();
    private JLabel songsName = new JLabel();
    private JLabel songsArtist = new JLabel();
    private JLabel songsAlbum = new JLabel();
    public SongPanel(String path) {
        this.setBackground(new Color(22,22,22));
        URL homeUrl;
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        File homeFile = new File(path);
        Image homeImage;
        ImageIcon homeIcon = null;
        try{
            homeUrl = homeFile.toURI().toURL();
             homeIcon = new ImageIcon(homeUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

         homeImage = homeIcon.getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH);
        ImageIcon homeIconn = new ImageIcon(homeImage);
        imageLabel.setIcon(homeIconn);
        this.add(imageLabel,BorderLayout.CENTER);
//        backGroundPanel.setMinimumSize(new Dimension(100,100));
//        backGroundPanel.setMaximumSize(new Dimension(100,100));
//        backGroundPanel.setPreferredSize(new Dimension(100,100));
        imageLabel.repaint();
        imageLabel.revalidate();
        songsName.setText("wegaaaaa");
        songsArtist.setText("Aeffw");
        songsAlbum.setText("Aweffffffff");
        this.add(imageLabel);
        add(Box.createRigidArea(new Dimension(0,5)));

        this.add(songsName);
        add(Box.createRigidArea(new Dimension(0,5)));

        this.add(songsArtist);
        add(Box.createRigidArea(new Dimension(0,5)));

        this.add(songsAlbum);
        add(Box.createRigidArea(new Dimension(0,5)));

    }
}
