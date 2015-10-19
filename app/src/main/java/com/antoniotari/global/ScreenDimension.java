package com.antoniotari.global;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.webkit.WebSettings.ZoomDensity;

public class ScreenDimension 
{
  private static int screenWidthPX = 0;
  private static int screenHeightPX = 0;
  private static int densityDpi = 0;
  private static DisplayMetrics metrics;

  public static void setMetrics(Activity activity) 
  {
      DisplayMetrics metrics = new DisplayMetrics();
      activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
      screenWidthPX = metrics.widthPixels;
      screenHeightPX = metrics.heightPixels;
      densityDpi = metrics.densityDpi;
  }

  /**
   * @return the screenWidthPX
   */
  public static int getScreenWidthPX() 
  {
      return screenWidthPX;
  }

  /**
   * @param screenWidthPX
   *            the screenWidthPX to set
   */
  public static void setScreenWidthPX(int screenWidthPX) 
  {
	  ScreenDimension.screenWidthPX = screenWidthPX;
  }

  /**
   * @return the screenHeightPX
   */
  public static int getScreenHeightPX() 
  {
      return screenHeightPX;
  }

  /**
   * @param screenHeightPX
   *            the screenHeightPX to set
   */
  public static void setScreenHeightPX(int screenHeightPX) 
  {
	  ScreenDimension.screenHeightPX = screenHeightPX;
  }

  /**
   * @return the densityDpi
   */
  public static int getDensityDpi() 
  {
      return densityDpi;
  }

  /**
   * @param densityDpi
   *            the densityDpi to set
   */
  public static void setDensityDpi(int densityDpi) 
  {
	  ScreenDimension.densityDpi = densityDpi;
  }

  /**
   * @return the metrics
   */
  public static DisplayMetrics getMetrics() 
  {
      return metrics;
  }

  /**
   * @return the density
   */
  public static ZoomDensity getZoomDensity() 
  {
      if (densityDpi < 140)
          return ZoomDensity.CLOSE;
      if (densityDpi < 210)
          return ZoomDensity.MEDIUM;
      return ZoomDensity.FAR;
  }

  public static int getStatusBarHeight() 
  {
      ZoomDensity zd = getZoomDensity();

      if (zd == ZoomDensity.CLOSE)
          return 19;
      else if (zd == ZoomDensity.MEDIUM)
          return 25;
      else 
          return 38;

  }

}