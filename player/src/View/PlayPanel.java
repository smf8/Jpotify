package View;

import Model.Album;
import Model.Playlist;
import Model.Song;
import utils.FontManager;
import utils.IO.FileIO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class PlayPanel extends JPanel{
    private JLabel picLabel = new JLabel();
    private JLabel nameLabel = new JLabel();
    private JLabel creatorLabel= new JLabel();
    private JLabel listSizeLabel = new JLabel();
    private JPanel picPanel = new JPanel();
    private JPanel stringInfPanel = new JPanel();
    private JPanel mainInfPanel = new JPanel();

    public PlayPanel(Album album){
        mainInfPanel.setLayout(new BorderLayout());
        stringInfPanel.setLayout(new BoxLayout(stringInfPanel,BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());
        URL homeUrl;
        File homeFile = new File(album.getImageURI());
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
        picLabel.setIcon(homeIconn);
        picLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        picPanel.add(picLabel);
        nameLabel.setText(album.getTitle());
        nameLabel.setFont(FontManager.getUbuntuBold(25f));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        stringInfPanel.add(Box.createRigidArea(new Dimension(0,70)));

        stringInfPanel.add(nameLabel);
        stringInfPanel.add(Box.createRigidArea(new Dimension(0,5)));

        creatorLabel.setText(album.getArtist());
        creatorLabel.setFont(FontManager.getUbuntu(20f));
        creatorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        stringInfPanel.add(creatorLabel);
        stringInfPanel.add(Box.createRigidArea(new Dimension(0,5)));

        listSizeLabel.setText(album.getSongs().size()+"");
        listSizeLabel.setFont(FontManager.getUbuntuLight(15f));
        listSizeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        stringInfPanel.add(listSizeLabel);
        stringInfPanel.add(Box.createRigidArea(new Dimension(0,5)));

        stringInfPanel.setBackground(new Color(22,22,22));
        stringInfPanel.setBorder(new EmptyBorder(30,30,0,0));
        mainInfPanel.add(picPanel,BorderLayout.WEST);
        mainInfPanel.add(stringInfPanel,BorderLayout.CENTER);
        this.add(mainInfPanel, BorderLayout.NORTH);

    }
    public PlayPanel(Playlist playlist){
        mainInfPanel.setLayout(new BorderLayout());
        stringInfPanel.setLayout(new BoxLayout(stringInfPanel,BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());
        URL homeUrl;
        File homeFile = new File(playlist.getImageURI());
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
        picLabel.setIcon(homeIconn);
        picLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        picPanel.add(picLabel);
        nameLabel.setText(playlist.getTitle());
        nameLabel.setFont(FontManager.getUbuntuBold(25f));

        stringInfPanel.add(nameLabel);
        stringInfPanel.setBackground(new Color(22,22,22));
        stringInfPanel.setBorder(new EmptyBorder(30,30,0,0));
        creatorLabel.setText(playlist.getCreator());
        creatorLabel.setFont(FontManager.getUbuntu(20f));
        stringInfPanel.add(creatorLabel);

        listSizeLabel.setText(playlist.getSongs().size()+"");

        listSizeLabel.setFont(FontManager.getUbuntuLight(15f));
        stringInfPanel.add(listSizeLabel);

        mainInfPanel.add(picPanel,BorderLayout.WEST);
        mainInfPanel.add(stringInfPanel,BorderLayout.CENTER);
        this.add(mainInfPanel,BorderLayout.NORTH);

    }
    public void addSongs(SongPanel songPanel){
        this.add(songPanel,BorderLayout.CENTER);
    }


}
