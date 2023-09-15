package com.example.madassignment;

import java.util.*;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {


    private ArrayList<Integer> data;
    private RecyclerView recyclerView;

    private EditText userNameTextBox;

    private Button saveUserButton;

    private CreateUser userModel;
//    CreateUser userModel;

    private String userName;

    int userIcon;

    private ConstraintLayout profileFragmentBackground;

    private AnimationDrawable animationDrawable;

    private NavigationData navModel;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userModel = new ViewModelProvider(getActivity()).get(CreateUser.class);
        navModel = new ViewModelProvider(getActivity()).get(NavigationData.class);
        UserDao userDao = initialiseDB();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerView = view.findViewById(R.id.recycler_icon);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4,
            GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        data = getIconData();
        UserIconAdapter userIconAdapter =new UserIconAdapter(data, userModel, this);
        recyclerView.setAdapter(userIconAdapter);

        saveUserButton = view.findViewById(R.id.save_user_button);
        userNameTextBox = view.findViewById(R.id.user_text);

        // Animates the background gradient
        profileFragmentBackground = (ConstraintLayout) view.findViewById(R.id.profile_fragment);
        animationDrawable = (AnimationDrawable) profileFragmentBackground.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(2000);
        userModel.userIcon.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != 0 && !Objects.equals(String.valueOf(userNameTextBox.getText()), "")){
                    saveUserButton.setEnabled(true);
                }
            }
        });

        //TODO add query to check for duplicated usernames

        saveUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               userName = String.valueOf(userNameTextBox.getText());
               userIcon = userModel.getUserIcon();
               saveUser(userName, userIcon);
               navModel.setClickedValue(2);
               userModel.setUserIcon(0);
               userModel.setUserName("");
            }
        });


        return view;
    }


    public ArrayList<Integer> getIconData() {
        data = new ArrayList<Integer>();
        data.add(R.drawable.user_icon1);
        data.add(R.drawable.user_icon2);
        data.add(R.drawable.user_icon3);
        data.add(R.drawable.user_icon4);
        data.add(R.drawable.user_icon5);
        data.add(R.drawable.user_icon6);
        data.add(R.drawable.user_icon7);
        data.add(R.drawable.user_icon8);
        data.add(R.drawable.user_icon9);
        data.add(R.drawable.user_icon10);
        data.add(R.drawable.user_icon11);
        data.add(R.drawable.user_icon12);
        data.add(R.drawable.user_icon13);
        data.add(R.drawable.user_icon14);
        data.add(R.drawable.user_icon15);
        data.add(R.drawable.user_icon16);

        // TODO: Add more user icons when time

        return data;
    }

    public UserDao initialiseDB() {
        return UserDbInstance.getDatabase(getContext()).userDao();
    }


    public void saveUser(String name, int value) {
        UserDao userDao = initialiseDB();
        User user = new User();
        user.setUserName(name);
        user.setUserIcon(value);
        user.setUserLosses(0);
        user.setUserWins(0);
        userDao.insert(user);
    }

    // Starts the animation when fragment is active
    @Override
    public void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    // Pauses the animation when the fragment is not active
    @Override
    public void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }

}