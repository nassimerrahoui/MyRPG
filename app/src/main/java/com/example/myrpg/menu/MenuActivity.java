package com.example.myrpg.menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrpg.R;

public class MenuActivity extends AppCompatActivity {

    Button continue_mode;
    Button new_game_mode;
    Button quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen);

        continue_mode = (Button) findViewById(R.id.continue_mode);
        new_game_mode = (Button) findViewById(R.id.new_game_mode);
        quit = (Button) findViewById(R.id.quit);

        continue_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goContinueMode();
            }
        });

        new_game_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNewGameMode();
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goQuit();
            }
        });

    }

    public void goContinueMode() {
        MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.loadsave);
        mp.start();
        startActivity(new Intent(this, LevelsActivity.class));
    }

    public void goNewGameMode() {
        MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.loadsave);
        mp.start();
        SharedPreferences.Editor edit = this.getSharedPreferences("PROGRESSION",MODE_PRIVATE).edit();
        edit.clear();
        edit.apply();
        startActivity(new Intent(this, LevelsActivity.class));
    }

    public void goQuit() {
        finishAffinity();
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
