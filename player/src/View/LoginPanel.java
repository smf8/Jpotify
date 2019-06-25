package View;

import Model.User;
import utils.FontManager;
import utils.IO.FileIO;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
         this.setBackground(new Color(22,22,22));
         this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
         this.setAlignmentX(CENTER_ALIGNMENT);
         welcomeLabel.setText("Welcome");
         welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);
         welcomeLabel.setFont(FontManager.getUbuntuBold(22f));
         welcomeLabel.setForeground(Color.WHITE);
         this.add(welcomeLabel);
         add(Box.createRigidArea(new Dimension(0,25)));

        // init username field
         userNameLabel.setText("Username : ");
         userNamePanel.setLayout(new BorderLayout());
         userNamePanel.add(userNameLabel,BorderLayout.WEST);
         userNamePanel.add(userNameTextField,BorderLayout.CENTER);
         userNameLabel.setForeground(Color.WHITE);
         userNamePanel.setBackground(new Color(22,22,22));
         add(userNamePanel);
         add(Box.createRigidArea(new Dimension(0,15)));
         // init password field

         passPanel.setLayout(new BorderLayout());
         passPanel.add(passWordLabel,BorderLayout.WEST);
         passPanel.add(passField,BorderLayout.CENTER);
         passPanel.setBackground(new Color(22,22,22));
         passWordLabel.setForeground(Color.WHITE);
         passWordLabel.setText("Password : ");
         add(passPanel);
         add(Box.createRigidArea(new Dimension(0,15)));


         // sign in button init
        signInButton.setText("Sign In");
        signInButton.setBackground(new Color(97,97,97));
        signInButton.setForeground(Color.WHITE);

        // what to do for login

        signInButton.addActionListener(actionEvent -> {
            ArrayList<User> users;
            if ((users = Main.databaseHandler.getUserByUsername(userNameTextField.getText())).size() != 0) {
                User user = users.get(0);
                if (user.getPassword().equals(FileIO.MD5(String.valueOf(passField.getPassword())))) {
                    // can login
                    System.out.println("Logged in");
                    Main.user = user;
                }else {
                    welcomeLabel.setText("Try again !");
                    welcomeLabel.setForeground(Color.RED);
                    this.validate();
                    this.repaint();
                }
            }else {
                welcomeLabel.setText("Try again");
                welcomeLabel.setForeground(Color.RED);
                this.validate();
                this.repaint();
            }
        });
        this.add(signInButton);
         add(Box.createRigidArea(new Dimension(0,15)));
         // init sign up button

         createAccount.setText("Don't have an account? Create one");
         createAccount.setBackground(new Color(97,97,97));
         createAccount.setForeground(Color.WHITE);
         // what to do for signing up :
         createAccount.addActionListener(actionEvent -> {
             this.removeAll();
             SignUpPanel signUpPanel = new SignUpPanel();
             this.add(signUpPanel);
             this.validate();
             this.repaint();
         });
         add(createAccount);
     }

}
