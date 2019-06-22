package utils.playback;

import Model.Song;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * class to support play functionality, this class gets a queue of songs and gives play / pause / step / shuffle / repeat functionality
 */
public class PlaybackManager {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    private MP3Player player;
    private ArrayList<Song> songQueue = new ArrayList<>(); // an Arraylist of songs that are playable, switch between them by queueIndex;
    private int queueIndex = 0; // controller of which songs to play
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
                if (!isPlayerStopped) {
                    if (repeat) {
                        play();
                    } else {
                        queueIndex++;
                        play();
                    }
                }
            }
        });
    }

    public void shouldRepeat(boolean repeat) {
        this.repeat = repeat;
    }


    public void next() {
        if (queueIndex <= (songQueue.size()-1))
        queueIndex++;
        else{
            // resetting queue
            queueIndex = 0;
        }
        play();
    }

    public void previous() {
        if (queueIndex >= 1) {
            queueIndex--;
        }else{
            queueIndex = 0;
        }
        play();
    }

    /**
     * method initializes the player if it's not and stop and reinitialize it when not.
     * call MP3Player play method inside a thread
     */
    public void play() {
        if (player == null) {
            initPlayer();
        } else if (!this.player.isPaused() || player.isComplete() || player.isStopped()) {
            stop();
            initPlayer();
        }
        isPlayerStopped = false;
//        playbackThread = new Thread(() -> {
//            try {
//                player.resume();
//            } catch (JavaLayerException e) {
//                e.printStackTrace();
//            }
//        });
//        playbackThread.setDaemon(true);
//        playbackThread.setName("Song utils.playback thread");
//        playbackThread.start();
        executorService.execute(() -> {
            try {
                player.resume();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * calls updates Song queue and calls play()
     *
     * @param songInQueue the song about to be played
     */
    public void play(Song songInQueue) {
        songQueue.add(queueIndex, songInQueue);
        play();
    }

    public void shuffle() {
        Collections.shuffle(songQueue);
        queueIndex = 0;
//        play();
    }

    /**
     * call MP3Player stop method and destroys the play thread
     */
    public void stop() {
        isPlayerStopped = true;
        if (player != null && !player.isStopped()) {
//            playbackThread.interrupt();
//            System.out.println("dsahlkj");
            player.stop();
//            executorService.shutdown();
//            playbackThread = null;
        }
    }

    public Song getCurrentSong() {
        return songQueue.get(queueIndex);
    }

    /**
     * pauses the audio by invoking MP3Player.pause() method
     */
    public void pause() {
        isPlayerStopped = true;
        if (this.player != null && !this.player.isPaused()) {
            this.player.pause();
        }
    }

    public int getSec() {
        return player.getCurrentFrame() * 26;
    }

    /**
     * jumps to the given milisecond of song
     *
     * @param miliseconds - remember that each frame lasts 26 mili seconds. so in order to jump to i'th second we must go to i*60000/26'th frame
     */

    public void move(int miliseconds) {
        if (player == null) {
            initPlayer();
        } else if (!this.player.isPaused() || player.isComplete() || player.isStopped()) {
            stop();
            initPlayer();
        }
        executorService.execute(() -> {
            try {
                player.play((miliseconds) / 26);
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        });
    }

    public void changeVolume(float vol) {
        if (player != null) {
            this.player.setVol(vol);
        }
    }


}
