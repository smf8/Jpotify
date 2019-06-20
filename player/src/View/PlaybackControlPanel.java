package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class PlaybackControlPanel extends JPanel{
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JSlider slider = new JSlider(JSlider.HORIZONTAL,0,30,30);
    JLabel playLabel = new JLabel();
    JLabel pauseLabel = new JLabel();
    JLabel shuffleLabel  = new JLabel();
    JLabel repeatLabel = new JLabel();
    JLabel nextLabel = new JLabel();
    JLabel previousLabel  = new JLabel();
    public PlaybackControlPanel(){
        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new BorderLayout());
        panel3.setLayout(new FlowLayout());
        this.setLayout(new BorderLayout());

        slider.setMajorTickSpacing(100);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        //slider.addChangeListener(changeLis);
        panel2.add(slider);
        URL playUrl = null;
        URL pausUrl = null;
        URL nextUrl = null;
        URL previousUrl = null;
        URL shuffleUrl = null;
        URL repeatUrl = null;
        try{
            File playFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "play.png");
            playUrl =playFile.toURI().toURL();
            File pausFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "paus.png");
            pausUrl =pausFile.toURI().toURL();
            File shuffleFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "shuffle.png");
            shuffleUrl =shuffleFile.toURI().toURL();
            File repeatFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "repeat.png");
            repeatUrl =repeatFile.toURI().toURL();
            File nextFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "next.png");
            nextUrl =nextFile.toURI().toURL();
            File previousFlie = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "previous.png");
            previousUrl =previousFlie.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageIcon playIcon = new ImageIcon(new ImageIcon(playUrl).getImage().getScaledInstance(30,30,   Image.SCALE_DEFAULT));
        ImageIcon pausIcon = new ImageIcon(new ImageIcon(pausUrl).getImage().getScaledInstance(30,30,   Image.SCALE_DEFAULT));
        ImageIcon shuffleIcon = new ImageIcon(new ImageIcon(shuffleUrl).getImage().getScaledInstance(30,30,   Image.SCALE_DEFAULT));
        ImageIcon repeatIcon = new ImageIcon(new ImageIcon(repeatUrl).getImage().getScaledInstance(30,30,   Image.SCALE_DEFAULT));
        ImageIcon nextIcon = new ImageIcon(new ImageIcon(nextUrl).getImage().getScaledInstance(30,30,   Image.SCALE_DEFAULT));
        ImageIcon previousIcon = new ImageIcon(new ImageIcon(previousUrl).getImage().getScaledInstance(30,30,   Image.SCALE_DEFAULT));

        playLabel.setIcon(playIcon);
        pauseLabel.setIcon(pausIcon);
        shuffleLabel.setIcon(shuffleIcon);
        repeatLabel.setIcon(repeatIcon);
        nextLabel.setIcon(nextIcon);
        previousLabel.setIcon(previousIcon);
        panel1.add(shuffleLabel);
        panel1.add(previousLabel);
        panel1.add(playLabel);
        panel1.add(nextLabel);
        panel1.add(repeatLabel);
        this.setSize(3,3);
        this.add(panel1,BorderLayout.NORTH);
        this.add(panel2,BorderLayout.CENTER);
    }
}
