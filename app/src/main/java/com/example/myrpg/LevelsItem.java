package com.example.myrpg;

public class LevelsItem {
    public boolean checked;
    public String title;

    public LevelsItem(String title) {
        this.checked = false;
        this.title = title;
    }
    public LevelsItem(String title, boolean checked) {
        this.checked = checked;
        this.title = title;
    }
}
