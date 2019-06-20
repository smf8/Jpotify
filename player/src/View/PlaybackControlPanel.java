package View;

import javax.swing.*;
import java.awt.*;

public class PlaybackControlPanel extends JPanel{
    JSlider slider = new JSlider(JSlider.HORIZONTAL,15,30,15);
    JLabel play;
    JLabel pause;
    public PlaybackControlPanel(){
        this.setLayout(new BorderLayout());
        add(slider,BorderLayout.CENTER);
        slider.setMajorTickSpacing(100);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);




    }
}
