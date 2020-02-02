package com.example.myrpg.level;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.myrpg.game.GameView;
import com.example.myrpg.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LevelFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private static final String level_id = "level_id";
    private int levelId;

    public LevelFragment() {
        // Required empty public constructor
    }

    public LevelFragment(int levelId) {
        this.levelId = levelId;
    }

    public static LevelFragment newInstance(int levelId) {
        LevelFragment fragment = new LevelFragment();
        Bundle args = new Bundle();
        args.putInt(level_id, levelId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ConstraintLayout c = (ConstraintLayout) inflater.inflate(R.layout.game_screen, container, false);
        FloatingActionButton menu = c.findViewById(R.id.menu_button);
        FloatingActionButton action = c.findViewById(R.id.action_button);

        LinearLayout selectedPersonnageStats = c.findViewById(R.id.selectedPersonnageStats);
        ImageView selectedPersonnageImage = c.findViewById(R.id.img_stat_personnage);
        TextView selectedPersonnageName = c.findViewById(R.id.name);
        TextView selectedPersonnageHp = c.findViewById(R.id.hp);
        TextView selectedPersonnageMp = c.findViewById(R.id.mp);

        ArrayList<View> buttons = new ArrayList<>();
        buttons.add(menu);
        buttons.add(action);

        ArrayList<View> stats = new ArrayList<>();
        stats.add(selectedPersonnageStats);
        stats.add(selectedPersonnageImage);
        stats.add(selectedPersonnageName);
        stats.add(selectedPersonnageHp);
        stats.add(selectedPersonnageMp);

        GameView gameView = new GameView(getContext(), levelId, buttons, stats, mListener);

        c.addView(gameView);

        return c;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void finishGame(boolean terminated);
    }
}
