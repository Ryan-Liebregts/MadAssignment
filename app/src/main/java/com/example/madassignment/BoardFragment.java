package com.example.madassignment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
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

    private Button settingsButton;
    private ImageButton undoButton, resetButton;

    private NavigationData navModel;
    private UserData userModel;
    private GameData gameModel;
    private ValueAnimator notification_anim;

    ImageView winCondition;
    private MediaPlayer mediaPlayer;

    ImageButton player1Icon;
    ImageButton player1IconDull;
    ImageButton player2Icon;
    ImageButton player2IconDull;
    TextView player1Moves;
    TextView player2Moves;
    int gameMode;
    TextView player1Name;
    TextView player2Name;
    ImageView player1Symbol;
    ImageView winConditionIcon;
    ImageView player2Symbol;
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

    // Variable declaration
    char[][] gameBoard;
    int boardSize;
    int winConditionInput;
    int locI;
    int locJ;
    int otherLocI;
    int otherLocJ;
    int cyan = Color.CYAN;
    float volume = 1.0f;
    boolean isPlayer1GoingFirst, isThereAWinner, validInput = true, isPlayer1sTurn, isDraw, isGameOver;
    char playerMarker, otherMarker;
    private TextView gameOverText;
    TextView invalidMoveText;
    ArrayList<BoardButtonData> data;
    private GameData gameData;
    BoardButtonAdapter adapter;
    RecyclerView rv;
    static Timer boardTimer;
    long startTime;
    static boolean timerRunning = false;
    TextView timerText;
    ArrayList<int[]> moveList;

    int player1moves;

    int player2moves;

    EditUser editUserModel;
    Handler handler = new Handler();
    static CountDownTimer countDownTimer;
    long delayMillis = 300;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board, container, false);
        //load users from DB by id
        loadUsers();
        // Initialise button and text variables
        resetButton = view.findViewById(R.id.reset_button);
        gameOverText = view.findViewById(R.id.gameoverText);
        undoButton = view.findViewById(R.id.undo_button);
        winCondition = view.findViewById(R.id.win_condition_icon);

        winConditionIcon = view.findViewById(R.id.win_condition_icon);

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
            System.out.println("HI I am exiting");
            return view;
        }
        //All code below will only execute if allowed to by the if statement above use multiple returns as an if/else
        //This function handles settings hte game user data such as icons and symbols and username on the board
        setGameUserData(view);

        // Set board size
        boardSize = gameData.getBoardSize();

        // Set locI, locJ, otherLocI and otherLocJ values to 0
        locI = 0;
        locJ = 0;
        otherLocI = 0;
        otherLocJ = 0;

        // Set isThereAWinner and isDraw to false
        isThereAWinner = false;
        isDraw = false;
        isGameOver = false;

        // Is the player going first?
        if(userModel.getFirstMove() == 1) gameData.setIsPlayer1GoingFirst(true);
        else if(userModel.getFirstMove() == 2) gameData.setIsPlayer1GoingFirst(false);

        isPlayer1GoingFirst = gameData.getIsPlayer1GoingFirst();
        if(isPlayer1GoingFirst) isPlayer1sTurn = true;
        else isPlayer1sTurn = false;

        // Set player and ai marker
        playerMarker = gameData.getPlayer1MarkerSymbol();
        if(gameData.getGameMode() == 1){
            otherMarker = gameData.getAIMarkerSymbol();
        }
        else{
            otherMarker = gameData.getPlayer2MarkerSymbol(); //VARIABLE NAME SHOULD BE CHANGED BUT HAS TO STAY LIKE THIS FOR NOW CAUSE ILL NEED TO CHANGE YI XIANGS BIT AND I CBF READING THE CODE FOR IT NOW ITS TOO CONFUSING
        }

        // Win condition from game data used to dynamically show on board the game
        winConditionInput = gameData.getWinCondition();
        switch(winConditionInput){
            case 3:
                winConditionIcon.setImageResource(R.drawable.three_win_condition);
                break;
            case 4:
                winConditionIcon.setImageResource(R.drawable.four_win_condition);
                break;
            case 5:
                winConditionIcon.setImageResource(R.drawable.five_win_condition);
                break;
        }

        // Initialise button and text variables
        resetButton = view.findViewById(R.id.reset_button);
        gameOverText = view.findViewById(R.id.gameoverText);
        invalidMoveText = view.findViewById(R.id.invalidmoveText);
        undoButton = view.findViewById(R.id.undo_button);
        timerText = view.findViewById(R.id.timerTextView);

        // Set game over text as invisible
        gameOverText.setVisibility(View.INVISIBLE);

        // Create new data array for grid buttons, and filled with empty button data
        data = new ArrayList<BoardButtonData>();
        for(int i = 0; i < gameData.getBoardSize() * gameData.getBoardSize(); i++) {
            data.add(new BoardButtonData(0, i));
        }

        // Create recyclerview grid
        rv = view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), boardSize, GridLayoutManager.VERTICAL,false);
        rv.setLayoutManager(gridLayoutManager);
        adapter = new BoardButtonAdapter(data, BoardFragment.this);
        rv.setAdapter(adapter);

        // Initialise board to size
        gameBoard = new char[boardSize][boardSize];

        // If need to get previous game state, retrieve previous game state information
        if(gameData.getNeedSaveGameState() == true){
                retrieveGameBoardState();
                // Initialise move list
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

        // If game mode is player vs ai, and player 1 doest not go first, AI moves
        if(!isPlayer1GoingFirst && gameData.getGameMode() == 1){
            if(gameData.getNeedSaveGameState() == true){
                gameData.setWhoseTurn(gameData.getWhoseTurn());
            } else {
                gameData.setWhoseTurn(3); //Set whose turn to AI
                aiMove(gameBoard);
                isPlayer1sTurn = true;
                gameData.setWhoseTurn(1); //Set whose turn to players
            }
            gameData.setGameBoard(gameBoard);
            gameData.setMoveList(moveList);
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
        gameData.setNeedSaveGameState(false);
        // Start timer
        startTimer();


        // Reset button listener
        // Reset button listener - Modified by Ryan
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(notification_anim != null && notification_anim.isRunning()) {
                    notification_anim.cancel();
                }

                Animation reset = AnimationUtils.loadAnimation(getActivity(),R.anim.reset_rotation_anim);

                // Animation listener for pressing reset button
                reset.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Animation started - Changes colour of the button to show it has been pressed
                        resetButton.setColorFilter(cyan);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Animation ended - removes colour filter at the end of the animation
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

        // Reset button listener - Added by Ryan
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
            }
        });

        return view;
    }
    //load user data from db
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
        System.out.println("yeah saving was required");
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
                        System.out.println("entered player marker");
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
                        System.out.println("entered player marker");
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

    public void setGameUserData(View view) {
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

        //setting the symbol for nots or crosses
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

    // Function for AI's marker placement
    public void aiMove(char[][] pGameBoard){

        do {
            Random rand = new Random();

            int aiMarkerCordsRow = rand.nextInt(pGameBoard.length), aiMarkerCordsCol = rand.nextInt(pGameBoard.length); // Randomly select board position
            if(pGameBoard[aiMarkerCordsRow][aiMarkerCordsCol] != '-') validInput = false;
            else {
                pGameBoard[aiMarkerCordsRow][aiMarkerCordsCol] = otherMarker;
                otherLocI = aiMarkerCordsRow;
                otherLocJ = aiMarkerCordsCol;
                validInput = true;
            }
        } while(!validInput);

        int adapterDataIndex = (otherLocI * pGameBoard.length) + otherLocJ; // Determine where AI placed marker in terms of adapter data arraylist index
        adapter.data.get(adapterDataIndex).setMarkerSymbol(gameData.getAIMarkerSymbol()); // Set board button data to appropriate symbol
        adapter.data.get(adapterDataIndex).setImageResource(userModel.getUserSymbol2()); // Set board button data to appropriate drawable
        adapter.notifyDataSetChanged(); //Notify adapter to update UI

        // Add move to move list
        int[] move = {otherLocI, otherLocJ};
        gameData.setPlayer2Moves(gameData.getPlayer2Moves() + 1);
        moveList.add(move);

        //TODO: Printing game board for testing purposes, can be deleted
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                System.out.print(gameBoard[i][j]);
            }
            System.out.println("");
        }
    }

    // Check's how many markers there are in a row, with row direction based on [pNextI,pNextJ]
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


    // Checks if there is a winner on the board
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

    // Ends the game
    // Disables all board buttons, and displays game over text
    public void endGame(boolean pIsPlayer1sTurn, boolean pIsDraw){
        //initialise DB
        UserDao userDao = initialiseDB();
        //update the amount of games played
        gameData.setIsGameOver(true);
        System.out.println("enters endgame");

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

        // Sets game over text and player stats
        if(pIsDraw)  {
            System.out.println("Its a draw");
            String message = "GAME OVER: DRAW!";
            gameOverAnim(message);
        }
        else if(pIsPlayer1sTurn && gameData.getGameMode() == 1 )  {
            System.out.println("Its game mode 1 and user 1 has won");
            String winMessage = String.format("GAME OVER: %s WINS!", userModel.getUserName());
            gameOverAnim(winMessage);

            userDao.updateUserWins(userModel.getUserId());
        }
        else if (!pIsPlayer1sTurn && gameData.getGameMode() == 1) {
            System.out.println("Its game mode 1 and user AI has won");
            String winMessage = "GAME OVER: AI WINS!";
            gameOverAnim(winMessage);
            userDao.updateUserLosses(userModel.getUserId());
        }
        else if(pIsPlayer1sTurn && gameData.getGameMode() == 2)  {
            System.out.println("Its game mode 2 and user 1 has won");
            System.out.println(String.format("GAME OVER: %s WINS!", userModel.getUserName()));
            String winMessage = String.format("GAME OVER: %s WINS!", userModel.getUserName());
            gameOverAnim(winMessage);

            userDao.updateUserWins(userModel.getUserId());
            userDao.updateUserLosses(userModel.getUserId2());
        }
        else if (!pIsPlayer1sTurn && gameData.getGameMode() == 2) {
            System.out.println("Its game mode 2 and user 2 has won");
            System.out.println(String.format("GAME OVER: %s WINS!", userModel.getUserName2()));
            String winMessage = String.format("GAME OVER: %s WINS!", userModel.getUserName2());
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

    // Restarts game
    // Enables board buttons and clears game board
    public void resetGame() {
        // Sets game over text to invisible
        gameData.setPlayer2Moves(0);
        gameData.setPlayer1Moves(0);
        gameData.setIsGameOver(false);
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
            gameData.setWhoseTurn(1); //set who turn to player 1
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

        // Reset timer
        stopTimer();
        startTimer();
    }

    // Checks if all spaces are taken
    public boolean isAllSpacesTaken(char[][] pGameBoard) {
        for (int i = 0; i < pGameBoard.length; i++) {
            for (int j = 0; j < pGameBoard.length; j++) {
                if(pGameBoard[i][j] == '-') return false;
            }
        }
        return true;
    }

    public void invalidMoveClicked() {
        invalidMoveText.setText("INVALID MOVE!");
        System.out.println("invalid clicked");
        System.out.println(Boolean.toString(gameData.getIsInvalidMove()));

        if(gameData.getIsInvalidMove()) {
            System.out.println("goes in");
            System.out.println(Boolean.toString(gameData.getIsInvalidMove()));

            invalidMoveText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClicked(int pPosition) {
        // Sync adapter data with game board
        updateGameBoard(adapter.data);

        if (invalidMoveText != null) {
            invalidMoveText.setVisibility(View.INVISIBLE);
        }

/*        //TODO: Printing game board for testing purposes, can be deleted
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                System.out.print(gameBoard[i][j]);
            }
            System.out.println("");
        }
*/
        // Set isPlayer1sTurn to appropriate value and Determines button location in array from list position
        if(gameData.whoseTurn.getValue() == 1){
            locI = pPosition / gameData.getBoardSize();
            locJ = pPosition % gameData.getBoardSize();
            isPlayer1sTurn = true;
            int[] move = {locI, locJ};
            gameData.setPlayer1Moves(gameData.getPlayer1Moves()+ 1);
            moveList.add(move);
        }
        else if(gameData.whoseTurn.getValue() == 2){
            otherLocI = pPosition / gameData.getBoardSize();
            otherLocJ = pPosition % gameData.getBoardSize();
            isPlayer1sTurn = false;
            int[] move = {otherLocI, otherLocJ};
            gameData.setPlayer2Moves(gameData.getPlayer2Moves()+ 1);
            moveList.add(move);
        }

        // Check if there is a winner or a draw
        // It is a draw if all spaces on the board are taken and there is no winner
        if(gameData.whoseTurn.getValue() == 1) isThereAWinner = checkIfThereIsWinner(gameBoard, winConditionInput, locI, locJ, isPlayer1sTurn);
        else if(gameData.whoseTurn.getValue() == 2) isThereAWinner = checkIfThereIsWinner(gameBoard, winConditionInput, otherLocI, otherLocJ, isPlayer1sTurn);
        isDraw = ((isAllSpacesTaken(gameBoard)) && (!isThereAWinner));

        // If game mode is player vs ai, and game is not over, AI moves, and then checks if there is a winner or draw
        if (!isThereAWinner && !isDraw && gameData.getGameMode() == 1) {
            gameData.setWhoseTurn(3); // Set whose turn to AI
            isPlayer1sTurn = false;

            // Use the postDelayed() method to execute code after the specified delay.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    aiMove(gameBoard); // AI moves
                    isThereAWinner = checkIfThereIsWinner(gameBoard, winConditionInput, otherLocI, otherLocJ, isPlayer1sTurn);
                    isDraw = ((isAllSpacesTaken(gameBoard)) && (!isThereAWinner)); // It is a draw if all spaces on the board are taken and there is no winner
                    if(!isThereAWinner && !isDraw) {
                        gameData.whoseTurn.setValue(1); //Set whoseTurn to 1 (Player 1's Turn)
                        isPlayer1sTurn = true; //Set isPlayers1s turn to true
                    }

                    gameData.setGameBoard(gameBoard);
                    gameData.setMoveList(moveList);

                }
            }, delayMillis);
        }

        // If on pvp mode, change to other players turn if there are no winner or no draw
        if(!isThereAWinner && !isDraw && gameData.getGameMode() == 2 && gameData.whoseTurn.getValue() == 1){
            gameData.whoseTurn.setValue(2);
            isPlayer1sTurn = false;
        }
        else if(!isThereAWinner && !isDraw && gameData.getGameMode() == 2 && gameData.whoseTurn.getValue() == 2){
            gameData.whoseTurn.setValue(1);
            isPlayer1sTurn = true;
        }

        // If game is over, end game
        if(isThereAWinner || isDraw) {
            endGame(isPlayer1sTurn, isDraw);
        }
        else{
            // Reset timer
            stopTimer();
            startTimer();
        }

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

    // Gets data from adapter and updates game board
    public void updateGameBoard(ArrayList pData){
        int index = 0;
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                gameBoard[i][j] = adapter.data.get(index).getMarkerSymbol();
                index++;
            }
        }
        gameData.setGameBoard(gameBoard);
    }

    public UserDao initialiseDB() {
        return UserDbInstance.getDatabase(getContext()).userDao();
    }

    // Undo the user's previous move
    public void undoMove(){
        if(moveList.size() > 0){
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
            else if(gameData.getGameMode() == 2 && gameData.getWhoseTurn() == 1){
                int adapterDataIndex = (moveList.get(moveList.size() - 1)[0] * gameBoard.length) + moveList.get(moveList.size() - 1)[1]; // Get most recent marker placement
                adapter.data.get(adapterDataIndex).setMarkerSymbol('-'); // Change adapter data to remove marker
                adapter.data.get(adapterDataIndex).setImageResource(0); // Change adapter data to remove marker
                adapter.notifyDataSetChanged(); //Notify adapter to update UI
                moveList.remove(moveList.size() - 1); // Remove most recent move from move list
                gameData.setWhoseTurn(2); //Set player 2's turn
                gameData.setPlayer2Moves(gameData.getPlayer2Moves() -1);
            }
            else if(gameData.getGameMode() == 2 && gameData.getWhoseTurn() == 2){
                int adapterDataIndex = (moveList.get(moveList.size() - 1)[0] * gameBoard.length) + moveList.get(moveList.size() - 1)[1]; // Get most recent marker placement
                adapter.data.get(adapterDataIndex).setMarkerSymbol('-'); // Change adapter data to remove marker
                adapter.data.get(adapterDataIndex).setImageResource(0); // Change adapter data to remove marker
                adapter.notifyDataSetChanged(); //Notify adapter to update UI
                moveList.remove(moveList.size() - 1); // Remove most recent move from move list
                gameData.setWhoseTurn(1); //Set player 1's turn
                gameData.setPlayer1Moves(gameData.getPlayer1Moves() -1);

            }
            updateGameBoard(adapter.data);
            gameData.setMoveList(moveList);
        }
    }

    // Start the count down timer
    // Code below is a modified version of the code from:
    //https://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android
    public void startTimer(){
        countDownTimer = new CountDownTimer(gameData.getTimerLength(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) millisUntilFinished / 1000; // Obtain seconds remaining
                int minutesRemaining = secondsRemaining / 60; // Obtain minutes remaining
                timerText.setText(String.format("%02d:%02d", minutesRemaining, secondsRemaining)); // Set text
            }

            // When the timer finishes, execute code
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
                endGame(isPlayer1sTurn, isDraw); // End game
            }
        };

        countDownTimer.start(); // Start countdown timer
    }

    // Stop the count down timer
    public static void stopTimer(){
        if (countDownTimer != null) {
            countDownTimer.cancel(); // If countdowntimer exists, cancel it
        }
    }

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

}