package View;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class OptionsPanel extends JPanel {
    JLabel HOME = new JLabel("HOME");
    JLabel Search = new JLabel("SEARCH");
    public OptionsPanel(){
        this.setLayout(new BorderLayout());
        URL fileUrl = null;
        try {
            File file = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "home.png");
            fileUrl = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageIcon home = new ImageIcon(new ImageIcon(fileUrl).getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
        HOME.setIcon(home);
        add(HOME,BorderLayout.WEST);
    }
}
