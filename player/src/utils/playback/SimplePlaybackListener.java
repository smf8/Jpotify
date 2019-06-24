package utils.playback;

import View.PlaybackControlPanel;

public class SimplePlaybackListener implements MP3Player.PlaybackListener {
    private PlaybackControlPanel playbackControlPanel;

    public SimplePlaybackListener(PlaybackControlPanel playbackControlPanel){
        this.playbackControlPanel = playbackControlPanel;
    }
    @Override
    public void playbackStarted(MP3Player.PlaybackEvent event) {
        playbackControlPanel.updateInformation();
        System.out.println("started");
    }

    @Override
    public void playbackPaused(MP3Player.PlaybackEvent event) {
        System.out.println("paused");
    }

    @Override
    public void playbackFinished(MP3Player.PlaybackEvent event) {
        System.out.println("stopped");
    }
}
