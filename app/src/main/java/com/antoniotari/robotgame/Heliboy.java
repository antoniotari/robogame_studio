package com.antoniotari.robotgame;

import com.antoniotari.android.injection.ApplicationGraph;

public class Heliboy extends Enemy {
    public Heliboy(int centerX, int centerY) {
        ApplicationGraph.getObjectGraph().inject(this);
        setCenterX(centerX);
        setCenterY(centerY);
    }
}
 