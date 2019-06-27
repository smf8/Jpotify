package Model;

import utils.IO.FileIO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SongTableRow {
    private ImageIcon addIcon;
    private ImageIcon artWork;
    private String title;
    private String album;
    private String artist;
    private Boolean checked;
    private String lastPlayed;
    private Song song;

    {
        try {
            addIcon = new ImageIcon(new ImageIcon(new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "add.png").toURI().toURL()).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public SongTableRow(Song song) {
        this.song = song;
        try {
            this.artWork = new ImageIcon(new ImageIcon(song.getArtWork().toURL()).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.title = song.getTitle();
        this.album = song.getAlbum();
        this.artist = song.getArtist();
        this.checked = false;
        LocalDate LocalDate = song.getPlayDate();
        this.lastPlayed = LocalDate.format(DateTimeFormatter.ofPattern(Song.DATE_FORMAT));

    }


    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public ImageIcon getAddIcon() {
        return addIcon;
    }

    public ImageIcon getArtWork() {
        return artWork;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getLastPlayed() {
        return lastPlayed;
    }

    public Song getSong() {
        return song;
    }
}
