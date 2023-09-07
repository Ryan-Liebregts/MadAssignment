package com.example.madassignment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    // Define UI Components
    private Button aiButton;
    private Button playerButton;
    private TextView menuTitle;
    private ImageView lightSpotImageView;
    private AnimationDrawable lightSpotAnimation;

    // Define ViewModels
    private NavigationData navModel;

    public MenuFragment() {
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
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // Define Buttons
        aiButton = view.findViewById(R.id.aiButton);
        playerButton = view.findViewById(R.id.playerButton);
        menuTitle = view.findViewById(R.id.menuTitle);
        lightSpotImageView = view.findViewById(R.id.lightSpotImageView);

        lightSpotAnimation = (AnimationDrawable) lightSpotImageView.getDrawable();

        // setting enter fade animation duration to 5 seconds
        lightSpotAnimation.setEnterFadeDuration(5000);
        // setting exit fade animation duration to 2 seconds
        lightSpotAnimation.setExitFadeDuration(2000);

        // Start the animation
        lightSpotAnimation.start();

        if(navModel.getAnimationClickedValue() == 0) {
            navModel.setClickedValue(99);
        }
        else {
            menuTitle.setAlpha(1);
            aiButton.setAlpha(1);
            playerButton.setAlpha(1);
        }

        /* Both AI_Button and Player_Button currently only direct to the board fragment
            this is to be changed when the backend for the AI & player back-end is added.
         */
        aiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navModel.setClickedValue(1);
            }
        });

        playerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navModel.setClickedValue(1);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Start the animation when the fragment is active
        if (lightSpotAnimation != null && !lightSpotAnimation.isRunning()) {
            lightSpotAnimation.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Pause the animation when the fragment is not active
        if (lightSpotAnimation != null && lightSpotAnimation.isRunning()) {
            lightSpotAnimation.stop();
        }
    }
}