package me.rbrickis.fakeimg;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        FakeImg img = new FakeImg("SoupSkids", 160, 160, "#282828", "#FFFFFF", new Font("Yanone Kaffeesatz Bold", Font.PLAIN, 160/4));
        BufferedImage image = img.renderImage();

        File file = new File("test.png");
        ImageIO.write(image, "png", file);

        for (String font : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
            System.out.println(font);
        }
    }

}
