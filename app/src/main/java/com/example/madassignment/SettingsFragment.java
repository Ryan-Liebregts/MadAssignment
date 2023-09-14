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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {


    // Define Buttons & Spinners
    private Button profileButton;
    private Button customizeButton;
    private ConstraintLayout settingsFragmentBackground;
    private AnimationDrawable animationDrawable;

    // Define Navigation Model
    private NavigationData navModel;

    // Define the list arrays for the spinners
    String[] boardSizes = {"3 x 3", "4 x 4", "5 x 5"};
    String[] winConditions = {"3", "4", "5"};

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
        // Define Buttons
        profileButton = view.findViewById(R.id.profileButton);
        customizeButton = view.findViewById(R.id.customizeButton);
        // Animates the background gradient
        settingsFragmentBackground = (ConstraintLayout) view.findViewById(R.id.settingsLayout);
        animationDrawable = (AnimationDrawable) settingsFragmentBackground.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(2000);



        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navModel.setClickedValue(3);
                navModel.setHistoricalClickedValue(2);
            }
        });

        customizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navModel.setClickedValue(4);
                navModel.setHistoricalClickedValue(2);

            }
        });

        // Create an ArrayAdapter to populate the boardSizeSpinner
        ArrayAdapter<String> boardSizeAdapter = new ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                boardSizes
        );

        return view;
    }

    // Starts the animation when fragment is active
    @Override
    public void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    // Pauses the animation when the fragment is not active
    @Override
    public void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }
}