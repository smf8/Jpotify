package View;

import utils.IO.FileIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchAndProfilesPanel extends JPanel {
    private JTextField searchTextField = new JTextField();
    private JLabel searhLabel = new JLabel();
    //    private JLabel profileLabel = new JLabel();
    private JPanel searchPanel = new JPanel();
    //private JPanel profilePanel = new JPanel();
    private ImageIcon search = null;

    public SearchAndProfilesPanel() {
        searchPanel.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
        searchTextField.setText("Search");
        searchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //yadet bashe ke click kard oon search avaliye pak beshe
            }
        });
        URL searchUrl = null;
        try {

            File searchFile = new File(FileIO.RESOURCES_RELATIVE + "icons" + File.separator + "z.png");
            searchUrl = searchFile.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        search = new ImageIcon(new ImageIcon(searchUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        searhLabel.setIcon(search);
        searchPanel.add(searhLabel, BorderLayout.WEST);
        searchPanel.add(searchTextField, BorderLayout.CENTER);
        searchPanel.setBackground(new Color(22,22,22));
        this.setBackground(new Color(22,22,22));
        this.add(searchPanel, BorderLayout.CENTER);
    }

}