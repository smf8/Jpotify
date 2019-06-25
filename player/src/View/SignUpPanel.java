package View;

import utils.IO.FileIO;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SignUpPanel extends JPanel{
    private JLabel createAccountLabel = new JLabel();
    private JLabel userNameLabel = new JLabel();
    private JTextField userNameTextField = new JTextField();
    private JLabel passWordLabel = new JLabel();
    private JButton setProfImageButton = new JButton("Set Your Profile Image");
    private JButton signUpButton = new JButton("Sign Up");
    private JPasswordField passField = new JPasswordField();
    private JPanel passPanel = new JPanel();
    private JPanel userNamePanel = new JPanel();
    private Font font;
    public SignUpPanel(){
        userNamePanel.setLayout(new BorderLayout());
        passPanel.setLayout(new BorderLayout());
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        try {
             font = Font.createFont(Font.TRUETYPE_FONT, new File(FileIO.RESOURCES_RELATIVE + "font" + File.separator + "Roboto-Regular.ttf"));
            GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            genv.registerFont(font);
            font = font.deriveFont(20f);
            createAccountLabel.setFont(font);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        createAccountLabel.setText("Create New Account");
        this.add(createAccountLabel);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));

        userNameLabel.setText("USERNAME");
        userNameTextField.setFont(font);
        userNamePanel.add(userNameLabel,BorderLayout.WEST);
        userNamePanel.add(userNameTextField,BorderLayout.CENTER);
        userNamePanel.setMaximumSize(new Dimension(500,40));
        userNamePanel.setMinimumSize(new Dimension(500,40));
        userNamePanel.setPreferredSize(new Dimension(500,40));


        this.add(userNamePanel);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));



        passWordLabel.setText("PASSWORD");
        passPanel.add(passWordLabel,BorderLayout.WEST);

        passPanel.add(passField,BorderLayout.CENTER);
        passPanel.setMaximumSize(new Dimension(500,40));
        passPanel.setMinimumSize(new Dimension(500,40));
        passPanel.setPreferredSize(new Dimension(500,40));
        passPanel.add(passField,BorderLayout.CENTER);
        add(passPanel);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));

        add(setProfImageButton);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));

        add(signUpButton);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(Box.createRigidArea(new Dimension(0,5)));
    }

}
