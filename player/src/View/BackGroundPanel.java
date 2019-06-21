package View;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class BackGroundPanel extends JPanel {
    private Image backGroundImage;

    public BackGroundPanel(){
        URL backGroundUrl = null;
        try {
            File backGroundFile = new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "icons" + File.separator + "lanadelrey.png");
            backGroundUrl = backGroundFile.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        backGroundImage = Toolkit.getDefaultToolkit().createImage(backGroundUrl);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(backGroundImage, 0, 0, null);
    }
}
