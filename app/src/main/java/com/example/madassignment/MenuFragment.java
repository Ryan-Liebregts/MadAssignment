package com.example.madassignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuFragment extends Fragment {

    // Define UI Components
    private Button aiButton;
    private Button playerButton;
    private ImageView menuTitle;
    private ImageView lightSpot1;
    private ImageView lightSpot2;
    private ImageView lightSpot3;
    private ImageView lightSpot4;

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
        lightSpot1 = view.findViewById(R.id.lightSpot1);
        lightSpot2 = view.findViewById(R.id.lightSpot2);
        lightSpot3 = view.findViewById(R.id.lightSpot3);
        lightSpot4 = view.findViewById(R.id.lightSpot4);

        fadeAnimation(lightSpot1, 1);
        fadeAnimation(lightSpot2, 0);
        fadeAnimation(lightSpot3, 0);
        fadeAnimation(lightSpot4, 1);

        if(navModel.getAnimationClickedValue() == 0) {
            navModel.setClickedValue(99);
        }
        else {
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

    public void fadeAnimation(ImageView light_spot, int offset) {
        // Create a fade-in animation
        // Offset integer allows for variability in light spot fade behaviour
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        if (offset == 1) {
            fadeIn.setDuration(1500); // Fade in after 0.5 seconds
        }
        else {
            fadeIn.setDuration(1000); // Fade in after 1 second
        }
        fadeIn.setFillAfter(true);

        // Create a fade-out animation
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        if (offset == 1) {
            fadeOut.setStartOffset(1500);
            fadeOut.setDuration(1500); // Fade out after 0.5 seconds
        }
        else {
            fadeOut.setStartOffset(1000);
            fadeOut.setDuration(1000); // Fade out after 1 second
        }
        fadeOut.setFillAfter(true);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Restart the specific animation within the set
                light_spot.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Restart the specific animation within the set
                light_spot.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        light_spot.startAnimation(fadeIn);
    }
}