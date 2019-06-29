package video;

import View.PlaybackControlPanel;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;

public class SimpleVideoPlaybackListener extends MediaPlayerEventAdapter {
    private VideoPlaybackControlPanel playbackControlPanel;

    public SimpleVideoPlaybackListener(VideoPlaybackControlPanel playbackControlPanel) {
        this.playbackControlPanel = playbackControlPanel;
    }


    @Override
    public void playing(MediaPlayer mediaPlayer) {
        playbackControlPanel.updateInformation();
        playbackControlPanel.startProgress();
    }

    @Override
    public void paused(MediaPlayer mediaPlayer) {
        playbackControlPanel.stopProgress();
    }

    @Override
    public void stopped(MediaPlayer mediaPlayer) {
        playbackControlPanel.resetProgress();
    }

    @Override
    public void finished(MediaPlayer mediaPlayer) {
        mediaPlayer.submit(() -> playbackControlPanel.getPlaybackManager().next());
    }
}
