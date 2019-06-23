package utils;

import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontManager {
    public static Font getUbuntu(float size){
        try {
            return new FontUIResource(Font.createFont(Font.TRUETYPE_FONT, new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "font" + File.separator + "Ubuntu-Regular.ttf")).deriveFont(size));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Font getUbuntuBold(float size){
        try {
            return new FontUIResource(Font.createFont(Font.TRUETYPE_FONT, new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "font" + File.separator + "Ubuntu-Bold.ttf")).deriveFont(size));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Font getUbuntuLight(float size){
        try {
            return new FontUIResource(Font.createFont(Font.TRUETYPE_FONT, new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "font" + File.separator + "Ubuntu-Light.ttf")).deriveFont(size));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Font getUbuntuCondensed(float size){
        try {
            return new FontUIResource(Font.createFont(Font.TRUETYPE_FONT, new File("player" + File.separator + "src" + File.separator + "resources" + File.separator + "font" + File.separator + "UbuntuCondensed-Regular.ttf")).deriveFont(size));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
