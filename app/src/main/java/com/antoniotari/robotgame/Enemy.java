package com.antoniotari.robotgame;

import com.kilobolt.framework.Graphics;

import android.graphics.Rect;

public class Enemy {
    private int power, centerX, speedX, centerY;
    private Background bg;// = GameScreen.getBg1();
    private Robot robot = Robot.getInstance();// GameScreen.getRobot();

    public Rect rect = new Rect(0, 0, 0, 0);
    public int health = MAX_HEALTH;
    public static final int MAX_HEALTH = 5;

    private int movementSpeed;

    private ExplosionAnimation mExplosionAnimation;

    //-----------------------------------------------------------------
    //------------
    // Behavioral Methods
    public void update() {
        follow();
        centerX += speedX;
        speedX = bg.getSpeedX() * 5 + movementSpeed;
        rect.set(centerX - 25, centerY - 25, centerX + 25, centerY + 35);

        if (Rect.intersects(rect, Robot.yellowRed)) {
            checkCollision();
        }
    }

    public void reset(){
        health=MAX_HEALTH;
        setCenterY(robot.getCenterY());
        mExplosionAnimation.frameCounter=0;
    }

    //-----------------------------------------------------------------
    //------------
    private void checkCollision() {
        if (Rect.intersects(rect, Robot.rect) || Rect.intersects(rect, Robot.rect2)
                || Rect.intersects(rect, Robot.rect3) || Rect.intersects(rect, Robot.rect4)) {

            die();
        }
    }

    //-----------------------------------------------------------------
    //------------
    public void follow() {
        if (centerX < -95 || centerX > 810) {
            movementSpeed = 0;
        } else if (Math.abs(robot.getCenterX() - centerX) < 5) {
            movementSpeed = 0;
        } else {
            if (robot.getCenterX() >= centerX) {
                movementSpeed = 1;
            } else {
                movementSpeed = -1;
            }
        }
    }

    //-----------------------------------------------------------------
    //------------
    public void die() {
        getExplosionAnimation().setDieCoordinates(getCenterX(),getCenterY());
        setCenterX(-100);
        Assets.enemyDeath.play(0.6f);
    }

    public boolean isDead(){
        return getCenterX() < -90;
    }

    //-----------------------------------------------------------------
    //------------
    public void attack() {

    }

    //-----------------------------------------------------------------
    //------------
    public int getPower() {
        return power;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public Background getBg() {
        return bg;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public void setBg(Background bg) {
        this.bg = bg;
    }

    public void setExplosionAnimation(Graphics graphics) {
        mExplosionAnimation = new ExplosionAnimation(graphics);
    }

    public ExplosionAnimation getExplosionAnimation() {
        return mExplosionAnimation;
    }
}