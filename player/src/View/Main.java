package View;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JPOTIFY");
        OptionsPanel optionsPanel = new OptionsPanel();
        frame.setContentPane(optionsPanel);
        frame.setVisible(true);
    }


}
