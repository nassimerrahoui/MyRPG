package com.example.myrpg.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrpg.R;

public class MenuActivity extends AppCompatActivity {

    Button story_mode;
    Button options;
    Button quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen);

        story_mode = (Button) findViewById(R.id.story_mode);
        options = (Button) findViewById(R.id.options);
        quit = (Button) findViewById(R.id.quit);

        story_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goStoryMode();
            }
        });

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goOptions();
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goQuit();
            }
        });
    }

    public void goStoryMode() {
        startActivity(new Intent(this, LevelsActivity.class));
    }

    public void goOptions() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void goQuit() {
        startActivity(new Intent(this,MainActivity.class));
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
