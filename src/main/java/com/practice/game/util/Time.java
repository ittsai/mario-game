package com.practice.game.util;

public class Time {
    public static float timeStarted = System.nanoTime();

    public static float getTime() {
        // nano to second
        return  (float) ((System.nanoTime() - timeStarted) * 1E-9);
    }
}
