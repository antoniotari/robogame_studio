package com.antoniotari.robotgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidGame;

public class SampleGame extends AndroidGame 
{
    public static String map;
    boolean firstTimeCreate = true;

    /**
     * getInitScreen() method checks whether this is the first time that SampleGame has been opened. 
     * If it is the first time, we call the load method inside the Assets class, which loads our music. 
     * This allows us to manage music without having multiple instances of background music. Notice that
     * we also define a String called map, which contains the information from the map1.txt file. 
     * We then return a SplashLoadingScreen which is the first screen that we will see in our game
     */
    
    @Override
    public Screen getInitScreen()
    {
        if (firstTimeCreate) 
        {
            Assets.load(this);
            firstTimeCreate = false;
        }

        InputStream is = getResources().openRawResource(R.raw.map1);
        map = convertStreamToString(is);

        return new SplashLoadingScreen(this);
    }

    /**
     * override the onBackPressed() method to handle back button presses. 
     * We have set it so that when the user presses the back button, 
     * whatever is defined in the current screen's backButton() method is called
     */
    @Override
    public void onBackPressed() 
    {
        getCurrentScreen().backButton();
    }

	//-----------------------------------------------------------------
	//------------a method that takes the .txt file and returns a String
    private static String convertStreamToString(InputStream is) 
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) 
            {
                sb.append((line + "\n"));
            }
        } catch (IOException e) {
            Log.w("LOG", e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.w("LOG", e.getMessage());
            }
        }
        return sb.toString();
    }

	//-----------------------------------------------------------------
	//------------
    @Override
    public void onResume() 
    {
        super.onResume();
        Assets.theme.play();
    }

	//-----------------------------------------------------------------
	//------------
    @Override
    public void onPause() 
    {
        super.onPause();
        Assets.theme.pause();
    }
}