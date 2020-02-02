package com.example.myrpg.menu;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrpg.R;
import com.example.myrpg.level.Level0Activity;
import com.example.myrpg.level.Level1Activity;
import com.example.myrpg.level.Level2Activity;

import java.util.ArrayList;
import java.util.List;

public class LevelsActivity extends AppCompatActivity {

    ListView levels;
    protected static ArrayList<Integer> levelsDone = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_screen);

        levels = findViewById(R.id.levels);

        int levelDone = getIntent().getIntExtra("level", -1);
        boolean isDone = getIntent().getBooleanExtra("terminated", false);
        if(!levelsDone.contains(levelDone) && isDone) {
            levelsDone.add(levelDone);
        }
        SharedPreferences progression = getSharedPreferences("PROGRESSION", MODE_PRIVATE);
        for(int i=0; i < 3; i++) {
            String levelDoneSharedPreferences = progression.getString("Level " + i,"false");
            if(levelDoneSharedPreferences.equals("true") && !levelsDone.contains(i)) {
                levelsDone.add(i);
            }
        }


        List<LevelsItem> items = new ArrayList<>(3);
        for(int i=0; i < 3; i++) {
            Boolean levelCleared = levelsDone.contains(i);
            items.add(new LevelsItem("Level " + i, levelCleared));
            SharedPreferences.Editor edit = progression.edit();
            edit.putString("Level " + i, levelCleared.toString());
            edit.apply();
        }

        LevelsAdapter levelsAdapater = new LevelsAdapter(this, R.layout.level_item, items);
        levels.setAdapter(levelsAdapater);
        levels.setItemsCanFocus(true);

        levels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.swing3);
                mp.start();
                goLevel(position);
            }
        });
    }

    public void goLevel(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, Level0Activity.class));
                break;
            case 1:
                startActivity(new Intent(this, Level1Activity.class));
                break;
            case 2:
                startActivity(new Intent(this, Level2Activity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideSystemUI();
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
