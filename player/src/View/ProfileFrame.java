package View;

import Model.User;
import utils.FontManager;
import utils.IO.FileIO;
import utils.IO.MyFileChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

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
    public ProfileFrame() {
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

        picEditeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    URL url = null;
                    Image image;
                    ImageIcon imageIcon = null;

                    myFileChooser = new MyFileChooser(ProfileFrame.this, null, true);
//                uri =  myFileChooser.getImageFile();
//                TagReader reader = new TagReader();
                    try {
                        url = myFileChooser.getImageFile().toURL();
                        imageIcon = new ImageIcon(url);
                        image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(image);
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    }
                    profPicLabel.setIcon(imageIcon);
                    User curr = Main.user;
                    Main.usersHandler.removeUser(curr.getUsername());
                    try {
                        curr.setProfileImage(url.toURI());
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                    Main.usersHandler.addUser(curr);
                }
        });

        picPanel.add(picEditeButton);
        picPanel.add(Box.createRigidArea(new Dimension(0,10)));

        userName.setAlignmentX(Component.CENTER_ALIGNMENT);
        userName.setFont(FontManager.getUbuntu(30f));
        picPanel.add(userName);
        picPanel.add(Box.createRigidArea(new Dimension(0,5)));

        infAndFriendsPanel.add(infPanel,BorderLayout.NORTH);
        //******table ********
//        infPanel.add();
//        infPanel.add(Box.createRigidArea(new Dimension(0,5)));

        this.add(picPanel,BorderLayout.WEST);
        this.setBackground(new Color(22,22,22));
        this.validate();
        this.repaint();
        this.add(infAndFriendsPanel,BorderLayout.CENTER);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }
}
