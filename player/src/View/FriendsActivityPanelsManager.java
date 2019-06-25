package View;

import Model.Song;

import javax.swing.*;
import java.awt.*;
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
        this.add(findFriendsButton);
        add(Box.createRigidArea(new Dimension(0,10)));
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
            add(Box.createRigidArea(new Dimension(0,0)));
        }
    }
}
