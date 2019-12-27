package com.example.myrpg;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Level0Activity extends AppCompatActivity {

    GameView gameView;
    FrameLayout frameLayout;
    ConstraintLayout gameButtons;
    FloatingActionButton menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        frameLayout = new FrameLayout(this);
        gameButtons = new ConstraintLayout(this);
        menu = new FloatingActionButton(this);
        menu.setId(R.id.menu);

        ConstraintLayout.LayoutParams menuParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );

        ConstraintLayout.LayoutParams frameLayoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );

        menuParams.rightToRight = frameLayout.getRight();
        menuParams.bottomToBottom = frameLayout.getBottom();
        menuParams.topToTop = frameLayout.getTop();
        menuParams.leftToLeft = frameLayout.getLeft();

        menu.setLayoutParams(menuParams);
        gameButtons.setLayoutParams(params);
        frameLayout.setLayoutParams(frameLayoutParams);

        gameButtons.addView(menu);
        frameLayout.addView(gameView);
        frameLayout.addView(gameButtons);
        setContentView(frameLayout);
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
