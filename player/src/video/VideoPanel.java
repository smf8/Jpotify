package video;
import Model.Video;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VideoPanel extends JPanel {
//    private
    private EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent=null;
    private VideoPlaybackManager playbackManager;
    public VideoPanel(ArrayList<Video> videos){
        this.setLayout(new BorderLayout());
        embeddedMediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        this.add(embeddedMediaPlayerComponent,BorderLayout.CENTER);
        playbackManager = new VideoPlaybackManager(videos, embeddedMediaPlayerComponent);
    }

    public EmbeddedMediaPlayerComponent getEmbeddedMediaPlayerComponent() {
        return embeddedMediaPlayerComponent;
    }
}
