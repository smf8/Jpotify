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
import java.net.URI;

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
        setProfImageButton.setBackground(new Color(97,97,97));
        setProfImageButton.setForeground(Color.WHITE);
        setProfImageButton.addActionListener(actionEvent -> {
            MyFileChooser fileChooser = new MyFileChooser(null, null, true);
            URI profileURI = fileChooser.getImageFile().normalize();
            if (profileURI!=null){
                profileImageURI = profileURI;
            }
        });
        add(setProfImageButton);
        add(Box.createRigidArea(new Dimension(0, 5)));

        // sign up button

        signUpButton.setAlignmentX(CENTER_ALIGNMENT);
        signUpButton.setBackground(new Color(97,97,97));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.addActionListener(actionEvent -> {
            User user = new User(userNameTextField.getText(), FileIO.MD5(String.valueOf(passField.getPassword())));
            user.setProfileImage(profileImageURI);
            if (!userNameTextField.getText().equals("")) {
                if (!Main.databaseHandler.addUser(user)) {
                    createAccountLabel.setText("Username taken, choose another !");
                    createAccountLabel.setForeground(Color.RED);
                    this.validate();
                    this.repaint();
                } else {
                    createAccountLabel.setText("Account created successfully");
                    createAccountLabel.setForeground(Color.GREEN);
                    this.validate();
                    this.repaint();
                    Main.user = user;
                    // start the main application

                    MainFrame mainFrame = new MainFrame();
                    Main.closeFrame();

                }
            }
        });
        add(signUpButton);

        add(Box.createRigidArea(new Dimension(0, 20)));
    }

}
