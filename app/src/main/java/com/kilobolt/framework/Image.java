package com.kilobolt.framework;

import com.kilobolt.framework.Graphics.ImageFormat;

public interface Image {
    int getWidth();
    int getHeight();
    ImageFormat getFormat();
    void dispose();
}
