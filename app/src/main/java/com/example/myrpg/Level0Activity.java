package com.example.myrpg;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Level0Activity extends AppCompatActivity implements LevelFragment.OnFragmentInteractionListener {

    LevelFragment level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frame = new FrameLayout(this);
        frame.setId(R.id.level0activity);
        setContentView(frame);

        FragmentManager fragmentManager = getSupportFragmentManager();
        level = new LevelFragment();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.level0activity,level);
        ft.commit();

        ft.show(level);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
