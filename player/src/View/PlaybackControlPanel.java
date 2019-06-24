package View;

import Model.Song;
import utils.FontManager;
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
    private boolean isLiked = false;
    private PlaybackManager playbackManager;
    private long duration;
    private int playState = 0;

    private Thread progressThread;
    public PlaybackControlPanel(PlaybackManager playbackManager) {
        this.playbackManager = playbackManager;
        playbackManager.setPlaybackListener(new SimplePlaybackListener(this));
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
            File likeFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "like.png");
            likeUrl = likeFile.toURI().toURL();
            File disLikeFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "dislike.png");
            disLikeUrl = disLikeFile.toURI().toURL();
            File playFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "play.png");
            playUrl = playFile.toURI().toURL();
            File pausFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "paus.png");
            pausUrl = pausFile.toURI().toURL();
            File shuffleFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "shuffle.png");
            shuffleUrl = shuffleFile.toURI().toURL();
            File nextFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "next.png");
            nextUrl = nextFile.toURI().toURL();
            File previousFlie = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "previous.png");
            previousUrl = previousFlie.toURI().toURL();
            File volumeFlie = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "volume.png");
            volumeUrl = volumeFlie.toURI().toURL();
            File isNotRepeatingFlie = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "repeat(1).png");
            isNotRepeatingUrl = isNotRepeatingFlie.toURI().toURL();
            File isRepeatingFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "repeat(2).png");
            isRepeatingUrl = isRepeatingFile.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageIcon playIcon = new ImageIcon(new ImageIcon(playUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon pausIcon = new ImageIcon(new ImageIcon(pausUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon shuffleIcon = new ImageIcon(new ImageIcon(shuffleUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon nextIcon = new ImageIcon(new ImageIcon(nextUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon previousIcon = new ImageIcon(new ImageIcon(previousUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon volumeIcon = new ImageIcon(new ImageIcon(volumeUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon isNotRepeatingIcon = new ImageIcon(new ImageIcon(isNotRepeatingUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon isRepeatingIcon = new ImageIcon(new ImageIcon(isRepeatingUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon likeImage = new ImageIcon(new ImageIcon(likeUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon disLikeImage = new ImageIcon(new ImageIcon(disLikeUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        //Setting Listeners
        playLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isPlaying == false) {
                    // change label image to pause image
                    playbackManager.play();
                    playLabel.setIcon(pausIcon);
                    progressThread = new Thread(() -> {
                        while (playState <= duration) {
//                            System.out.println(playState);
//                            System.out.println(duration);
                            musicSlider.setValue(playState);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                break;
                            }
                            playState += 100;
                        }
                    });
                    progressThread.start();
                    isPlaying = true;
                } else if (isPlaying == true) {
                    playbackManager.pause();
                    progressThread.interrupt();
                    progressThread = null;
                    playLabel.setIcon(playIcon);
                    isPlaying = false;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        nextLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playbackManager.next();
                playState = 0;
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        previousLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playbackManager.previous();
                playState = 0;
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        repeatLabel.addMouseListener(new MouseListener() {
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

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        shuffleLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playState = 0;
                playbackManager.shuffle();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        dislikeLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isLiked == false) {
                    dislikeLabel.setIcon(likeImage);
                    isLiked = true;
                } else if (isLiked == true) {
                    dislikeLabel.setIcon(disLikeImage);
                    isLiked = false;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

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
//        buttonsControlPanel.setPreferredSize( new Dimension(80,80));
//        buttonsControlPanel.setMinimumSize(new Dimension(80,80));
//        buttonsControlPanel.setMaximumSize(new Dimension(80,80));

//        musicStringInformationPanel.setPreferredSize( new Dimension(80,80));
//        musicStringInformationPanel.setMinimumSize(new Dimension(80,80));
//        musicStringInformationPanel.setMaximumSize(new Dimension(80,80));
        songImageLabel.setIcon(playIcon);
//        songImageLabel.setPreferredSize( new Dimension(80,80));
//        songImageLabel.setMinimumSize(new Dimension(60,60));
//        songImageLabel.setMaximumSize(new Dimension(60,60));
//        musicImagePanel.add(songImageLabel);
//        musicImagePanel.setBackground(Color.pink);
//        musicImagePanel.setPreferredSize( new Dimension(80,80));
        musicInformation.add(songImageLabel,BorderLayout.WEST);
        musicInformation.add(musicStringInformationPanel,BorderLayout.CENTER);
//        musicInformation.setPreferredSize( new Dimension(80,80));
//        musicInformation.setMinimumSize(new Dimension(80,80));
//        musicInformation.setMaximumSize(new Dimension(80,80));
//        this.setPreferredSize( new Dimension(0,80));
//        this.setMinimumSize(new Dimension(0,80));
//        this.setMaximumSize(new Dimension(0,80));
        this.add(musicInformation,BorderLayout.WEST);
        this.add(buttunsVolumProgressPanel,BorderLayout.CENTER);
    }

    private void setupMusicSlider() {
        System.out.println("init");
        Song currentSong = playbackManager.getCurrentSong();
        duration = currentSong.getLength();
        musicSlider = new JProgressBar(0, (int) duration);
//        musicSlider = new JSlider(JSlider.HORIZONTAL, 0, (int) (duration), 0);
        musicSlider.setValue(0);

        musicSlider.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                double pos = mouseEvent.getX() / (double) musicSlider.getWidth();
                playState = (int) (duration * pos);
                playbackManager.move((int) (duration * pos));
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
    }
    // set image method

    private void setupAudioSlider() {
        volumeSlider = new JSlider(JSlider.HORIZONTAL, -50, 20, 0);
        volumeSlider.setValue(-20);
        volumeSlider.addChangeListener(changeEvent -> {
            playbackManager.changeVolume(((JSlider) changeEvent.getSource()).getValue());
        });
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

        //Updating name
        songNameLabel.setText(playbackManager.getCurrentSong().getTitle());
        //Updating Artist
        songArtistLabel.setText(playbackManager.getCurrentSong().getArtist());
    }

}
