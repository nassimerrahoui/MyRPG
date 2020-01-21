package com.example.myrpg;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LevelFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LevelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LevelFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LevelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LevelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LevelFragment newInstance(String param1, String param2) {
        LevelFragment fragment = new LevelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        GameView gameView = new GameView(getContext(), buttons, stats, mListener);

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
        void finishGame();
    }
}
