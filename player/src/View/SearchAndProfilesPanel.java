package View;

import Model.Album;
import Model.Playlist;
import Model.Song;
import utils.IO.DatabaseAlterListener;
import utils.IO.FileIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static View.Main.databaseHandler;

public class SearchAndProfilesPanel extends JPanel {
    private JTextField searchTextField = new JTextField();
    private JLabel searhLabel = new JLabel();
    private JPanel searchPanel = new JPanel();
    private ImageIcon search = null;

    ArrayList<Album> resultAlbum;
    ArrayList<Song> resultSong;
    ArrayList<Playlist> resultPlaylist;

    public SearchAndProfilesPanel() {
        searchPanel.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
        searchTextField.setText("Search");
        searchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //yadet bashe ke click kard oon search avaliye pak beshe
            }
        });
        URL searchUrl = null;
        try {

            File searchFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "z.png");
            searchUrl = searchFile.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        search = new ImageIcon(new ImageIcon(searchUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        searhLabel.setIcon(search);
        searchPanel.add(searhLabel, BorderLayout.WEST);

        searchTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                searchTextField.setText("");
            }
        });
        searchPanel.add(searchTextField, BorderLayout.CENTER);
        searchPanel.setBackground(new Color(22,22,22));

        searchTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchTextField.setText("");
            }
        });
        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (searchTextField.getText().length() >= 3){
                    new Thread(()->{
                        resultAlbum = databaseHandler.searchAlbum(searchTextField.getText());
                        resultSong = databaseHandler.searchSong(searchTextField.getText());
                        resultPlaylist = databaseHandler.searchPlaylist(searchTextField.getText());
                        MainFrame.setContentPanel(setupSearchResult());
                    }).start();
                }
            }
        });
        this.setBackground(new Color(22,22,22));
        this.add(searchPanel, BorderLayout.CENTER);
    }

    private JPanel setupSearchResult(){
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        AlbumsPanel albumsPanel = new AlbumsPanel();
        for (Album m : resultAlbum){
            albumsPanel.addAlbum(m);
        }
        albumsPanel.showAlbums();

        for (AlbumPanel p : albumsPanel.getPanels()) {
            p.setViewUpdateListener((parent, child) -> {
                MainFrame.setContentPanel(child);
            });
        }

        albumsPanel.setAlignmentX(CENTER_ALIGNMENT);
        container.add(albumsPanel);
        SongPanel songs = new SongPanel(resultSong,3,null);
        songs.setDatabaseAlterListener(new DatabaseAlterListener() {
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
                    databaseHandler.deepInsertSong(s);
                }).start();
            }
        });
        container.add(songs);
        return container;
    }

}