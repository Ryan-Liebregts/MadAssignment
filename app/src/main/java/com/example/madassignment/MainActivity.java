package com.example.madassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    /* -----------------------------------------------------------------------------------------
            Function: Initialise View models + Elements
            Author: Parakram + Ryan
     ---------------------------------------------------------------------------------------- */
    private ImageView background;
    NavigationBarFragment navBarFragment = new NavigationBarFragment();
    NavigationData navigationData;
    FragmentManager fm = getSupportFragmentManager();

    GameData gameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create viewModels
        navigationData = new ViewModelProvider(this).get(NavigationData.class);
        gameData = new ViewModelProvider(this).get(GameData.class);
        loadNavBar();

        background = findViewById(R.id.backgroundView);

        /* -----------------------------------------------------------------------------------------
                Function: Fragment Navigation
                Author: Ryan + Parakram
                Description: Handles the navigation of the application, loading and setting
                    appropriate backgrounds for the fragments
                    - Modified by Jules to handle board timer logic to prevent errors
         ---------------------------------------------------------------------------------------- */
        navigationData.clickedValue.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                switch(integer) {
                    case 0:
                        System.out.println("The value is :" + String.valueOf(integer));
                        BoardFragment.stopTimer(); // Stop timer before loading new fragment (will cause error otherwise)
                        loadMenuFragment(); // setClickValue == 0
                        background.setBackgroundResource(R.drawable.wooden_background);
                        break;
                    case 1:
                        System.out.println("The value is :" + String.valueOf(integer));
                        loadBoardFragment();
                        background.setBackgroundResource(R.drawable.wooden_background);
                        break;
                    case 2:
                        System.out.println("The value is :" + String.valueOf(integer));
                        BoardFragment.stopTimer(); // Stop timer before loading new fragment (will cause error otherwise)
                        loadSettingsFragment();
                        background.setBackgroundResource(R.drawable.gradient_animation);
                        break;
                    case 3:
                        System.out.println("The value is :" + String.valueOf(integer));
                        loadProfileFragment();
                        background.setBackgroundResource(R.drawable.gradient_animation);
                        break;
                    case 4:
                        System.out.println("The value is :" + String.valueOf(integer));
                        loadLeaderBoardFragment();
                        background.setBackgroundResource(R.drawable.gradient_animation);
                        break;
                    case 5:
                        System.out.println("The value is :" + String.valueOf(integer));
                        loadUserSelectFragment();
                        background.setBackgroundResource(R.drawable.gradient_animation);
                        break;
                    case 6:
                        System.out.println("The value is :" + String.valueOf(integer));
                        loadUsersFragment();
                        background.setBackgroundResource(R.drawable.gradient_animation);
                        break;
                    case 99:
                        System.out.println("The value is :" + String.valueOf(integer));
                        loadMenuAnimationFragment();
                        background.setBackgroundResource(R.drawable.wooden_background);
                        break;
                }
            }
        });
    }

    /* -----------------------------------------------------------------------------------------
            Function: loadMenuFragment()
            Author: Ryan + Parakram
            Description: Determines if body fragment is currently full, if so replace with menu
                otherwise add menu fragment
     ---------------------------------------------------------------------------------------- */
    private void loadMenuFragment() {
        // Defines other fragments
        MenuFragment menuFragment = new MenuFragment();
        Fragment mainContainer = fm.findFragmentById(R.id.body_container);

        //If currently active, removes boardFragment
        if (mainContainer != null) {
            fm.beginTransaction().replace(R.id.body_container, menuFragment, "menuFragment").commit();
        }
        else {
            fm.beginTransaction().add(R.id.body_container, menuFragment, "menuFragment").commit();
        }

    }

    /* -----------------------------------------------------------------------------------------
            Function: loadMenuAnimationFragment()
            Author: Ryan
            Description: Determines if body fragment is currently full, if so replace with menu
                animation otherwise add menu animation fragment
     ---------------------------------------------------------------------------------------- */
    private void loadMenuAnimationFragment() {
        // Defines other fragments
        MenuAnimationFragment menuAnimationFragment = new MenuAnimationFragment();
        Fragment mainContainer = fm.findFragmentById(R.id.body_container);


        //If currently active, removes boardFragment
        if (mainContainer != null) {
            fm.beginTransaction().replace(R.id.body_container, menuAnimationFragment, "menuAnimationFragment").commit();
        }
        else {
            fm.beginTransaction().add(R.id.body_container, menuAnimationFragment, "menuAnimationFragment").commit();
        }

    }

    /* -----------------------------------------------------------------------------------------
            Function: loadBoardFragment()
            Author: Ryan + Parakram
            Description: Determines if body fragment is currently full, if so replace with board
                otherwise add board fragment
     ---------------------------------------------------------------------------------------- */
    private void loadBoardFragment() {
        // Defines  fragments
        BoardFragment boardFragment = new BoardFragment();
        Fragment bodyContainer = fm.findFragmentById(R.id.body_container);

        //replaces or adds fragment according to if there is already a fragment
        if (bodyContainer != null) {
            fm.beginTransaction().replace(R.id.body_container, boardFragment, "boardFragment").commit();
        }
        else {
            fm.beginTransaction().add(R.id.body_container, boardFragment, "boardFragment").commit();
        }
    }

    /* -----------------------------------------------------------------------------------------
            Function: loadSettingsFragment()
            Author: Ryan + Parakram
            Description: Determines if body fragment is currently full, if so replace with
                settings otherwise add settings fragment
     ---------------------------------------------------------------------------------------- */
    private void loadSettingsFragment() {
        // Defines  fragments
        SettingsFragment settingsFragment = new SettingsFragment();
        Fragment bodyContainer = fm.findFragmentById(R.id.body_container);

        //replaces or adds fragment according to if there is already a fragment
        if (bodyContainer != null) {
            fm.beginTransaction().replace(R.id.body_container, settingsFragment, "settingsFragment").commit();
        }
        else {
            fm.beginTransaction().add(R.id.body_container, settingsFragment, "settingsFragment").commit();
        }
    }

    /* -----------------------------------------------------------------------------------------
            Function: loadProfileFragment()
            Author: Ryan + Parakram
            Description: Determines if body fragment is currently full, if so replace with
                profile otherwise add profile fragment
     ---------------------------------------------------------------------------------------- */
    private void loadProfileFragment() {
        // Defines  fragments
        ProfileFragment profileFragment = new ProfileFragment();
        Fragment bodyContainer = fm.findFragmentById(R.id.body_container);

        //replaces or adds fragment according to if there is already a fragment
        if (bodyContainer != null) {
            fm.beginTransaction().replace(R.id.body_container, profileFragment, "profileFragment").commit();
        }
        else {
            fm.beginTransaction().add(R.id.body_container, profileFragment, "profileFragment").commit();
        }
    }

    /* -----------------------------------------------------------------------------------------
            Function: loadLeaderBoardFragment()
            Author: Ryan + Parakram
            Description: Determines if body fragment is currently full, if so replace with
                leaderboard otherwise add leaderboard fragment
     ---------------------------------------------------------------------------------------- */
    private void loadLeaderBoardFragment() {
        // Defines  fragments
        LeaderBoardFragment leaderBoardFragment = new LeaderBoardFragment();
        Fragment bodyContainer = fm.findFragmentById(R.id.body_container);

        //replaces or adds fragment according to if there is already a fragment
        if (bodyContainer != null) {
            fm.beginTransaction().replace(R.id.body_container, leaderBoardFragment, "leaderBoardFragment").commit();
        }
        else {
            fm.beginTransaction().add(R.id.body_container, leaderBoardFragment, "leaderBoardFragment").commit();
        }
    }

    /* -----------------------------------------------------------------------------------------
            Function: loadUserSelectFragment()
            Author: Parakram
            Description: Determines if body fragment is currently full, if so replace with
                user select otherwise add user select fragment
     ---------------------------------------------------------------------------------------- */
    private void loadUserSelectFragment() {
        // Defines  fragments
        SelectUserFragment selectUserFragment = new SelectUserFragment();
        Fragment bodyContainer = fm.findFragmentById(R.id.body_container);

        //replaces or adds fragment according to if there is already a fragment
        if (bodyContainer != null) {
            fm.beginTransaction().replace(R.id.body_container, selectUserFragment, "selectUserFragment").commit();
        }
        else {
            fm.beginTransaction().add(R.id.body_container, selectUserFragment, "selectUserFragment").commit();
        }
    }

    /* -----------------------------------------------------------------------------------------
            Function: loadUserFragment()
            Author: Parakram
            Description: Determines if body fragment is currently full, if so replace with
                user otherwise add user fragment
     ---------------------------------------------------------------------------------------- */
    private void loadUsersFragment() {
        // Defines  fragments
        UsersFragment usersFragment = new UsersFragment();
        Fragment bodyContainer = fm.findFragmentById(R.id.body_container);
        //replaces or adds fragment according to if there is already a fragment
        if (bodyContainer != null) {
            fm.beginTransaction().replace(R.id.body_container, usersFragment, "usersFragment").commit();
        }
        else {
            fm.beginTransaction().add(R.id.body_container, usersFragment, "usersFragment").commit();
        }
    }

    /* -----------------------------------------------------------------------------------------
            Function: loadNavBar()
            Author: Parakram
            Description: Determines if navBar fragment is currently full, if so replace with
                navBar otherwise add navBar fragment
     ---------------------------------------------------------------------------------------- */
    private void loadNavBar() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.navBar_container);
        if (frag!= null) {
            fm.beginTransaction().replace(R.id.navBar_container, navBarFragment, "navBarFragment").commit();
        }
        else {
            fm.beginTransaction().add(R.id.navBar_container, navBarFragment, "navBarFragment").commit();
        }
    }



}