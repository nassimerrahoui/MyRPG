package com.example.myrpg;

import android.graphics.Bitmap;

public class Personnage extends GameObject {

    protected static int compteurIdControlable = 1;
    protected static int compteurIdIncontrolable = -1;
    protected final int id;
    protected String name;
    protected int hp;
    protected int mp;
    protected int atk;
    protected int def;

    protected Bitmap bmp;

    public Personnage(Bitmap bmp, boolean controlable) {
        this.bmp = bmp;
        this.hp = 100;
        this.mp = 100;
        this.atk = 10;
        this.def = 10;
        if(controlable) {
            this.id = Personnage.compteurIdControlable++;
        } else {
            this.id = Personnage.compteurIdIncontrolable--;
        }
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public int getId() {
        return id;
    }
}
