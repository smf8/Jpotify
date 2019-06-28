package View;

import Model.User;
import utils.FontManager;
import utils.IO.DatabaseConnection;
import utils.IO.DatabaseHandler;
import utils.IO.DatabaseHelper;
import utils.IO.FileIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class FindFriendPanel extends JFrame {

    public FindFriendPanel(FriendsActivityPanelsManager.AddFriendListener listener){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(22,22,22));
        JTextField usernameField = new JTextField("Username");
        usernameField.setPreferredSize(new Dimension(300, 50));
        usernameField.setFont(FontManager.getUbuntu(16f));
        usernameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                usernameField.setText("");
            }
        });
        usernameField.setBackground(new Color(22,22,22));
        usernameField.setForeground(Color.WHITE);
        usernameField.setAlignmentX(CENTER_ALIGNMENT);
        JButton button = new JButton("Add Friend");
        button.setForeground(Color.WHITE);
        button.setFont(FontManager.getUbuntuBold(15f));
        button.setBackground(new Color(22,22,22));
        button.setPreferredSize(new Dimension(300,40));

        JLabel resultLabel = new JLabel();
        resultLabel.setAlignmentX(CENTER_ALIGNMENT);
        resultLabel.setFont(FontManager.getUbuntu(14f));
        resultLabel.setForeground(Color.WHITE);

        button.addActionListener(actionEvent -> {
            if (FileIO.checkIfFileExists(FileIO.RESOURCES_RELATIVE + usernameField.getText() + ".db")){
                // found user
                resultLabel.setText("Found user, adding it");
                DatabaseConnection connection = new DatabaseConnection(usernameField.getText());
                DatabaseHandler handler = new DatabaseHelper(connection.getConnection());
                User friend = Main.usersHandler.getUserByUsername(usernameField.getText(), handler).get(0);
                listener.addFriend(friend);
                FindFriendPanel.this.dispatchEvent(new WindowEvent(FindFriendPanel.this, WindowEvent.WINDOW_CLOSING));
            }else{
                resultLabel.setText("User not found, try again.");
            }
        });
        panel.add(usernameField);
        panel.add(button);
        panel.add(resultLabel);
        this.add(panel);
        setResizable(false);
        pack();
        setVisible(true);
    }
}
