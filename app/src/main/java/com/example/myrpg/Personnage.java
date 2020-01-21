package com.example.myrpg;

import android.graphics.Bitmap;

public class Personnage extends GameObject {

    protected static int compteurIdControlable = 1;
    protected static int compteurIdIncontrolable = -1;
    protected final int id;
    protected String name;
    protected int hp;
    protected int previousHp;
    protected int mp;
    protected int atk;
    protected int def;

    protected Bitmap bmp1;
    protected Bitmap bmp2;

    public Personnage(Bitmap bmp1, Bitmap bmp2, boolean controlable) {
        this.bmp1 = bmp1;
        this.bmp2 = bmp2;
        this.hp = 100;
        this.previousHp = hp;
        this.mp = 100;
        this.atk = 10;
        this.def = 10;
        if(controlable) {
            this.id = Personnage.compteurIdControlable++;
        } else {
            this.id = Personnage.compteurIdIncontrolable--;
        }
    }

    public Bitmap getBmp1() {
        return bmp1;
    }

    public void setBmp1(Bitmap bmp1) {
        this.bmp1 = bmp1;
    }

    public int getId() {
        return id;
    }
}
