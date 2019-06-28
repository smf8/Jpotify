package utils.playback;

import Model.Request;
import View.Main;
import View.MainFrame;
import View.PlaybackControlPanel;
import uk.co.caprica.vlcj.media.MediaRef;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;

import static View.MainFrame.queueIndex;
import static View.MainFrame.songsQueue;

public class SimplePlaybackListener extends MediaPlayerEventAdapter {
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
