package com.example.myrpg.menu;

import java.util.Calendar;
import java.util.Date;

public class LevelsItem {
    public boolean checked;
    public String title;
    public String date;

    public LevelsItem(String title, boolean checked, String date) {
        this.checked = checked;
        this.title = title;
        this.date = date;
    }
}
