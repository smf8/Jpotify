package View;

import utils.IO.FileIO;
import utils.IO.MyFileChooser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ProfileFrame extends JFrame {
    private JLabel profPicLabel = new JLabel();
//    private JLabel nameLabel = new JLabel();
    //    private JLabel
    private JPanel picPanel = new JPanel();
    private JPanel infAndFriendsPanel = new JPanel();
    private JPanel infPanel = new JPanel();
    private JLabel userName = new JLabel("username : niknm");
    //    private JPanel
    private JButton picEditeButton = new JButton("Change pic");
    public ProfileFrame() {
        infPanel.setLayout(new BoxLayout(infPanel,BoxLayout.Y_AXIS));
        picPanel.setLayout(new BoxLayout(picPanel,BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());
        profPicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        URL url;
        Image image;
        ImageIcon imageIcon = null;
        try {
            File homeFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "o.png");
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
        picPanel.add(picEditeButton);
        picPanel.add(Box.createRigidArea(new Dimension(0,15)));

        userName.setAlignmentX(Component.CENTER_ALIGNMENT);
        picPanel.add(userName);
        picPanel.add(Box.createRigidArea(new Dimension(0,5)));

        infAndFriendsPanel.add(infPanel,BorderLayout.NORTH);
        //******table ********
//        infPanel.add();
//        infPanel.add(Box.createRigidArea(new Dimension(0,5)));

        this.add(picPanel,BorderLayout.WEST);
        this.add(infAndFriendsPanel,BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }
}
