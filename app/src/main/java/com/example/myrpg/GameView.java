package com.example.myrpg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Vector;

public class GameView extends SurfaceView {

    protected final static int NB_CASE_LARGEUR = 8;
    protected final static int NB_CASE_HAUTEUR = 6;
    protected int width;
    protected int height;
    protected final Vector<Vector<Cell>> cells;
    private SurfaceHolder holder;
    private GameEngine gameEngine;
    private long lastClick;
    private ArrayList<Personnage> personnages = new ArrayList<Personnage>();

    public GameView(Context context) {
        super(context);
        // recuperation de la taille de l'ecran
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        cells = new Vector<>();
        for (int i = 0; i < NB_CASE_LARGEUR; i++) {
            cells.add(new Vector<Cell>(height));
            int x = 0;
            int y = 0;
            for (int j = 0; j < NB_CASE_HAUTEUR; j++) {
                cells.get(i).add(new Cell(context,this, x, y));
                x = x + (width / NB_CASE_LARGEUR);
                y = y + (height / NB_CASE_HAUTEUR);
            }
        }
        createsPersonnages();

        gameEngine = new GameEngine(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameEngine.setRunning(false);
                while (retry) {
                    try {
                        gameEngine.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameEngine.setRunning(true);
                gameEngine.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }
        });
    }

    public Vector<Vector<Cell>> getCells() {
        return cells;
    }


    public void createsPersonnages() {
        Bitmap p_bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.char1_right);
        Personnage p = new Personnage(p_bmp);
        cells.get(4).get(4).setPersonnage(p);
    }

    @SuppressLint("WrongCall")
    @Override
    protected void onDraw(Canvas canvas) {
        // set background
        Bitmap bg = BitmapFactory.decodeResource(this.getResources(), R.drawable.map_test_2);
        Rect src = new Rect(0,0,bg.getWidth(), bg.getHeight());
        Rect dest = new Rect(0,0,width, height);
        canvas.drawBitmap(bg, src, dest,null);
        cells.get(4).get(4).onDraw(canvas);
    }
}