package com.example.madassignment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

public class BoardFragment extends Fragment implements BoardButtonAdapter.AdapterCallback {
    private ImageButton undoButton, resetButton;
    private NavigationData navModel;
    private UserData userModel;
    private ValueAnimator notification_anim;
    private ImageView winCondition;
    private ImageButton player1Icon;
    private ImageButton player1IconDull;
    private ImageButton player2Icon;
    private ImageButton player2IconDull;
    private TextView player1Moves;
    private TextView player2Moves;
    private TextView player1Name;
    private TextView player2Name;
    private ImageView player1Symbol;
    private ImageView player2Symbol;

    private char[][] gameBoard;
    private int boardSize;
    private int winConditionInput;
    private int locI;
    private int locJ;
    private int otherLocI;
    private int otherLocJ;
    private int cyan = Color.CYAN;
    private boolean isPlayer1GoingFirst, isThereAWinner, validInput = true, isPlayer1sTurn, isDraw;
    private char playerMarker, otherMarker;
    private TextView gameOverText;
    private TextView invalidMoveText;
    private ArrayList<BoardButtonData> data;
    private GameData gameData;
    private BoardButtonAdapter adapter;
    private RecyclerView rv;
    private TextView timerText;
    private ArrayList<int[]> moveList;
    private EditUser editUserModel;
    private Handler handler = new Handler();
    static CountDownTimer countDownTimer;
    long delayMillis = 300;
    private boolean savedState;
    boolean hasTimerRanOut;
    static boolean currentTimerExists = false;

    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userModel = new ViewModelProvider(getActivity()).get(UserData.class);
        navModel = new ViewModelProvider(getActivity()).get(NavigationData.class);
        gameData = new ViewModelProvider(getActivity()).get(GameData.class);
        editUserModel = new ViewModelProvider(getActivity()).get(EditUser.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board, container, false);
        //load users from DB by id
        loadUsers();

        /* -----------------------------------------------------------------------------------------
            Function: Initialise layout elements
            Author: Jules
            Description: Initialises data within the fragment
         ---------------------------------------------------------------------------------------- */
        resetButton = view.findViewById(R.id.reset_button);
        gameOverText = view.findViewById(R.id.gameoverText);
        undoButton = view.findViewById(R.id.undo_button);
        winCondition = view.findViewById(R.id.win_condition_icon);
        timerText = view.findViewById(R.id.timerTextView);
        invalidMoveText = view.findViewById(R.id.invalidmoveText);
        player1Icon = view.findViewById(R.id.player_1_icon);
        player1IconDull= view.findViewById(R.id.player_1_icon_dull);
        player1Name = view.findViewById(R.id.player_1_name);
        player1Moves = view.findViewById(R.id.player_1_moves);
        player1Symbol = view.findViewById(R.id.player_1_symbol);
        player2Icon = view.findViewById(R.id.player_2_icon);
        player2IconDull= view.findViewById(R.id.player_2_icon_dull);
        player2Name = view.findViewById(R.id.player_2_name);
        player2Moves = view.findViewById(R.id.player_2_moves);
        player2Symbol = view.findViewById(R.id.player_2_symbol);

        // Set game over text as invisible
        gameOverText.setVisibility(View.INVISIBLE);

        // Set board size
        boardSize = gameData.getBoardSize();

        // Set win condition ImageView
        if(gameData.getWinCondition() == 3) {
            winCondition.setImageResource(R.drawable.three_win_condition);
        }
        if(gameData.getWinCondition() == 4) {
            winCondition.setImageResource(R.drawable.four_win_condition);
        }
        if(gameData.getWinCondition() == 5) {
            winCondition.setImageResource(R.drawable.five_win_condition);
        }


        ///this is just an error case just in case we somehow get to teh board and dont have a user selected
        if ((gameData.getGameMode() == 1 && userModel.getUserId() == 0) || (gameData.getGameMode() == 2 && userModel.getUserId() == 0 && userModel.getUserId2() == 0)) {
            navModel.setClickedValue(0);
            return view;
        }
        //All code below will only execute if allowed to by the if statement above use multiple returns as an if/else
        //This function handles settings hte game user data such as icons and symbols and username on the board
        setGameUserData(view);

        // Set locI, locJ, otherLocI and otherLocJ values to 0
        locI = 0;
        locJ = 0;
        otherLocI = 0;
        otherLocJ = 0;

        // Set isThereAWinner and isDraw to false
        isThereAWinner = false;
        isDraw = false;

        // Is the player going first?
        if(userModel.getFirstMove() == 1) gameData.setIsPlayer1GoingFirst(true);
        else if(userModel.getFirstMove() == 2) gameData.setIsPlayer1GoingFirst(false);
        isPlayer1GoingFirst = gameData.getIsPlayer1GoingFirst();
        isPlayer1sTurn = isPlayer1GoingFirst;

        // Set player and ai marker
        playerMarker = gameData.getPlayer1MarkerSymbol();
        if(gameData.getGameMode() == 1){
            otherMarker = gameData.getAIMarkerSymbol();
        }
        else{
            otherMarker = gameData.getPlayer2MarkerSymbol();
        }

        // Win condition from game data used to dynamically show on board the game
        winConditionInput = gameData.getWinCondition();
        switch(winConditionInput){
            case 3:
                winCondition.setImageResource(R.drawable.three_win_condition);
                break;
            case 4:
                winCondition.setImageResource(R.drawable.four_win_condition);
                break;
            case 5:
                winCondition.setImageResource(R.drawable.five_win_condition);
                break;
        }

