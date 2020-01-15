package com.example.myrpg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
            Rect src = new Rect (0, 0, personnage.getBmp().getWidth(), personnage.getBmp().getHeight());
            Rect dst = new Rect ( x, y, x+personnage.getBmp().getWidth(), y+personnage.getBmp().getHeight());
            canvas.drawBitmap(personnage.getBmp(), src, dst, null);
        } else {
            Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
            Bitmap bmp = Bitmap.createBitmap(cell_width, cell_height, conf);
            bmp.eraseColor(Color.BLACK);
            Rect src = new Rect (0, 0, x, y);
            Rect dst = new Rect ( x, y, x+cell_width, y+cell_height);
            canvas.drawBitmap(bmp, src, dst, null);

            myPaint.setStyle(Paint.Style.FILL);
            myPaint.setColor(Color.BLACK);
            canvas.drawRect(x,y,x+cell_width+10,y+cell_height+10, myPaint);
        }

        if(this.flagSelectable) {
            //Log.i("DRAW", "SELECTABLE ");
            //Log.i("CELL DRAW", "colonne " + colonne + " : ligne " + ligne);
            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth(10);
            myPaint.setColor(Color.RED);
            canvas.drawRect(x,y,x+cell_width,y+cell_height, myPaint);
            //Log.i("AFTER DRAW", "SELECTABLE ");
        } else {
            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setColor(Color.BLACK);
            canvas.drawRect(x,y,x+cell_width,y+cell_height, myPaint);
        }
        //canvas.drawRect(x,y,x+cell_width,y+cell_height, myPaint);
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
