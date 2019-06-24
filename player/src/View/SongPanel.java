package View;


import Model.Song;
import Model.SongTableRow;
import utils.FontManager;
import utils.IO.DatabaseAlterListener;
import utils.IO.DatabaseConnection;
import utils.IO.DatabaseHandler;
import utils.IO.DatabaseHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SongPanel extends JPanel {
    JTable table;
    private boolean DEBUG = true;
    private ArrayList<Song> songs;
    private ArrayList<SongTableRow> rows = new ArrayList<>();
    private DatabaseAlterListener databaseAlterListener;
    private SongTableModel model;
    //    public void
    public SongPanel(ArrayList<Song> songs) {
        super(new BorderLayout());
        this.songs = songs;
        model = new SongTableModel();

        TableColumn column0 = new TableColumn(0);
        column0.setHeaderValue("Add");

        TableColumn column1 = new TableColumn(1);
        column1.setHeaderValue("Artwork");

        TableColumn column2 = new TableColumn(2);
        column2.setHeaderValue("Title");

        TableColumn column3 = new TableColumn(3);
        column3.setHeaderValue("Album");

        TableColumn column4 = new TableColumn(4);
        column4.setHeaderValue("Artist");

        TableColumn column5 = new TableColumn(5);
        column5.setHeaderValue("Last Played");

        TableColumn column6 = new TableColumn(6);
        column6.setHeaderValue("Select");
        model.addColumn(column0.getHeaderValue());
        model.addColumn(column1.getHeaderValue());
        model.addColumn(column2.getHeaderValue());
        model.addColumn(column3.getHeaderValue());
        model.addColumn(column4.getHeaderValue());
        model.addColumn(column5.getHeaderValue());
        model.addColumn(column6.getHeaderValue());
        table = new JTable(model);
        table.setRowHeight(80);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        table.setRowMargin(10);
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        //Set up column sizes.
        initColumnSizes(table);
        scrollPane.setBackground(new Color(22, 22, 22));
        table.setBackground(new Color(22, 22, 22));
        table.setForeground(Color.WHITE);
        table.setFont(FontManager.getUbuntuBold(16));
        //Add the scroll pane to this panel.
        add(scrollPane, BorderLayout.CENTER);
        JButton addBtn = new JButton("Add Song");
        JButton removeBtn = new JButton("Remove");
        JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
        buttonsPanel.add(addBtn);
        buttonsPanel.add(removeBtn);
        for (int i = 0; i< songs.size(); i++) {
            SongTableRow row = new SongTableRow(songs.get(i));
            rows.add(row);
            model.addRow(new Object[]{row.getAddIcon(), row.getArtWork(),
                    row.getTitle(),
                    row.getAlbum(), row.getArtist(), row.getLastPlayed(), row.getChecked()});
        }

        addBtn.addActionListener(actionEvent -> {
            Song s  = (new DatabaseHelper(new DatabaseConnection("test").getConnection()).searchSong("love").get(0));
            addSong(s);
        });
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public ArrayList<Song> getSelectedSongs(){
        SongTableModel songTableModel = (SongTableModel) table.getModel();
        ArrayList<Song> selected = new ArrayList<>();
        for (int i = 0; i < songs.size(); i++) {
            Boolean checked = (Boolean) songTableModel.getValueAt(i,6);
            if (checked){
                selected.add(songs.get(i));
            }
        }
        return selected;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */

    public void setDatabaseAlterListener(DatabaseAlterListener databaseAlterListener) {
        this.databaseAlterListener = databaseAlterListener;
    }

    public void addSong(Song song) {
        songs.add(song);
        SongTableRow row = new SongTableRow(song);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                model.addRow(new Object[]{row.getAddIcon(), row.getArtWork(),
                        row.getTitle(),
                        row.getAlbum(), row.getArtist(), row.getLastPlayed(), row.getChecked()});
            }
        });
        rows.add(new SongTableRow(song));
    }

    public void removeSong(Song song) {
        int index = -1;
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).equals(song)) {
                index = i;
            }
        }
        songs.remove(index);
        rows.remove(index);
        SongTableModel model = (SongTableModel) table.getModel();
        model.removeRow(index);
        databaseAlterListener.removeSong(song);
        // tell to remove song from somewhere
    }

    /*
     * This method picks good column sizes.
     * If all column heads are wider than the column's cells'
     * contents, then you can just use column.sizeWidthToFit().
     */
    private void initColumnSizes(JTable table) {
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(1).setMaxWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);
        table.getColumnModel().getColumn(6).setMaxWidth(30);

    }

    /**
     * Table model for the data, nothing special here
     */
    class SongTableModel extends DefaultTableModel {

        public SongTableModel() {
            super(0,0);
        }

        public int getColumnCount() {
            return 7;
        }

        public Object getValueAt(int row, int col) {
            switch (col) {
                case 0:
                    return rows.get(row).getAddIcon();
                case 1:
                    return rows.get(row).getArtWork();
                case 2:
                    return rows.get(row).getTitle();
                case 3:
                    return rows.get(row).getAlbum();
                case 4:
                    return rows.get(row).getArtist();
                case 5:
                    return rows.get(row).getLastPlayed();
                case 6:
                    return rows.get(row).getChecked();
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
            return col == 6;
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            SongTableRow row1 = rows.get(row);
            row1.setChecked((Boolean) value);
            fireTableCellUpdated(row, col);

        }

    }
}
