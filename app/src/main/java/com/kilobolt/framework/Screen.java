package com.kilobolt.framework;

/**
 * This is an abstract class (not really an interface, which you cannot use to create objects with (although you can subclass it). It
 * contains abstract methods (which state the parameters but are not implemented). Some of these methods will be familiar, such as paint and
 * update They now have a parameter called deltaTime (which takes into account how much time passed since the last time the method was
 * called) that we can use to create framerate independent movement (more on this later).
 */

public abstract class Screen {
    protected final Game game;

    public Screen(Game game) {
        this.game = game;
    }

    public abstract void update(float deltaTime);

    public abstract void paint(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();

    public abstract void backButton();
}
