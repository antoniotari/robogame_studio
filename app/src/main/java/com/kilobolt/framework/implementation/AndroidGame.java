package com.kilobolt.framework.implementation;

import com.antoniotari.android.injection.ApplicationGraph;
import com.antoniotari.android.jedi.ScreenDimension;
import com.antoniotari.robotgame.GameUtil;
import com.kilobolt.framework.Audio;
import com.kilobolt.framework.FileIO;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Input;
import com.kilobolt.framework.Screen;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import javax.inject.Inject;

public abstract class AndroidGame extends Activity implements Game {
    private AndroidFastRenderView renderView;
    private Graphics graphics;
    private Audio audio;
    private Input input;
    private FileIO fileIO;
    private Screen screen;
    private WakeLock wakeLock;

    @Inject ScreenDimension mScreenDimension;
    @Inject GameUtil mGameUtil;
    //-----------------------------------------------------------------
    //------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationGraph.getObjectGraph().inject(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        //ScreenDimension.setMetrics(this);
        int frameBufferWidth = isPortrait ? 480 : 800;
        int frameBufferHeight = isPortrait ? 800 : 480;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);
        //Float dpiFact=Float.valueOf((float)ScreenDimension.getDensityDpi()/160.0f);

        mGameUtil.setScreenWidth((isPortrait ? mScreenDimension.getScreenWidthPX() : mScreenDimension.getScreenHeightPX()));
        mGameUtil.setScreenHeight((!isPortrait ? mScreenDimension.getScreenWidthPX() : mScreenDimension.getScreenHeightPX()));

        float scaleX = (float) frameBufferWidth / mGameUtil.getScreenWidth();
        // getWindowManager().getDefaultDisplay().getWidth();// ScreenDimension.getScreenWidthPX();
        float scaleY = (float) frameBufferHeight / mGameUtil.getScreenHeight();
        // getWindowManager().getDefaultDisplay().getHeight();// ScreenDimension.getScreenHeightPX();

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getInitScreen();
        setContentView(renderView);

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "com.antoniotari.RoboGame");
    }

    //-----------------------------------------------------------------
    //------------
    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    //-----------------------------------------------------------------
    //------------
    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();

        if (isFinishing()) {
            screen.dispose();
        }
    }

    //-----------------------------------------------------------------
    //------------
    @Override
    public Input getInput() {
        return input;
    }

    //-----------------------------------------------------------------
    //------------
    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    //-----------------------------------------------------------------
    //------------
    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    //-----------------------------------------------------------------
    //------------
    @Override
    public Audio getAudio() {
        return audio;
    }

    //-----------------------------------------------------------------
    //------------
    @Override
    public void setScreen(Screen screen) {
        if (screen == null) {
            throw new IllegalArgumentException("Screen must not be null");
        }

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    //-----------------------------------------------------------------
    //------------
    public Screen getCurrentScreen() {
        return screen;
    }
}