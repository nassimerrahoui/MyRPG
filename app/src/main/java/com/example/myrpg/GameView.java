package com.example.myrpg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Vector;

public class GameView extends SurfaceView {

    protected final static int NB_CASE_LARGEUR = 10;
    protected final static int NB_CASE_HAUTEUR = 6;
    protected int width;
    protected int height;
    protected int cell_width;
    protected int cell_height;
    protected Vector<Vector<Cell>> cells;
    private SurfaceHolder holder;
    private GameEngine gameEngine;
    private long lastClick;
    private ArrayList<Personnage> personnages = new ArrayList<Personnage>();
    private boolean personnageSelected;
    private ConstraintLayout screen;
    private FloatingActionButton menu;
    private FloatingActionButton action;
    private FrameLayout parent;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public GameView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        // instanciation des attributs
        personnageSelected = false;

        screen = ((Activity)context).findViewById(R.id.game_screen);

        parent = (FrameLayout)getParent();



        if(screen == null) {
            System.out.println("LLLLLLLLLLLLLLLLLLLLLLLL");
        }
        menu = screen.findViewById(R.id.menu_button);
        action = screen.findViewById(R.id.action_button);

        if(menu == null) {
            System.out.println("MERDEEEEEEEEEEEEEEEEEEEEEEEE");
        }

        // recuperation de la taille de l'ecran
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        cell_width = width / NB_CASE_LARGEUR;
        cell_height = height / NB_CASE_HAUTEUR;

        // remplissage des cellules
        cells = new Vector<>();
        for (int i = 0; i < NB_CASE_LARGEUR; i++) {
            cells.add(new Vector<Cell>(height));
            int x = i * cell_width;
            int y = 0;
            for (int j = 0; j < NB_CASE_HAUTEUR; j++) {
                cells.get(i).add(new Cell(context,this, x, y));
                y = y + cell_height;
            }
        }
        createsPersonnages();

        // Thread du jeu
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
       // Bitmap p_bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.char1_right);
      //Personnage p = new Personnage(p_bmp);
        // cells.get(4).get(4).setPersonnage(p);
    }

    @SuppressLint("WrongCall")
    @Override
    protected void onDraw(Canvas canvas) {
        // set background
        //Bitmap bg = BitmapFactory.decodeResource(this.getResources(), R.drawable.map1);
        //Rect src = new Rect(0,0,bg.getWidth(), bg.getHeight());
        Rect dest = new Rect(0,0,width, height);
        //canvas.drawBitmap(bg, src, dest,null);
       // cells.get(4).get(4).onDraw(canvas, cell_width, cell_height);
        for(int i = 0; i < NB_CASE_LARGEUR; i++) {
            for(int j = 0; j < NB_CASE_HAUTEUR; j++) {
                cells.get(i).get(j).onDraw(canvas, cell_width, cell_height);
            }
        }
    }

    protected void getPersonnageMenu(float x, float y) {
        menu.show();
        action.show();
        personnageSelected = false;
    }

    protected void getCellSelected(Cell c) {
        if(c.getPersonnage() == null){
            menu.hide();
            action.hide();
            personnageSelected = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        // indices de la cell touchee
        int cellX = (int) x / cell_width;
        int cellY = (int) y / cell_height;

        // eviter debordement
        if(cellX >= NB_CASE_LARGEUR)
            cellX = NB_CASE_LARGEUR;
        // eviter debordement
        if(cellY >= NB_CASE_HAUTEUR)
            cellY = NB_CASE_HAUTEUR;

        Cell c = cells.get(cellX).get(cellY);
        if(personnageSelected) {
            getPersonnageMenu(x,y);
        } else {
            getCellSelected(c);
        }
        return super.onTouchEvent(event);
    }
}