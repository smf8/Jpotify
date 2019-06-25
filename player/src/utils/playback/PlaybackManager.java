package utils.playback;

import Model.Song;
import javazoom.jl.decoder.JavaLayerException;

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
    private MP3Player.PlaybackListener playbackListener;


    /**
     * constructor with empty queue, use addSong method to add songs to queue
     *
     * @param songsToAdd List of songs
     */
    public PlaybackManager(ArrayList<Song> songsToAdd) {
        if (songsToAdd!= null && songsToAdd.size()>=1) {
            songQueue.addAll(songsToAdd);
        }
    }

    private void initPlayer() {
        if (songQueue.size() >= 1) {
            try {
                player = new MP3Player(songQueue.get(queueIndex).getLocation().toURL());
            } catch (JavaLayerException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            player.setPlaybackListener(playbackListener);
//        if (!isPlayerStopped) {
//            if (repeat) {
//                play();
//            } else {
//                queueIndex++;
//                play();
//            }
//        }
        }
    }

    public void shouldRepeat(boolean repeat) {
        this.repeat = repeat;
    }


    public void next() {
        if (songQueue.size() >= 1) {
            if (queueIndex <= (songQueue.size() - 1))
                queueIndex++;
            else {
                // resetting queue
                queueIndex = 0;
            }
            playbackListener.musicChanged(songQueue.get(queueIndex));
            play();
        }
    }
    public void setPlaybackListener(MP3Player.PlaybackListener listener){
        this.playbackListener = listener;
    }

    /**
     * plays previous song if queue size is greater than 1
     */
    public void previous() {
        if (songQueue.size() >= 1) {
            if (queueIndex >= 1) {
                queueIndex--;
            } else {
                queueIndex = 0;
            }
            playbackListener.musicChanged(songQueue.get(queueIndex));
            play();
        }
    }

    /**
     * method initializes the player if it's not and stop and reinitialize it when not.
     * call MP3Player play method inside a thread
     */
    public void play() {
        if (songQueue.size() >= 1) {
            if (player == null) {
                initPlayer();
            } else if (!this.player.isPaused() || player.isComplete() || player.isStopped()) {
                stop();
                initPlayer();
            }
            isPlayerStopped = false;
            executorService.execute(() -> {
                try {
                    player.resume();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * calls updates Song queue and calls play()
     *
     * @param songInQueue the song about to be played
     */
    public void play(Song songInQueue) {
        songQueue.add(queueIndex, songInQueue);
        playbackListener.musicChanged(songInQueue);
        play();
    }

    /**
     * shuffles playlist by stopping the player and shuffling song queue and then playing the player
     */
    public void shuffle() {
        if (songQueue.size() >=1) {
            stop();
            Collections.shuffle(songQueue);
            queueIndex = 0;
            initPlayer();
            play();
        }
    }

    /**
     * call MP3Player stop method and destroys the play thread
     */
    public void stop() {
        isPlayerStopped = true;
        if (player != null && !player.isStopped()) {
//            playbackThread.interrupt();
//            System.out.println("dsahlkj");
            playbackListener.playbackPaused(null);
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
        if (songQueue.size() >= 1) {
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
    }
    public void resetQueue(ArrayList<Song> newQueue){
        songQueue = newQueue;
        queueIndex = 0;
    }
    public void changeVolume(float vol) {
        if (player != null) {
            this.player.setVol(vol);
        }
    }


}
