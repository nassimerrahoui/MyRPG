package com.example.myrpg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class LevelsActivity extends AppCompatActivity {

    ListView levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_screen);

        levels = (ListView) findViewById(R.id.levels);

        String items [] = new String[3];
        for(int i=0; i < 3; i++) {
            items[i] = "Level " + i;
        }

        ArrayAdapter<String> levelsAdapater =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        levels.setAdapter(levelsAdapater);

        levels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
