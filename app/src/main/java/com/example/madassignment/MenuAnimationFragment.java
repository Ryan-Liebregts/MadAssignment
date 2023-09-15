package com.example.madassignment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAnimationFragment extends Fragment {
    private ImageView menuTitle;
    private ImageView lightSpot1;
    private ImageView lightSpot2;
    private ImageView lightSpot3;
    private ImageView lightSpot4;

    private AnimatorSet translateAnimationSet;

    private NavigationData navModel;

    public MenuAnimationFragment() {
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
        View view = inflater.inflate(R.layout.fragment_menu_animation, container, false);

        // Define UI Items
        menuTitle = view.findViewById(R.id.menuTitleAnimation);
        lightSpot1 = view.findViewById(R.id.lightSpot1);
        lightSpot2 = view.findViewById(R.id.lightSpot2);
        lightSpot3 = view.findViewById(R.id.lightSpot3);
        lightSpot4 = view.findViewById(R.id.lightSpot4);

        fadeAnimation(lightSpot1, 1);
        fadeAnimation(lightSpot2, 0);
        fadeAnimation(lightSpot3, 0);
        fadeAnimation(lightSpot4, 1);


        // Listen for when the view is laid out to get its height
        menuTitle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Calculate the final Y position to center the title on the screen
                int screenHeight = menuTitle.getRootView().getHeight();
                int titleHeight = menuTitle.getHeight();
                float finalY = (screenHeight - titleHeight) / 2f - 500;

                // Create a translation animation to make the title fall
                ObjectAnimator translateY = ObjectAnimator.ofFloat(menuTitle, "translationY", -titleHeight, finalY);
                translateY.setDuration(4000); // Set the duration in milliseconds
                translateY.setInterpolator(new AccelerateDecelerateInterpolator());

                translateAnimationSet = new AnimatorSet();
                translateAnimationSet.play(translateY);
                translateAnimationSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Animation ended, perform an action like navigating to a different fragment
                        navModel.setClickedValue(0);
                        navModel.setAnimationClickedValue(1);
                    }
                });

                translateAnimationSet.start();

                // Remove the global layout listener to avoid multiple callbacks
                menuTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        // Allows the user to be able to skip the startup animation
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navModel.setClickedValue(0);
                navModel.setAnimationClickedValue(1);
            }
        });

        return view;
    }

    public void fadeAnimation(ImageView light_spot, int offset) {
        // Create a fade-in animation
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

    @Override
    public void onPause() {
        super.onPause();
        if (translateAnimationSet != null && translateAnimationSet.isRunning()) {
            translateAnimationSet.cancel();
        }
    }
}