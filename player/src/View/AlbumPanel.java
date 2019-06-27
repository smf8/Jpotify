package View;

import Model.Album;
import Model.Song;
import utils.FontManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AlbumPanel extends JPanel {
    private JLabel imageLabel = new JLabel();
    private JLabel albumsName = new JLabel();
    private JLabel albumsArtist = new JLabel();
    private Album album;
    private ViewUpdateListener listener = null;

    public void setViewUpdateListener( ViewUpdateListener listener){
        this.listener = listener;
    }
    //    private JLabel songsAlbum = new JLabel();
    public AlbumPanel(Album album) {
        this.album = album;
        this.setBackground(new Color(22, 22, 22));
        URL homeUrl;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        File homeFile = new File(album.getImageURI().getPath());
        Image homeImage;
        ImageIcon homeIcon = null;
        try {
            homeUrl = homeFile.toURI().toURL();
            homeIcon = new ImageIcon(homeUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        homeImage = homeIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon homeIconn = new ImageIcon(homeImage);
        imageLabel.setIcon(homeIconn);
        this.add(imageLabel, BorderLayout.CENTER);
//        backGroundPanel.setMinimumSize(new Dimension(100,100));
//        backGroundPanel.setMaximumSize(new Dimension(100,100));
//        backGroundPanel.setPreferredSize(new Dimension(100,100));
        imageLabel.repaint();
        imageLabel.revalidate();
        if (album.getTitle().length() > 30)
            albumsName.setText(album.getTitle().substring(0, 27) + " ...");
        else
            albumsName.setText(album.getTitle());
        albumsName.setForeground(Color.WHITE);
        albumsName.setFont(FontManager.getUbuntuBold(13f));
        if (album.getArtist().length() > 30)
            albumsArtist.setText(album.getArtist().substring(0, 27) + " ...");
        else
            albumsArtist.setText(album.getArtist());
        albumsArtist.setForeground(Color.WHITE);
        albumsArtist.setFont(FontManager.getUbuntu(13f));
        this.add(imageLabel);
        add(Box.createRigidArea(new Dimension(0, 5)));

        this.add(albumsName);
        add(Box.createRigidArea(new Dimension(0, 5)));

        this.add(albumsArtist);
        add(Box.createRigidArea(new Dimension(0, 5)));

//        this.add(songsAlbum);
//        add(Box.createRigidArea(new Dimension(0,5)));
    this.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            ArrayList<Song> songs =album.getSongs();
            SongPanel songPanel = new SongPanel(songs);
            PlayPanel playPanel = new PlayPanel(album);
            playPanel.addSongs(songPanel);
            listener.update(null , playPanel);
        }
    });
    }

    public Album getAlbum() {
        return album;
    }
}
