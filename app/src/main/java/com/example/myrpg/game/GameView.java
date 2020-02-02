package com.example.myrpg.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myrpg.R;
import com.example.myrpg.level.LevelFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class GameView extends SurfaceView {

    /** attributs interaction avec activité */
    LevelFragment.OnFragmentInteractionListener listener;

    /** attributs de l'ecran*/
    protected int width;
    protected int height;
    private SurfaceHolder holder;
    private Drawable bg;
    private int levelId;

    /** attributs des cellules du jeu */
    protected final static int NB_CASE_LARGEUR = 10;
    protected final static int NB_CASE_HAUTEUR = 6;
    protected final int cell_width;
    protected final int cell_height;
    protected Vector<Vector<Cell>> cells;

    /** Thread du jeu */
    private GameEngine gameEngine;
    private boolean isWin;

    /** attributs concernant les personnageControlables */
    protected ArrayList<Personnage> personnageControlables = new ArrayList<Personnage>();
    protected ArrayList<Personnage> personnageIncontrolables = new ArrayList<Personnage>();
    private FloatingActionButton menu;
    private FloatingActionButton action;
    private final static ReentrantLock lock = new ReentrantLock();
    private final static Condition condition = lock.newCondition();

    /** attributs de personnage selectionne lorsque qu'on touche l'ecran */
    private LinearLayout selectedPersonnageStats;
    private ImageView selectedPersonnageImage;
    private TextView selectedPersonnageName;
    private TextView selectedPersonnageHp;
    private TextView selectedPersonnageMp;
    private Personnage selectedPersonnage;


    public GameView(Context context, int levelId, ArrayList<View> buttons, ArrayList<View> stats, LevelFragment.OnFragmentInteractionListener listener) {
        super(context);

        //set listener
        this.listener = listener;

        //set background
        //bg = ContextCompat.getDrawable(context, R.drawable.map1);
        //setBackground(bg);
        setBackgroundColor(Color.TRANSPARENT);

        // level
        this.levelId = levelId;

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

        // ajout des personnageControlables aux cellules
        createsPersonnages();

        // construction des boutons
        menu = (FloatingActionButton) buttons.get(0);
        menu.setImageResource(R.drawable.cancel);
        action = (FloatingActionButton) buttons.get(1);
        action.setImageResource(R.drawable.sword);
        createListener();

        // affichage des stats
        selectedPersonnageStats = (LinearLayout) stats.get(0);
        selectedPersonnageImage = (ImageView) stats.get(1);
        selectedPersonnageName = (TextView) stats.get(2);
        selectedPersonnageHp = (TextView) stats.get(3);
        selectedPersonnageMp = (TextView) stats.get(4);
        selectedPersonnage = null;

        gameLoop();
    }

    /** Thread de la partie */
    private void gameLoop() {
        isWin = false;
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
        if(!isWin) {
            if(isLost()) {
                ((Activity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(true);
                        builder.setTitle(getResources().getString(R.string.level_title)+levelId);
                        builder.setMessage(getResources().getString(R.string.lost));
                        builder.setPositiveButton(getResources().getString(R.string.lost_msg), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.finishGame(false);
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            } else if(gameEngine.isRunning()) {
                for (int i = 0; i < NB_CASE_LARGEUR; i++) {
                    for (int j = 0; j < NB_CASE_HAUTEUR; j++) {
                        cells.get(i).get(j).onDraw(canvas, cell_width, cell_height);
                    }
                }
            }
        } else {
            ((Activity) getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(true);
                    builder.setTitle(getResources().getString(R.string.level_title) + levelId);
                    builder.setMessage(getResources().getString(R.string.win));
                    builder.setPositiveButton(getResources().getString(R.string.win_msg), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.finishGame(true);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }

    public void createsPersonnages() {
        GeneratePersonnage.createPersonnagesControlables(this,cells,levelId);
        GeneratePersonnage.createPersonnagesIncontrolables(this,cells,levelId);
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
        selectedPersonnage = c.getPersonnage();

        MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.unshit);
        mp.start();

        // Afficher stats
        selectedPersonnageStats.setVisibility(View.VISIBLE);
        selectedPersonnageImage.setBackground(new BitmapDrawable(getResources(),c.personnage.getBmp1()));
        selectedPersonnageHp.setText(c.getPersonnage().hp + getResources().getString(R.string.HP));
        selectedPersonnageMp.setText(c.getPersonnage().mp + getResources().getString(R.string.MP));
        selectedPersonnageStats.bringToFront();
    }

    protected void getPersonnageIncontrolableMenu(Cell c) {
        menu.show();

        // Afficher stats
        selectedPersonnageStats.setVisibility(View.VISIBLE);
        selectedPersonnageImage.setBackground(new BitmapDrawable(getResources(),c.personnage.getBmp1()));
        selectedPersonnageHp.setText(c.getPersonnage().hp + getResources().getString(R.string.HP));
        selectedPersonnageMp.setText(c.getPersonnage().mp + getResources().getString(R.string.MP));
        selectedPersonnageStats.bringToFront();
    }

    protected void cancelPersonnageMenu() {
        menu.hide();
        action.hide();
        selectedPersonnageStats.setVisibility(View.GONE);
        selectedPersonnage = null;
        resetSelectableCells();
    }

    protected void onCellTouchEvent(Cell c) {
       boolean hasPerso = c.getPersonnage() != null;
        if(hasPerso) {
           if(c.getPersonnage().getId() > 0 && !c.getPersonnage().hasMoved) {
               getPersonnageControlableMenu(c);
           } else {
               getPersonnageIncontrolableMenu(c);
           }
       }
    }

    protected void onSelectableCellTouchEvent(Cell c) {
        // - 100000 pour le test
        MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.swing3);
        mp.start();
        Personnage p = c.getPersonnage();
        int dmg = p.def + p.RNG.nextInt(6) - 3 + selectedPersonnage.atk + selectedPersonnage.RNG.nextInt( 10) - 5;
        if(p.hp - dmg <= 0) {
            p.hp = 0;
            personnageIncontrolables.remove(p);
            mp = MediaPlayer.create(getContext(), R.raw.ogre2);
            mp.start();
            Toast.makeText(getContext(), getResources().getString(R.string.enemy_down), Toast.LENGTH_SHORT).show();
        } else {
            c.getPersonnage().hp -= dmg;
            Toast.makeText(getContext(),getResources().getString(R.string.enemy_hurt) + dmg + getResources().getString(R.string.HP), Toast.LENGTH_SHORT).show();
        }
        c.setFlagSelectable(false);
        int indexSelectecPersonnage = personnageControlables.indexOf(selectedPersonnage);
        personnageControlables.get(indexSelectecPersonnage).setHasMoved(true);
        cancelPersonnageMenu();
    }

    protected void ennemyAction(){
        for(Personnage p: personnageIncontrolables) {
            Collections.shuffle(personnageControlables);
            Personnage controlable = personnageControlables.get(0);
            int dmg = controlable.def + controlable.RNG.nextInt(10) - 5 + p.atk + p.RNG.nextInt( 6) - 3;
            if(controlable.hp - dmg <= 0) {
                controlable.hp = 0;
                personnageControlables.remove(controlable);
                Toast.makeText(getContext(),getResources().getString(R.string.player_down), Toast.LENGTH_SHORT).show();
            } else {
                controlable.hp -= dmg;
                Toast.makeText(getContext(),getResources().getString(R.string.player_hurt) + dmg + getResources().getString(R.string.HP), Toast.LENGTH_SHORT).show();
            }
        }
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
        if (cellX >= NB_CASE_LARGEUR)
            cellX = NB_CASE_LARGEUR;
        // eviter debordement
        if (cellY >= NB_CASE_HAUTEUR)
            cellY = NB_CASE_HAUTEUR;

        Cell c = cells.get(cellX).get(cellY);

        if (selectedPersonnage == null) {
            onCellTouchEvent(c);
        } else {
            if (c.flagSelectable) {
                onSelectableCellTouchEvent(c);
            }
        }
        if(personnageIncontrolables.size() == 0) {
            isWin = true;
        }
        if(allControllablesMoved(personnageControlables)) {
            ennemyAction();
            for(Personnage p: personnageControlables) {
                p.setHasMoved(false);
            }
        }
        return super.onTouchEvent(event);
    }

    public boolean allControllablesMoved(ArrayList<Personnage> controlables) {
        for(int i = 0; i < controlables.size(); i++) {
            if(!controlables.get(i).hasMoved()) {
                return false;
            }
        }
        return true;
    }

    public boolean isWin() {
        return isWin;
    }

    public boolean isLost() {
        return personnageControlables.size() <= 0;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_APP_SWITCH) {
            try {
                gameEngine.wait();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
}