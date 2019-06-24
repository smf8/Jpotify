package View;

import utils.IO.FileIO;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LoginPanel extends JPanel {
    private JLabel welcomeLabel = new JLabel();
    private JLabel userNameLabel = new JLabel();
     private JTextField userNameTextField = new JTextField();
     private JLabel passWordLabel = new JLabel();
     private JPasswordField passField = new JPasswordField();
     private JButton signInButton = new JButton();
     private JButton createAccount = new JButton();
     private JPanel userNamePanel = new JPanel();
     private JPanel passPanel = new JPanel();

     public LoginPanel(){
         userNamePanel.setLayout(new BorderLayout());
         passPanel.setLayout(new BorderLayout());
         this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

         try {
             Font font = Font.createFont(Font.TRUETYPE_FONT, new File(FileIO.RESOURCES_RELATIVE + "font" + File.separator + "Roboto-Regular.ttf"));
             GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
             genv.registerFont(font);
             font = font.deriveFont(12f);
             welcomeLabel.setFont(font);
         } catch (FontFormatException | IOException e) {
             e.printStackTrace();
         }
         welcomeLabel.setText("WELCOME");
         this.add(welcomeLabel);
         add(Box.createRigidArea(new Dimension(0,5)));
         add(Box.createRigidArea(new Dimension(0,5)));
         add(Box.createRigidArea(new Dimension(0,5)));
         add(Box.createRigidArea(new Dimension(0,5)));
         add(Box.createRigidArea(new Dimension(0,5)));

         userNameLabel.setText("UserName");

         userNamePanel.add(userNameLabel,BorderLayout.WEST);
         userNamePanel.add(userNameTextField,BorderLayout.CENTER);
         add(userNamePanel);
         add(Box.createRigidArea(new Dimension(0,5)));
         add(Box.createRigidArea(new Dimension(0,5)));

         passWordLabel.setText("PASSWORD");
         passPanel.add(passWordLabel,BorderLayout.WEST);
         passPanel.add(passField,BorderLayout.CENTER);
     //    passField.setText("PASSWORD");
         add(passPanel);
         add(Box.createRigidArea(new Dimension(0,5)));
         add(Box.createRigidArea(new Dimension(0,5)));

        signInButton.setText("SIGN IN");
        this.add(signInButton);
         add(Box.createRigidArea(new Dimension(0,5)));
         add(Box.createRigidArea(new Dimension(0,5)));


         createAccount.setText("Don't have an account? Create one");
         add(createAccount);
     }
}
