package me.rbrickis.fakeimg.utils;

import java.awt.Color;

public final class ImageUtils {

    public static Color toColor(int rgba) {
        int red = (rgba >> 16) & 0xFF;
        int green = (rgba >> 8) & 0xFF;
        int blue = (rgba >> 0) & 0xFF;
        int alpha =  (rgba >> 24) & 0xff;
        System.out.println("RGBA : " + rgba);
        System.out.println("RED : " + red);
        System.out.println("GREEN : " + green);
        System.out.println("BLUE : " + blue);
        System.out.println("ALPHA : " + alpha);
        return new Color(red, green, blue, alpha);
    }


}
