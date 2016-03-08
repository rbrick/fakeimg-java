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

    /**
     * This takes all the given information, and renders it into an image that can be displayed.
     *
     * @return A buffered image of the given height and width, with the given background color, and the given text
     *         colored with the given text color
     */
    public BufferedImage renderImage() {
        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        // Get the graphics so we can now manipulate the image
        Graphics2D graphics = image.createGraphics();

        // Set the color
        graphics.setColor(Color.decode(this.bgColor));
        // Fill in the area
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        // Set the font
        graphics.setFont(this.font);

        // Reset the color used for the text
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
