package com.example.myrpg.game;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;

public class GameEngine extends Thread {

    private GameView view;
    private boolean running = false;

    public GameEngine(GameView view) {
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    public boolean isRunning() { return running;}

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        boolean end= false;
        while (running) {
                Canvas c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    end = view.isWin() || view.isLost();
                    view.onDraw(c);
                    if(end){
                        running = false;
                    }
                }
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
            }
        }
    }
}
