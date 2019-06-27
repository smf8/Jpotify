package View;

import Model.Album;
import Model.Song;
import Model.User;
import utils.FontManager;
import utils.IO.DatabaseConnection;
import utils.IO.DatabaseHelper;
import utils.IO.FileIO;
import utils.IO.MyFileChooser;
import utils.TagReader;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;

public class SignUpPanel extends JPanel {
    private JLabel createAccountLabel = new JLabel();
    private JLabel userNameLabel = new JLabel();
    private JTextField userNameTextField = new JTextField();
    private JLabel passWordLabel = new JLabel();
    private JButton setProfImageButton = new JButton("Set Your Profile Image");
    private JButton signUpButton = new JButton("Sign Up");
    private JPasswordField passField = new JPasswordField();
    private JPanel passPanel = new JPanel();
    private JPanel userNamePanel = new JPanel();
    private URI profileImageURI = URI.create(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "no-profile.png");

    public SignUpPanel() {
        this.setBackground(new Color(22, 22, 22));
        userNamePanel.setLayout(new BorderLayout());
        passPanel.setLayout(new BorderLayout());
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        createAccountLabel.setText("Create New Account");
        createAccountLabel.setAlignmentX(CENTER_ALIGNMENT);
        createAccountLabel.setFont(FontManager.getUbuntuBold(22f));
        createAccountLabel.setForeground(Color.WHITE);
        this.add(createAccountLabel);

        add(Box.createRigidArea(new Dimension(0, 20)));
        // init username
        userNameLabel.setText("Username : ");
        userNameLabel.setForeground(Color.WHITE);
        userNamePanel.add(userNameLabel, BorderLayout.WEST);
        userNamePanel.add(userNameTextField, BorderLayout.CENTER);
        userNamePanel.setBackground(new Color(22, 22, 22));
        userNameTextField.setFont(FontManager.getUbuntu(18f));
        this.setAlignmentX(LEFT_ALIGNMENT);
        this.add(userNamePanel);
        add(Box.createRigidArea(new Dimension(0, 20)));


        passWordLabel.setText("Password : ");
        passWordLabel.setForeground(Color.WHITE);
        passPanel.add(passWordLabel, BorderLayout.WEST);
        passPanel.add(passField, BorderLayout.CENTER);
        passPanel.setBackground(new Color(22, 22, 22));
        passPanel.add(passField, BorderLayout.CENTER);
        add(passPanel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        // profile selection

        setProfImageButton.setAlignmentX(CENTER_ALIGNMENT);
        setProfImageButton.setBackground(new Color(97, 97, 97));
        setProfImageButton.setForeground(Color.WHITE);
        setProfImageButton.addActionListener(actionEvent -> {
            MyFileChooser fileChooser = new MyFileChooser(null, null, true);
            URI profileURI = fileChooser.getImageFile();
            if (profileURI != null) {
                profileImageURI = profileURI;
            }
        });
        add(setProfImageButton);
        add(Box.createRigidArea(new Dimension(0, 5)));

        // sign up button

        signUpButton.setAlignmentX(CENTER_ALIGNMENT);
        signUpButton.setBackground(new Color(97, 97, 97));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.addActionListener(actionEvent -> {
            User user = new User(userNameTextField.getText(), FileIO.MD5(String.valueOf(passField.getPassword())));
            user.setProfileImage(profileImageURI);
            if (!userNameTextField.getText().equals("")) {
//                ArrayList<URI> usersDatabaseFiles = FileIO.findFilesRecursive(new File(FileIO.RESOURCES_RELATIVE));
                if (!Main.usersHandler.addUser(user)) {
                    System.out.println("fai");
                    createAccountLabel.setText("Username taken, choose another !");
                    createAccountLabel.setForeground(Color.RED);
                    this.validate();
                    this.repaint();
                } else {
                    System.out.println("succ");
                    createAccountLabel.setText("Account created successfully");
                    createAccountLabel.setForeground(Color.GREEN);
                    this.validate();
                    this.repaint();
                    Main.user = user;
                    // start the main application
                    // setting the database handler
                    DatabaseConnection connection = new DatabaseConnection(userNameTextField.getText());
                    connection.initMusicsTable();
                    Main.databaseHandler = new DatabaseHelper(connection.getConnection());

                    // check if there isn't any song in database
                    ArrayList<Song> songs = Main.databaseHandler.searchSong("");
                    if (songs.size() <= 0) {
                        MyFileChooser fileChooser = new MyFileChooser(this, null, false);
                        fileChooser.setTitle("Select musics folder");
                        URI dir = fileChooser.getFolderURI();
                        while (dir == null) {
                            fileChooser.setTitle("Mesle bache adam chiz entekhab kon dg");
                            dir = fileChooser.getFolderURI();
                        }
                        ArrayList<URI> mp3sInFolder = FileIO.findMP3Files(FileIO.findFilesRecursive(new File(dir)));
                        TagReader reader = new TagReader();
                        for (URI mp3File : mp3sInFolder) {
                            try {
                                reader.getAdvancedTags(mp3File.toURL());
                                songs.add(reader.getSong());
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        Main.databaseHandler.deepInsertSong(songs);
                        ArrayList<Album> insertedAlbums = Main.databaseHandler.searchAlbum("");
                        for (Album album : insertedAlbums){
                            Main.user.addAlbum(album);
                        }
                        Main.usersHandler.removeUser(user.getUsername());
                        Main.usersHandler.addUser(user);
                    }
                    MainFrame mainFrame = new MainFrame();
                    Main.closeFrame();

                }
            } else {
                createAccountLabel.setText("Invalid input !");
                createAccountLabel.setForeground(Color.RED);
                this.validate();
                this.repaint();
            }
        });
        add(signUpButton);

        add(Box.createRigidArea(new Dimension(0, 20)));
    }

}
