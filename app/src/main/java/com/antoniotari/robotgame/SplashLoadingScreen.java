package com.antoniotari.robotgame;

import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.Graphics.ImageFormat;

/**
 * Since this is a subclass of the Screen superclass, we must call all of 
 * Screen's methods. We have the update method, in which we load our 
 * first Image as an RGB565 (which does not support transparency but 
 * takes up the least amount of memory). We do not paint anything. 
 * As soon as the loading of the splash.jpg is complete, we go straight 
 * to the LoadingScreen class
 *
 */

public class SplashLoadingScreen extends Screen 
{
    public SplashLoadingScreen(Game game) 
    {
        super(game);
    }

    @Override
    public void update(float deltaTime) 
    {
        Graphics g = game.getGraphics();
        Assets.splash= g.newImage("splash.png", ImageFormat.RGB565);
        game.setScreen(new LoadingScreen(game));
    }

    @Override
    public void paint(float deltaTime)
    {

    }

    @Override
    public void pause() 
    {

    }

    @Override
    public void resume() 
    {

    }

    @Override
    public void dispose() 
    {

    }

    @Override
    public void backButton() 
    {

    }
}