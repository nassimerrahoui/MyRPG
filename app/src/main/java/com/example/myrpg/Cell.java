package com.example.myrpg;

import android.content.Context;
import android.graphics.Canvas;
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

    public void onDraw(Canvas canvas) {
        if(personnage != null) {
            Rect src = new Rect (0, 0, personnage.getBmp().getWidth(), personnage.getBmp().getHeight());
            Rect dst = new Rect ( x, y, x+personnage.getBmp().getWidth()*3, y+personnage.getBmp().getHeight()*3);
            canvas.drawBitmap(personnage.getBmp(), src, dst, null);
        }
    }
}
