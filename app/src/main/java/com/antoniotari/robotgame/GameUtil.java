package com.antoniotari.robotgame;

import com.antoniotari.android.injection.ApplicationGraph;

/**
 * Created by antonio on 20/10/15.
 */
public enum GameUtil {
    INSTANCE;

    private int screenWidth;
    private int screenHeight;

    GameUtil(){
        ApplicationGraph.getObjectGraph().inject(this);
    }

    public static GameUtil getInstance(){
        return INSTANCE;
    }

    public void setScreenHeight(final int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public void setScreenWidth(final int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }
}
