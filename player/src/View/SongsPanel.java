package View;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class SongsPanel extends JPanel {
        private ArrayList<SongPanel> songPanels = new ArrayList<>();

        public SongsPanel(){
            this.setBackground(new Color(22,22,22));
            SongPanel songPanel1 = new SongPanel("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "1.JPG");
            SongPanel songPanel2 = new SongPanel("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "2.JPG");
            SongPanel songPanel3 = new SongPanel("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "3.JPG");
            SongPanel songPanel4 = new SongPanel("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "4.JPG");
            SongPanel songPanel5 = new SongPanel("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "5.JPG");
            SongPanel songPanel6 = new SongPanel("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "6.JPG");
            SongPanel songPanel7 = new SongPanel("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "7.JPG");
            SongPanel songPanel8 = new SongPanel("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "8.JPG");
            SongPanel songPanel9 = new SongPanel("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "9.JPG");

            songPanels.add(songPanel1);
            songPanels.add(songPanel2);
            songPanels.add(songPanel3);
            songPanels.add(songPanel4);
            songPanels.add(songPanel5);
            songPanels.add(songPanel6);
            songPanels.add(songPanel7);
            songPanels.add(songPanel8);
            songPanels.add(songPanel9);
            this.setLayout(new WrapLayout(WrapLayout.LEFT));
            this.setSize(new Dimension(300,0));

            for(SongPanel x: songPanels){
                this.add(x);
            }
        }
}
