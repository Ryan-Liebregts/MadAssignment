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

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    /* -----------------------------------------------------------------------------------------
            Function: Initialise View models + Elements
            Author: Parakram
     ---------------------------------------------------------------------------------------- */
    private ArrayList<Integer> data;
    private RecyclerView recyclerView;

    private EditText userNameTextBox;

    private Button saveUserButton;

    private CreateUser userModel;

    private EditUser editUser;

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
        editUser = new ViewModelProvider(getActivity()).get(EditUser.class);
        UserDao userDao = initialiseDB();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //set isEdit boolean
        boolean isEdit = editUser.getUserId() != 0;

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        /* -----------------------------------------------------------------------------------------
            Function: Initialise layout elements
            Author: Parakram
         ---------------------------------------------------------------------------------------- */
        saveUserButton = view.findViewById(R.id.save_user_button);
        userNameTextBox = view.findViewById(R.id.user_text);
        recyclerView = view.findViewById(R.id.recycler_icon);

        /* -----------------------------------------------------------------------------------------
            Function: Initialise animation elements
            Author: Ryan
            Description: Sets the fade in and fade out characteristics of the background
         ---------------------------------------------------------------------------------------- */
        profileFragmentBackground = (ConstraintLayout) view.findViewById(R.id.profile_fragment);
        animationDrawable = (AnimationDrawable) profileFragmentBackground.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(2000);

        /* -----------------------------------------------------------------------------------------
            Function: Initialise Recycler Grid elements
            Author: Parakram
         ---------------------------------------------------------------------------------------- */
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4,
                GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        //get data for recycler
        data = getIconData();
        //set adapter and view adapter
        UserIconAdapter userIconAdapter =new UserIconAdapter(data, userModel, this, editUser);
        recyclerView.setAdapter(userIconAdapter);

        //set default value
        userNameTextBox.setText("");
        // set userName in the text box if its an edit
        if (isEdit) {
            userNameTextBox.setText(editUser.getUserName());
        }

        /* -----------------------------------------------------------------------------------------
            Function: userNameTextBox Click Listener
            Author: Parakram
         ---------------------------------------------------------------------------------------- */
        userNameTextBox.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                //set ther username for existing user for edit or new user for not edit
                if (isEdit) {
                    editUser.setUserName(String.valueOf(s));
                }
                else {
                    userModel.setUserName(String.valueOf(s));
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        if(isEdit){
            editUser.userIcon.observe(getViewLifecycleOwner(), new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer != 0 && !Objects.equals(editUser.getUserName(), "")){
                        saveUserButton.setEnabled(true);
                    }
                    else{
                        saveUserButton.setEnabled(false);
                    }
                }
            });
            editUser.userName.observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String string) {
                    if (editUser.getUserIcon() != 0 && !Objects.equals(string, "")){
                        saveUserButton.setEnabled(true);
                    }
                    else{
                        saveUserButton.setEnabled(false);
                    }
                }
            });
        }
        else{
            userModel.userIcon.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != 0 && !Objects.equals(userModel.getUserName(), "")){
                    saveUserButton.setEnabled(true);
                }
                else{
                    saveUserButton.setEnabled(false);
                }
            }
        });
            userModel.userName.observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String string) {
                    if (userModel.getUserIcon() != 0 && !Objects.equals(string, "")){
                        saveUserButton.setEnabled(true);
                    }
                    else{
                        saveUserButton.setEnabled(false);
                    }
                }
            });

        }
        /* -----------------------------------------------------------------------------------------
            Function: saveUserButton Click Listener
            Author: Parakram
         ---------------------------------------------------------------------------------------- */
        saveUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEdit){
                    updateUser();
                }
                else{
                    saveUser();
                    int userCount = editUser.getUserCount();
                    editUser.setUserCount(userCount + 1);
                }
               navModel.setClickedValue(navModel.getHistoricalClickedValue());

            }
        });
        return view;
    }

        /* -----------------------------------------------------------------------------------------
                Function: Icon Data Array
                Author: Ryan
                Description: Adds drawable elements to the icon data array
         ---------------------------------------------------------------------------------------- */
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

        return data;
    }

    public UserDao initialiseDB() {
        return UserDbInstance.getDatabase(getContext()).userDao();
    }

        /* -----------------------------------------------------------------------------------------
                Function: saveUser()
                Author: Parakram
         ---------------------------------------------------------------------------------------- */
    public void saveUser() {
        UserDao userDao = initialiseDB();
        User user = new User();
        user.setUserName(userModel.getUserName());
        user.setUserIcon(userModel.getUserIcon());
        user.setUserLosses(0);
        user.setUserWins(0);
        userDao.insert(user);
        userModel.setUserIcon(0);
        userModel.setUserName("");
    }
        /* -----------------------------------------------------------------------------------------
                Function: updateUser()
                Author: Parakram
         ---------------------------------------------------------------------------------------- */

    public void updateUser() {
        UserDao userDao = initialiseDB();
        userDao.updateUserIcon(editUser.getUserId(), editUser.getUserIcon());
        userDao.updateUserName(editUser.getUserId(), editUser.getUserName());
        editUser.setUserId(0);
        editUser.setUserName("");
        editUser.setUserIcon(0);
    }

    /* ---------------------------------------------------------------------------------------------
            Function: onResume()
            Author: Ryan
            Description: Starts the animation if the fragment is active
     -------------------------------------------------------------------------------------------- */
    @Override
    public void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    /* ---------------------------------------------------------------------------------------------
            Function: onPause()
            Author: Ryan
            Description: Stops the animation if the fragment is not active
     -------------------------------------------------------------------------------------------- */
    @Override
    public void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }


}