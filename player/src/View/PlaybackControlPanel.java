package View;

import Model.Song;
import utils.playback.PlaybackManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class PlaybackControlPanel extends JPanel {
    private JPanel buttonsControlPanel = new JPanel();
    private JPanel playProgressPanel = new JPanel();
    private JPanel volumePanel = new JPanel();
    private JPanel tempPanel = new JPanel();
    private JProgressBar musicSlider;
    private JSlider volumeSlider;
    private JLabel playLabel = new JLabel();
    private JLabel pauseLabel = new JLabel();
    private JLabel shuffleLabel = new JLabel();
    private JLabel repeatLabel = new JLabel();
    private JLabel nextLabel = new JLabel();
    private JLabel previousLabel = new JLabel();
    private JLabel volumeLabel = new JLabel();
    private PlaybackManager playbackManager;
    private int playState = 0;
    static class musicChangeListener implements ChangeListener{
        boolean isAuto = true;
        @Override
        public void stateChanged(ChangeEvent changeEvent) {

        }
    }
    private void setupMusicSlider(){
        Song currentSong = playbackManager.getCurrentSong();
        long duration = currentSong.getLength();
        musicSlider = new JProgressBar(0, (int) duration);
//        musicSlider = new JSlider(JSlider.HORIZONTAL, 0, (int) (duration), 0);
        musicSlider.setValue(0);
        playbackManager.play();
        musicSlider.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                double pos = mouseEvent.getX()/(double) musicSlider.getWidth();
                playState = (int) (duration*pos);
                playbackManager.move((int) (duration*pos));
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
        new Thread(() -> {
            while(playState<=duration){
                musicSlider.setValue(playState);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                playState+=100;
            }
        }).start();
    }
    private void setupAudioSlider(){
        volumeSlider = new JSlider(JSlider.HORIZONTAL, -50, 20, 0);
        volumeSlider.setValue(-20);
        volumeSlider.addChangeListener(changeEvent -> {
            playbackManager.changeVolume(((JSlider)changeEvent.getSource()).getValue());
        });
    }
    public PlaybackControlPanel(PlaybackManager playbackManager) {
        this.playbackManager = playbackManager;
        buttonsControlPanel.setLayout(new FlowLayout());
        playProgressPanel.setLayout(new BorderLayout());
        volumePanel.setLayout(new FlowLayout());
        tempPanel.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
        setupMusicSlider();
        setupAudioSlider();
        playProgressPanel.add(musicSlider);
        URL playUrl = null;
        URL pausUrl = null;
        URL nextUrl = null;
        URL previousUrl = null;
        URL shuffleUrl = null;
        URL repeatUrl = null;
        URL volumeUrl = null;
        try {
            File playFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "play.png");
            playUrl = playFile.toURI().toURL();
            File pausFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "paus.png");
            pausUrl = pausFile.toURI().toURL();
            File shuffleFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "shuffle.png");
            shuffleUrl = shuffleFile.toURI().toURL();
            File repeatFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "repeat.png");
            repeatUrl = repeatFile.toURI().toURL();
            File nextFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "next.png");
            nextUrl = nextFile.toURI().toURL();
            File previousFlie = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "previous.png");
            previousUrl = previousFlie.toURI().toURL();
            File volumeFlie = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "volume.png");
            volumeUrl = volumeFlie.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageIcon playIcon = new ImageIcon(new ImageIcon(playUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon pausIcon = new ImageIcon(new ImageIcon(pausUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon shuffleIcon = new ImageIcon(new ImageIcon(shuffleUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon repeatIcon = new ImageIcon(new ImageIcon(repeatUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon nextIcon = new ImageIcon(new ImageIcon(nextUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon previousIcon = new ImageIcon(new ImageIcon(previousUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon volumeIcon = new ImageIcon(new ImageIcon(volumeUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

        volumeLabel.setIcon(volumeIcon);
        playLabel.setIcon(playIcon);
        pauseLabel.setIcon(pausIcon);
        shuffleLabel.setIcon(shuffleIcon);
        repeatLabel.setIcon(repeatIcon);
        nextLabel.setIcon(nextIcon);
        previousLabel.setIcon(previousIcon);
        buttonsControlPanel.add(shuffleLabel);
        buttonsControlPanel.add(previousLabel);
        buttonsControlPanel.add(playLabel);
        buttonsControlPanel.add(nextLabel);
        buttonsControlPanel.add(repeatLabel);
//        panel1.add(volumeLabel);
//        panel1.add(volumeSlider);
        playProgressPanel.add(musicSlider, BorderLayout.CENTER);
        volumePanel.add(volumeLabel);
        volumePanel.add(volumeSlider);
        tempPanel.add(buttonsControlPanel, BorderLayout.NORTH);
        tempPanel.add(playProgressPanel, BorderLayout.CENTER);
        this.setSize(3, 3);
        this.add(tempPanel, BorderLayout.CENTER);
        this.add(volumePanel, BorderLayout.EAST);
    }
}
