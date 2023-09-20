package com.example.madassignment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class SelectUserFragment extends Fragment {


    private List<User> data;
    public List<Integer> dataSymbol;
    public ToggleButton toggleUser;
    private GameData gameData;
    private RecyclerView recyclerView;
    private RecyclerView symbolRecyclerView;
    private UserData userModel;
    private NavigationData navModel;
    private Button continueButton;
    private ConstraintLayout selectUserFragmentBackground;
    private AnimationDrawable animationDrawable;

    public SelectUserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameData = new ViewModelProvider(getActivity()).get(GameData.class);
        userModel = new ViewModelProvider(getActivity()).get(UserData.class);
        navModel = new ViewModelProvider(getActivity()).get(NavigationData.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_select_user, container, false);

        /* -----------------------------------------------------------------------------------------
            Function: Initialise layout elements
            Author: Parakram
         ---------------------------------------------------------------------------------------- */
        recyclerView = view.findViewById(R.id.recycler_user);
        symbolRecyclerView = view.findViewById(R.id.recycler_symbol);
        continueButton = view.findViewById(R.id.continue_button);
        toggleUser = view.findViewById(R.id.togglePlayer1);

        continueButton.setEnabled(false);

        toggleUser.setText("Select Player");
        toggleUser.setTextOn("Player 1");
        if (gameData.getGameMode() == 1) {
            toggleUser.setTextOff("AI");
        }
        else {
            toggleUser.setTextOff("Player 2");
        }

        /* -----------------------------------------------------------------------------------------
            Function: Initialise animation elements
            Author: Ryan
            Description: Sets the fade in and fade out characteristics of the background
         ---------------------------------------------------------------------------------------- */
        selectUserFragmentBackground = (ConstraintLayout) view.findViewById(R.id.select_user_fragment);
        animationDrawable = (AnimationDrawable) selectUserFragmentBackground.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(2000);


        toggleUser.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                userModel.setFirstMove(1);
            } else {
                userModel.setFirstMove(2);
            }
        });

        /* -----------------------------------------------------------------------------------------
            Function: Initialise Recycler Grid elements
            Author: Parakram
         ---------------------------------------------------------------------------------------- */
        //get data
        data = getUserData();
        dataSymbol = getIconData();
        //define grid
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4,
                GridLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 4,
                GridLayoutManager.VERTICAL, false);
        if (data.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            continueButton.setVisibility(View.GONE);
        }
        else {
            recyclerView.setLayoutManager(gridLayoutManager);
            //set adapter
            UserSelectAdapter userSelectAdapter =new UserSelectAdapter(data, userModel, gameData, this, navModel);
            recyclerView.setAdapter(userSelectAdapter);
        }

        symbolRecyclerView.setLayoutManager(gridLayoutManager2);
        SymbolSelectAdapter symbolSelectAdapter = new SymbolSelectAdapter(dataSymbol, userModel, gameData, this);
        symbolRecyclerView.setAdapter(symbolSelectAdapter);

        userModel.userId2.observe(getActivity(), new Observer<Long>() {
            @Override
            public void onChanged(Long integer) {
                if (gameData.getGameMode() == 1) {
                    if (userModel.getUserId() != 0 && userModel.getUserSymbol1() != 0 && userModel.getUserSymbol2() != 0 && userModel.getFirstMove() != 0) {
                        continueButton.setEnabled(true);
                    } else {
                        continueButton.setEnabled(false);
                    }
                }
                else {
                    if (userModel.getUserId2() != 0 && userModel.getUserId() != 0 && userModel.getUserSymbol1() != 0 && userModel.getUserSymbol2() != 0 && userModel.getFirstMove() != 0) {
                        continueButton.setEnabled(true);
                    } else {
                        continueButton.setEnabled(false);
                    }
                }
            }});
        userModel.userId.observe(getActivity(), new Observer<Long>() {
            @Override
            public void onChanged(Long integer) {
                if (gameData.getGameMode() == 1) {
                    if (userModel.getUserId() != 0 && userModel.getUserSymbol1() != 0 && userModel.getUserSymbol2() != 0 && userModel.getFirstMove() != 0) {
                        continueButton.setEnabled(true);
                    } else {
                        continueButton.setEnabled(false);
                    }
                }
                else {
                    if (userModel.getUserId2() != 0 && userModel.getUserId() != 0 && userModel.getUserSymbol1() != 0 && userModel.getUserSymbol2() != 0 && userModel.getFirstMove() != 0) {
                        continueButton.setEnabled(true);
                    } else {
                        continueButton.setEnabled(false);
                    }
                }
            }});

        userModel.userSymbol1.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (gameData.getGameMode() == 1) {
                    if (userModel.getUserId() != 0 && userModel.getUserSymbol1() != 0 && userModel.getUserSymbol2() != 0 && userModel.getFirstMove() != 0) {
                        continueButton.setEnabled(true);
                    } else {
                        continueButton.setEnabled(false);
                    }
                }
                else {
                    if (userModel.getUserId2() != 0 && userModel.getUserId() != 0 && userModel.getUserSymbol1() != 0 && userModel.getUserSymbol2() != 0 && userModel.getFirstMove() != 0) {
                        continueButton.setEnabled(true);
                    } else {
                        continueButton.setEnabled(false);
                    }
                }
            }});

        userModel.userSymbol2.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (gameData.getGameMode() == 1) {
                    if (userModel.getUserId() != 0 && userModel.getUserSymbol1() != 0 && userModel.getUserSymbol2() != 0 && userModel.getFirstMove() != 0) {
                        continueButton.setEnabled(true);
                    } else {
                        continueButton.setEnabled(false);
                    }
                }
                else {
                    if (userModel.getUserId2() != 0 && userModel.getUserId() != 0 && userModel.getUserSymbol1() != 0 && userModel.getUserSymbol2() != 0 && userModel.getFirstMove() != 0) {
                        continueButton.setEnabled(true);
                    } else {
                        continueButton.setEnabled(false);
                    }
                }
            }});

        userModel.firstMove.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (gameData.getGameMode() == 1) {
                    if (userModel.getUserId() != 0 && userModel.getUserSymbol1() != 0 && userModel.getUserSymbol2() != 0 && userModel.getFirstMove() != 0) {
                        continueButton.setEnabled(true);
                    } else {
                        continueButton.setEnabled(false);
                    }
                }
                else {
                    if (userModel.getUserId2() != 0 && userModel.getUserId() != 0 && userModel.getUserSymbol1() != 0 && userModel.getUserSymbol2() != 0 && userModel.getFirstMove() != 0) {
                        continueButton.setEnabled(true);
                    } else {
                        continueButton.setEnabled(false);
                    }
                }
            }});

        /* -----------------------------------------------------------------------------------------
            Function: continueButton Click Listener
            Author: Parakram
         ---------------------------------------------------------------------------------------- */
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                navModel.setClickedValue(1);
            }
        });
        return view;
    }

    /* -----------------------------------------------------------------------------------------
                Function: getLeaderBoardData()
                Author: Parakram
             ---------------------------------------------------------------------------------------- */
    public List<User> getUserData() {
        UserDao userDao = initialiseDB();
        data = null;
        data = userDao.getAllUsers();
        //create new fake user row for the button that will be displayed always in the RecycleView to add new user
        User button = new User();
        //give very large ID that won't be attainable
        button.setId(100000l);
        //set username that can be identified
        button.setUserName("button");
        //set the user icon image resource ID
        button.setUserIcon(R.drawable.add_icon);
        //add to data list
        data.add(button);
        return data;
    }
    public UserDao initialiseDB() {
        return UserDbInstance.getDatabase(getContext()).userDao();
    }

        /* -----------------------------------------------------------------------------------------
                Function: Icon Data Array
                Author: Ryan
                Description: Adds drawable elements to the icon data array
         ---------------------------------------------------------------------------------------- */
    public List<Integer> getIconData(){
        List<Integer> data = new ArrayList<Integer>();
        data.add(R.drawable.cross);
        data.add(R.drawable.circle);
        data.add(R.drawable.tree_icon);
        data.add(R.drawable.axe_icon);
        data.add(R.drawable.panda_icon);
        data.add(R.drawable.ladybug_icon);
        data.add(R.drawable.penguin_icon);
        data.add(R.drawable.kangaroo_icon);
        data.add(R.drawable.butterfly_icon);
        return data;
    }

    /* -----------------------------------------------------------------------------------------
                Function: onViewCreated()
                Author: Ryan
                Description: Handles the lifecycle of the animation, starts it when view is created
         ---------------------------------------------------------------------------------------- */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animationDrawable = (AnimationDrawable) selectUserFragmentBackground.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(2000);

        if (!animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    /* -----------------------------------------------------------------------------------------
                Function: onDestroyView()
                Author: Ryan
                Description: Handles the lifecycle of the animation, stops it when view is destroyed
         ---------------------------------------------------------------------------------------- */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }
}