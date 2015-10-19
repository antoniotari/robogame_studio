package com.kilobolt.framework.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * This class creates a SurfaceView (which you can use to create graphics-based UI and update very quickly). 
 */

/**
 * it is this class that gives us a direct window into our game. 
 * Also notice this is where update() and paint() are called "internally" . 
 * An important variable here is the deltaTime variable, which checks 
 * how much time has elapsed since the last time the update/paint methods 
 * were called. 
 */

/**
 * Android devices are rarely powerful enough to maintain 60fps indefinitely. 
 * The CPU will be burdened with incoming messages, internal changes, and much more.
 * That means that the frame rate will fluctuate.To prevent movement speed from 
 * depending on frame rate, we use this deltaTime variable to check how much time 
 * elapsed since the last update. If the update took twice as long (i.e. frame rate was halved), 
 * then deltatime would be doubled. We multiply this deltaTime throughout our game's 
 * update methods to ensure that no matter what the frame rate is, our character 
 * will move by the same amount given the same time period.
 * Of course, this means that our speed could go from 1 pixel per second to 10 
 * pixels per second. If we have a thin wall, this sudden increase in deltaTime could
 * mean that our collision detection system will break. That is why we cap the deltaTime 
 * (it is capped at 3.15 in the above example) so that if the game slows down too much, 
 * then we will let movement depend on frame rate so that we do not break our entire game 
 * trying to maintain consistent movement. This is prioritization at work.
 */

public class AndroidFastRenderView extends SurfaceView implements Runnable 
{
	AndroidGame game;
	Bitmap framebuffer;
	Thread renderThread = null;
	SurfaceHolder holder;
	volatile boolean running = false;

	//-----------------------------------------------------------------
	//------------
	public AndroidFastRenderView(AndroidGame game, Bitmap framebuffer) 
	{
		super(game);
		this.game = game;
		this.framebuffer = framebuffer;
		this.holder = getHolder();
	}

	//-----------------------------------------------------------------
	//------------
	public void resume() 
	{ 
		running = true;
		renderThread = new Thread(this);
		renderThread.start();   
	}      

	//-----------------------------------------------------------------
	//------------
	public void run()
	{
		Rect dstRect = new Rect();
		long startTime = System.nanoTime();
		while(running) 
		{  
			if(!holder.getSurface().isValid())
				continue;           

			float deltaTime = (System.nanoTime() - startTime) / 10000000.000f;
			startTime = System.nanoTime();

			if (deltaTime > 3.15)
			{
				deltaTime = (float) 3.15;
			}

			game.getCurrentScreen().update(deltaTime);
			game.getCurrentScreen().paint(deltaTime);

			Canvas canvas = holder.lockCanvas();
			canvas.getClipBounds(dstRect);
			canvas.drawBitmap(framebuffer, null, dstRect, null);                           
			holder.unlockCanvasAndPost(canvas);
		}
	}

	//-----------------------------------------------------------------
	//------------
	public void pause() 
	{                        
		running = false;                        
		while(true) 
		{
			try{
				renderThread.join();
				break;
			} catch (InterruptedException e){
				// retry
			}

		}
	}     
}