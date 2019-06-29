package utils.playback;

import Model.Request;
import View.Main;
import View.MainFrame;
import View.PlaybackControlPanel;
import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.MusixMatchException;
import org.jmusixmatch.entity.lyrics.Lyrics;
import org.jmusixmatch.entity.track.Track;
import org.jmusixmatch.entity.track.TrackData;
import uk.co.caprica.vlcj.media.MediaRef;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import utils.FontManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import static View.MainFrame.queueIndex;
import static View.MainFrame.songsQueue;

public class SimplePlaybackListener extends MediaPlayerEventAdapter {

    private boolean initLyrics(String songTitle, String artist){
        boolean result = true;
        org.jmusixmatch.MusixMatch instance = new MusixMatch(PlaybackControlPanel.API_KEY);
        Track track = null;
        try {
            track = instance.getMatchingTrack(songTitle, artist);
        } catch (MusixMatchException ex) {
            result = false;
        }
        if (track != null) {
            TrackData data = track.getTrack();

            int trackID = data.getTrackId();

            Lyrics lyrics = null;
            try {
                lyrics = instance.getLyrics(trackID);
            } catch (MusixMatchException ex) {
                result = false;
            }
            if (lyrics != null) {
                JFrame lyricsFrame = new JFrame(songTitle + " - " + artist);
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBackground(new Color(22, 22, 22));
                JLabel label = new JLabel();
                label.setText("<html>" + lyrics.getLyricsBody().replaceAll("\n", "<br/>") + "</html>");
                System.out.println();
                label.setBackground(new Color(22, 22, 22));
                label.setFont(FontManager.getUbuntu(16f));
                label.setForeground(Color.WHITE);
                label.setBorder(new EmptyBorder(20, 20, 20, 20));
                panel.add(label);
                lyricsFrame.add(panel);
                lyricsFrame.pack();
                lyricsFrame.setVisible(true);
            }
        }
        return result;
    }
    private PlaybackControlPanel playbackControlPanel;

    public SimplePlaybackListener(PlaybackControlPanel playbackControlPanel) {
        this.playbackControlPanel = playbackControlPanel;
    }

    @Override
    public void mediaChanged(MediaPlayer mediaPlayer, MediaRef media) {
        playbackControlPanel.updateInformation();
        playbackControlPanel.resetProgress();
        playbackControlPanel.logData();
    }

    @Override
    public void playing(MediaPlayer mediaPlayer) {
        System.out.println("started");
        playbackControlPanel.updateInformation();
        playbackControlPanel.startProgress();
        Main.user.listened(playbackControlPanel.getPlaybackManager().getCurrentSong(), Main.usersHandler);
        Main.databaseHandler.updateSong(playbackControlPanel.getPlaybackManager().getCurrentSong());
        initLyrics(playbackControlPanel.getPlaybackManager().getCurrentSong().getTitle(),playbackControlPanel.getPlaybackManager().getCurrentSong().getArtist());
        MainFrame.userClient.sendRequest(new Request(0, Main.user));
    }

    @Override
    public void paused(MediaPlayer mediaPlayer) {
        System.out.println("paused");
        playbackControlPanel.stopProgress();
    }

    @Override
    public void stopped(MediaPlayer mediaPlayer) {
        playbackControlPanel.resetProgress();
        Main.user.setCurrentSong(null);
        MainFrame.userClient.sendRequest(new Request(0, Main.user));
    }

    @Override
    public void finished(MediaPlayer mediaPlayer) {
        System.out.println("finished");
        mediaPlayer.submit(() -> playbackControlPanel.getPlaybackManager().next());
    }

    @Override
    public void error(MediaPlayer mediaPlayer) {
        super.error(mediaPlayer);
    }
}
