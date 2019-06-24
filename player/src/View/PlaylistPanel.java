package View;

import javax.swing.*;
import java.awt.*;

public class PlaylistPanel extends JPanel {
    private JTextField titleTextField = new JTextField();
    private JCheckBox permisionCheckBox = new JCheckBox("Public");
    private JButton songButton = new JButton("Choose the songs of your playlist");
    private JButton imageButton = new JButton("Choose the cover of your playlist");
    private JLabel createPlaylist = new JLabel("Create your playlist");
    private JLabel nameLabel = new JLabel("Choose the name of your playlist");
    private JPanel namePanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel songsListPanel = new JPanel();

    public PlaylistPanel() {
        this.setLayout(new BorderLayout());
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        createPlaylist.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(createPlaylist);
        add(Box.createRigidArea(new Dimension(0, 5)));

        titleTextField.setMaximumSize(new Dimension(500, 50));
        titleTextField.setMinimumSize(new Dimension(500, 50));
        titleTextField.setPreferredSize(new Dimension(500, 50));
//        Dimension location = Toolkit.getDefaultToolkit().getScreenSize();
//        titleTextField.setLocation(location.width/2 - this.getWidth()/2,location.height/2 - this.getHeight()/2)
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePanel.add(nameLabel);
        namePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        leftPanel.add(namePanel);
        titleTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePanel.add(titleTextField);
        namePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        leftPanel.add(namePanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));


//        permisionCheckBox.setMaximumSize(new Dimension(100,100));
//        permisionCheckBox.setMinimumSize(new Dimension(100,100));
//        permisionCheckBox.setPreferredSize(new Dimension(100,100));
//        permisionCheckBox.setSize(new Dimension(50,50));
        permisionCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
//        permisionCheckBox.setLocation(1000,200);
        leftPanel.add(permisionCheckBox);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        songButton.setMaximumSize(new Dimension(500, 50));
        songButton.setMinimumSize(new Dimension(500, 50));
        songButton.setPreferredSize(new Dimension(500, 50));
        songButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//        songButton.setBounds(0,0,500,50);
//        Dimension location2 = Toolkit.getDefaultToolkit().getScreenSize();
//        titleTextField.setLocation(location2.width/2 - this.getWidth()/2,location2.height/2 - this.getHeight()/2);

        leftPanel.add(songButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        imageButton.setMaximumSize(new Dimension(500, 50));
        imageButton.setMinimumSize(new Dimension(500, 50));
        imageButton.setPreferredSize(new Dimension(500, 50));
//        imageButton.setBounds(0,0,500,50);
        imageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(imageButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        this.add(leftPanel, BorderLayout.WEST);
        this.add(songsListPanel, BorderLayout.CENTER);
        this.setMaximumSize(new Dimension(1000, 500));
        this.setMinimumSize(new Dimension(1000, 500));
        this.setPreferredSize(new Dimension(1000, 500));
    }
}
