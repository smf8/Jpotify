package playback;

import javazoom.jl.decoder.*;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.JavaSoundAudioDevice;

import javax.sound.sampled.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;


/**
 * Usage : to play a song first initialize a player object and invoke resume() method inside a new Daemon thread
 *          for stopping player invoke stop() method and point thread to null
 *          for pausing thread use pause() method and stop the thread as well
 *          for controlling playback status extend a playbackAdapter in a class

 */
public class MP3Player {

    private final int lostFrames = 20; //some fraction of a second of the sound gets "lost" after every pause. 52 in original code
    private java.net.URL urlToStreamFrom;
    private String audioPath;
    private Bitstream bitstream;
    private Decoder decoder;
    private AudioDevice audioDevice;
    private boolean closed;
    private boolean complete;
    private boolean paused;
    private boolean stopped;
    private PlaybackListener listener;
    private int frameIndexCurrent;

    public MP3Player(URL urlToStreamFrom) throws JavaLayerException {
        this.urlToStreamFrom = urlToStreamFrom;
        this.listener = new PlaybackAdapter();
    }

    public MP3Player(String audioPath) throws JavaLayerException {
        this.audioPath = audioPath;
        this.listener = new PlaybackAdapter();
    }

    public void setPlaybackListener(PlaybackListener newPlaybackListener) {
        if (newPlaybackListener != null) {
            this.listener = newPlaybackListener;
        } else {
            throw new NullPointerException("PlaybackListener is null");
        }
    }

    private InputStream getAudioInputStream() throws IOException {
        if (this.audioPath != null) {
            return new FileInputStream(this.audioPath);
        } else if (this.urlToStreamFrom != null) {
            return this.urlToStreamFrom.openStream();
        }
        return null;
    }

