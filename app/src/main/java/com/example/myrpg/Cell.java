package com.example.myrpg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

public class Cell extends View {

    protected int x;
    protected int y;
    protected Personnage personnage;
    protected GameView gameBoard;
    protected boolean flagSelectable;

    public Cell(Context context, GameView gameBoard, int x, int y) {
        super(context);
        this.gameBoard = gameBoard;
        this.x = x;
        this.y = y;
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
        if(personnage != null) {
            Rect src = new Rect (0, 0, personnage.getBmp().getWidth(), personnage.getBmp().getHeight());
            Rect dst = new Rect ( x, y, x+personnage.getBmp().getWidth(), y+personnage.getBmp().getHeight());
            canvas.drawBitmap(personnage.getBmp(), src, dst, null);
        }
        Paint myPaint = new Paint();
        if(flagSelectable) {

            //Log.i("CELL", "SELECTABLE " + getX() + ", " + getY());
            myPaint.setStyle(Paint.Style.FILL);
        } else {
            myPaint.setStyle(Paint.Style.STROKE);
        }
        Log.v("Tag", "y : " + y + "x : "+x);
        canvas.drawRect(x,y,x+cell_width,y+cell_height, myPaint);
    }

    public void setFlagSelectable(boolean b) {
        this.flagSelectable = b;
    }

    public int getPosX() {
        return x;
    }

    public int getPosY() {
        return y;
    }
}
