package video;

import Model.Song;
import Model.Video;
import utils.FontManager;
import utils.IO.FileIO;
import utils.playback.PlaybackManager;
import View.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class VideoPlaybackControlPanel extends JPanel {
    private boolean isPlaying;
    private JPanel buttonsControlPanel = new JPanel();
    private JPanel playProgressPanel = new JPanel();
    private JPanel volumePanel = new JPanel();
    private JPanel tempPanel = new JPanel();
    private JPanel buttunsVolumProgressPanel = new JPanel();
    private JProgressBar videoSlider;
    private JSlider volumeSlider;
    private JLabel playLabel = new JLabel();
    private JLabel pauseLabel = new JLabel();
    private JLabel nextLabel = new JLabel();
    private JLabel previousLabel = new JLabel();
    private JLabel volumeLabel = new JLabel();
    private VideoPlaybackManager playbackManager;
    private long duration;
    private int playState = 0;
    double pos;
    private Thread progressThread;
    private ImageIcon playIcon;
    private ImageIcon pausIcon;


    public VideoPlaybackControlPanel(VideoPlaybackManager playbackManager) {
        this.playbackManager = playbackManager;
        Video currentSong = playbackManager.getCurrentVideo();
        duration = playbackManager.getEmbeddedMediaPlayerComponent().mediaPlayer().media().info().duration();
        System.out.println(duration);
        videoSlider = new JProgressBar(0, (int) duration);
        videoSlider.setStringPainted(true);
        videoSlider.setString("0:0/0:0");
        playbackManager.getEmbeddedMediaPlayerComponent().mediaPlayer().events().addMediaPlayerEventListener(new SimpleVideoPlaybackListener(this));
        isPlaying = false;
        buttonsControlPanel.setLayout(new FlowLayout());
        playProgressPanel.setLayout(new BorderLayout());
        volumePanel.setLayout(new FlowLayout());
        tempPanel.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
        buttunsVolumProgressPanel.setLayout(new BorderLayout());
        setupVideoSlider();
        setupAudioSlider();
        //URLs
        URL playUrl = null;
        URL pausUrl = null;
        URL nextUrl = null;
        URL previousUrl = null;
        URL volumeUrl = null;
        buttonsControlPanel.setBackground(new Color(97, 97, 97));
        volumePanel.setBackground(new Color(97, 97, 97));
        playProgressPanel.setBackground(new Color(97, 97, 97));
        try {
            File playFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "play.png");
            playUrl = playFile.toURI().toURL();
            File pausFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "paus.png");
            pausUrl = pausFile.toURI().toURL();
            File nextFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "next.png");
            nextUrl = nextFile.toURI().toURL();
            File previousFlie = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "previous.png");
            previousUrl = previousFlie.toURI().toURL();
            File volumeFlie = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "volume.png");
            volumeUrl = volumeFlie.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        playIcon = new ImageIcon(new ImageIcon(playUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        pausIcon = new ImageIcon(new ImageIcon(pausUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon nextIcon = new ImageIcon(new ImageIcon(nextUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon previousIcon = new ImageIcon(new ImageIcon(previousUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon volumeIcon = new ImageIcon(new ImageIcon(volumeUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        //Setting Listeners
        playLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isPlaying == false) {
                    // change label image to pause image
                    playbackManager.play();
                    playLabel.setIcon(pausIcon);
                    isPlaying = true;
                } else if (isPlaying == true) {
                    playbackManager.pasue();
                }
            }
        });
        nextLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playbackManager.next();
                playState = 0;
            }
        });
        previousLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playbackManager.prev();
                playState = 0;
            }
        });
        volumeLabel.setIcon(volumeIcon);
        playLabel.setIcon(playIcon);
        pauseLabel.setIcon(pausIcon);
        nextLabel.setIcon(nextIcon);
        previousLabel.setIcon(previousIcon);
        buttonsControlPanel.add(previousLabel);
        buttonsControlPanel.add(playLabel);
        buttonsControlPanel.add(nextLabel);
//        panel1.add(volumeLabel);
//        panel1.add(volumeSlider);
//        playProgressPanel.setBorder(new EmptyBorder(10, 0,15,0));
        playProgressPanel.add(videoSlider, BorderLayout.CENTER);
        volumePanel.add(volumeLabel);
        volumePanel.add(volumeSlider);
        tempPanel.add(buttonsControlPanel, BorderLayout.NORTH);
        playProgressPanel.setSize(new Dimension(0,30));
        tempPanel.setBackground(new Color(97,97,97));
        tempPanel.add(playProgressPanel, BorderLayout.SOUTH);
//        this.setSize(3, 3);
        buttunsVolumProgressPanel.add(tempPanel, BorderLayout.CENTER);
        buttunsVolumProgressPanel.add(volumePanel, BorderLayout.EAST);
        this.add(buttunsVolumProgressPanel,BorderLayout.CENTER);
    }

    public void setupVideoSlider() {

        videoSlider.setValue(0);

        videoSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pos = e.getX() / (double) videoSlider.getWidth();
                playState = (int) (duration * pos);
                playbackManager.move((int) (duration * pos));
                isPlaying = true;
            }
        });
    }
    // set image method

    /**
     * setups audio slider with default 80% audio<br>
     * <b>maximum value can be up to 200</b>
     */
    private void setupAudioSlider() {
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        volumeSlider.setValue(80);
        playbackManager.setVol(80f);
        volumeSlider.addChangeListener(changeEvent -> {
            playbackManager.setVol(((JSlider) changeEvent.getSource()).getValue());
        });
    }

    public void startProgress(){
        progressThread = new Thread(() -> {
            if (!isPlaying){
                playState = 0;
                isPlaying = true;
            }
            int c = 1;
            while (playState <= duration && isPlaying) {
                if (Thread.interrupted()){
                    return;
                }
                if (c == 10){
                    String secS = ((int) ((duration/1000)%60)) + "";
                    if (((int) ((duration/1000)%60)) < 10){
                        secS = 0 + "" + ((int) ((duration/1000)%60));
                    }
                    String len = ((int) ((duration/1000)/60)) + ":" + secS ;
                    int min = (playbackManager.getCurrentSecond()/1000)/60;
                    int sec = (playbackManager.getCurrentSecond()/1000)%60;
                    String secString = "" +sec;
                    if (sec < 10){
                        secString= 0 + "" + sec;
                    }
                    String passed = min+":"+secString+"/"+len;
                    videoSlider.setString(passed);
                    c=0;
                }else{
                    c++;
                }
                videoSlider.setValue(playState);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    return;
                }
                playState += 100;
            }
        });
        progressThread.start();
    }

    public void stopProgress(){
        progressThread.stop();
        progressThread = null;
        playLabel.setIcon(playIcon);
        isPlaying = false;
    }
    public void resetProgress(){
        playState = 0;
        videoSlider.setString("0:0/0:0");
        if (progressThread != null) {
            progressThread.stop();
            progressThread = null;
        }
        Video currentSong = playbackManager.getCurrentVideo();
        duration = currentSong.getLength();
        videoSlider.setMaximum((int) duration);
        setupVideoSlider();
    }
    public void updateInformation(){
        playLabel.setIcon(pausIcon);
        isPlaying = true;
    }
    public void logData(){
        System.out.println(duration);
        System.out.println(playState);
        System.out.println("-------");
    }

    public VideoPlaybackManager getPlaybackManager() {
        return playbackManager;
    }
}


