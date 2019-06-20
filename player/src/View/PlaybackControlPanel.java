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
    JSlider musicSlider = new JSlider(JSlider.HORIZONTAL,0,30,30);
    JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL,0,30,30);
    JLabel playLabel = new JLabel();
    JLabel pauseLabel = new JLabel();
    JLabel shuffleLabel  = new JLabel();
    JLabel repeatLabel = new JLabel();
    JLabel nextLabel = new JLabel();
    JLabel previousLabel  = new JLabel();
    JLabel volumeLabel = new JLabel();
    public PlaybackControlPanel(){
        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new BorderLayout());
        //panel3.setLayout(new FlowLayout());
        this.setLayout(new BorderLayout());

        musicSlider.setMajorTickSpacing(100);
        musicSlider.setMinorTickSpacing(1);
        musicSlider.setPaintTicks(true);
        musicSlider.setPaintLabels(true);
        //slider.addChangeListener(changeLis);
        panel2.add(musicSlider);
        URL playUrl = null;
        URL pausUrl = null;
        URL nextUrl = null;
        URL previousUrl = null;
        URL shuffleUrl = null;
        URL repeatUrl = null;
        URL volumeUrl = null;
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
            File volumeFlie = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "volume.png");
            volumeUrl = volumeFlie.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageIcon playIcon = new ImageIcon(new ImageIcon(playUrl).getImage().getScaledInstance(30,30,   Image.SCALE_SMOOTH));
        ImageIcon pausIcon = new ImageIcon(new ImageIcon(pausUrl).getImage().getScaledInstance(30,30,   Image.SCALE_SMOOTH));
        ImageIcon shuffleIcon = new ImageIcon(new ImageIcon(shuffleUrl).getImage().getScaledInstance(30,30,   Image.SCALE_SMOOTH));
        ImageIcon repeatIcon = new ImageIcon(new ImageIcon(repeatUrl).getImage().getScaledInstance(30,30,   Image.SCALE_SMOOTH));
        ImageIcon nextIcon = new ImageIcon(new ImageIcon(nextUrl).getImage().getScaledInstance(30,30,   Image.SCALE_SMOOTH));
        ImageIcon previousIcon = new ImageIcon(new ImageIcon(previousUrl).getImage().getScaledInstance(30,30,   Image.SCALE_SMOOTH));
        ImageIcon volumeIcon = new ImageIcon(new ImageIcon(volumeUrl).getImage().getScaledInstance(30,30,   Image.SCALE_SMOOTH));

        volumeLabel.setIcon(volumeIcon);
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
        panel1.add(volumeLabel);
        panel1.add(volumeSlider);
        //panel3.add(volumeLabel);
        //panel3.add(volumeSlider);
        this.setSize(3,3);
        this.add(panel1,BorderLayout.NORTH);
        this.add(panel2,BorderLayout.CENTER);
        this.add(panel3,BorderLayout.EAST);
    }
}
