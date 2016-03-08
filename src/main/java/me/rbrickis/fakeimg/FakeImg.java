package me.rbrickis.fakeimg;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FakeImg {

    private String text, bgColor, textColor;
    private int width, height;
    private Font font;


    public FakeImg(String text, int width, int height, String bgColor, String textColor, Font font) {
        this.text = text;
        this.width = width;
        this.height = height;
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getBackgroundColor() {
        return bgColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public Font getFont() {
        return font;
    }

    public BufferedImage renderImage() {
        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        // Get the graphics so we can now manipulate the image
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.decode(this.bgColor));
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        graphics.setFont(this.font);
        graphics.setColor(Color.decode(this.textColor));

        // Enable Anti-Aliasing
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        FontMetrics metrics = graphics.getFontMetrics();
        // This centers the text in the image
        int stringWidth =  metrics.stringWidth(this.text) / 2;
        int descent = metrics.getDescent();
        int textWidth = (image.getWidth()/2) - stringWidth;
        int textHeight = (image.getHeight()/2) + descent;

        // Center the text
        graphics.drawString(this.text, textWidth, textHeight);
        return image;
    }


}
