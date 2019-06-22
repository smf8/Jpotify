package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchAndProfilesPanel extends JPanel{
    private JTextField searchTextField = new JTextField();
    private JLabel searhLabel = new JLabel();
    private JLabel profileLabel = new JLabel();
    private JPanel searchPanel = new JPanel();
    //private JPanel profilePanel = new JPanel();
    private ImageIcon search = null;

    public SearchAndProfilesPanel(){
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

            File searchFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "search.png");
            searchUrl = searchFile.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        search = new ImageIcon(new ImageIcon(searchUrl).getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH));
        searhLabel.setIcon(search);
        searchPanel.add(searhLabel,BorderLayout.WEST);
        searchPanel.add(searchTextField,BorderLayout.CENTER);
        this.add(searchPanel,BorderLayout.CENTER);
    }
    public void setProfileInformation(String user){
        profileLabel.setText(user);
        profileLabel.setIcon(search);
        profileLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        this.add(profileLabel,BorderLayout.EAST);
    }
}
