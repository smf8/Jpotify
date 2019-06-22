package View;

import Model.Song;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class FriendsActivityPanelsManager extends JPanel {
    private ArrayList<FriendsActivityPanel> friendsActivityPanels  = new ArrayList<>();
    private JLabel friendActivityLabel = new JLabel("FRIEND ACTIVITY");
    private JButton findFriendsButton = new JButton("FIND FRIENDS");

    public FriendsActivityPanelsManager() {
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(friendActivityLabel);
        add(Box.createRigidArea(new Dimension(0,5)));
        this.add(findFriendsButton);
        add(Box.createRigidArea(new Dimension(0,5)));
        FriendsActivityPanel friendsActivityPanel = new FriendsActivityPanel("salam");
        friendsActivityPanels.add(friendsActivityPanel);
        FriendsActivityPanel friendsActivityPanel1 = new FriendsActivityPanel("khodefezzzz");
        friendsActivityPanels.add(friendsActivityPanel1);
    }
//    public void addFriend(String user){
//        FriendsActivityPanel friendsActivityPanel  = new FriendsActivityPanel(user);
//        friendsActivityPanels.add(friendsActivityPanel);
//    }
//    public void removeFriend()
    public void showFriends(){
        for(FriendsActivityPanel x: friendsActivityPanels){
            this.add(x);
            add(Box.createRigidArea(new Dimension(0,5)));
        }
    }
}
