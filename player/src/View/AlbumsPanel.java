package View;

import utils.IO.FileIO;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class AlbumsPanel extends JPanel {
        private ArrayList<AlbumPanel> songPanels = new ArrayList<>();

        public AlbumsPanel(){
            this.setBackground(new Color(22,22,22));
            AlbumPanel songPanel1 = new AlbumPanel(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "1.JPG");
            AlbumPanel songPanel2 = new AlbumPanel(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "2.JPG");
            AlbumPanel songPanel3 = new AlbumPanel(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "3.JPG");
            AlbumPanel songPanel4 = new AlbumPanel(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "4.JPG");
            AlbumPanel songPanel5 = new AlbumPanel(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "5.JPG");
            AlbumPanel songPanel6 = new AlbumPanel(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "6.JPG");
            AlbumPanel songPanel7 = new AlbumPanel(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "7.JPG");
            AlbumPanel songPanel8 = new AlbumPanel(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "8.JPG");
            AlbumPanel songPanel9 = new AlbumPanel(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "9.JPG");

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

            for(AlbumPanel x: songPanels){
                this.add(x);
            }
        }
}
