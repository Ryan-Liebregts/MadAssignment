package com.example.madassignment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {


    /* -----------------------------------------------------------------------------------------
            Function: Initialise View models + Elements
            Author: Ryan
            Description: Initializes data within the fragment
     ---------------------------------------------------------------------------------------- */
    private ImageButton profileButton;
    private ImageButton leaderBoardButton;
    private ConstraintLayout settingsFragmentBackground;
    private AnimationDrawable animationDrawable;
    private NavigationData navModel;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navModel = new ViewModelProvider(getActivity()).get(NavigationData.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        /* -----------------------------------------------------------------------------------------
            Function: Initialise layout elements
            Author: Ryan
            Description: Defines the layout elements by their layout ID
         ---------------------------------------------------------------------------------------- */
        profileButton = view.findViewById(R.id.profileButton);
        leaderBoardButton = view.findViewById(R.id.leaderBoardButton);

        /* -----------------------------------------------------------------------------------------
            Function: Initialise animation elements
            Author: Ryan
            Description: Sets the fade in and fade out characteristics of the background
         ---------------------------------------------------------------------------------------- */
        settingsFragmentBackground = (ConstraintLayout) view.findViewById(R.id.settingsLayout);
        animationDrawable = (AnimationDrawable) settingsFragmentBackground.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(2000);

        /* -----------------------------------------------------------------------------------------
            Function: Profile Click Listener
            Author: Ryan
            Description: Navigates to the profile fragment
                - Modified by Parakram to implement traversal behaviour
         ---------------------------------------------------------------------------------------- */
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navModel.setClickedValue(6);
            }
        });

        /* -----------------------------------------------------------------------------------------
            Function: Leaderboard Click Listener
            Author: Ryan
            Description: Navigates to the leaderboard fragment
         ---------------------------------------------------------------------------------------- */
        leaderBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navModel.setClickedValue(4);
            }
        });

        return view;
    }

    /* ---------------------------------------------------------------------------------------------
            Function: onResume()
            Author: Ryan
            Description: Starts the animation if the fragment is active
     -------------------------------------------------------------------------------------------- */
    @Override
    public void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    /* ---------------------------------------------------------------------------------------------
            Function: onPause()
            Author: Ryan
            Description: Stops the animation if the fragment is not active
     -------------------------------------------------------------------------------------------- */
    @Override
    public void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }
}