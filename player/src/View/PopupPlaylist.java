package View;

import Model.Playlist;
import Model.Song;
import utils.FontManager;
import utils.IO.FileIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class PopupPlaylist extends JPopupMenu {
    private PlaylistAlterListener listener;

    public void setPlaylistAlterListener(PlaylistAlterListener listener){
        this.listener = listener;
    }
    public PopupPlaylist(ArrayList<Playlist> playlist){
        super("Select playlist");
        this.setBackground(new Color(22,22,22));
        for (Playlist list : playlist){
            JMenuItem item;
            try {
                this.add(item = new JMenuItem(list.getTitle(), new ImageIcon(new ImageIcon(list.getImageURI().toURL()).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH))));
                item.setBackground(new Color(22,22,22));
                item.setFont(FontManager.getUbuntu(14f));
                item.setForeground(Color.WHITE);
                item.setHorizontalTextPosition(JMenuItem.RIGHT);
                item.addActionListener(actionEvent -> {
                    listener.updatePlaylist(list);
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
    }
    interface PlaylistAlterListener{
        void updatePlaylist(Playlist s);
    }
}
