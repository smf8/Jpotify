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
    private ArrayList<Song> songQueue = new ArrayList<>();
    private int queueIndex = 0;
    private Thread playbackThread;

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
    }
//    public void toggle(){
//    }
    public void next(){
        queueIndex++;
        play();
    }
    public void previous(){
        queueIndex--;
        play();
    }
    public void play(){
        if (player == null) {
            initPlayer();
        } else if (!this.player.isPaused() || player.isComplete() || player.isStopped()) {
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
    public long getCurrentPosition(){
        if (player != null){
            return player.getCurrentFrame()*26;
        }else
            return 0;
    }
    public void stop(){
        if (player != null && !player.isStopped()){
            player.stop();
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
