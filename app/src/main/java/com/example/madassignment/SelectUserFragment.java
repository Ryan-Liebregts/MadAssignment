package com.example.madassignment;

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

import java.util.List;

public class SelectUserFragment extends Fragment {

    private List<User> data;

    private GameData gameData;

    private RecyclerView recyclerView;

    private UserData userModel;

    private NavigationData navModel;

    private Button continueButton;

    private Button newCharButton;

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
        continueButton = view.findViewById(R.id.continue_button);
        newCharButton = view.findViewById(R.id.create_user_button);

        // Animates the background gradient
        selectUserFragmentBackground = (ConstraintLayout) view.findViewById(R.id.select_user_fragment);
        animationDrawable = (AnimationDrawable) selectUserFragmentBackground.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(2000);


        //set recycler
        //get data
        data = getLeaderBoardData();
        if (data.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            continueButton.setVisibility(View.GONE);
        }
        else {
            //define grid
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1,
                    GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            //set adapter
            UserSelectAdapter userSelectAdapter =new UserSelectAdapter(data, userModel, gameData, this);
            recyclerView.setAdapter(userSelectAdapter);
        }


        userModel.userId2.observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long integer) {
                if (userModel.getUserId2() != 0 && userModel.getUserId() != 0) {
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
                System.out.println("Hello");
                navModel.setClickedValue(1);
            }
        });

        newCharButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                System.out.println("Hello");
                navModel.setClickedValue(3);
                navModel.setHistoricalClickedValue(6);
            }
        });

        return view;
    }

    public List<User> getLeaderBoardData() {
        UserDao userDao = initialiseDB();


        data = null;
        data = userDao.getAllUsers();
        System.out.println(data);
        return data;
    }
    public UserDao initialiseDB() {
        return UserDbInstance.getDatabase(getContext()).userDao();
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