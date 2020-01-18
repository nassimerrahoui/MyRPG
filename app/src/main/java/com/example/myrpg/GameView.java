package com.example.myrpg;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Vector;

public class GameView extends SurfaceView {

    protected final static int NB_CASE_LARGEUR = 10;
    protected final static int NB_CASE_HAUTEUR = 6;
    protected int width;
    protected int height;
    protected final int cell_width;
    protected final int cell_height;
    protected Vector<Vector<Cell>> cells;
    private SurfaceHolder holder;
    private GameEngine gameEngine;
    private long lastClick;
    private ArrayList<Personnage> personnages = new ArrayList<Personnage>();
    private boolean personnageSelected;
    private FloatingActionButton menu;
    private FloatingActionButton action;
    private boolean isInitBackground;

    public GameView(Context context, ArrayList<View> buttons) {
        super(context);
        //set background
        setBackgroundColor(Color.TRANSPARENT);

        // recuperation de la taille de l'ecran
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        cell_width = width / NB_CASE_LARGEUR;
        cell_height = height / NB_CASE_HAUTEUR;

        // initialisation
        init(context, buttons);
    }

    private void init(Context context, ArrayList<View> buttons) {

        // creations des cellules du jeu
        cells = new Vector<>();
        for (int i = 0; i < NB_CASE_LARGEUR; i++) {
            cells.add(new Vector<Cell>(height));
            int x = i * cell_width;
            int y = 0;
            for (int j = 0; j < NB_CASE_HAUTEUR; j++) {
                cells.get(i).add(new Cell(context,this, x, y, i, j));
                y = y + cell_height;
            }
        }

        // ajout des personnages aux cellules
        createsPersonnages();

        // construction des boutons
        menu = (FloatingActionButton) buttons.get(0);
        action = (FloatingActionButton) buttons.get(1);
        createListener();

        // initialisations des attributs
        personnageSelected = false;
        isInitBackground = false;

        gameLoop();
    }

    /** Thread de la partie */
    private void gameLoop() {
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

    /** Met a jour l'affichage de l'ecran */
    @SuppressLint("WrongCall")
    @Override
    protected void onDraw(Canvas canvas) {

        /** PROBLEM ECRASEMENT BACKGROUND */
        if(!isInitBackground) {
            // set background
            Bitmap bg = BitmapFactory.decodeResource(this.getResources(), R.drawable.map1);
            Rect src = new Rect(0, 0, bg.getWidth(), bg.getHeight());
            Rect dest = new Rect(0, 0, width, height);
            canvas.drawBitmap(bg, src, dest, null);
            isInitBackground = true;
            Log.i("BACKGROUND", "SETTER");
        }
        /** PROBLEM ECRASEMENT BACKGROUND */

        for(int i = 0; i < NB_CASE_LARGEUR; i++) {
            for(int j = 0; j < NB_CASE_HAUTEUR; j++) {
                cells.get(i).get(j).onDraw(canvas, cell_width, cell_height);
            }
        }
    }

    public void createsPersonnages() {
        Bitmap p1_bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.char1_right);
        Personnage p1 = new Personnage(p1_bmp, true);
        cells.get(4).get(4).setPersonnage(p1);

        Bitmap p2_bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.char1_right);
        Bitmap mutable_p2_bmp = p2_bmp.copy(Bitmap.Config.ARGB_8888, true);
        Personnage p2 = new Personnage(mutable_p2_bmp, false);
        cells.get(2).get(2).setPersonnage(p2);
    }

    public void createListener() {
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.hide();
                action.hide();
                drawSelectableCells();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelPersonnageMenu();
            }
        });
    }

    protected void getPersonnageControlableMenu() {
        menu.show();
        action.show();
        personnageSelected = true;
    }

    protected void getPersonnageIncontrolableMenu() {
        menu.show();
        // Afficher stats ou quelque chose comme ça
    }

    protected void cancelPersonnageMenu() {
        menu.hide();
        action.hide();
        personnageSelected = false;
        resetSelectableCells();
    }

    protected void onCellTouchEvent(Cell c) {
       boolean hasPerso = c.getPersonnage() != null;
        if(hasPerso) {
           if(c.getPersonnage().getId() > 0) {
               getPersonnageControlableMenu();
           } else {
               getPersonnageIncontrolableMenu();
           }
       }
    }

    protected void onSelectableCellTouchEvent(Cell c) {
        c.getPersonnage().hp = 0;
        c.setFlagSelectable(false);
        Toast.makeText(getContext(),"Ennemi a ete tue", Toast.LENGTH_LONG).show();
    }

    protected void drawSelectableCells() {
        for(int i = 0; i < NB_CASE_LARGEUR; i++) {
            for(int j = 0; j < NB_CASE_HAUTEUR; j++) {
                if(cells.get(i).get(j).getPersonnage() != null && cells.get(i).get(j).getPersonnage().getId() < 0) {
                    cells.get(i).get(j).setFlagSelectable(true);
                    //Log.i("CELL", "REF " + cells.get(i).get(j).toString());
                    //Log.i("CELL", "Set flag to true " + i + ", " + j);
                }
            }
        }
    }

    protected void resetSelectableCells() {
        //Log.i("RESET CELLS", "reset cells");
        for (Vector<Cell> v : cells) {
            for (Cell c : v) {
                c.setFlagSelectable(false);
            }
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

        if(!personnageSelected) { onCellTouchEvent(c); }
        else {
            if(c.flagSelectable) { onSelectableCellTouchEvent(c);}
        }
        return super.onTouchEvent(event);
    }
}