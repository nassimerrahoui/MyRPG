package com.example.myrpg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Vector;

public class GameView extends SurfaceView {

    /** attributs de l'ecran*/
    protected int width;
    protected int height;
    private SurfaceHolder holder;
    private Drawable bg;

    /** attributs des cellules du jeu */
    protected final static int NB_CASE_LARGEUR = 10;
    protected final static int NB_CASE_HAUTEUR = 6;
    protected final int cell_width;
    protected final int cell_height;
    protected Vector<Vector<Cell>> cells;

    /** Thread du jeu */
    private GameEngine gameEngine;

    /** attributs concernant les personnages */
    private ArrayList<Personnage> personnages = new ArrayList<Personnage>();
    private FloatingActionButton menu;
    private FloatingActionButton action;

    /** attributs de personnage selectionne lorsque qu'on touche l'ecran */
    private LinearLayout selectedPersonnageStats;
    private ImageView selectedPersonnageImage;
    private TextView selectedPersonnageName;
    private TextView selectedPersonnageHp;
    private TextView selectedPersonnageMp;
    private boolean selectedPersonnage;


    public GameView(Context context, ArrayList<View> buttons, ArrayList<View> stats) {
        super(context);

        //set background
        //bg = ContextCompat.getDrawable(context, R.drawable.map1);
        //setBackground(bg);
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
        init(context, buttons, stats);
    }

    private void init(Context context, ArrayList<View> buttons, ArrayList<View> stats) {

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

        // affichage des stats
        selectedPersonnageStats = (LinearLayout) stats.get(0);
        selectedPersonnageImage = (ImageView) stats.get(1);
        selectedPersonnageName = (TextView) stats.get(2);
        selectedPersonnageHp = (TextView) stats.get(3);
        selectedPersonnageMp = (TextView) stats.get(4);
        selectedPersonnage = false;

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

    protected void getPersonnageControlableMenu(Cell c) {
        menu.show();
        action.show();
        selectedPersonnage = true;

        // Afficher stats
        selectedPersonnageStats.setVisibility(View.VISIBLE);
        selectedPersonnageHp.setText(c.getPersonnage().hp + " HP");
        selectedPersonnageMp.setText(c.getPersonnage().mp + " MP");
        selectedPersonnageStats.bringToFront();
    }

    protected void getPersonnageIncontrolableMenu(Cell c) {
        menu.show();

        // Afficher stats
        selectedPersonnageStats.setVisibility(View.VISIBLE);
        selectedPersonnageHp.setText(c.getPersonnage().hp + " HP");
        selectedPersonnageMp.setText(c.getPersonnage().mp + " MP");
        selectedPersonnageStats.bringToFront();
    }

    protected void cancelPersonnageMenu() {
        menu.hide();
        action.hide();
        selectedPersonnageStats.setVisibility(View.GONE);
        selectedPersonnage = false;
        resetSelectableCells();
    }

    protected void onCellTouchEvent(Cell c) {
       boolean hasPerso = c.getPersonnage() != null;
        if(hasPerso) {
           if(c.getPersonnage().getId() > 0) {
               getPersonnageControlableMenu(c);
           } else {
               getPersonnageIncontrolableMenu(c);
           }
       }
    }

    protected void onSelectableCellTouchEvent(Cell c) {
        if(c.getPersonnage().hp - 10 < 0) {
            c.getPersonnage().hp = 0;
            Toast.makeText(getContext(),"Ennemi a ete tue", Toast.LENGTH_LONG).show();
        } else {
            c.getPersonnage().hp -= 10;
            Toast.makeText(getContext(),"Ennemi a perdu 10 hp", Toast.LENGTH_LONG).show();
        }
        c.setFlagSelectable(false);
        cancelPersonnageMenu();
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

        if(!selectedPersonnage) { onCellTouchEvent(c); }
        else {
            if(c.flagSelectable) { onSelectableCellTouchEvent(c);}
        }
        return super.onTouchEvent(event);
    }
}