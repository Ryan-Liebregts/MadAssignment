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

    public ToggleButton toggleUser1;

    public ToggleButton toggleUser2;

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
        //define components
        recyclerView = view.findViewById(R.id.recycler_user);
        symbolRecyclerView = view.findViewById(R.id.recycler_symbol);
        continueButton = view.findViewById(R.id.continue_button);
        toggleUser1 = view.findViewById(R.id.togglePlayer1);

        toggleUser1.setText("Select Player");
        toggleUser1.setTextOn("Player 1");
        if (gameData.getGameMode() == 1) {
            toggleUser1.setTextOff("AI");
        }
        else {
            toggleUser1.setTextOff("Player 2");

        }
        // Animates the background gradient
        selectUserFragmentBackground = (ConstraintLayout) view.findViewById(R.id.select_user_fragment);
        animationDrawable = (AnimationDrawable) selectUserFragmentBackground.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(2000);


        toggleUser1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                userModel.setFirstMove(1);
            } else {
                userModel.setFirstMove(2);
            }
        });
        //set recycler
        //get data
        data = getLeaderBoardData();
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

        userModel.userId2.observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long integer) {
                if (userModel.getUserId2() != 0 && userModel.getUserId() != 0 && userModel.getFirstMove() != 0 && userModel.getUserSymbol1() != 0 && userModel.getUserSymbol2() != 0) {
                    continueButton.setEnabled(true);
                }
                else{
                    continueButton.setEnabled(false);
                }
            }
        });

        userModel.userId.observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long integer) {
                if(gameData.getGameMode() == 1) {
                    if (userModel.getUserId() != 0) {
                        continueButton.setEnabled(true);
                    }
                    else{
                        continueButton.setEnabled(false);
                    }
                }
                else {
                    if (userModel.getUserId2() != 0 && userModel.getUserId() != 0) {
                        continueButton.setEnabled(true);
                    }
                    else{
                        continueButton.setEnabled(false);
                    }
                }
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                navModel.setClickedValue(1);
            }
        });
//TODO Continue button logic - pk
        return view;
    }

    public List<User> getLeaderBoardData() {
        UserDao userDao = initialiseDB();
        data = null;
        data = userDao.getAllUsers();
        User button = new User();
        button.setId(100000l);
        button.setUserName("button");
        button.setUserIcon(R.drawable.add_icon);
        data.add(button);
        return data;
    }
    public UserDao initialiseDB() {
        return UserDbInstance.getDatabase(getContext()).userDao();
    }


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

    // The following two methods handle the lifecycle of the animation to prevent errors
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }
}