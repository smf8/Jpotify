package View;

import Model.Song;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;
import utils.FontManager;
import utils.IO.FileIO;
import utils.playback.PlaybackManager;
import utils.playback.SimplePlaybackListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlaybackControlPanel extends JPanel {
    private boolean isPlaying;
    private boolean isRepeating;
    private JPanel buttonsControlPanel = new JPanel();
    private JPanel playProgressPanel = new JPanel();
    private JPanel volumePanel = new JPanel();
    private JPanel tempPanel = new JPanel();
    private JPanel buttunsVolumProgressPanel = new JPanel();
    private JPanel musicInformation = new JPanel();
    private JPanel musicStringInformationPanel = new JPanel();
    private JProgressBar musicSlider;
    private JSlider volumeSlider;
    private JLabel playLabel = new JLabel();
    private JLabel pauseLabel = new JLabel();
    private JLabel shuffleLabel = new JLabel();
    private JLabel repeatLabel = new JLabel();
    private JLabel nextLabel = new JLabel();
    private JLabel previousLabel = new JLabel();
    private JLabel volumeLabel = new JLabel();
    private JLabel dislikeLabel = new JLabel();
    private JLabel songImageLabel = new JLabel();
    private JLabel songNameLabel = new JLabel();
    private JLabel songArtistLabel = new JLabel();
    private ImageIcon likeImage;
    private ImageIcon disLikeImage;
    private boolean isLiked = false;
    private PlaybackManager playbackManager;
    private long duration;
    private int playState = 0;
    double pos;
    private Thread progressThread;

    private ImageIcon playIcon;
    private ImageIcon pausIcon;


    public PlaybackControlPanel(PlaybackManager playbackManager) {
        this.playbackManager = playbackManager;
        Song currentSong = playbackManager.getCurrentSong();
        duration = currentSong.getLength();
        musicSlider = new JProgressBar(0, (int) duration);
        playbackManager.getMediaController().mediaPlayer().events().addMediaPlayerEventListener(new SimplePlaybackListener(this));
        musicStringInformationPanel.setLayout(new BoxLayout(musicStringInformationPanel,BoxLayout.Y_AXIS));
        musicInformation.setLayout(new BorderLayout());
        isPlaying = false;
        isRepeating = false;
        buttonsControlPanel.setLayout(new FlowLayout());
        playProgressPanel.setLayout(new BorderLayout());
        volumePanel.setLayout(new FlowLayout());
        tempPanel.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
        buttunsVolumProgressPanel.setLayout(new BorderLayout());
        setupMusicSlider();
        setupAudioSlider();
        //URLs
        URL likeUrl = null;
        URL disLikeUrl = null;
        URL playUrl = null;
        URL pausUrl = null;
        URL nextUrl = null;
        URL previousUrl = null;
        URL shuffleUrl = null;
        URL repeatUrl = null;
        URL volumeUrl = null;
        URL isNotRepeatingUrl = null;
        URL isRepeatingUrl = null;
        buttonsControlPanel.setBackground(new Color(97, 97, 97));
        volumePanel.setBackground(new Color(97, 97, 97));
        playProgressPanel.setBackground(new Color(97, 97, 97));
        try {
            File likeFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "like.png");
            likeUrl = likeFile.toURI().toURL();
            File disLikeFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "dislike.png");
            disLikeUrl = disLikeFile.toURI().toURL();
            File playFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "play.png");
            playUrl = playFile.toURI().toURL();
            File pausFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "paus.png");
            pausUrl = pausFile.toURI().toURL();
            File shuffleFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "shuffle.png");
            shuffleUrl = shuffleFile.toURI().toURL();
            File nextFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "next.png");
            nextUrl = nextFile.toURI().toURL();
            File previousFlie = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "previous.png");
            previousUrl = previousFlie.toURI().toURL();
            File volumeFlie = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "volume.png");
            volumeUrl = volumeFlie.toURI().toURL();
            File isNotRepeatingFlie = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "repeat(1).png");
            isNotRepeatingUrl = isNotRepeatingFlie.toURI().toURL();
            File isRepeatingFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "repeat(2).png");
            isRepeatingUrl = isRepeatingFile.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        playIcon = new ImageIcon(new ImageIcon(playUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        pausIcon = new ImageIcon(new ImageIcon(pausUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon shuffleIcon = new ImageIcon(new ImageIcon(shuffleUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon nextIcon = new ImageIcon(new ImageIcon(nextUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon previousIcon = new ImageIcon(new ImageIcon(previousUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon volumeIcon = new ImageIcon(new ImageIcon(volumeUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon isNotRepeatingIcon = new ImageIcon(new ImageIcon(isNotRepeatingUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon isRepeatingIcon = new ImageIcon(new ImageIcon(isRepeatingUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        likeImage = new ImageIcon(new ImageIcon(likeUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        disLikeImage = new ImageIcon(new ImageIcon(disLikeUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
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
                    playbackManager.pause();
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
                playbackManager.previous();
                playState = 0;
            }
        });
        repeatLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //       playbackManager.
                if (isRepeating == false) {
                    repeatLabel.setIcon(isRepeatingIcon);
                    playbackManager.shouldRepeat(true);
                    isRepeating = true;
                } else if (isRepeating == true) {
                    repeatLabel.setIcon(isNotRepeatingIcon);
                    playbackManager.shouldRepeat(false);
                    isRepeating = false;
                }
            }
        });
        shuffleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playState = 0;
                playbackManager.shuffle();
            }
        });
        dislikeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isLiked) {
                    dislikeLabel.setIcon(likeImage);
                    isLiked = true;
                    Main.user.likeSong(playbackManager.getCurrentSong());
                    Main.usersHandler.removeUser(Main.user.getUsername());
                    Main.usersHandler.addUser(Main.user);
                } else{
                    dislikeLabel.setIcon(disLikeImage);
                    isLiked = false;
                    Main.user.dislikeSong(playbackManager.getCurrentSong());
                    Main.usersHandler.removeUser(Main.user.getUsername());
                    Main.usersHandler.addUser(Main.user);
                }
            }
        });
        volumeLabel.setIcon(volumeIcon);
        playLabel.setIcon(playIcon);
        pauseLabel.setIcon(pausIcon);
        shuffleLabel.setIcon(shuffleIcon);
        repeatLabel.setIcon(isNotRepeatingIcon);
        nextLabel.setIcon(nextIcon);
        dislikeLabel.setIcon(disLikeImage);
        previousLabel.setIcon(previousIcon);
        buttonsControlPanel.add(dislikeLabel);
        buttonsControlPanel.add(shuffleLabel);
        buttonsControlPanel.add(previousLabel);
        buttonsControlPanel.add(playLabel);
        buttonsControlPanel.add(nextLabel);
        buttonsControlPanel.add(repeatLabel);
//        panel1.add(volumeLabel);
//        panel1.add(volumeSlider);
//        playProgressPanel.setBorder(new EmptyBorder(10, 0,15,0));
        playProgressPanel.add(musicSlider, BorderLayout.CENTER);
        volumePanel.add(volumeLabel);
        volumePanel.add(volumeSlider);
        tempPanel.add(buttonsControlPanel, BorderLayout.NORTH);
        playProgressPanel.setSize(new Dimension(0,30));
        tempPanel.setBackground(new Color(97,97,97));
        tempPanel.add(playProgressPanel, BorderLayout.SOUTH);
//        this.setSize(3, 3);
        buttunsVolumProgressPanel.add(tempPanel, BorderLayout.CENTER);
        buttunsVolumProgressPanel.add(volumePanel, BorderLayout.EAST);
        songNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        musicStringInformationPanel.add(songNameLabel);
        musicStringInformationPanel.add(Box.createRigidArea(new Dimension(0,0)));
        songArtistLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        musicStringInformationPanel.setFont(FontManager.getUbuntu(8f));
//        musicStringInformationPanel.repaint();
        songNameLabel.setFont(FontManager.getUbuntuBold(16f));
        songArtistLabel.setFont(FontManager.getUbuntu(16f));
        musicStringInformationPanel.add(songArtistLabel);
        musicStringInformationPanel.add(Box.createRigidArea(new Dimension(0,0)));
        musicStringInformationPanel.setBackground(new Color(97,97,97));
        songImageLabel.setBackground(new Color(97,97,97));
        musicInformation.add(songImageLabel,BorderLayout.WEST);
        musicInformation.add(musicStringInformationPanel,BorderLayout.CENTER);
        this.add(musicInformation,BorderLayout.WEST);
        this.add(buttunsVolumProgressPanel,BorderLayout.CENTER);
    }

    public void setupMusicSlider() {

        musicSlider.setValue(0);

        musicSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pos = e.getX() / (double) musicSlider.getWidth();
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
        playbackManager.changeVolume(80f);
        volumeSlider.addChangeListener(changeEvent -> {
            playbackManager.changeVolume(((JSlider) changeEvent.getSource()).getValue());
        });
    }

    public void startProgress(){
        progressThread = new Thread(() -> {
            if (!isPlaying){
                playState = 0;
                isPlaying = true;
            }
            while (playState <= duration && isPlaying) {
                if (Thread.interrupted()){
                    return;
                }
//                System.out.println(Thread.currentThread().getName());
//                            System.out.println(playState);
//                            System.out.println(duration);
                musicSlider.setValue(playState);
//                System.out.println(playState);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
//                    System.out.println("Progress Stopped");
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
            if (progressThread != null) {
                progressThread.stop();
                progressThread = null;
            }
            Song currentSong = playbackManager.getCurrentSong();
            duration = currentSong.getLength();
            musicSlider.setMaximum((int) duration);
            setupMusicSlider();
    }
    public void updateInformation(){
       //Updating artWork
        URL songArtWorkUrl;
        Image songArtWorkImage;
        ImageIcon songArtWorkIcon = null;
        try{
            songArtWorkUrl = playbackManager.getCurrentSong().getArtWork().toURL();
            songArtWorkIcon = new ImageIcon(songArtWorkUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        songArtWorkImage = songArtWorkIcon.getImage().getScaledInstance(60,60,Image.SCALE_SMOOTH);
        ImageIcon homeIconn = new ImageIcon(songArtWorkImage);
        songImageLabel.setIcon(homeIconn);
        if (Main.user.getLikedSongs().contains(playbackManager.getCurrentSong())){
            System.out.println(playbackManager.getCurrentSong().getTitle());
            isLiked = true;
            dislikeLabel.setIcon(likeImage);
            this.validate();
            this.repaint();
        }else{
            isLiked = false;
            dislikeLabel.setIcon(disLikeImage);
            this.validate();
            this.repaint();
        }
        //Updating name
        songNameLabel.setText(playbackManager.getCurrentSong().getTitle());
        //Updating Artist
        songArtistLabel.setText(playbackManager.getCurrentSong().getArtist());

        playLabel.setIcon(pausIcon);
        isPlaying = true;
    }
    public void logData(){
//        System.out.println(duration);
//        System.out.println(playState);
//        System.out.println("-------");
    }

    public PlaybackManager getPlaybackManager() {
        return playbackManager;
    }
}
