package com.example.myrpg;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

public class GameEngine extends Thread {

    private GameView view;
    private boolean running = false;

    public GameEngine(GameView view) {
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        while (running) {
                Canvas c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                }
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
            }
        }
    }
}
