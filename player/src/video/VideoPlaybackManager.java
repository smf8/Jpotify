package video;

import Model.Song;
import Model.Video;
import uk.co.caprica.vlcj.media.Meta;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import java.io.File;
import java.util.ArrayList;

public class VideoPlaybackManager {
    private EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent;
    private ArrayList<Video> videoQueue;
    private int queueIndex;

    public VideoPlaybackManager(ArrayList<Video> queue, EmbeddedMediaPlayerComponent mediaPlayerComponent) {
        this.embeddedMediaPlayerComponent = mediaPlayerComponent;
        videoQueue = queue;
    }

    public void play() {

        if (videoQueue.size() >= 1) {
            if (!embeddedMediaPlayerComponent.mediaPlayer().media().isValid()){
                embeddedMediaPlayerComponent.mediaPlayer().media().prepare(new File(videoQueue.get(queueIndex).getLocation()).getAbsolutePath());
            }else{
                embeddedMediaPlayerComponent.mediaPlayer().controls().play();
            }
        }
    }
    public void play(Video videoInQueue) {
        if (videoQueue.contains(videoInQueue)){
            queueIndex = videoQueue.indexOf(videoInQueue);
            embeddedMediaPlayerComponent.mediaPlayer().media().play(new File(videoInQueue.getLocation()).getAbsolutePath());
        }else{
            videoQueue.add(queueIndex, videoInQueue);
            embeddedMediaPlayerComponent.mediaPlayer().media().play(new File(videoInQueue.getLocation()).getAbsolutePath());
        }
    }

    public void pasue() {
        if (embeddedMediaPlayerComponent.mediaPlayer().status().canPause()){
            embeddedMediaPlayerComponent.mediaPlayer().controls().pause();
        }
    }

    public void next() {
        if (videoQueue.size() >= 1) {
            if (queueIndex <= (videoQueue.size() - 1)) {
                queueIndex++;
            } else {
//                 resetting queue
                queueIndex = 0;
            }
            System.out.println(embeddedMediaPlayerComponent.mediaPlayer().media().play(new File(videoQueue.get(queueIndex).getLocation()).getAbsolutePath()));

        }
    }

    public Video getCurrentVideo(){
        return videoQueue.get(queueIndex);
    }
    public void prev() {
        if (videoQueue.size() >= 1) {
            if (queueIndex >= 1) {
                queueIndex--;
            } else {
                queueIndex = 0;
            }
            embeddedMediaPlayerComponent.mediaPlayer().media().play(new File(videoQueue.get(queueIndex).getLocation()).getAbsolutePath());
        }
    }

    public void move(int miliseconds){
            if (videoQueue.size() >= 1) {
                if (embeddedMediaPlayerComponent.mediaPlayer().status().isSeekable()) {
                    embeddedMediaPlayerComponent.mediaPlayer().controls().setTime(miliseconds);
                }
            }
        }

    public void setVol(float vol) {
        embeddedMediaPlayerComponent.mediaPlayer().audio().setVolume((int) vol);
    }

    public int getCurrentSecond() {
        return (int) (embeddedMediaPlayerComponent.mediaPlayer().status().position()* embeddedMediaPlayerComponent.mediaPlayer().status().length());

    }

    public EmbeddedMediaPlayerComponent getEmbeddedMediaPlayerComponent() {
        return embeddedMediaPlayerComponent;
    }
}
