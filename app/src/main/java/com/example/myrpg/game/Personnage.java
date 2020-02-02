package com.example.myrpg.game;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Random;

public class Personnage {

    protected static int compteurIdControlable = 1;
    protected static int compteurIdIncontrolable = -1;
    protected final int id;
    protected final String name;
    protected int hp;
    protected int previousHp;
    protected int mp;
    protected int atk;
    protected int def;
    protected Random RNG;
    protected boolean hasMoved;

    protected Bitmap bmp1;
    protected Bitmap bmp2;

    protected boolean animationFrame;

    public Personnage(Bitmap bmp1, Bitmap bmp2, boolean controlable) {
        this.bmp1 = bmp1;
        this.bmp2 = bmp2;
        this.hp = 100;
        this.previousHp = hp;
        this.mp = 100;
        this.atk = 10;
        this.def = 10;
        this.RNG = new Random();
        this.hasMoved = false;
        this.animationFrame = false;
        if(controlable) {
            this.id = Personnage.compteurIdControlable++;
            this.name = "Principiante";
        } else {
            this.id = Personnage.compteurIdIncontrolable--;
            this.name = "El diablo";
        }
    }

    public int getId() {
        return id;
    }
    
    public Bitmap getBmp1() { return bmp1; }
    public Bitmap getBmp2() { return bmp2; }

    public boolean hasMoved() {
        return hasMoved;
    }
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public void setBmp1(Bitmap bmp1) { this.bmp1 = bmp1; }
    public void setBmp2(Bitmap bmp2) { this.bmp2 = bmp2; }
}
