package com.antoniotari.robotgame.application;

import com.antoniotari.android.jedi.JediUtil;
import com.antoniotari.robotgame.injection.RoboModule;
import com.facebook.drawee.backends.pipeline.Fresco;

import android.app.Application;

/**
 * Created by anthony on 9/19/15.
 */
public class RoboApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JediUtil.init(this, new RoboModule(this));
        Fresco.initialize(this);
    }
}