        // Create new data array for grid buttons, and filled with empty button data
        data = new ArrayList<BoardButtonData>();
        for(int i = 0; i < gameData.getBoardSize() * gameData.getBoardSize(); i++) {
            data.add(new BoardButtonData(0, i));
        }

        /* -----------------------------------------------------------------------------------------
            Function: Create Recyclerview Grid
            Author: Jules
            Description: Create the RecyclerView Grid for the board based on board game size
         ---------------------------------------------------------------------------------------- */
        rv = view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), boardSize, GridLayoutManager.VERTICAL,false);
        rv.setLayoutManager(gridLayoutManager);
        adapter = new BoardButtonAdapter(data, BoardFragment.this);
        rv.setAdapter(adapter);

        // Initialise board
        gameBoard = new char[boardSize][boardSize];


        int screenOrientation = getResources().getConfiguration().orientation;


        // If need to get previous game state, retrieve previous game state information
        if(gameData.getNeedSaveGameState() == true){
                if (gameData.getMoveList().size() != 0) {
                    retrieveGameBoardState();
                    // Initialise move list
                }

                moveList = gameData.getMoveList();
            // If previous game state was a game over, reset board
            if(gameData.getIsGameOver() == true){
                resetGame(); //Reset the board
                gameData.setIsGameOver(false);
            }
            // Else create default data
        } else {
            // Create board filled with '-'
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    gameBoard[i][j] = '-';
                }
            }
            gameData.setGameBoard(gameBoard);

            // Initialise move list
            moveList = new ArrayList<int[]>();
        }


        // If game mode is player vs ai, and player 1 goes first, p1 moves
        if(isPlayer1GoingFirst && gameData.getGameMode() == 1){
            if(gameData.getNeedSaveGameState() == true){
                gameData.setWhoseTurn(gameData.getWhoseTurn());
            } else {
                gameData.setWhoseTurn(1); //Set whose turn to player 1
            }
        }

        // If game mode is player vs ai, and player 1 doest not go first, AI moves
        if(!isPlayer1GoingFirst && gameData.getGameMode() == 1){
            if(gameData.getNeedSaveGameState() == true){
                gameData.setWhoseTurn(gameData.getWhoseTurn());
            } else {
                gameData.setWhoseTurn(3); //Set whose turn to AI
                aiMove(gameBoard); // AI moves
                isPlayer1sTurn = true;
                gameData.setWhoseTurn(1); //Set whose turn to players
            }
            gameData.setGameBoard(gameBoard); // Update game board in game data
            gameData.setMoveList(moveList); // Update move list in game data
        }

        // If game mode is pvp and player 1 is not going first, set whose turn to player 2
        // or if player 1 is going first, set whose turn to player 1
        if(!isPlayer1GoingFirst && gameData.getGameMode() == 2){
            if(gameData.getNeedSaveGameState() == true){
                gameData.setWhoseTurn(gameData.getWhoseTurn());
            } else {
                gameData.setWhoseTurn(2); //Set whose turn to player 2
            }
        }
        else if(isPlayer1GoingFirst && gameData.getGameMode() == 2){
            if(gameData.getNeedSaveGameState() == true){
                gameData.setWhoseTurn(gameData.getWhoseTurn());
            } else {
                gameData.setWhoseTurn(1); //Set whose turn to player 1
            }
        }

        // Set has Timer ran out to false
        hasTimerRanOut = false;

        // Start timer
        startTimer();
        if(!currentTimerExists) startTimer();

        /* -----------------------------------------------------------------------------------------
            Function: Reset Button ClickListener
            Author: Jules
            Description: Resets the game board by calling the reset() function
            - Modified by Ryan to implement the rotation and colour change animations
         ---------------------------------------------------------------------------------------- */
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(notification_anim != null && notification_anim.isRunning()) {
                    notification_anim.cancel();
                }

                Animation reset = AnimationUtils.loadAnimation(getActivity(),R.anim.reset_rotation_anim);

                // Animation listener for reset button
                reset.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Animation start - changes button to cyan
                        resetButton.setColorFilter(cyan);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Animation end - removes cyan
                        resetButton.setColorFilter(null);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // Can be left empty as no repeats are occurring
                    }
                });

                resetButton.startAnimation(reset);
                resetGame(); //Reset the board

            }
        });

        /* -----------------------------------------------------------------------------------------
            Function: Undo Button ClickListener
            Author: Ryan
            Description: Provides the rotation and colour change for the undo button
            - Modified by Jules to implement the undo logic
         ---------------------------------------------------------------------------------------- */
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(moveList.size() == 1 && gameData.gameMode.getValue() == 1 && !isPlayer1GoingFirst) return; //If player vs ai, ai goes first, ignore undo button for first move

                Animation undo = AnimationUtils.loadAnimation(getActivity(),R.anim.undo_rotation_anim);

                // Animation listener for pressing reset button
                undo.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Animation started - Changes colour of the button to show it has been pressed
                        undoButton.setColorFilter(cyan);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Animation ended - removes colour filter at the end of the animation
                        undoButton.setColorFilter(null);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // Can be left empty as no repeats are occurring
                    }
                });

                undoButton.startAnimation(undo);
                undoMove(); //Undo last player move

                //Reset timer
                stopTimer();
                startTimer();
            }
        });

        return view;
    }

    //This function allows the fragment to load the game users directly from the database
    public void loadUsers() {
        UserDao userDao = initialiseDB();
        User player1 = userDao.getUserByID(userModel.getUserId());
        User player2 = userDao.getUserByID(userModel.getUserId2());
        userModel.setUserIcon(player1.getUserIcon());
        userModel.setUserName(player1.getUserName());
        if (gameData.getGameMode() != 1) {
            userModel.setUserIcon2(player2.getUserIcon());
            userModel.setUserName2(player2.getUserName());
        }
    }

    //set the user data above the board

    public void retrieveGameBoardState(){

        // sets current fragment gameBoard to previous gameBoard
        gameBoard = gameData.getGameBoard();
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                System.out.println(gameBoard[i][j]);
            }
        }

        int adapterDataStateIndex;
        // for AI gamemode
        if(gameData.getGameMode() == 1) {
            for (int i = 0; i < gameBoard.length; i++) {
                for (int j = 0; j < gameBoard[0].length; j++) {
                    // update all AI symbols with drawable
                    if (gameBoard[i][j] == gameData.getAIMarkerSymbol()) {
                        // update all player symbols with drawable
                        adapterDataStateIndex = (i * gameData.getBoardSize()) + j;
                        adapter.data.get(adapterDataStateIndex).setMarkerSymbol(gameData.getAIMarkerSymbol()); // Set board button data to appropriate symbol
                        adapter.data.get(adapterDataStateIndex).setImageResource(userModel.getUserSymbol2()); // Set board button data to appropriate drawable
                    } else if (gameBoard[i][j] == gameData.getPlayer1MarkerSymbol()) {
                        // update all player symbols with drawable
                        adapterDataStateIndex = (i * gameData.getBoardSize()) + j;
                        adapter.data.get(adapterDataStateIndex).setMarkerSymbol(gameData.getPlayer1MarkerSymbol()); // Set board button data to appropriate symbol
                        adapter.data.get(adapterDataStateIndex).setImageResource(userModel.getUserSymbol1()); // Set board button data to appropriate drawable
                    }
                }
            }
            // for pvp gamemode
        } else if (gameData.getGameMode() == 2) {
            for (int i = 0; i < gameBoard.length; i++) {
                for (int j = 0; j < gameBoard[0].length; j++) {
                    // update all AI symbols with drawable
                    if (gameBoard[i][j] == gameData.getPlayer2MarkerSymbol()) {
                        // update all player symbols with drawable
                        adapterDataStateIndex = (i * gameData.getBoardSize()) + j;
                        adapter.data.get(adapterDataStateIndex).setMarkerSymbol(gameData.getPlayer2MarkerSymbol()); // Set board button data to appropriate symbol
                        adapter.data.get(adapterDataStateIndex).setImageResource(userModel.getUserSymbol2()); // Set board button data to appropriate drawable
                    } else if (gameBoard[i][j] == gameData.getPlayer1MarkerSymbol()) {
                        // update all player symbols with drawable
                        adapterDataStateIndex = (i * gameData.getBoardSize()) + j;
                        adapter.data.get(adapterDataStateIndex).setMarkerSymbol(gameData.getPlayer1MarkerSymbol()); // Set board button data to appropriate symbol
                        adapter.data.get(adapterDataStateIndex).setImageResource(userModel.getUserSymbol1()); // Set board button data to appropriate drawable
                    }
                }
            }
        }

        adapter.notifyDataSetChanged(); //Notify adapter to update UI

    }

    //This is for setting hte users data on the baord fragment
    public void setGameUserData(View view) {
        // Initialising the setting the symbol for naughts or crosses
        player1Symbol.setImageResource(userModel.getUserSymbol1());
        player2Symbol.setImageResource(userModel.getUserSymbol2());

        player1Icon.setImageResource(userModel.getUserIcon());
        player1IconDull.setImageResource(userModel.getUserIcon());
        player1Name.setText(userModel.getUserName());
        player1Moves.setText("0 Moves");
        player2Moves.setText("0 Moves");


        if (gameData.getGameMode() == 1) {
            player2Icon.setImageResource(R.drawable.robot_icon);
            player2IconDull.setImageResource(R.drawable.robot_icon);
            player2Name.setText("AI");
        }
        else {
            player2Icon.setImageResource(userModel.getUserIcon2());
            player2IconDull.setImageResource(userModel.getUserIcon2());
            player2Name.setText(userModel.getUserName2());
        }

        player1Icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUserModel.setUserName(userModel.getUserName());
                editUserModel.setUserId(userModel.getUserId());
                editUserModel.setUserIcon(userModel.getUserIcon());
                stopTimer();
                navModel.setClickedValue(3);
                gameData.setNeedSaveGameState(true);
                navModel.setHistoricalClickedValue(1);
            }
        });

        player1IconDull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUserModel.setUserName(userModel.getUserName());
                editUserModel.setUserId(userModel.getUserId());
                editUserModel.setUserIcon(userModel.getUserIcon());
                stopTimer();
                navModel.setClickedValue(3);
                gameData.setNeedSaveGameState(true);
                navModel.setHistoricalClickedValue(1);
            }
        });

        if (gameData.getGameMode() != 1) {
            player2Icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editUserModel.setUserName(userModel.getUserName2());
                    editUserModel.setUserId(userModel.getUserId2());
                    editUserModel.setUserIcon(userModel.getUserIcon2());
                    stopTimer();
                    navModel.setClickedValue(3);
                    gameData.setNeedSaveGameState(true);

                    navModel.setHistoricalClickedValue(1);
                }
            });

            player2IconDull.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editUserModel.setUserName(userModel.getUserName2());
                    editUserModel.setUserId(userModel.getUserId2());
                    editUserModel.setUserIcon(userModel.getUserIcon2());
                    stopTimer();
                    navModel.setClickedValue(3);
                    gameData.setNeedSaveGameState(true);
                    navModel.setHistoricalClickedValue(1);
                }
            });
        }
        //moves updating code
        gameData.player1Moves.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                player1Moves.setText(String.format("%d MOVES", integer));
            }
        });
        gameData.player2Moves.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                player2Moves.setText(String.format("%d MOVES", integer));
            }
        });

        gameData.whoseTurn.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer != 1) {
                    //make the icon for the player 1 dull and hte player 2 bright
                    player1Icon.setVisibility(View.GONE);
                    player1IconDull.setVisibility(View.VISIBLE);
                    player2Icon.setVisibility(View.VISIBLE);
                    player2IconDull.setVisibility(View.GONE);
                }
                else{
                    //make the player 1 icon bright and the player 2 dull
                    player2Icon.setVisibility(View.GONE);
                    player2IconDull.setVisibility(View.VISIBLE);
                    player1Icon.setVisibility(View.VISIBLE);
                    player1IconDull.setVisibility(View.GONE);
                }
            }
        });
    }

    /* -----------------------------------------------------------------------------------------
        Function: aiMove(char[][] pGameBoard)
        Author: Jules
        Description: Provides the logic for the AI's marker placement
        ---------------------------------------------------------------------------------------- */
    public void aiMove(char[][] pGameBoard){

        // Do While loop repeated runs until a empty position in the game board is found
        do {
            Random rand = new Random(); //Set random seed

            int aiMarkerCordsRow = rand.nextInt(pGameBoard.length), aiMarkerCordsCol = rand.nextInt(pGameBoard.length); // Randomly select board position

            if(pGameBoard[aiMarkerCordsRow][aiMarkerCordsCol] != '-') validInput = false; // If selected position is already validInput is set to false
            else {
                pGameBoard[aiMarkerCordsRow][aiMarkerCordsCol] = otherMarker; // Game board position is set to AI marker
                otherLocI = aiMarkerCordsRow; // Set otherLocI to randomly selected row position
                otherLocJ = aiMarkerCordsCol; // Set otherLocJ to randomly selected column position
                validInput = true; // validInput is set to true
            }
        } while(!validInput);

        int adapterDataIndex = (otherLocI * pGameBoard.length) + otherLocJ; // Determine where AI placed marker in adapter data arraylist index
        adapter.data.get(adapterDataIndex).setMarkerSymbol(gameData.getAIMarkerSymbol()); // Set board button data to appropriate symbol
        adapter.data.get(adapterDataIndex).setImageResource(userModel.getUserSymbol2()); // Set board button data to appropriate drawable
        adapter.notifyDataSetChanged(); //Notify adapter to update UI

        // Add move to move list
        int[] move = {otherLocI, otherLocJ};
        gameData.setPlayer2Moves(gameData.getPlayer2Moves() + 1);
        moveList.add(move);

        /*
        //Printing game board for testing purposes, can be deleted
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                System.out.print(gameBoard[i][j]);
            }
            System.out.println("");
        } */
    }

    /* ---------------------------------------------------------------------------
        Function: checkConsecutiveMarkers
        Author: Yi Xiang
        Notifications: -
        Purpose: Check's how many markers there are in a row, with row direction based on [pNextI,pNextJ]
        --------------------------------------------------------------------------- */
    public boolean checkConsecutiveMarkers(char[][] pGameBoard, int pWinConditionInput, int pLocI, int pLocJ, boolean pIsPlayer1sTurn, int pNextI, int pNextJ){
        int playerMarkerCount;
        int otherMarkerCount;
        int indI;
        int indJ;
        boolean inARowForward;
        boolean inARowReverse;

        playerMarkerCount = 0;
        otherMarkerCount = 0;
        // If called for player, will check how many in a row both ways
        if(pIsPlayer1sTurn == true) {
            inARowForward = true;
            inARowReverse = true;
            indI = pLocI;
            indJ = pLocJ;
            playerMarkerCount = 1; // player counter = 1 because includes the currently placed marker
            while(inARowForward == true) { // counts how many consecutive player counters forward
                if (((indI + pNextI) < pGameBoard.length) && ((indI + pNextI) >= 0) && ((indJ + pNextJ) < pGameBoard.length) && ((indJ + pNextJ) >= 0)) { // makes sure next check is inside board
                    if (pGameBoard[(indI + pNextI)][(indJ + pNextJ)] == playerMarker) {
                        playerMarkerCount++; // if next forward consecutive space contains player marker, increase count
                        indI = indI + pNextI;
                        indJ = indJ + pNextJ;
                    } else {
                        inARowForward = false;
                    }
                } else {
                    inARowForward = false;
                }
            }
            indI = pLocI;
            indJ = pLocJ;

            while(inARowReverse==true){ // counts how many consecutive player counters reverse
                if (((indI - pNextI) < pGameBoard.length) && ((indI - pNextI) >= 0) && ((indJ - pNextJ) < pGameBoard.length) && ((indJ - pNextJ) >= 0)) { // makes sure next check is inside board
                    if (pGameBoard[(indI - pNextI)][(indJ - pNextJ)] == playerMarker) {
                        playerMarkerCount++; // if next reverse consecutive space contains player marker, increase count
                        indI = indI - pNextI;
                        indJ = indJ - pNextJ;
                    }
                    else {
                        inARowReverse=false;
                    }
                }
                else {
                    inARowReverse=false;
                }
            }
        } else {
            inARowForward = true;
            inARowReverse = true;
            indI = otherLocI;
            indJ = otherLocJ;
            otherMarkerCount = 1; // ai counter = 1 because includes the currently placed marker
            while(inARowForward==true) { // counts how many consecutive ai counters forward
                if (((indI + pNextI) < pGameBoard.length) && ((indI + pNextI) >= 0) && ((indJ + pNextJ) < pGameBoard.length) && ((indJ + pNextJ) >= 0)) {
                    if (pGameBoard[(indI + pNextI)][(indJ + pNextJ)] == otherMarker) {
                        otherMarkerCount++; // if next forward consecutive space contains ai marker, increase count
                        indI = indI + pNextI;
                        indJ = indJ + pNextJ;
                    } else {
                        inARowForward = false;
                    }
                } else {
                    inARowForward = false;
                }
            }
            indI = otherLocI;
            indJ = otherLocJ;
            while(inARowReverse==true){ // counts how many consecutive ai counters reverse
                if (((indI - pNextI) < pGameBoard.length) && ((indI - pNextI) >= 0) && ((indJ - pNextJ) < pGameBoard.length) && ((indJ - pNextJ) >= 0)) {
                    if (pGameBoard[(indI - pNextI)][(indJ - pNextJ)] == otherMarker) {
                        otherMarkerCount++; // if next reverse consecutive space contains ai marker, increase count
                        indI = indI - pNextI;
                        indJ = indJ - pNextJ;
                    }
                    else {
                        inARowReverse=false;
                    }
                }
                else {
                    inARowReverse=false;
                }
            }
        }
        if(playerMarkerCount >= pWinConditionInput || otherMarkerCount >= pWinConditionInput) return true; // if either counter meets win condition, consecutive markers is true
        return false;
    }

    /* ---------------------------------------------------------------------------
        Function: checkIfThereIsWinner
        Author: Yi Xiang
        Notifications: -
        Purpose: checks if there is a winner on the board, if so return true
        --------------------------------------------------------------------------- */
    public boolean checkIfThereIsWinner(char[][] pGameBoard, int pWinConditionInput, int pLocI, int pLocJ, boolean pIsPlayer1sTurn){
        // Checks if there are enough markers in a row to meet the win condition
        // Horizontal [0,+1]
        if((checkConsecutiveMarkers(pGameBoard, pWinConditionInput, pLocI, pLocJ, pIsPlayer1sTurn, 0, 1))){
            return true;
            // Vertical [+1,0]
        } else if ((checkConsecutiveMarkers(pGameBoard, pWinConditionInput, pLocI, pLocJ, pIsPlayer1sTurn, 1, 0))) {
            return true;
            // Diagonal Top Left Bottom Right [+1,+1]
        } else if ((checkConsecutiveMarkers(pGameBoard, pWinConditionInput, pLocI, pLocJ, pIsPlayer1sTurn, 1, 1))) {
            return true;
            // Diagonal Top Right Bottom Left [+1,-1]
        }  else if ((checkConsecutiveMarkers(pGameBoard, pWinConditionInput, pLocI, pLocJ, pIsPlayer1sTurn, 1, -1))) {
            return true;
        } else {

        }
        return false;
    }

    /* -----------------------------------------------------------------------------------------
        Function: endGame(boolean pIsPlayer1sTurn, boolean pIsDraw)
        Author: Jules + Yi Xiang + Parakram
        Description: Ends the game by disabling buttons, display appropriate game over text
        and updating database
        ---------------------------------------------------------------------------------------- */
    public void endGame(boolean pIsPlayer1sTurn, boolean pIsDraw){


        //initialise DB
        UserDao userDao = initialiseDB();

        //update the amount of games played
        gameData.setIsGameOver(true);

        if (gameData.getGameMode() == 1) {
            userDao.updateUserGamesPlayed(userModel.getUserId());
        }
        else{
            userDao.updateUserGamesPlayed(userModel.getUserId());
            userDao.updateUserGamesPlayed(userModel.getUserId2());
        }

        // Stop Timer
        stopTimer();

        //Clear move list
        moveList.clear();
        gameData.setMoveList(moveList);

        String winMessage = "";

        // Sets game over text and player stats
        if(pIsDraw)  {
            String message = "GAME OVER: DRAW!";
            gameOverAnim(message);
        }
        else if(pIsPlayer1sTurn && gameData.getGameMode() == 1 )  {
            if(hasTimerRanOut) winMessage = String.format("TIMER OVER: %s WINS!", userModel.getUserName());
            else winMessage = String.format("GAME OVER: %s WINS!", userModel.getUserName());

            gameOverAnim(winMessage);

            userDao.updateUserWins(userModel.getUserId());
        }
        else if (!pIsPlayer1sTurn && gameData.getGameMode() == 1) {
            if(hasTimerRanOut) winMessage = String.format("TIMER OVER: AI WINS!", userModel.getUserName());
            else winMessage = "GAME OVER: AI WINS!";

            gameOverAnim(winMessage);
            userDao.updateUserLosses(userModel.getUserId());
        }
        else if(pIsPlayer1sTurn && gameData.getGameMode() == 2)  {
            if(hasTimerRanOut) winMessage = String.format("TIMER OVER: %s WINS!", userModel.getUserName());
            else winMessage = String.format("GAME OVER: %s WINS!", userModel.getUserName());

            gameOverAnim(winMessage);

            userDao.updateUserWins(userModel.getUserId());
            userDao.updateUserLosses(userModel.getUserId2());
        }
        else if (!pIsPlayer1sTurn && gameData.getGameMode() == 2) {
            if(hasTimerRanOut) winMessage = String.format("TIMER OVER: %s WINS!", userModel.getUserName2());
            else winMessage = String.format("GAME OVER: %s WINS!", userModel.getUserName2());

            gameOverAnim(winMessage);

            userDao.updateUserWins(userModel.getUserId2());
            userDao.updateUserLosses(userModel.getUserId());
        }

        // Disable all board buttons
        for(int i = 0; i < boardSize*boardSize; i++){
            adapter.data.get(i).setEnabledState(false);
        }

        // Disable undo button
        undoButton.setEnabled(false);

        // Notify adapter to update UI
        adapter.notifyDataSetChanged();

        // Sets game over text to visible
        gameOverText.setVisibility(View.VISIBLE);
    }

    /* -----------------------------------------------------------------------------------------
        Function: resetGame()
        Author: Jules + Yi Xiang
        Description: Resets game by clearing game board data and adapter data
        ---------------------------------------------------------------------------------------- */
    public void resetGame() {

        //set the game dat ato reset
        gameData.setNeedSaveGameState(false);

        // Sets game over text to invisible
        gameData.setPlayer2Moves(0);
        gameData.setPlayer1Moves(0);
        gameData.setIsGameOver(false);

        // Sets game over text to invisible
        gameOverText.setVisibility(View.INVISIBLE);
        invalidMoveText.setVisibility(View.INVISIBLE);

        // Clears board array
        for(int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                gameBoard[i][j] = '-';
            }
        }
        gameData.setGameBoard(gameBoard);

        // Clears move list
        moveList.clear();
        gameData.setMoveList(moveList);

        // Change all adapter data to default values, and enable all board buttons
        for(int i = 0; i < boardSize*boardSize; i++) {
            adapter.data.get(i).setMarkerSymbol('-');
            adapter.data.get(i).setImageResource(0);
            adapter.data.get(i).setEnabledState(true);
        }

        // Enable undo button
        undoButton.setEnabled(true);

        // Notify adapter to update UI
        adapter.notifyDataSetChanged();

        // If game mode is player vs ai and ai moves first, ai moves
        if(!isPlayer1GoingFirst && gameData.getGameMode() == 1){
            gameData.setWhoseTurn(3); //Set whose turn to AI
            aiMove(gameBoard); // AI moves
            gameData.setWhoseTurn(1); //Set who turn to player 1
            isPlayer1sTurn = true;

        }
        else if(isPlayer1GoingFirst && gameData.getGameMode() == 1){
            gameData.setWhoseTurn(1); //Set whose turn to player 1
            isPlayer1sTurn = true;
        }

        // If game mode is pvp, set to appropriate players turn
        if(!isPlayer1GoingFirst && gameData.getGameMode() == 2) {
            gameData.setWhoseTurn(2); //Set whose turn to player 2
            isPlayer1sTurn = false;
        }
        else if(isPlayer1GoingFirst && gameData.getGameMode() == 2){
            gameData.setWhoseTurn(1); //Set whose turn to player 1
            isPlayer1sTurn = true;
        }

        gameData.setGameBoard(gameBoard);
        gameData.setMoveList(moveList);

        // Set hasTimerRanOut to false
        hasTimerRanOut = false;

        // Reset timer
        stopTimer();
        startTimer();
    }

    /* -----------------------------------------------------------------------------------------
        Function: isAllSpacesTaken(char[][] pGameBoard)
        Author: Jules
        Description: Checks if all spaces are filled on the board
        ---------------------------------------------------------------------------------------- */
    public boolean isAllSpacesTaken(char[][] pGameBoard) {
        for (int i = 0; i < pGameBoard.length; i++) {
            for (int j = 0; j < pGameBoard.length; j++) {
                if(pGameBoard[i][j] == '-') return false; // Return false if there is an empty space
            }
        }
        return true; // Return true if all spaces are taken
    }

    /* ---------------------------------------------------------------------------
    Function: invalidMoveClicked
    Author: Yi Xiang
    Notifications: -
    Purpose: set UI text of invalid move to visible
        --------------------------------------------------------------------------- */
    public void invalidMoveClicked() {
        invalidMoveText.setText("INVALID MOVE!");

        if(gameData.getIsInvalidMove()) {

            invalidMoveText.setVisibility(View.VISIBLE);
        }
    }


    /* ---------------------------------------------------------------------------
        Function: onItemClicked(int pPosition)
        Author: Jules
        Purpose: Function is executed when a board button is pressed
        --------------------------------------------------------------------------- */
    @Override
    public void onItemClicked(int pPosition) {

        // Sync adapter data with game board
        updateGameBoard(adapter.data);

        if (invalidMoveText != null) {
            invalidMoveText.setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                System.out.print(gameBoard[i][j]);
            }
            System.out.println("");
        }

        // Set isPlayer1sTurn to appropriate value and Determines button location in array from list position
        if(gameData.whoseTurn.getValue() == 1){
            // Button position is determine in game board based on adapter data index
            locI = pPosition / gameData.getBoardSize();
            locJ = pPosition % gameData.getBoardSize();

            //Set isPlayer1sTurn to true
            isPlayer1sTurn = true;

            //Add move to move plist
            int[] move = {locI, locJ};
            gameData.setPlayer1Moves(gameData.getPlayer1Moves()+ 1);
            moveList.add(move);
        }
        else if(gameData.whoseTurn.getValue() == 2){
            // Button position is determine in game board based on adapter data index
            otherLocI = pPosition / gameData.getBoardSize();
            otherLocJ = pPosition % gameData.getBoardSize();

            //Set isPlayer1sTurn to false
            isPlayer1sTurn = false;

            //Add move to move plist
            int[] move = {otherLocI, otherLocJ};
            gameData.setPlayer2Moves(gameData.getPlayer2Moves()+ 1);
            moveList.add(move);
        }

        // Check if there is a winner or a draw, it is a draw if all spaces on the board are taken and there is no winner
        if(gameData.whoseTurn.getValue() == 1) isThereAWinner = checkIfThereIsWinner(gameBoard, winConditionInput, locI, locJ, isPlayer1sTurn);
        else if(gameData.whoseTurn.getValue() == 2) isThereAWinner = checkIfThereIsWinner(gameBoard, winConditionInput, otherLocI, otherLocJ, isPlayer1sTurn);
        isDraw = ((isAllSpacesTaken(gameBoard)) && (!isThereAWinner));

        // If game mode is player vs ai, and game is not over, AI moves, and then checks if there is a winner or draw
        if (!isThereAWinner && !isDraw && gameData.getGameMode() == 1) {
            gameData.setWhoseTurn(3); // Set whose turn to AI
            isPlayer1sTurn = false; //Set isPlayer1sTurn to false

            // Use the postDelayed() method to execute code after the specified delay.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    aiMove(gameBoard); // AI moves

                    // Check if there is a winner or a draw
                    isThereAWinner = checkIfThereIsWinner(gameBoard, winConditionInput, otherLocI, otherLocJ, isPlayer1sTurn);
                    isDraw = ((isAllSpacesTaken(gameBoard)) && (!isThereAWinner));

                    // If there is no winner or draw change turn to player 1
                    if(!isThereAWinner && !isDraw) {
                        gameData.whoseTurn.setValue(1); //Set whoseTurn to 1 (Player 1's Turn)
                        isPlayer1sTurn = true; //Set isPlayers1s turn to true
                    }

                    // Update game board and move list in gameData
                    gameData.setGameBoard(gameBoard);
                    gameData.setMoveList(moveList);

                }
            }, delayMillis);
        }

        // If on pvp mode, change to other players turn if there are no winner or no draw
        if(!isThereAWinner && !isDraw && gameData.getGameMode() == 2 && gameData.whoseTurn.getValue() == 1){
            gameData.whoseTurn.setValue(2); //Set whoseTurn to 2 (Player 2's Turn)
            isPlayer1sTurn = false;
        }
        else if(!isThereAWinner && !isDraw && gameData.getGameMode() == 2 && gameData.whoseTurn.getValue() == 2){
            gameData.whoseTurn.setValue(1); //Set whoseTurn to 1 (Player 1's Turn)
            isPlayer1sTurn = true;
        }

        // If game is over, end game
        if(isThereAWinner || isDraw) {
            endGame(isPlayer1sTurn, isDraw); // End game
        }
        else{
            // Reset timer
            stopTimer();
            startTimer();
        }

        // Update game board and move list in gameData
        gameData.setGameBoard(gameBoard);
        gameData.setMoveList(moveList);

        // Delay ending game, so that ai move delay can update board in time
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // If game is over, end game
                if (isThereAWinner || isDraw) {
                    endGame(isPlayer1sTurn, isDraw);
                } else {
                    // Reset timer
                    stopTimer();
                    startTimer();
                }
            }
        }, delayMillis);
    }

    /* ---------------------------------------------------------------------------
        Function: updateGameBoard(ArrayList pData)
        Author: Jules
        Purpose: Updates the game board with the adapter data
        --------------------------------------------------------------------------- */
    public void updateGameBoard(ArrayList pData){
        int index = 0;
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                gameBoard[i][j] = adapter.data.get(index).getMarkerSymbol(); // Set position in game board to appropriate marker
                index++; // Increment index
            }
        }
        gameData.setGameBoard(gameBoard); // Update game board in gameData
    }

    public UserDao initialiseDB() {
        return UserDbInstance.getDatabase(getContext()).userDao();
    }

    /* -----------------------------------------------------------------------------------------
        Function: undoMove()
        Author: Jules
        Description: Undo the previous move
        ---------------------------------------------------------------------------------------- */
    public void undoMove(){
        // If the moveList is not empty
        if(moveList.size() > 0){
            // If player vs ai, and its the players turn
            if(gameData.getGameMode() == 1 && gameData.getWhoseTurn() == 1){
                int adapterDataIndex = (moveList.get(moveList.size() - 1)[0] * gameBoard.length) + moveList.get(moveList.size() - 1)[1]; // Get most recent marker placement
                adapter.data.get(adapterDataIndex).setMarkerSymbol('-'); // Change adapter data to remove marker
                adapter.data.get(adapterDataIndex).setImageResource(0); // Change adapter data to remove marker
                adapter.notifyDataSetChanged(); //Notify adapter to update UI
                moveList.remove(moveList.size() - 1); // Remove most recent move from move list

                adapterDataIndex = (moveList.get(moveList.size() - 1)[0] * gameBoard.length) + moveList.get(moveList.size() - 1)[1]; // Get most recent marker placement
                adapter.data.get(adapterDataIndex).setMarkerSymbol('-'); // Change adapter data to remove marker
                adapter.data.get(adapterDataIndex).setImageResource(0); // Change adapter data to remove marker
                adapter.notifyDataSetChanged(); //Notify adapter to update UI
                moveList.remove(moveList.size() - 1); // Remove most recent move from move list

                gameData.setPlayer1Moves(gameData.getPlayer1Moves() -1);
                gameData.setPlayer2Moves(gameData.getPlayer2Moves() -1);

            }
            //If player vs player, and its player 1s turn
            else if(gameData.getGameMode() == 2 && gameData.getWhoseTurn() == 1){
                int adapterDataIndex = (moveList.get(moveList.size() - 1)[0] * gameBoard.length) + moveList.get(moveList.size() - 1)[1]; // Get most recent marker placement
                adapter.data.get(adapterDataIndex).setMarkerSymbol('-'); // Change adapter data to remove marker
                adapter.data.get(adapterDataIndex).setImageResource(0); // Change adapter data to remove marker
                adapter.notifyDataSetChanged(); //Notify adapter to update UI
                moveList.remove(moveList.size() - 1); // Remove most recent move from move list
                gameData.setWhoseTurn(2); //Set player 2's turn

                gameData.setPlayer2Moves(gameData.getPlayer2Moves() -1);
            }
            //If player vs player, and its player 2s turn
            else if(gameData.getGameMode() == 2 && gameData.getWhoseTurn() == 2){
                int adapterDataIndex = (moveList.get(moveList.size() - 1)[0] * gameBoard.length) + moveList.get(moveList.size() - 1)[1]; // Get most recent marker placement
                adapter.data.get(adapterDataIndex).setMarkerSymbol('-'); // Change adapter data to remove marker
                adapter.data.get(adapterDataIndex).setImageResource(0); // Change adapter data to remove marker
                adapter.notifyDataSetChanged(); //Notify adapter to update UI
                moveList.remove(moveList.size() - 1); // Remove most recent move from move list
                gameData.setWhoseTurn(1); //Set player 1's turn

                gameData.setPlayer1Moves(gameData.getPlayer1Moves() -1);

            }

            updateGameBoard(adapter.data); //Update the game board with the adapter data
            gameData.setMoveList(moveList); // Update game board in gameData
        }
    }

    /* ----------------------------------------------------------------------------------------------------------------------------
        Function: startTimer()
        Author: Jules
        Description: Start the count down timer
        Source: Modified version from https://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
        ----------------------------------------------------------------------------------------------------------------------- */
    public void startTimer(){
        // Initialise count down
        countDownTimer = new CountDownTimer(gameData.getTimerLength(), 1000) {
            //Every second the code is executed
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) millisUntilFinished / 1000; // Obtain seconds remaining
                int minutesRemaining = secondsRemaining / 60; // Obtain minutes remaining
                timerText.setText(String.format("%02d:%02d", minutesRemaining, secondsRemaining)); // Set text
            }

            // When the timer finishes the code is executed
            @Override
            public void onFinish() {
                timerText.setText("00:00"); // Set timer text to 00:00

                // Whoever turn it is, reverse it
                if(isPlayer1sTurn) {
                    isPlayer1sTurn = false;
                }
                else{
                    isPlayer1sTurn = true;
                }

                hasTimerRanOut = true;

                endGame(isPlayer1sTurn, isDraw); // End game
            }
        };

        currentTimerExists = true;
        countDownTimer.start(); // SStart countdown timer
    }

    /* -----------------------------------------------------------------------------------------
        Function: undoMove()
        Author: Jules
        Description: Stop the count down timer
        ---------------------------------------------------------------------------------------- */
    public static void stopTimer(){
        if (countDownTimer != null) {
            countDownTimer.cancel(); // If countdowntimer exists, cancel it
            currentTimerExists = false;
        }
    }

    /* -----------------------------------------------------------------------------------------
            Function: gameOverAnim()
            Author: Ryan
            Description: Defines the "pulsing" animation for the GAME OVER text
         ---------------------------------------------------------------------------------------- */
    public void gameOverAnim(String winMessage) {
        notification_anim = ValueAnimator.ofFloat(20, 30);
        notification_anim.setRepeatCount(ValueAnimator.INFINITE); // Play once, then reverse
        notification_anim.setRepeatMode(ValueAnimator.REVERSE);
        notification_anim.setDuration(300);

        // Animation listener for gameOverText
        notification_anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animator) {
                gameOverText.setText(winMessage);
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animator) {
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animator) {
                // Left blank on purpose
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animator) {
            }
        });

        notification_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                gameOverText.setTextSize(TypedValue.COMPLEX_UNIT_SP, animatedValue);
            }
        });

        notification_anim.start();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        stopTimer();
        startTimer();

    }


}