package com.example.madassignment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MenuFragment extends Fragment {

    // Define UI Components
    private ImageButton aiButton;
    private ImageButton playerButton;
    private ImageView lightSpot1;
    private ImageView lightSpot2;
    private ImageView lightSpot3;
    private ImageView lightSpot4;

    private ImageButton winConditionLeft;
    private ImageButton winConditionRight;

    private ImageButton boardSizeLeft;
    private ImageButton boardSizeRight;
    private ImageView winImageContainer;

    private UserData userModel;
    private ImageView boardImageContainer;
    private int winCondition;
    private int boardSize;
    private int cyan = Color.CYAN;

    // Define ViewModels
    private NavigationData navModel;

    private GameData gameData;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navModel = new ViewModelProvider(getActivity()).get(NavigationData.class);
        gameData = new ViewModelProvider(getActivity()).get(GameData.class);
        userModel = new ViewModelProvider(getActivity()).get(UserData.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        //clear all stored user data from previous games
        userModel.resetUserData();

        // Define Buttons
        winConditionLeft = view.findViewById(R.id.left_win_button);
        winConditionRight= view.findViewById(R.id.right_win_button);
        boardSizeRight = view.findViewById(R.id.right_board_button);
        boardSizeLeft = view.findViewById(R.id.left_board_button);
        aiButton = view.findViewById(R.id.aiButton);
        playerButton = view.findViewById(R.id.playerButton);
        lightSpot1 = view.findViewById(R.id.lightSpot1);
        lightSpot2 = view.findViewById(R.id.lightSpot2);
        lightSpot3 = view.findViewById(R.id.lightSpot3);
        lightSpot4 = view.findViewById(R.id.lightSpot4);

        //define images
        winImageContainer = view.findViewById(R.id.win_condition);
        boardImageContainer = view.findViewById(R.id.board_size);

        // Initialize by removing colour filters on buttons
        aiButton.setColorFilter(null);
        playerButton.setColorFilter(null);

        fadeAnimation(lightSpot1, 1);
        fadeAnimation(lightSpot2, 0);
        fadeAnimation(lightSpot3, 0);
        fadeAnimation(lightSpot4, 1);

        if(navModel.getAnimationClickedValue() == 0) {
            navModel.setClickedValue(99);
        }

        winCondition = gameData.getWinCondition();
        boardSize = gameData.getBoardSize();

        // Initializations, maintains correctness if orientation change has occurred
        if (boardSize == 3) {
            boardSizeLeft.setVisibility(View.INVISIBLE);
            boardImageContainer.setImageResource(R.drawable.three_size_grid);
            if (gameData.getWinCondition() == 3) {
                winConditionRight.setVisibility(View.INVISIBLE);
            }
        }
        if (boardSize == 4) {
            boardSizeLeft.setVisibility(View.VISIBLE);
            boardSizeRight.setVisibility(View.VISIBLE);
            boardImageContainer.setImageResource(R.drawable.four_size_grid);
        }
        if (boardSize == 5) {
            boardSizeRight.setVisibility(View.INVISIBLE);
            boardImageContainer.setImageResource(R.drawable.five_size_grid);
        }

        // Initializations, maintains correctness if orientation change has occurred
        if (winCondition == 3) {
            if (boardSize == 3) {
                winConditionLeft.setVisibility(View.INVISIBLE);
                winConditionRight.setVisibility(View.INVISIBLE);
            }
            else {
                winConditionLeft.setVisibility(View.INVISIBLE);
                winConditionRight.setVisibility(View.VISIBLE);
            }
            winImageContainer.setImageResource(R.drawable.three_win_condition);
        }
        if (winCondition == 4) {
            if (boardSize == 4){
                winConditionLeft.setVisibility(View.INVISIBLE);
                winConditionRight.setVisibility(View.VISIBLE);
            }
            else if (boardSize == 5){
                winConditionLeft.setVisibility(View.VISIBLE);
                winConditionRight.setVisibility(View.VISIBLE);
            }
            winImageContainer.setImageResource(R.drawable.four_win_condition);
        }
        if (winCondition == 5) {
            if (boardSize == 5){
                winConditionLeft.setVisibility(View.VISIBLE);
                winConditionRight.setVisibility(View.INVISIBLE);
            }
            winImageContainer.setImageResource(R.drawable.five_win_condition);
        }

        /* Both AI_Button and Player_Button currently only direct to the board fragment
            this is to be changed when the backend for the AI & player back-end is added.
         */
        aiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aiButton.setColorFilter(cyan);

                navModel.setClickedValue(5);
                navModel.setHistoricalClickedValue(0);
                gameData.setGameMode(1);
            }
        });

        playerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerButton.setColorFilter(cyan);

                navModel.setClickedValue(5);
                navModel.setHistoricalClickedValue(0);
                gameData.setGameMode(2);
                System.out.println("The value of the game mose has been set ot " + gameData.getGameMode());

            }
        });

        winConditionLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleWinConditionClick(-1);
            }
        });

        winConditionRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleWinConditionClick(1);
            }
        });

        boardSizeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBoardSizeClick(-1);
            }
        });


        boardSizeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBoardSizeClick(1);
            }
        });


        return view;
    }

    /* ---------------------------------------------------------------------------------------------
    Function: fadeAnimation()
    Author: Ryan
    Modifications: -
    Purpose: Defines the behaviour of the light-spot animations for the background
    --------------------------------------------------------------------------------------------- */

    public void fadeAnimation(ImageView light_spot, int offset) {
        // Create a fade-in animation
        // Offset integer allows for variability in light spot fade behaviour
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        if (offset == 1) {
            fadeIn.setDuration(1500); // Fade in after 0.5 seconds
        }
        else {
            fadeIn.setDuration(1000); // Fade in after 1 second
        }
        fadeIn.setFillAfter(true);

        // Create a fade-out animation
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        if (offset == 1) {
            fadeOut.setStartOffset(1500);
            fadeOut.setDuration(1500); // Fade out after 0.5 seconds
        }
        else {
            fadeOut.setStartOffset(1000);
            fadeOut.setDuration(1000); // Fade out after 1 second
        }
        fadeOut.setFillAfter(true);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Restart the specific animation within the set
                light_spot.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Restart the specific animation within the set
                light_spot.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        light_spot.startAnimation(fadeIn);
    }

    public void handleWinConditionClick(int direction ){
        winCondition = gameData.getWinCondition();
        if(direction == -1) {
            System.out.println("The left button is clicked");
            winCondition += direction;
            gameData.setWinCondition(winCondition);
        }
        else {
            System.out.println("The right button is clicked");
            if(winCondition < gameData.getBoardSize()){
                winCondition += direction;
                gameData.setWinCondition(winCondition);
            }
        }

        // Updated by Ryan to hide the win condition arrows if they are not applicable eg 5 win for 3x3 board

        switch (winCondition) {
            case 3:
                winConditionLeft.setEnabled(false);
                if (gameData.getBoardSize() == 3) {
                    winConditionLeft.setVisibility(View.INVISIBLE);
                    winConditionRight.setVisibility(View.INVISIBLE);
                }
                else {
                    winConditionLeft.setVisibility(View.INVISIBLE);
                    winConditionRight.setVisibility(View.VISIBLE);
                }
                winImageContainer.setImageResource(R.drawable.three_win_condition);
                break;
            case 4:
                winConditionLeft.setEnabled(true);
                winConditionRight.setEnabled(true);
                if (gameData.getBoardSize()== 4){
                    winConditionLeft.setVisibility(View.VISIBLE);
                    winConditionRight.setVisibility(View.INVISIBLE);
                }
                else if (gameData.getBoardSize() == 5){
                    winConditionLeft.setVisibility(View.VISIBLE);
                    winConditionRight.setVisibility(View.VISIBLE);
                }
                winImageContainer.setImageResource(R.drawable.four_win_condition);
                break;
            case 5:
                winConditionRight.setEnabled(false);
                winImageContainer.setImageResource(R.drawable.five_win_condition);

                if (gameData.getBoardSize() == 5){
                    winConditionLeft.setVisibility(View.VISIBLE);
                    winConditionRight.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    public void handleBoardSizeClick(int direction){
        boardSize = gameData.getBoardSize();
        if(direction == -1) {
            System.out.println("The left button is clicked");
        }
        else {
            System.out.println("The right button is clicked");
        }

        boardSize += direction;
        gameData.setBoardSize(boardSize);

        // Updated by Ryan to hide the win condition arrows if they are not applicable eg 5 win for 3x3 board

        switch (boardSize) {
            case 3:
                boardSizeLeft.setEnabled(false);
                boardImageContainer.setImageResource(R.drawable.three_size_grid);
                boardSizeLeft.setVisibility(View.INVISIBLE);
                boardSizeRight.setVisibility(View.VISIBLE);

                if (gameData.getWinCondition() == 3) {
                    winConditionRight.setVisibility(View.INVISIBLE);
                }
                break;
            case 4:
                boardSizeLeft.setEnabled(true);
                boardSizeRight.setEnabled(true);
                boardImageContainer.setImageResource(R.drawable.four_size_grid);
                boardSizeLeft.setVisibility(View.VISIBLE);
                boardSizeRight.setVisibility(View.VISIBLE);
                if (gameData.getWinCondition() == 3) {
                    winConditionRight.setVisibility(View.VISIBLE);
                }
                else if (gameData.getWinCondition() == 4) {
                    winConditionRight.setVisibility(View.INVISIBLE);
                    winConditionLeft.setVisibility(View.VISIBLE);
                }
                break;
            case 5:
                boardSizeRight.setEnabled(false);
                boardImageContainer.setImageResource(R.drawable.five_size_grid);
                boardSizeLeft.setVisibility(View.VISIBLE);
                boardSizeRight.setVisibility(View.INVISIBLE);

                if (gameData.getWinCondition() == 3) {
                    winConditionRight.setVisibility(View.VISIBLE);
                }
                else if (gameData.getWinCondition() == 4) {
                    winConditionRight.setVisibility(View.VISIBLE);
                    winConditionLeft.setVisibility(View.VISIBLE);
                }
                else if (gameData.getWinCondition() == 5) {
                    winConditionRight.setVisibility(View.INVISIBLE);
                    winConditionLeft.setVisibility(View.VISIBLE);
                }
                break;
        }

        if(boardSize<gameData.getWinCondition()){
            handleWinConditionClick(-1);

        }

    }
}