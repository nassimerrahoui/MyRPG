package com.example.myrpg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class Cell extends View {

    protected int x;
    protected int y;
    protected Personnage personnage;
    protected GameView gameBoard;

    public Cell(Context context, GameView gameBoard, int x, int y) {
        super(context);
        this.gameBoard = gameBoard;
        this.x = x;
        this.y = y;
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
        } else {
            Paint myPaint = new Paint();
            myPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(x,y,x+cell_width,y+cell_height, myPaint);
        }
    }
}
