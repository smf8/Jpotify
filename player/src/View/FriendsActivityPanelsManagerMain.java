package View;

import javax.swing.*;
import java.awt.*;

public class FriendsActivityPanelsManagerMain {


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        FriendsActivityPanelsManager friendsActivityPanelsManager = new FriendsActivityPanelsManager();
        friendsActivityPanelsManager.showFriends();
        frame.add(friendsActivityPanelsManager);
        frame.pack();
        frame.setVisible(true);
    }
}
