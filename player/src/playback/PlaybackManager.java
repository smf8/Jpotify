package playback;

import Model.Song;
import javazoom.jl.decoder.JavaLayerException;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * class to support play functionality, this class gets a queue of songs and gives play / pause / step / shuffle / repeat functionality
 */
public class PlaybackManager {
    private MP3Player player;
    private ArrayList<Song> songQueue = new ArrayList<>(); // an Arraylist of songs that are playable, switch between them by queueIndex;
    private int queueIndex = 0; // controller of which songs to play
    private Thread playbackThread; // the thread in which audio file will start to play
    private boolean isPlayerStopped;
    private boolean repeat;

    /**
     * constructor with empty queue, use addSong method to add songs to queue
     *
     * @param songsToAdd List of songs
     */
    public PlaybackManager(ArrayList<Song> songsToAdd) {
        songQueue.addAll(songsToAdd);
    }

    private void initPlayer() {
        try {
            player = new MP3Player(songQueue.get(queueIndex).getLocation().toURL());
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        player.setPlaybackListener(new MP3Player.PlaybackListener() {
            @Override
            public void playbackStarted(MP3Player.PlaybackEvent event) {

            }

            @Override
            public void playbackPaused(MP3Player.PlaybackEvent event) {

            }


            // handling what to do when reached the end of file
            @Override
            public void playbackFinished(MP3Player.PlaybackEvent event) {
                    if (!isPlayerStopped){
                        if (repeat){
                            play();
                        }else {
                            queueIndex++;
                            play();
                        }
                    }
            }
        });
    }

    public void shouldRepeat(boolean repeat){
        this.repeat = repeat;
    }
    public void next(){
        queueIndex++;
        play();
    }
    public void previous(){
        queueIndex--;
        play();
    }

    /**
     * method initializes the player if it's not and stop and reinitialize it when not.
     * call MP3Player play method inside a thread
     */
    public void play(){
        if (player == null) {
            initPlayer();
        } else if (!this.player.isPaused() || player.isComplete() || player.isStopped()) {
            stop();
            initPlayer();
        }
        isPlayerStopped = true;
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

    /**
     * calls updates Song queue and calls play()
     * @param songInQueue the song about to be played
     */
    public void play(Song songInQueue) {
        queueIndex = 0;
        songQueue.set(0, songInQueue);
        play();
    }
    public void shuffle(){
        Collections.shuffle(songQueue);
        queueIndex = 0;
//        play();
    }
    /**
     * call MP3Player stop method and destroys the play thread
     */
    public void stop(){
        isPlayerStopped = true;
        if (player != null && !player.isStopped()){
            player.stop();
            playbackThread = null;
        }
    }

    /**
     * pauses the audio by invoking MP3Player.pause() method
     */
    public void pause(){
        isPlayerStopped = true;
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
    public void move(int miliseconds){
        int currentSec = 0;
        if (player == null){
            initPlayer();
        }else if (!this.player.isPaused() || player.isComplete() || player.isStopped()){
            currentSec = player.getCurrentFrame();
            stop();
            initPlayer();
        }
        int finalCurrentSec = currentSec;
        playbackThread = new Thread(() -> {
            try {
                player.play(finalCurrentSec + (miliseconds)/26);
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        });
        playbackThread.setDaemon(true);
        playbackThread.setName("Song playback thread");
        playbackThread.start();
    }
    public void changeVolume(float vol){
        if (player != null){
            this.player.setVol(vol);
        }
    }
}
