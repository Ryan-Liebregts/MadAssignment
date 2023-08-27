package com.example.madassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // Declare viewModel parameters
    NavigationData navigationData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create viewModels
        navigationData = new ViewModelProvider(this).get(NavigationData.class);

        navigationData.clickedValue.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 0) {
                    loadMenuFragment(); // setClickValue == 0
                }
                if (integer == 1) {
                    loadBoardFragment(); // setClickValue == 1
                }
                if (integer == 2) {
                    loadSettingsFragment(); // setClickValue == 2
                }
            }
        });
    }

    private void loadMenuFragment() {
        FragmentManager fm = getSupportFragmentManager();
        // Defines other fragments
        Fragment boardFragment = fm.findFragmentById(R.id.board_container);
        Fragment settingsFragment = fm.findFragmentById(R.id.settings_container);

        //If currently active, removes boardFragment
        if (boardFragment != null) {
            fm.beginTransaction().remove(boardFragment).commit();
        }

        //If currently active, removes settingsFragment
        if (settingsFragment != null) {
            fm.beginTransaction().remove(settingsFragment).commit();
        }

        MenuFragment menuFragment = new MenuFragment();
        fm.beginTransaction().replace(R.id.menu_container, menuFragment, "menuFragment").commit();
    }

    private void loadBoardFragment() {
        FragmentManager fm = getSupportFragmentManager();
        // Defines other fragments
        Fragment menuFragment = fm.findFragmentById(R.id.menu_container);
        Fragment settingsFragment = fm.findFragmentById(R.id.settings_container);

        //If currently active, removes menuFragment
        if (menuFragment != null) {
            fm.beginTransaction().remove(menuFragment).commit();
        }

        //If currently active, removes settingsFragment
        if (settingsFragment != null) {
            fm.beginTransaction().remove(settingsFragment).commit();
        }

        BoardFragment boardFragment = new BoardFragment();
        fm.beginTransaction().replace(R.id.board_container, boardFragment, "boardFragment").commit();
    }

    private void loadSettingsFragment() {
        FragmentManager fm = getSupportFragmentManager();
        // Defines other fragments
        Fragment boardFragment = fm.findFragmentById(R.id.board_container);
        Fragment menuFragment = fm.findFragmentById(R.id.menu_container);

        //If currently active, removes boardFragment
        if (boardFragment != null) {
            fm.beginTransaction().remove(boardFragment).commit();
        }

        //If currently active, removes menuFragment
        if (menuFragment != null) {
            fm.beginTransaction().remove(menuFragment).commit();
        }

        SettingsFragment settingsFragment = new SettingsFragment();
        fm.beginTransaction().replace(R.id.settings_container, settingsFragment, "settingsFragment").commit();
    }

}