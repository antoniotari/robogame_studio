package com.antoniotari.robotgame;

import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Graphics.ImageFormat;
import com.kilobolt.framework.Image;

/**
 * Created by antonio on 19/10/15.
 */
public class ExplosionAnimation extends Animation {

    private int x;
    private int y;
    int frameCounter=0;

    public ExplosionAnimation(Graphics graphics){
        super();
        for(int i=1;i<=19;i++){
            String imagePath="explosion/explosion"+ (i<10?"0":"") + i + ".webp";
            addFrame(graphics.newImage(imagePath, ImageFormat.ARGB4444), 444);
        }
    }

    @Override
    public synchronized Image getImage() {
        update(500);
        if(frames.size()<=frameCounter++){
            return null;
        }
        return super.getImage();
    }

    public void setDieCoordinates(int x,int y){
        if(x>-100) {
            this.x = x;
            this.y = y;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
