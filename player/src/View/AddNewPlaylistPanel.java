package View;

import Model.Playlist;
import Model.Song;
import Model.SongTableRow;
import utils.IO.FileIO;
import utils.IO.MyFileChooser;
import utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class AddNewPlaylistPanel extends JPanel {
    private JTextField titleTextField = new JTextField();
    private JCheckBox permisionCheckBox = new JCheckBox("Public");
    private JButton songButton = new JButton("Choose the songs of your playlist");
    private JButton imageButton = new JButton("Choose the cover of your playlist");
    private JLabel createPlaylist = new JLabel("Create your playlist");
    private JLabel nameLabel = new JLabel("Choose the name of your playlist");
    private JPanel namePanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel songsListPanel = new JPanel();
    private JButton createButton = new JButton("Create");
    private MyFileChooser myFileChooser;
    private ArrayList<SongTableRow> songArrayList = new ArrayList<>();
    private JTable table;
    private PlaylistTableModel model;

    private URI artworkURI;
    public AddNewPlaylistPanel() {
        this.setLayout(new BorderLayout());
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(22,22,22));
        createPlaylist.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(createPlaylist);
        add(Box.createRigidArea(new Dimension(0, 5)));
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));


        titleTextField.setMaximumSize(new Dimension(500, 50));
        titleTextField.setMinimumSize(new Dimension(500, 50));
        titleTextField.setPreferredSize(new Dimension(500, 50));
//        Dimension location = Toolkit.getDefaultToolkit().getScreenSize();
//        titleTextField.setLocation(location.width/2 - this.getWidth()/2,location.height/2 - this.getHeight()/2)
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setBackground(new Color(22,22,22));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePanel.add(nameLabel);
        namePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        leftPanel.add(namePanel);
        titleTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePanel.add(titleTextField);
        namePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        leftPanel.add(namePanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));


//        permisionCheckBox.setMaximumSize(new Dimension(100,100));
//        permisionCheckBox.setMinimumSize(new Dimension(100,100));
//        permisionCheckBox.setPreferredSize(new Dimension(100,100));
//        permisionCheckBox.setSize(new Dimension(50,50));
        permisionCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
//        permisionCheckBox.setLocation(1000,200);
        leftPanel.add(permisionCheckBox);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        songButton.setMaximumSize(new Dimension(500, 50));
        songButton.setMinimumSize(new Dimension(500, 50));
        songButton.setPreferredSize(new Dimension(500, 50));
        songButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//        songButton.setBounds(0,0,500,50);
//        Dimension location2 = Toolkit.getDefaultToolkit().getScreenSize();
//        titleTextField.setLocation(location2.width/2 - this.getWidth()/2,location2.height/2 - this.getHeight()/2);

        leftPanel.add(songButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        imageButton.setMaximumSize(new Dimension(500, 50));
        imageButton.setMinimumSize(new Dimension(500, 50));
        imageButton.setPreferredSize(new Dimension(500, 50));
//        imageButton.setBounds(0,0,500,50);
        imageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(imageButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
//        leftPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(createButton);


        this.add(leftPanel, BorderLayout.WEST);
        songsListPanel.setBackground(new Color(22,22,22));


        this.add(songsListPanel, BorderLayout.CENTER);
        this.setMaximumSize(new Dimension(1000, 500));
        this.setMinimumSize(new Dimension(1000, 500));
        this.setPreferredSize(new Dimension(1000, 500));

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(titleTextField.getText());
            }
        });
        initPlaylistTable();
        songButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                URI uri;
                myFileChooser = new MyFileChooser(AddNewPlaylistPanel.this, null, false);
                uri = myFileChooser.getFolderURI();
                ArrayList<URI> foundSongs = FileIO.findMP3Files(FileIO.findFilesRecursive(new File(uri)));
                TagReader reader = new TagReader();
                for (URI songURI : foundSongs){
                    try {
                        reader.getAdvancedTags(songURI.toURL());
                        SongTableRow row = new SongTableRow(reader.getSong());
                        songArrayList.add(row);
                        model.addRow(new Object[]{row.getTitle(), row.getAlbum(), row.getArtist(), row.getChecked()});
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        imageButton.addActionListener(e -> {
            myFileChooser = new MyFileChooser(AddNewPlaylistPanel.this, null, true);
                artworkURI = myFileChooser.getImageFile();
        });

        createButton.addActionListener(e->{
            String name = titleTextField.getText();
            boolean isPublic = permisionCheckBox.isSelected();
            ArrayList<Song> selectedSongs = new ArrayList<>();
            for (int i = 0; i < songArrayList.size(); i++) {
                Boolean checked = (Boolean) model.getValueAt(i, 3);
                if (checked)
                    selectedSongs.add(songArrayList.get(i).getSong());
            }
            if(artworkURI == null){
                artworkURI = selectedSongs.get(0).getArtWork();
            }
            Playlist playlist = new Playlist(0, name, Main.user.getUsername(), artworkURI, isPublic, true);
            playlist.setSongs(selectedSongs);
            Main.databaseHandler.insertPlaylist(playlist);
            MainFrame.getInstance().updatePlaylists();
            MainFrame.getInstance().playListFrame.dispatchEvent(new WindowEvent(MainFrame.getInstance().playListFrame, WindowEvent.WINDOW_CLOSING));
        });
    }

    private void initPlaylistTable(){
        model = new PlaylistTableModel();
        TableColumn column0 = new TableColumn(0);
        column0.setHeaderValue("Title");

        TableColumn column1 = new TableColumn(1);
        column1.setHeaderValue("Album");

        TableColumn column2 = new TableColumn(2);
        column2.setHeaderValue("Artist");

        TableColumn column3 = new TableColumn(3);
        column3.setHeaderValue("Select");

        model.addColumn(column0.getHeaderValue());
        model.addColumn(column1.getHeaderValue());
        model.addColumn(column2.getHeaderValue());
        model.addColumn(column3.getHeaderValue());
        table = new JTable(model);
        table.setRowHeight(40);
//        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setRowMargin(10);
        JScrollPane scrollPane = new JScrollPane(table);
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setMaxWidth(60);
        scrollPane.setBackground(new Color(22, 22, 22));
        table.setBackground(new Color(22, 22, 22));
        table.setForeground(Color.WHITE);
        table.setFont(FontManager.getUbuntu(12));
        songsListPanel.add(scrollPane);
        model.fireTableDataChanged();
        table.validate();
        table.repaint();
        songsListPanel.validate();
        songsListPanel.repaint();

    }
    class PlaylistTableModel extends DefaultTableModel{
        public PlaylistTableModel() {
            super(0, 0);
        }

        public int getColumnCount() {
            return 4;
        }

        public Object getValueAt(int row, int col) {
            switch (col) {
                case 0:
                    return songArrayList.get(row).getTitle();
                case 1:
                    return songArrayList.get(row).getAlbum();
                case 2:
                    return songArrayList.get(row).getArtist();
                case 3:
                    return songArrayList.get(row).getChecked();
                default:
                    return null;
            }
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            return col == 3;
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            SongTableRow row1 = songArrayList.get(row);
            row1.setChecked((Boolean) value);
            fireTableCellUpdated(row, col);
        }
    }
}
