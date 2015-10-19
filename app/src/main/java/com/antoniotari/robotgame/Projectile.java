package com.antoniotari.robotgame;

import android.graphics.Rect;

public class Projectile {

    private int x, y, speedX;
    private boolean visible;

    private Rect rect;

    public Projectile(int startX, int startY) {
        x = startX;
        y = startY;
        speedX = 7;
        visible = true;

        rect = new Rect(0, 0, 0, 0);
    }

    public void update() {
        x += speedX;
        rect.set(x, y, x + 10, y + 5);
        if (x > 800) {
            visible = false;
            rect = null;
        }
        if (x < 800) {
            checkCollision();
        }
    }

    private void checkCollision() {
        if (Rect.intersects(rect, GameScreen.hb.rect)) {
            visible = false;

            if (GameScreen.hb.health > 0) {
                GameScreen.hb.health -= 1;
            }
            if (GameScreen.hb.health == 0) {
                //GameScreen.hb.setCenterX(-100);
                GameScreen.hb.die();
            }
        }

        if (Rect.intersects(rect, GameScreen.hb2.rect)) {
            visible = false;

            if (GameScreen.hb2.health > 0) {
                GameScreen.hb2.health -= 1;
            }
            if (GameScreen.hb2.health == 0) {
                //GameScreen.hb2.setCenterX(-100);
                GameScreen.hb2.die();
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeedX() {
        return speedX;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}