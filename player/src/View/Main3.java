package View;

import javax.swing.*;
import java.awt.*;

public class Main3 {


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        FriendsActivityPanelsManager friendsActivityPanelsManager = new FriendsActivityPanelsManager();
        friendsActivityPanelsManager.showFriends();
        frame.add(friendsActivityPanelsManager);
        frame.pack();
        frame.setVisible(true);
    }
}
