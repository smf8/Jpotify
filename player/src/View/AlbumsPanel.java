package View;


import Model.Album;
import Model.Playlist;
import utils.IO.FileIO;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class AlbumsPanel extends JPanel {
        private ArrayList<AlbumPanel> albumPanels = new ArrayList<>();
        private int mark = 0;
        public AlbumsPanel(){
            this.setBackground(new Color(22,22,22));
            this.setLayout(new WrapLayout(WrapLayout.LEFT));
            this.setSize(new Dimension(300,0));
//
            for(AlbumPanel x: albumPanels){
                this.add(x);
            }
        }
        public void addAlbum(Playlist album){
            AlbumPanel albumPanel = new AlbumPanel(album);
            for(int i=0;i<albumPanels.size();i++){
                if(albumPanels.get(i).getAlbum().equals(album)){
                    mark++;
                    break;
                }
            }
            if(mark ==0){
                albumPanels.add(albumPanel);
            }
        }
        public void removeAlbum(Album album){
            for(int i=0;i<albumPanels.size();i++){
                if (albumPanels.get(i).getAlbum().equals(album)){
                    albumPanels.remove(i);
                }
        }}
        public void showAlbums(){
            for(AlbumPanel x: albumPanels){
                this.add(x);
            }
        }
        public ArrayList<AlbumPanel> getPanels(){
            return albumPanels;
        }
}
