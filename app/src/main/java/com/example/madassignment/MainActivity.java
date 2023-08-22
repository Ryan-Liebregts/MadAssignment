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
        String test;
        int var;

        // Create viewModels
        navigationData = new ViewModelProvider(this).get(NavigationData.class);

        navigationData.clickedValue.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 0) {
                    loadMenuFragment();
                }
                if (integer == 1) {
                    boardFragment();
                }
            }
        });
    }

    private void loadMenuFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment noteFragment = fm.findFragmentById(R.id.menu_container);

        if (noteFragment != null) {
            fm.beginTransaction().remove(noteFragment).commit();
        }

        MenuFragment menuFragment = new MenuFragment();
        fm.beginTransaction().replace(R.id.menu_container, menuFragment, "menuFragment").commit();
    }

    private void boardFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment noteFragment = fm.findFragmentById(R.id.board_container);

        if (noteFragment != null) {
            fm.beginTransaction().remove(noteFragment).commit();
        }

        BoardFragment boardFragment = new BoardFragment();
        fm.beginTransaction().replace(R.id.board_container, boardFragment, "boardFragment").commit();
    }

}