package com.antoniotari.robotgame.injection;

import com.antoniotari.android.injection.ForApplication;
import com.antoniotari.robotgame.Enemy;
import com.antoniotari.robotgame.GameUtil;
import com.antoniotari.robotgame.Heliboy;
import com.antoniotari.robotgame.Robot;
import com.antoniotari.robotgame.activities.SampleGame;
import com.antoniotari.robotgame.application.RoboApplication;
import com.kilobolt.framework.implementation.AndroidGame;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by antonio on 19/10/15.
 */

/**
 * Created by anthony on 9/19/15.
 */

@Module (
        injects = {
                RoboApplication.class,
                SampleGame.class,
                Robot.class,
                Enemy.class,
                Heliboy.class,
                AndroidGame.class,
                GameUtil.class
        },
        includes = {
        },
        complete = false,
        library=true
)
public class RoboModule {

    private final Application mApplication;

    public RoboModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    @ForApplication
    Application provideApplication() {
        return mApplication;
    }

    @Provides @Singleton
    Robot provideRobot(){
        return Robot.getInstance();
    }

    @Provides @Singleton
    GameUtil provideGameUtil(){
        return GameUtil.getInstance();
    }
}