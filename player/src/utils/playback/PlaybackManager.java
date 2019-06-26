package utils.playback;

import Model.Song;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

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
    private MediaPlayerEventListener playbackListener;
    private AudioPlayerComponent mediaController;

    /**
     * constructor with empty queue, use addSong method to add songs to queue
     *
     * @param songsToAdd List of songs
     */
    public PlaybackManager(ArrayList<Song> songsToAdd, AudioPlayerComponent mediaController) {
        if (songsToAdd!= null && songsToAdd.size()>=1) {
            songQueue.addAll(songsToAdd);
        }
        this.mediaController = mediaController;
    }

    private void initPlayer() {
        if (songQueue.size() >= 1) {
//            try {
//                player = new MP3Player(songQueue.get(queueIndex).getLocation().toURL());
//            } catch (JavaLayerException e) {
//                e.printStackTrace();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            player.setPlaybackListener(playbackListener);
        }

    }

    public void shouldRepeat(boolean repeat) {
        mediaController.mediaPlayer().controls().setRepeat(repeat);
    }


    public void next() {
        if (songQueue.size() >= 1) {
            if (queueIndex <= (songQueue.size() - 1))
                queueIndex++;
            else {
//                 resetting queue
                queueIndex = 0;
            }
            System.out.println(mediaController.mediaPlayer().media().play(songQueue.get(queueIndex).getLocation().getPath()));
//            playbackListener.musicChanged(songQueue.get(queueIndex));
//            play();
        }
    }
    public void setPlaybackListener(MediaPlayerEventListener listener){
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
            mediaController.mediaPlayer().media().play(songQueue.get(queueIndex).getLocation().getPath());
//            playbackListener.musicChanged(songQueue.get(queueIndex));
//            play();
        }
    }

    /**
     * method initializes the player if it's not and stop and reinitialize it when not.
     * call MP3Player play method inside a thread
     */
    public void play() {
        if (songQueue.size() >= 1) {
            initPlayer();
                if (!mediaController.mediaPlayer().media().isValid()){
                    System.out.println(songQueue.get(queueIndex).getLocation().getPath());
                    System.out.println(mediaController.mediaPlayer().media().play(songQueue.get(queueIndex).getLocation().getPath()));
                }else{
                    mediaController.mediaPlayer().controls().play();
                }


//            if (player == null) {
//                initPlayer();
//            } else if (!this.player.isPaused() || player.isComplete() || player.isStopped()) {
//                stop();
//                initPlayer();
//            }
//            isPlayerStopped = false;
//            executorService.execute(() -> {
//                try {
//                    playbackListener.playbackStarted(null);
//                    player.resume();
//                } catch (JavaLayerException e) {
//                    e.printStackTrace();
//                }
//            });
        }
    }

    /**
     * calls updates Song queue and calls play()
     *
     * @param songInQueue the song about to be played
     */
    public void play(Song songInQueue) {
        songQueue.add(queueIndex, songInQueue);
        mediaController.mediaPlayer().media().play(songInQueue.getLocation().getPath());
    }

    /**
     * shuffles playlist by stopping the player and shuffling song queue and then playing the player
     */
    public void shuffle() {
        if (songQueue.size() >=1) {
            mediaController.mediaPlayer().controls().stop();
            Collections.shuffle(songQueue);
            queueIndex = 0;
            mediaController.mediaPlayer().media().play(songQueue.get(queueIndex).getLocation().getPath());
        }
    }

    /**
     * call MP3Player stop method and destroys the play thread
     */
    public void stop() {
        if (mediaController.mediaPlayer().status().isPlaying()){
            mediaController.mediaPlayer().controls().stop();
        }
//        isPlayerStopped = true;
//        if (player != null && !player.isStopped()) {
//            playbackThread.interrupt();
//            System.out.println("dsahlkj");
//            playbackListener.playbackFinished(null);
//            player.stop();
//            executorService.shutdown();
//            playbackThread = null;
//        }
    }

    public Song getCurrentSong() {
        return songQueue.get(queueIndex);
    }

    /**
     * pauses the mediaController by invoking MP3Player.pause() method
     */
    public void pause() {
        if (mediaController.mediaPlayer().status().canPause()){
            mediaController.mediaPlayer().controls().pause();
        }
//        isPlayerStopped = true;
//        if (this.player != null && !this.player.isPaused()) {
//            playbackListener.playbackPaused(null);
//            this.player.pause();
//        }
    }

    public int getSec() {
//        return player.getCurrentFrame() * 26;
        return (int) (mediaController.mediaPlayer().status().position()* mediaController.mediaPlayer().status().length());
    }

    /**
     * jumps to the given milisecond of song
     *
     * @param miliseconds - remember that each frame lasts 26 mili seconds. so in order to jump to i'th second we must go to i*60000/26'th frame
     */

    public void move(int miliseconds) {
        if (songQueue.size() >= 1) {
            if (mediaController.mediaPlayer().status().isSeekable()){
                mediaController.mediaPlayer().controls().setTime(miliseconds);
            }
//            if (player == null) {
//                initPlayer();
//            } else if (!this.player.isPaused() || player.isComplete() || player.isStopped()) {
//                stop();
//                initPlayer();
//            }
//            executorService.execute(() -> {
//                try {
//                    playbackListener.playbackStarted(null);
//                    player.play((miliseconds) / 26);
//                } catch (JavaLayerException e) {
//                    e.printStackTrace();
//                }
//            });
        }
    }

    public AudioPlayerComponent getController(){
        return mediaController;
    }
    public void resetQueue(ArrayList<Song> newQueue){
        songQueue = newQueue;
        queueIndex = 0;
    }
    public void changeVolume(float vol) {
        mediaController.mediaPlayer().audio().setVolume((int) vol);
//        if (player != null) {
//            this.player.setVol(vol);
//        }
    }

    public AudioPlayerComponent getMediaController() {
        return mediaController;
    }
}
