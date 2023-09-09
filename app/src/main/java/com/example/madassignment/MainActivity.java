package com.example.madassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // Declare viewModel parameters
    NavigationBarFragment navBarFragment = new NavigationBarFragment();
    NavigationData navigationData;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create viewModels
        navigationData = new ViewModelProvider(this).get(NavigationData.class);
        loadNavBar();
        navigationData.clickedValue.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                switch(integer) {
                    case 0:
                        System.out.println("The value is :" + String.valueOf(integer));
                        loadMenuFragment(); // setClickValue == 0
                        break;
                    case 1:
                        System.out.println("The value is :" + String.valueOf(integer));
                        loadBoardFragment();
                        break;
                    case 2:
                        System.out.println("The value is :" + String.valueOf(integer));
                        loadSettingsFragment();
                        break;
                    case 3:
                        System.out.println("The value is :" + String.valueOf(integer));
                        loadProfileFragment();
                        break;
                    case 4:
                        System.out.println("The value is :" + String.valueOf(integer));
                        loadCustomizeFragment();
                        break;
                    case 5:
                        System.out.println("The value is :" + String.valueOf(integer));
                        loadLeaderBoardFragment();
                        break;
                    case 99:
                        System.out.println("The value is :" + String.valueOf(integer));
                        loadMenuAnimationFragment();
                        break;
                }
            }
        });
    }

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

    private void loadCustomizeFragment() {
        // Defines  fragments
        CustomizeFragment customizeFragment = new CustomizeFragment();
        Fragment bodyContainer = fm.findFragmentById(R.id.body_container);

        //replaces or adds fragment according to if there is already a fragment
        if (bodyContainer != null) {
            fm.beginTransaction().replace(R.id.body_container, customizeFragment, "customizeFragment").commit();
        }
        else {
            fm.beginTransaction().add(R.id.body_container, customizeFragment, "customizeFragment").commit();
        }
    }

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