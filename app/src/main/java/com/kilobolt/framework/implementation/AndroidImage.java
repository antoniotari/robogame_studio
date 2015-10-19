package com.kilobolt.framework.implementation;

import com.kilobolt.framework.Graphics.ImageFormat;
import com.kilobolt.framework.Image;

import android.graphics.Bitmap;

public class AndroidImage implements Image {
    Bitmap bitmap;
    private ImageFormat format;

    public AndroidImage(Bitmap bitmap, ImageFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public ImageFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }
}
