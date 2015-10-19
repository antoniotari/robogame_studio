package com.kilobolt.framework;

import android.graphics.Paint;

/**
 * The Graphics interface contains many methods that we will use to draw images to the screen. It has the familiar drawImage method and many
 * other self explanatory methods. clearScreen fills the entire screen with a color. ARGB in the last method stands for Alpha, Red, Green,
 * and Blue. Using four parameters, you can specify an RGB color and an Alpha value (opacity).
 */

public interface Graphics {
    enum ImageFormat {
        ARGB8888,
        ARGB4444,
        RGB565
    }

    Image newImage(String fileName, ImageFormat format);
    void clearScreen(int color);
    void drawLine(int x, int y, int x2, int y2, int color);
    void drawRect(int x, int y, int width, int height, int color);
    void drawImage(Image image, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight);
    void drawImage(Image Image, int x, int y);
    void drawString(String text, int x, int y, Paint paint);
    int getWidth();
    int getHeight();
    void drawARGB(int i, int j, int k, int l);
}
