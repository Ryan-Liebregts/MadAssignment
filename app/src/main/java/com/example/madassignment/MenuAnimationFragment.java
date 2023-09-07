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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAnimationFragment extends Fragment {
    private TextView menuTitle;
    private ImageView lightSpotImageView;

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

        // Listen for when the view is laid out to get its height
        menuTitle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Calculate the final Y position to center the title on the screen
                int screenHeight = menuTitle.getRootView().getHeight();
                int titleHeight = menuTitle.getHeight();
                float finalY = (screenHeight - titleHeight) / 2f;

                // Create a translation animation to make the title fall
                ObjectAnimator translateY = ObjectAnimator.ofFloat(menuTitle, "translationY", -titleHeight, finalY);
                translateY.setDuration(2000); // Set the duration in milliseconds
                translateY.setInterpolator(new AccelerateDecelerateInterpolator());

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(translateY);
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Animation ended, perform an action like navigating to a different fragment
                        navModel.setClickedValue(0);
                        navModel.setAnimationClickedValue(1);
                    }
                });

                animatorSet.start();

                // Remove the global layout listener to avoid multiple callbacks
                menuTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        return view;
    }
}