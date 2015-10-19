package com.kilobolt.framework.implementation;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;

/**
 * The three important classes here are Bitmap, Canvas, and Paint.
 * 1. Bitmap just allows you to create image objects.
 * 2. Canvas is really a canvas for your images. You draw images onto the canvas, which will appear on the screen.
 * 3. Paint is used for styling what you draw to the screen.
 * When you draw an image to the screen, you first store it to memory, and you just 
 * call the same file from memory each time that this image is used. 
 * No matter how many GBs are in a device's RAM, only a small chunk 
 * is dedicated to each app. This "heap" can be as little as 16MB, 
 * so you really have to be careful with memory management
 *
 */

/**
 * Each pixel in an image takes up a bit (1/8 of a byte) of memory. 
 * So you can calculate the total memory usage by multiplying the width and height; 
 * however, there is a third dimension to consider: depth. This is where ImageFormats come in.
 * You will recognize the following ImageFormats from the Graphics interface 
 * (RGB565, ARGB4444, ARGB8888) in the code above. These are three formats that 
 * can be used when storing images to memory.
 * The first (RGB565) takes up the least memory (at least in practice). 
 * Red, Green, and Blue (RGB) have depths of 5, 6, and 5, respectively. 
 * However, there is no Alpha value (opacity/transparency) to consider.
 * The second (ARGB4444) has a total depth of 16. (Use this when you need transparency in your image).
 * The third (ARGB8888) has a total depth of 32. (You should almost never need to use this format. ARGB4444 usually is enough).
 * To simplify, the quality of your image will improve if you use the 3rd format as opposed to the 1st, 
 * but you will take up memory much faster. A single 1000x1000 image with depth of 32 will 
 * take up 32,000,000 bits, or 4MB. If you have four of those on a device with 16MB, 
 * you will get an out of memory exception and your game will crash
 */

public class AndroidGraphics implements Graphics 
{
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) 
    {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    @Override
    public Image newImage(String fileName, ImageFormat format) 
    {
        Config config = null;
        if (format == ImageFormat.RGB565)
            config = Config.RGB_565;
        else if (format == ImageFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;
        
        
        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = ImageFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = ImageFormat.ARGB4444;
        else
            format = ImageFormat.ARGB8888;

        return new AndroidImage(bitmap, format);
    }

    @Override
    public void clearScreen(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }


    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
    }
    
    @Override
    public void drawARGB(int a, int r, int g, int b) {
        paint.setStyle(Style.FILL);
       canvas.drawARGB(a, r, g, b);
    }
    
    @Override
    public void drawString(String text, int x, int y, Paint paint){
    	canvas.drawText(text, x, y, paint);

    	
    }
    

    public void drawImage(Image Image, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;
        
        
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth;
        dstRect.bottom = y + srcHeight;

        canvas.drawBitmap(((AndroidImage) Image).bitmap, srcRect, dstRect,
                null);
    }
    
    @Override
    public void drawImage(Image Image, int x, int y) {
        canvas.drawBitmap(((AndroidImage)Image).bitmap, x, y, null);
    }
    
    public void drawScaledImage(Image Image, int x, int y, int width, int height, int srcX, int srcY, int srcWidth, int srcHeight){
    	
    	
   	 srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;
        
        
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + width;
        dstRect.bottom = y + height;
        
   
        
        canvas.drawBitmap(((AndroidImage) Image).bitmap, srcRect, dstRect, null);
        
    }
   
    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }
}
