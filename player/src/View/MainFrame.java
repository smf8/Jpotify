package View;

import javax.swing.*;

public class MainFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        MainPanel mainPanel = new MainPanel();
        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
