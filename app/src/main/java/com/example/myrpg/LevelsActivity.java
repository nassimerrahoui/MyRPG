package com.example.myrpg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class LevelsActivity extends AppCompatActivity {

    ListView levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_screen);

        levels = findViewById(R.id.levels);

        int levelDone = getIntent().getIntExtra("level", -1);

        List<LevelsItem> items = new ArrayList<>(3);
        for(int i=0; i < 3; i++) {
            items.add(new LevelsItem("Level " + i,levelDone == i));
        }

        LevelsAdapter levelsAdapater = new LevelsAdapter(this, R.layout.level_item, items);
        levels.setAdapter(levelsAdapater);
        levels.setItemsCanFocus(true);

        levels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("LISENER ITEM", "OK");
                goLevel(position);
            }
        });
    }

    public void goLevel(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this,Level0Activity.class));
                break;
            case 1:
                break;
            case 2:
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
