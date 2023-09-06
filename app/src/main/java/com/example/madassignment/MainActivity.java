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
                if (integer == 0) {
                    loadMenuFragment(); // setClickValue == 0
                }
                if (integer == 1) {
                    loadBoardFragment();
                }
                if (integer == 2) {
                    loadSettingsFragment(); // setClickValue == 2
                }
                if (integer == 3) {
                    loadProfileFragment();
                }
                if (integer == 4) {
                    loadCustomizeFragment();
                }
            }
        });
    }

    private void loadMenuFragment() {
        FragmentManager fm = getSupportFragmentManager();
        // Defines other fragments
        Fragment boardFragment = fm.findFragmentById(R.id.board_container);
        Fragment settingsFragment = fm.findFragmentById(R.id.settings_container);
        Fragment profileFragment = fm.findFragmentById(R.id.profile_container);
        Fragment customizeFragment = fm.findFragmentById(R.id.customize_container);

        //If currently active, removes boardFragment
        if (boardFragment != null) {
            fm.beginTransaction().remove(boardFragment).commit();
        }

        //If currently active, removes settingsFragment
        if (settingsFragment != null) {
            fm.beginTransaction().remove(settingsFragment).commit();
        }

        //If currently active, removes profileFragment
        if (profileFragment != null) {
            fm.beginTransaction().remove(profileFragment).commit();
        }

        //If currently active, removes customizeFragment
        if (customizeFragment != null) {
            fm.beginTransaction().remove(customizeFragment).commit();
        }


        MenuFragment menuFragment = new MenuFragment();
        fm.beginTransaction().replace(R.id.menu_container, menuFragment, "menuFragment").commit();
    }

    private void loadBoardFragment() {
        FragmentManager fm = getSupportFragmentManager();
        // Defines other fragments
        Fragment menuFragment = fm.findFragmentById(R.id.menu_container);
        Fragment settingsFragment = fm.findFragmentById(R.id.settings_container);
        Fragment profileFragment = fm.findFragmentById(R.id.profile_container);
        Fragment customizeFragment = fm.findFragmentById(R.id.customize_container);

        //If currently active, removes menuFragment
        if (menuFragment != null) {
            fm.beginTransaction().remove(menuFragment).commit();
        }

        //If currently active, removes settingsFragment
        if (settingsFragment != null) {
            fm.beginTransaction().remove(settingsFragment).commit();
        }

        //If currently active, removes profileFragment
        if (profileFragment != null) {
            fm.beginTransaction().remove(profileFragment).commit();
        }

        //If currently active, removes customizeFragment
        if (customizeFragment != null) {
            fm.beginTransaction().remove(customizeFragment).commit();
        }

//
        BoardFragment boardFragment = new BoardFragment();
        fm.beginTransaction().replace(R.id.board_container, boardFragment, "boardFragment").commit();
    }

    private void loadSettingsFragment() {
        FragmentManager fm = getSupportFragmentManager();
        // Defines other fragments
        Fragment boardFragment = fm.findFragmentById(R.id.board_container);
        Fragment menuFragment = fm.findFragmentById(R.id.menu_container);
        Fragment profileFragment = fm.findFragmentById(R.id.profile_container);
        Fragment customizeFragment = fm.findFragmentById(R.id.customize_container);

        //If currently active, removes boardFragment
        if (boardFragment != null) {
            fm.beginTransaction().remove(boardFragment).commit();
        }

        //If currently active, removes menuFragment
        if (menuFragment != null) {
            fm.beginTransaction().remove(menuFragment).commit();
        }

        //If currently active, removes profileFragment
        if (profileFragment != null) {
            fm.beginTransaction().remove(profileFragment).commit();
        }

        //If currently active, removes customizeFragment
        if (customizeFragment != null) {
            fm.beginTransaction().remove(customizeFragment).commit();
        }

        SettingsFragment settingsFragment = new SettingsFragment();
        fm.beginTransaction().replace(R.id.settings_container, settingsFragment, "settingsFragment").commit();
    }

    private void loadProfileFragment() {
        FragmentManager fm = getSupportFragmentManager();
        // Defines other fragments
        Fragment boardFragment = fm.findFragmentById(R.id.board_container);
        Fragment settingsFragment = fm.findFragmentById(R.id.settings_container);
        Fragment menuFragment = fm.findFragmentById(R.id.menu_container);
        Fragment customizeFragment = fm.findFragmentById(R.id.customize_container);

        //If currently active, removes boardFragment
        if (boardFragment != null) {
            fm.beginTransaction().remove(boardFragment).commit();
        }

        //If currently active, removes settingsFragment
        if (settingsFragment != null) {
            fm.beginTransaction().remove(settingsFragment).commit();
        }

        //If currently active, removes menuFragment
        if (menuFragment != null) {
            fm.beginTransaction().remove(menuFragment).commit();
        }

        //If currently active, removes customizeFragment
        if (customizeFragment != null) {
            fm.beginTransaction().remove(customizeFragment).commit();
        }

        ProfileFragment profileFragment = new ProfileFragment();
        fm.beginTransaction().replace(R.id.profile_container, profileFragment, "profileFragment").commit();
    }

    private void loadCustomizeFragment() {
        FragmentManager fm = getSupportFragmentManager();
        // Defines other fragments
        Fragment boardFragment = fm.findFragmentById(R.id.board_container);
        Fragment settingsFragment = fm.findFragmentById(R.id.settings_container);
        Fragment menuFragment = fm.findFragmentById(R.id.menu_container);
        Fragment profileFragment = fm.findFragmentById(R.id.profile_container);

        //If currently active, removes boardFragment
        if (boardFragment != null) {
            fm.beginTransaction().remove(boardFragment).commit();
        }

        //If currently active, removes settingsFragment
        if (settingsFragment != null) {
            fm.beginTransaction().remove(settingsFragment).commit();
        }

        //If currently active, removes menuFragment
        if (menuFragment != null) {
            fm.beginTransaction().remove(menuFragment).commit();
        }

        //If currently active, removes profileFragment
        if (profileFragment != null) {
            fm.beginTransaction().remove(profileFragment).commit();
        }

        CustomizeFragment customizeFragment = new CustomizeFragment();
        fm.beginTransaction().replace(R.id.customize_container, customizeFragment, "customizeFragment").commit();
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