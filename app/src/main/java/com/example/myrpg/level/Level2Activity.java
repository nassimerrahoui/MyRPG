package com.example.myrpg.level;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myrpg.R;
import com.example.myrpg.menu.LevelsActivity;

public class Level2Activity extends AppCompatActivity implements LevelFragment.OnFragmentInteractionListener {

    private final static int id = 2;
    LevelFragment level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frame = new FrameLayout(this);
        frame.setId(R.id.level2activity);
        setContentView(frame);

        FragmentManager fragmentManager = getSupportFragmentManager();
        level = new LevelFragment();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.level2activity,level);
        ft.commit();

        ft.show(level);
    }


    @Override
    public void finishGame() {
        Intent levels_screen = new Intent(this, LevelsActivity.class);
        levels_screen.putExtra("level", id);
        startActivity(levels_screen);
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
