package com.example.myrpg.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.myrpg.R;

import java.util.Vector;

public class GeneratePersonnage {

    public static void createPersonnagesControlables(GameView v, Vector<Vector<Cell>> cells, int levelId) {

        if(levelId == 0) {
            Bitmap p1_bmp = BitmapFactory.decodeResource(v.getResources(), R.drawable.hero1);
            Bitmap mutable_p1_bmp = p1_bmp.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap p1_bmp2 = BitmapFactory.decodeResource(v.getResources(), R.drawable.hero2);
            Bitmap mutable_p1_bmp2 = p1_bmp2.copy(Bitmap.Config.ARGB_8888, true);
            Personnage p1 = new Personnage(mutable_p1_bmp, mutable_p1_bmp2, true);
            cells.get(4).get(4).setPersonnage(p1);
            v.personnageControlables.add(p1);
        } else if(levelId == 1) {
            Bitmap p1_bmp = BitmapFactory.decodeResource(v.getResources(), R.drawable.hero1);
            Bitmap mutable_p1_bmp = p1_bmp.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap p1_bmp2 = BitmapFactory.decodeResource(v.getResources(), R.drawable.hero2);
            Bitmap mutable_p1_bmp2 = p1_bmp2.copy(Bitmap.Config.ARGB_8888, true);
            Personnage p1 = new Personnage(mutable_p1_bmp, mutable_p1_bmp2, true);
            cells.get(4).get(4).setPersonnage(p1);
            v.personnageControlables.add(p1);

            Bitmap p2_bmp = BitmapFactory.decodeResource(v.getResources(), R.drawable.compagnon1);
            Bitmap mutable_p2_bmp = p2_bmp.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap p2_bmp2 = BitmapFactory.decodeResource(v.getResources(), R.drawable.compagnon2);
            Bitmap mutable_p2_bmp2 = p2_bmp2.copy(Bitmap.Config.ARGB_8888, true);
            Personnage p2 = new Personnage(mutable_p2_bmp, mutable_p2_bmp2, true);
            cells.get(4).get(3).setPersonnage(p2);
            v.personnageControlables.add(p2);
        } else if(levelId == 2) {
            Bitmap p1_bmp = BitmapFactory.decodeResource(v.getResources(), R.drawable.hero1);
            Bitmap mutable_p1_bmp = p1_bmp.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap p1_bmp2 = BitmapFactory.decodeResource(v.getResources(), R.drawable.hero2);
            Bitmap mutable_p1_bmp2 = p1_bmp2.copy(Bitmap.Config.ARGB_8888, true);
            Personnage p1 = new Personnage(mutable_p1_bmp, mutable_p1_bmp2, true);
            cells.get(4).get(4).setPersonnage(p1);
            v.personnageControlables.add(p1);
        }

    }

    public static void createPersonnagesIncontrolables(GameView v, Vector<Vector<Cell>> cells, int levelId) {
        //Log.i("LVL", ""+levelId);

        if(levelId == 0) {
            Bitmap p2_bmp = BitmapFactory.decodeResource(v.getResources(), R.drawable.mechant1);
            Bitmap mutable_p2_bmp = p2_bmp.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap p2_bmp2 = BitmapFactory.decodeResource(v.getResources(), R.drawable.mechant2);
            Bitmap mutable_p2_bmp2 = p2_bmp2.copy(Bitmap.Config.ARGB_8888, true);
            Personnage p2 = new Personnage(mutable_p2_bmp, mutable_p2_bmp2, false);
            cells.get(2).get(2).setPersonnage(p2);
            v.personnageIncontrolables.add(p2);

        } else if(levelId == 1) {
            Bitmap p2_bmp = BitmapFactory.decodeResource(v.getResources(), R.drawable.mechant1);
            Bitmap mutable_p2_bmp = p2_bmp.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap p2_bmp2 = BitmapFactory.decodeResource(v.getResources(), R.drawable.mechant2);
            Bitmap mutable_p2_bmp2 = p2_bmp.copy(Bitmap.Config.ARGB_8888, true);
            Personnage p2 = new Personnage(mutable_p2_bmp, mutable_p2_bmp2, false);
            cells.get(2).get(2).setPersonnage(p2);
            v.personnageIncontrolables.add(p2);

            Bitmap p3_bmp = BitmapFactory.decodeResource(v.getResources(), R.drawable.mechant1);
            Bitmap mutable_p3_bmp = p3_bmp.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap p3_bmp2 = BitmapFactory.decodeResource(v.getResources(), R.drawable.mechant2);
            Bitmap mutable_p3_bmp2 = p3_bmp2.copy(Bitmap.Config.ARGB_8888, true);
            Personnage p3 = new Personnage(mutable_p3_bmp, mutable_p3_bmp2, false);
            cells.get(2).get(3).setPersonnage(p3);
            v.personnageIncontrolables.add(p3);
        } else if(levelId == 2) {
            Bitmap p2_bmp = BitmapFactory.decodeResource(v.getResources(), R.drawable.char1_right);
            Bitmap mutable_p2_bmp = p2_bmp.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap p2_bmp2 = BitmapFactory.decodeResource(v.getResources(), R.drawable.char1_down);
            Bitmap mutable_p2_bmp2 = p2_bmp2.copy(Bitmap.Config.ARGB_8888, true);
            Personnage p2 = new Personnage(mutable_p2_bmp, mutable_p2_bmp2, false);
            cells.get(2).get(2).setPersonnage(p2);
            v.personnageIncontrolables.add(p2);

            Bitmap p3_bmp = BitmapFactory.decodeResource(v.getResources(), R.drawable.incontrolable);
            Bitmap mutable_p3_bmp = p3_bmp.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap p3_bmp2 = BitmapFactory.decodeResource(v.getResources(), R.drawable.incontrolable_2);
            Bitmap mutable_p3_bmp2 = p3_bmp2.copy(Bitmap.Config.ARGB_8888, true);
            Personnage p3 = new Personnage(mutable_p3_bmp, mutable_p3_bmp2, false);
            cells.get(2).get(3).setPersonnage(p3);
            v.personnageIncontrolables.add(p3);

            Bitmap p4_bmp = BitmapFactory.decodeResource(v.getResources(), R.drawable.char1_right);
            Bitmap mutable_p4_bmp = p4_bmp.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap p4_bmp2 = BitmapFactory.decodeResource(v.getResources(), R.drawable.char1_down);
            Bitmap mutable_p4_bmp2 = p4_bmp2.copy(Bitmap.Config.ARGB_8888, true);
            Personnage p4 = new Personnage(mutable_p4_bmp, mutable_p4_bmp2, false);
            cells.get(2).get(4).setPersonnage(p4);
            v.personnageIncontrolables.add(p4);
        }

    }

}
