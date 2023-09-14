package com.example.madassignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

public class NavigationBarFragment extends Fragment {

    private ImageButton backButton;
    private ImageButton settingsButton;
    private ImageButton leaderBoardButton;
    private ImageView menuTitle;

    private NavigationData navigationData;

    public NavigationBarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigationData = new ViewModelProvider(getActivity()).get(NavigationData.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_navigation_bar, container, false);
        NavigationData navigationData = new ViewModelProvider(getActivity()).get(NavigationData.class);
        backButton = view.findViewById(R.id.backButton);
        settingsButton = view.findViewById(R.id.settingsButton);
        leaderBoardButton = view.findViewById(R.id.leaderBoardButton);
        menuTitle = view.findViewById(R.id.menuTitle);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starts the undo button animation - updated by Ryan
                Animation settings = AnimationUtils.loadAnimation(getActivity(),R.anim.settings_rotation_anim);
                settingsButton.startAnimation(settings);
                navigationData.setHistoricalClickedValue(navigationData.getClickedValue());
                navigationData.setClickedValue(2);

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (navigationData.getClickedValue() == 4 || navigationData.getClickedValue() == 3) {
                    if (navigationData.getHistoricalClickedValue() != 6) {
                        navigationData.setClickedValue(2);
                    }
                    else {
                        navigationData.setClickedValue(6);
                    }
                }
                else if (navigationData.getClickedValue() == 2){
                    if (navigationData.getHistoricalClickedValue() != 4 || navigationData.getHistoricalClickedValue() != 3) {
                        navigationData.setClickedValue(1);
                    }
                    else {
                        navigationData.setClickedValue(navigationData.getHistoricalClickedValue());
                    }
                }
                else if (navigationData.getClickedValue() == 5) {
                    navigationData.setClickedValue(navigationData.getHistoricalClickedValue());
                }
            }
        });

        leaderBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationData.setHistoricalClickedValue(navigationData.getClickedValue());
                navigationData.setClickedValue(5);
            }
        });

        navigationData.clickedValue.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch(integer) {
                    case 0:
                        // Menu Fragment
                        settingsButton.setVisibility(View.VISIBLE);
                        leaderBoardButton.setVisibility(View.VISIBLE);
                        backButton.setVisibility(View.GONE);
                        break;
                    case 1:
                        // Board Fragment
                        settingsButton.setVisibility(View.VISIBLE);
                        leaderBoardButton.setVisibility(View.VISIBLE);
                        backButton.setVisibility(View.GONE);
                        break;
                    case 2:
                        // Settings Fragment
                        settingsButton.setVisibility(View.GONE);
                        leaderBoardButton.setVisibility(View.GONE);
                        backButton.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        // Profile Fragment
                        settingsButton.setVisibility(View.GONE);
                        leaderBoardButton.setVisibility(View.GONE);
                        backButton.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        // Customize Fragment
                        settingsButton.setVisibility(View.GONE);
                        leaderBoardButton.setVisibility(View.GONE);
                        backButton.setVisibility(View.VISIBLE);
                    case 5:
                        // Leaderboard Fragment
                        settingsButton.setVisibility(View.GONE);
                        backButton.setVisibility(View.VISIBLE);
                        leaderBoardButton.setVisibility(View.GONE);
                    case 6:
                        // User Select Fragment
                        settingsButton.setVisibility(View.GONE);
                        backButton.setVisibility(View.VISIBLE);
                        leaderBoardButton.setVisibility(View.GONE);
                    case 99:
                        // Menu Animation Fragment
                        settingsButton.setVisibility(View.GONE);
                        backButton.setVisibility(View.GONE);
                        leaderBoardButton.setVisibility(View.GONE);
                        menuTitle.setVisibility(View.GONE);
                }

            }
        });
        return view;
    }
}