package com.example.myrpg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Cell extends View {

    protected int x;
    protected int y;
    protected int colonne;
    protected int ligne;
    protected Personnage personnage;
    protected GameView gameBoard;
    protected boolean flagSelectable;

    public Cell(Context context, GameView gameBoard, int x, int y, int i, int j) {
        super(context);
        this.gameBoard = gameBoard;
        this.x = x;
        this.y = y;
        this.colonne = i;
        this.ligne = j;
        this.flagSelectable = false;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public void setPersonnage(Personnage personnage) {
        this.personnage = personnage;
    }

    protected void update(Move m) {
        switch (m) {
            case up:
                break;
            case down:
                break;
            case right:
                break;
            case left:
                break;
            default:
                break;
        }
    }

    public void onDraw(Canvas canvas, int cell_width, int cell_height) {

        Paint myPaint = new Paint();

        if(personnage != null) {
            if(this.flagSelectable) {
                //Log.i("DRAW", "SELECTABLE ");
                //Log.i("CELL DRAW", "colonne " + colonne + " : ligne " + ligne);
                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(10);
                myPaint.setColor(Color.RED);
                canvas.drawRect(x+10,y+10,x+cell_width-10,y+cell_height-10, myPaint);
                canvas.drawBitmap(personnage.getBmp1(), x + cell_width/4, y + cell_height/4, null);
            } else if(personnage.hp < personnage.previousHp){
                personnage.previousHp = personnage.hp;
                if(personnage.hp == 0) {
                    //Log.i("PERSO NULL", "OK");
                    personnage.getBmp1().eraseColor(Color.TRANSPARENT);
                    personnage = null;
                }
            } else {
                //Log.i("PERSO HERE", "OK");
                canvas.drawBitmap(personnage.getBmp1(), x + cell_width/4, y + cell_height/4, null);
            }
        } else {
            //Log.i("EMPTY CELL", "OK");
            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setColor(Color.BLACK);
            canvas.drawRect(x,y,x+cell_width,y+cell_height, myPaint);
            myPaint.setStyle(Paint.Style.FILL);
            myPaint.setColor(Color.WHITE);
            canvas.drawRect(x+2,y+2,x+cell_width-2,y+cell_height-2, myPaint);
        }
    }

    public void setFlagSelectable(boolean b) {
        //Log.i("SET SELECTABLE", "SETTER");
        this.flagSelectable = b;
    }

    public int getPosX() {
        return x;
    }

    public int getPosY() {
        return y;
    }
}
