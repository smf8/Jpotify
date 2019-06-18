package playback;

import javazoom.jl.decoder.JavaLayerException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

public class TestPlayer {
    private MP3Player player;
    private Thread playbackThread;
    private URL currentFileUrl;
    private PlaybackListener playbackListener = new PlaybackListener();
    public TestPlayer(URL fileUrl){
        this.currentFileUrl = fileUrl;
    }
    private void initPlayer(){
        try {
            player = new MP3Player(currentFileUrl);
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
        player.setPlaybackListener(playbackListener);
    }
    public void play() throws JavaLayerException {
        if (player == null){
            initPlayer();
        }else if (!this.player.isPaused() || player.isComplete() || player.isStopped()){
            stop();
            initPlayer();
        }
        playbackThread = new Thread(() -> {
            try {
                player.resume();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        });
        playbackThread.setDaemon(true);
        playbackThread.setName("Song playback thread");
        playbackThread.start();
    }
    public void stop(){
        if (this.player != null && !this.player.isStopped()) {
            this.player.stop();
            playbackThread = null;
        }
    }
    public void pause(){
        if (this.player != null && !this.player.isPaused()){
            this.player.pause();

            if(this.playbackThread != null){
                this.playbackThread = null;
            }
        }
    }

    /**
     * jumps to the given milisecond of song
     * @param miliseconds - remember that each frame lasts 26 mili seconds. so in order to jump to i'th second we must go to i*60000/26'th frame
     */
    public void play(int miliseconds){
        if (player == null){
            initPlayer();
        }else if (!this.player.isPaused() || player.isComplete() || player.isStopped()){
            stop();
            initPlayer();
        }
        playbackThread = new Thread(() -> {
            try {
                player.play(miliseconds/26);
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        });
        playbackThread.setDaemon(true);
        playbackThread.setName("Song playback thread");
        playbackThread.start();
    }

    public static void main(String[] args) throws MalformedURLException, JavaLayerException {
        Scanner sc = new Scanner(System.in);
        int doo;
        File file = new File("player/src/resources/test");
        String[] musics = file.list();
        Path filePath = FileSystems.getDefault().getPath("player" + File.separator + "src" + File.separator + "resources" + File.separator + "test").normalize().toAbsolutePath().resolve(musics[2]);
        TestPlayer testPlayer = new TestPlayer(filePath.toUri().toURL());
        while((doo = sc.nextInt()) != -1){
            if (doo == 1){
                testPlayer.play();
            }else if (doo == 2){
            }
            testPlayer.player.setVol(doo);
        }
    }
    static class PlaybackListener extends MP3Player.PlaybackAdapter {
        // PlaybackListener members
        @Override
        public void playbackStarted(MP3Player.PlaybackEvent playbackEvent){
            System.err.println("PlaybackStarted()");
        }

        @Override
        public void playbackPaused(MP3Player.PlaybackEvent playbackEvent){
            System.err.println("PlaybackPaused()");
        }

        @Override
        public void playbackFinished(MP3Player.PlaybackEvent playbackEvent){
            System.err.println("PlaybackStopped()");
        }
    }
}
