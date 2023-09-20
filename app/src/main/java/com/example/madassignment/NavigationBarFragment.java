package com.example.madassignment;

import android.graphics.Color;
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

    /* -----------------------------------------------------------------------------------------
            Function: Initialise View models + Elements
            Author: Parakram
     ---------------------------------------------------------------------------------------- */
    private ImageButton settingsButton, backButton;
    private ImageView menuTitle;
    private GameData gameData;
    private  CreateUser userModel;
    private EditUser editUserModel;

    NavigationData navigationData;

    public NavigationBarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigationData = new ViewModelProvider(getActivity()).get(NavigationData.class);
        editUserModel = new ViewModelProvider(getActivity()).get(EditUser.class);
        userModel = new ViewModelProvider(getActivity()).get(CreateUser.class);
        gameData = new ViewModelProvider(getActivity()).get(GameData.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_navigation_bar, container, false);
        //NavigationData navigationData = new ViewModelProvider(getActivity()).get(NavigationData.class);
        /* -----------------------------------------------------------------------------------------
            Function: Initialise layout elements
            Author: Ryan
            Description: Defines layout elements from their layout IDs
         ---------------------------------------------------------------------------------------- */
        backButton = view.findViewById(R.id.backButton);
        settingsButton = view.findViewById(R.id.settingsButton);
        menuTitle = view.findViewById(R.id.menuTitle);

        /* The navigation integers describe the following fragments:
            navigationData == 0 -> Menu Fragment
            navigationData == 1 -> Board Fragment
            navigationData == 2 -> Settings Fragment
            navigationData == 3 -> Profile Fragment
            navigationData == 4 -> Leaderboard Fragment
            navigationData == 5 -> User Select Fragment
            navigationData == 6 -> Users Fragment
            navigationData == 99 -> Menu Animation Fragment
         */

        /* -----------------------------------------------------------------------------------------
            Function: Settings Click Listener
            Author: Parakram
            Description: Navigates to the settings fragment
                - Modified by Ryan to implement animation for settings button
         ---------------------------------------------------------------------------------------- */
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starts the undo button animation - updated by Ryan
                Animation settings = AnimationUtils.loadAnimation(getActivity(),R.anim.settings_rotation_anim);
                settingsButton.startAnimation(settings);

                navigationData.setSettingsHistoricalValue(navigationData.getClickedValue());
                navigationData.setClickedValue(2);

            }
        });

        /* -----------------------------------------------------------------------------------------
            Function: Back Click Listener
            Author: Parakram
            Description: Navigates to the last known fragment (or alternate fragment in special
                cases).
                - Modified by Ryan to implement animation for back button and fix some navigation
                    errors
                - Modified by Yi Xiang to implement data saving
         ---------------------------------------------------------------------------------------- */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation back = AnimationUtils.loadAnimation(getActivity(),R.anim.back_anim);

                // Animation listener for pressing back button
                back.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Animation started - Changes colour of the button to show it has been pressed
                        int cyan = Color.CYAN;
                        backButton.setColorFilter(cyan);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Animation ended - removes colour filter at the end of the animation
                        backButton.setColorFilter(null);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // Can be left empty as no repeats are occurring
                    }
                });

                backButton.startAnimation(back);

                if(navigationData.getClickedValue() == 0) {
                    // Reset board data
                    gameData.setNeedSaveGameState(false);
                }

                if(navigationData.getClickedValue() == 1) {
                    // If we are on the Board Fragment, take us back to the Menu Fragment
                    gameData.setNeedSaveGameState(false);
                    navigationData.setClickedValue(0);
                }
                if (navigationData.getClickedValue() == 2){
                    // if at settings and going back to board then save state
                    if (navigationData.getSettingsHistoricalValue() == 1) {
                        gameData.setNeedSaveGameState(true);
                    }
                    //there is separate variable to navigate bake to the page that the settings button was pressed from
                    navigationData.setClickedValue(navigationData.getSettingsHistoricalValue());

                }
                else if (navigationData.getClickedValue() == 3) {
                    //restore state of the ViewModels for next visit to profile fragment
                    userModel.setUserIcon(0);
                    userModel.setUserName("");
                    editUserModel.setDeleteUserId(0L);
                    editUserModel.setDeleteUserPosition(0);
                    editUserModel.setUserIcon(0);
                    editUserModel.setUserName("");
                    editUserModel.setUserId(0L);
                    //go back to the recent page you came from
                    navigationData.setClickedValue(navigationData.getHistoricalClickedValue());
                    //code to store state if needed

                }
                else if (navigationData.getClickedValue() == 4) {
                    navigationData.setClickedValue(2);
                }
                else if (navigationData.getClickedValue() == 5) {
                    // If we are on the User Select Fragment, take us back to the Menu Fragment
                    navigationData.setClickedValue(0);
                }

                else if (navigationData.getClickedValue() == 6) {
                    if (editUserModel.getUserCount() > 0) {
                        navigationData.setClickedValue(2);
                    }
                    else {
                        navigationData.setClickedValue(0);
                    }

                }
            }

        });

        /* -----------------------------------------------------------------------------------------
            Function: Navigation Data Observer
            Author: Parakram
            Description: Defines the behaviour of the navBar elements on different pages
                - Modified by Ryan to add menu title visibility
                - Modified by Ryan to add user page and title card animation behaviour
         ---------------------------------------------------------------------------------------- */
        navigationData.clickedValue.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch(integer) {
                    case 0:
                        settingsButton.setVisibility(View.VISIBLE);
                        backButton.setVisibility(View.GONE);
                        menuTitle.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        settingsButton.setVisibility(View.VISIBLE);
                        backButton.setVisibility(View.VISIBLE);
                        menuTitle.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        settingsButton.setVisibility(View.GONE);
                        backButton.setVisibility(View.VISIBLE);
                        menuTitle.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        settingsButton.setVisibility(View.GONE);
                        backButton.setVisibility(View.VISIBLE);
                        menuTitle.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        settingsButton.setVisibility(View.GONE);
                        backButton.setVisibility(View.VISIBLE);
                        menuTitle.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        settingsButton.setVisibility(View.VISIBLE);
                        backButton.setVisibility(View.VISIBLE);
                        menuTitle.setVisibility(View.VISIBLE);
                        break;
                    case 99:
                        settingsButton.setVisibility(View.GONE);
                        backButton.setVisibility(View.GONE);
                        menuTitle.setVisibility(View.GONE);
                        break;
                }

            }
        });
        return view;
    }
}