    public boolean play() throws JavaLayerException {
        return this.play(0);
    }
    public int getCurrentFrame(){
        return frameIndexCurrent;
    }
    public long getCurrent(){
        return this.audioDevice.getPosition();
    }
    public boolean play(int frameIndexStart) throws JavaLayerException {
        return this.play(frameIndexStart, -1, lostFrames);
    }
    public void setVol(float val){
        if (this.audioDevice instanceof JavaSoundAudioDevice)
        {
            JavaSoundAudioDevice jsAudio = (JavaSoundAudioDevice) audioDevice;
            jsAudio.setLineGain(val);
        }

    }
    public boolean play(int frameIndexStart, int frameIndexFinal, int correctionFactorInFrames) throws JavaLayerException {
        try {
            this.bitstream = new Bitstream(this.getAudioInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.audioDevice = FactoryRegistry.systemRegistry().createAudioDevice();
        this.decoder = new Decoder();
        this.audioDevice.open(this.decoder);

        boolean shouldContinueReadingFrames = true;

        this.paused = false;
        this.stopped = false;
        this.frameIndexCurrent = 0;

        while (shouldContinueReadingFrames == true && this.frameIndexCurrent < frameIndexStart - correctionFactorInFrames) {
            shouldContinueReadingFrames = this.skipFrame();
            this.frameIndexCurrent++;
        }

        if (this.listener != null) {
            this.listener.playbackStarted(new PlaybackEvent(this, PlaybackEvent.EventType.Instances.Started, this.audioDevice.getPosition()));
        }

        if (frameIndexFinal < 0) {
            frameIndexFinal = Integer.MAX_VALUE;
        }

        while (shouldContinueReadingFrames == true && this.frameIndexCurrent < frameIndexFinal) {
            if (this.paused || this.stopped) {
                shouldContinueReadingFrames = false;
                try {
                    Thread.sleep(1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                shouldContinueReadingFrames = this.decodeFrame();
                this.frameIndexCurrent++;
            }
        }

        // last frame, ensure all data flushed to the audio device.
        if (this.audioDevice != null && !this.paused) {
            this.audioDevice.flush();

            synchronized (this) {
                this.complete = (this.closed == false);
                this.close();
            }

            // report to listener
            if (this.listener != null) {
                int audioDevicePosition = -1;
                if (this.audioDevice != null) {
                    audioDevicePosition = this.audioDevice.getPosition();
                } else {
                    //throw new NullPointerException("attribute audioDevice in " + this.getClass() + " is NULL");
                }
                PlaybackEvent playbackEvent = new PlaybackEvent(this, PlaybackEvent.EventType.Instances.Stopped, audioDevicePosition);
                this.listener.playbackFinished(playbackEvent);
            }
        }

        return shouldContinueReadingFrames;
    }


    public boolean resume() throws JavaLayerException {
        return this.play(this.frameIndexCurrent);
    }

    public synchronized void close() {
        if (this.audioDevice != null) {
            this.closed = true;

            this.audioDevice.close();

            this.audioDevice = null;

            try {
                this.bitstream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    protected boolean decodeFrame() throws JavaLayerException {
        boolean returnValue = false;
        if (this.stopped) { //nothing for decode
            return false;
        }

        try {
            if (this.audioDevice != null) {
                Header header = this.bitstream.readFrame();
                if (header != null) {
                    // sample buffer set when decoder constructed
                    SampleBuffer output = (SampleBuffer) this.decoder.decodeFrame(header, this.bitstream);

                    synchronized (this) {
                        if (this.audioDevice != null) {
                            this.audioDevice.write(output.getBuffer(), 0, output.getBufferLength());
                        }
                    }

                    this.bitstream.closeFrame();
                    returnValue = true;
                } else {
                    System.out.println("End of file"); //end of file
                    //this.stop();
                    returnValue = false;
                }
            }
        } catch (RuntimeException ex) {
            throw new JavaLayerException("Exception decoding audio frame", ex);
        }
        return returnValue;
    }

    public void pause() {
        if (!this.stopped) {
            this.paused = true;
            if (this.listener != null) {
                this.listener.playbackPaused(new PlaybackEvent(this, PlaybackEvent.EventType.Instances.Paused, this.audioDevice.getPosition()));
            }
            this.close();
        }
    }

    protected boolean skipFrame() throws JavaLayerException {
        boolean returnValue = false;
        Header header = this.bitstream.readFrame();

        if (header != null) {
            this.bitstream.closeFrame();
            returnValue = true;
        }

        return returnValue;
    }
    public void stop() {
        if (!this.stopped) {
            if (!this.closed) {
                this.listener.playbackFinished(new PlaybackEvent(this, PlaybackEvent.EventType.Instances.Stopped, this.audioDevice.getPosition()));
                this.close();
            } else if (this.paused) {
                int audioDevicePosition = -1; //this.audioDevice.getPosition(), audioDevice is null
                this.listener.playbackFinished(new PlaybackEvent(this, PlaybackEvent.EventType.Instances.Stopped, audioDevicePosition));
            }
            this.stopped = true;
        }
    }

    /**
     * @return the closed
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * @return the complete
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * @return the paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * @return the stopped
     */
    public boolean isStopped() {
        return stopped;
    }


    public interface PlaybackListener {
        void playbackStarted(PlaybackEvent event);

        void playbackPaused(PlaybackEvent event);

        void playbackFinished(PlaybackEvent event);
    }

    // inner classes
    public static class PlaybackEvent {
        public MP3Player source;
        public EventType eventType;
        public int frameIndex;

        public PlaybackEvent(MP3Player source, EventType eventType, int frameIndex) {
            this.source = source;
            this.eventType = eventType;
            this.frameIndex = frameIndex;
        }

        public static class EventType {
            public String name;

            public EventType(String name) {
                this.name = name;
            }

            public static class Instances {
                public static EventType Started = new EventType("Started");
                public static EventType Paused = new EventType("Paused");
                public static EventType Stopped = new EventType("Stopped");
            }
        }
    }

    public static class PlaybackAdapter implements PlaybackListener {
        @Override
        public void playbackStarted(PlaybackEvent event) {
            System.err.println("Playback started");
        }

        @Override
        public void playbackPaused(PlaybackEvent event) {
            System.err.println("Playback paused");
        }

        @Override
        public void playbackFinished(PlaybackEvent event) {
            System.err.println("Playback stopped");
        }
    }
}