package View;

import javax.swing.*;
import java.util.jar.JarFile;

public class Main2 {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        PlaybackControlPanel playbackControlPanel = new PlaybackControlPanel();
        frame.add(playbackControlPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
