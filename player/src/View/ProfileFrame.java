package View;

import Model.Playlist;
import Model.User;
import utils.FontManager;
import utils.IO.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class ProfileFrame extends JFrame {
    private JLabel profPicLabel = new JLabel();
//    private JLabel nameLabel = new JLabel();
    //    private JLabel
    private JPanel picPanel = new JPanel();
    private JPanel infAndFriendsPanel = new JPanel();
    private JPanel infPanel = new JPanel();
    private JLabel userName = new JLabel(Main.user.getUsername());
    //    private JPanel
    private JButton picEditeButton = new JButton("Change pic");
    private MyFileChooser myFileChooser;
    public ProfileFrame(){
        infPanel.setLayout(new BoxLayout(infPanel,BoxLayout.Y_AXIS));
        picPanel.setLayout(new BoxLayout(picPanel,BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());
        profPicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//Setting Background
        picPanel.setBackground(new Color(22,22,22));
        picPanel.validate();
        picPanel.repaint();

        infPanel.setBackground(new Color(22,22,22));
        infPanel.validate();
        infPanel.repaint();

        URL url;
        Image image;
        ImageIcon imageIcon = null;
        try {
            File homeFile = new File(Main.user.getProfileImage());
            url = homeFile.toURI().toURL();
            imageIcon = new ImageIcon(url);
            image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        profPicLabel.setIcon(imageIcon);
        picPanel.add(profPicLabel);
        picPanel.add(Box.createRigidArea(new Dimension(0,5)));

        picEditeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        picEditeButton.addActionListener(e -> {
            URL url1 = null;
            Image image1;
            ImageIcon imageIcon1 = null;

            myFileChooser = new MyFileChooser(ProfileFrame.this, null, true);
//                uri =  myFileChooser.getImageFile();
//                TagReader reader = new TagReader();
            try {
                url1 = myFileChooser.getImageFile().toURL();
                imageIcon1 = new ImageIcon(url1);
                image1 = imageIcon1.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                imageIcon1 = new ImageIcon(image1);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
            profPicLabel.setIcon(imageIcon1);
            User curr = Main.user;
            Main.usersHandler.removeUser(curr.getUsername());
            try {
                curr.setProfileImage(url1.toURI());
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
            Main.usersHandler.addUser(curr);
        });
            picPanel.add(picEditeButton);
        picPanel.add(Box.createRigidArea(new Dimension(0,10)));

        userName.setAlignmentX(Component.CENTER_ALIGNMENT);
        userName.setFont(FontManager.getUbuntu(30f));
        picPanel.add(userName);
        picPanel.add(Box.createRigidArea(new Dimension(0,5)));

        infAndFriendsPanel.add(infPanel,BorderLayout.NORTH);

        this.add(picPanel,BorderLayout.WEST);
        this.setBackground(new Color(22,22,22));
        this.validate();
        this.repaint();
        this.add(infAndFriendsPanel,BorderLayout.CENTER);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }


    /**
     * constructor to load a friend profile panel
     * @param friend
     */
    public ProfileFrame(User friend){
        DatabaseConnection connection = new DatabaseConnection(friend.getUsername());
        DatabaseHandler handler = new DatabaseHelper(connection.getConnection());
        friend = Main.usersHandler.getUserByUsername(friend.getUsername(), handler).get(0);
        handler.close();
        connection.close();
        picPanel.setLayout(new BoxLayout(picPanel,BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());
        profPicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//Setting Background
        picPanel.setBackground(new Color(22,22,22));
        picPanel.validate();
        picPanel.repaint();

        URL url;
        Image image;
        ImageIcon imageIcon = null;
        try {
            File homeFile = new File(friend.getProfileImage());
            url = homeFile.toURI().toURL();
            imageIcon = new ImageIcon(url);
            image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        profPicLabel.setIcon(imageIcon);
        picPanel.add(profPicLabel);
        picPanel.add(Box.createRigidArea(new Dimension(0,5)));

        // download song button
        if (friend.getSongs().size()>=1 && friend.isOnline() && !MainFrame.getAllSongs().contains(friend.getCurrentSong())){
            JButton loadButton = new JButton("Load " + friend.getSongs().get(0).getTitle() + " from Friend");
            loadButton.setBackground(new Color(22,22,22));
            loadButton.setForeground(Color.WHITE);
            loadButton.setFont(FontManager.getUbuntu(16f));
            loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            User finalFriend = friend;
            loadButton.addActionListener(actionEvent -> MainFrame.userClient.receiveFileRequest(finalFriend));
            picPanel.add(loadButton);
        }

        // init load playlist panel
        JButton friendPlaylist = new JButton("Show playlist");
        friendPlaylist.setBackground(new Color(22,22,22));
        friendPlaylist.setForeground(Color.WHITE);
        friendPlaylist.setFont(FontManager.getUbuntu(16f));
        friendPlaylist.setAlignmentX(Component.CENTER_ALIGNMENT);
            // check if there exists a public playlist
            ArrayList<Playlist> publicPlaylists = new ArrayList<>();
            for (Playlist playlist : friend.getPlaylists()){
                if (playlist.isPublic()) {
                    publicPlaylists.add(playlist);
                }
            }
            friendPlaylist.addActionListener(actionEvent -> {
                AlbumsPanel albumsPanel = new AlbumsPanel();
                for (Playlist p : publicPlaylists){
                    albumsPanel.addAlbum(p);
                }
                albumsPanel.showAlbums();
                MainFrame.setContentPanel(albumsPanel);
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            });
            if (publicPlaylists.size()>=1){
                picPanel.add(friendPlaylist);
            }


        userName.setAlignmentX(Component.CENTER_ALIGNMENT);
        userName.setFont(FontManager.getUbuntu(30f));
        picPanel.add(userName);
        picPanel.add(Box.createRigidArea(new Dimension(0,5)));

        infAndFriendsPanel.add(picPanel,BorderLayout.NORTH);

        this.add(infAndFriendsPanel,BorderLayout.CENTER);
        this.setBackground(new Color(22,22,22));
        this.validate();
        this.repaint();
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }
}
