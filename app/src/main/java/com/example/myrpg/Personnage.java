package com.example.myrpg;

import android.graphics.Bitmap;

public class Personnage extends GameObject {

    protected String name;
    protected int hp;
    protected int mp;
    protected int atk;
    protected int def;

    protected Bitmap bmp;

    public Personnage(Bitmap bmp) {
        this.bmp = bmp;
        this.hp = 100;
        this.mp = 100;
        this.atk = 10;
        this.def = 10;
    }

    public Bitmap getBmp() {
        return bmp;
    }
}
