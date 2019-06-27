package View;

import Model.Song;
import Model.User;
import utils.IO.DatabaseConnection;
import utils.IO.DatabaseHandler;
import utils.IO.DatabaseHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class FriendsActivityPanelsManager extends JPanel {
    private ArrayList<FriendsActivityPanel> friendsActivityPanels  = new ArrayList<>();
    private JPanel friendActivityPanel = new JPanel();
    private JLabel friendActivityLabel = new JLabel("             FRIEND ACTIVITY");
    private JButton findFriendsButton = new JButton("FIND FRIENDS");

    public FriendsActivityPanelsManager() {
//        findFriendsButton.setSize(250,50);

        findFriendsButton.setMaximumSize(new Dimension(130,30));
        findFriendsButton.setMinimumSize(new Dimension(130,30));
        findFriendsButton.setPreferredSize(new Dimension(130,30));
        findFriendsButton.setBackground(new Color(22,22,22));
        friendActivityPanel.setLayout(new BorderLayout());
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setBackground(new Color(22,22,22));
        friendActivityPanel.add(friendActivityLabel,BorderLayout.CENTER);
        friendActivityPanel.setPreferredSize(new Dimension(250,10));
        friendActivityPanel.setMinimumSize(new Dimension(250,10));
        friendActivityPanel.setMaximumSize(new Dimension(250,50));
        friendActivityPanel.setBackground(new Color(22,22,22));
        this.add(friendActivityPanel);
        add(Box.createRigidArea(new Dimension(0,20)));
        findFriendsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        findFriendsButton.setForeground(Color.WHITE);
        this.add(findFriendsButton);
        add(Box.createRigidArea(new Dimension(0,10)));
    }

    public void addFriendToPanel(User user){
        FriendsActivityPanel friendsActivityPanel = new FriendsActivityPanel(user);
        friendsActivityPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ProfileFrame profileFrame = new ProfileFrame();
            }
        });
        friendsActivityPanels.add(friendsActivityPanel);
    }
    public void clearFirendsPanel(){

        for(FriendsActivityPanel x: friendsActivityPanels) {
            this.remove(x);
        }
        friendsActivityPanels.clear();
        validate();

    }
    public void showFriends(){
        for(FriendsActivityPanel x: friendsActivityPanels){
            this.add(x);
            add(Box.createRigidArea(new Dimension(0,0)));
        }
        validate();
        repaint();
    }

    /**
     * travles through database to find user's friend's and give them to the manager
     */
    public void updateFriendsList(){
        ArrayList<User> currentFriends = new ArrayList<>();
        ArrayList<User> tmp;
        clearFirendsPanel();
        for (String username : Main.user.getFriends().split(Song.HASH_SEPERATOR)) {
            DatabaseConnection userC = new DatabaseConnection(username);
            // set a handler to read a special user's songs
            DatabaseHandler handler = new DatabaseHelper(userC.getConnection());
            System.out.println(username + "***");
            tmp = Main.usersHandler.getUserByUsername(username, handler);
            currentFriends.add(tmp.get(0));
            addFriendToPanel(tmp.get(0));
            userC.close();
            handler.close();
        }
        showFriends();
    }
}
