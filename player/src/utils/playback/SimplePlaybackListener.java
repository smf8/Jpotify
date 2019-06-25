package utils.playback;

import Model.Song;
import View.PlaybackControlPanel;

public class SimplePlaybackListener implements MP3Player.PlaybackListener {
    private PlaybackControlPanel playbackControlPanel;

    public SimplePlaybackListener(PlaybackControlPanel playbackControlPanel){
        this.playbackControlPanel = playbackControlPanel;
    }
    @Override
    public void playbackStarted(MP3Player.PlaybackEvent event) {
        System.out.println("started");
        playbackControlPanel.updateInformation();
        playbackControlPanel.startProgress();

    }

    @Override
    public void playbackPaused(MP3Player.PlaybackEvent event) {
        System.out.println("paused");
        playbackControlPanel.stopProgress();
    }

    @Override
    public void playbackFinished(MP3Player.PlaybackEvent event) {
        System.out.println("finished");
        playbackControlPanel.resetProgress();
    }

    @Override
    public void musicChanged(Song newSong) {
        System.out.println("music changed - " + Thread.activeCount());
        playbackControlPanel.resetProgress();
        playbackControlPanel.logData();
    }
}
