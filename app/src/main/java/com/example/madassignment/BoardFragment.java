package com.example.madassignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button settingsButton;
    private NavigationData navModel;


    public BoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BoardFragment newInstance(String param1, String param2) {
        BoardFragment fragment = new BoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        navModel = new ViewModelProvider(getActivity()).get(NavigationData.class);
    }

    // Variable declaration
    char[][] gameBoard;
    int boardSize; // TODO: collect this from Settings
    int winConditionInput; // TODO: collect this from Settings
    int locI;
    int locJ;
    int aiLocI;
    int aiLocJ;
    boolean isPlayerGoingFirst, isThereAWinner, validInput = true, isPlayersTurn, isDraw;
    char playerMarker, aiMarker;
    ImageButton buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix, buttonSeven, buttonEight, buttonNine, resetButton, undoButton;
    TextView gameOverText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board, container, false);

        // Set board size (for now it will be stuck at 3), and set isThereAWinner and isDraw to false
        boardSize = 3;
        locI = 0;
        locJ = 0;
        isThereAWinner = false;
        isDraw = false;

        // Is the player going first? (for now it will be stuck as true);
        isPlayerGoingFirst = true;
        isPlayersTurn = true;

        // Set player and ai marker
        playerMarker = 'x';
        aiMarker = 'o';

        // Create board filled with '-'
        gameBoard = new char[boardSize][boardSize];
        for(int i = 0; i < boardSize; i++)
        {
            for(int j = 0; j < boardSize; j++)
            {
                gameBoard[i][j] = '-';
            }
        }

        // Initialise button and text variables
        buttonOne = view.findViewById(R.id.buttonOne);
        buttonTwo = view.findViewById(R.id.buttonTwo);
        buttonThree = view.findViewById(R.id.buttonThree);
        buttonFour = view.findViewById(R.id.buttonFour);
        buttonFive = view.findViewById(R.id.buttonFive);
        buttonSix = view.findViewById(R.id.buttonSix);
        buttonSeven = view.findViewById(R.id.buttonSeven);
        buttonEight = view.findViewById(R.id.buttonEight);
        buttonNine = view.findViewById(R.id.buttonNine);
        resetButton = view.findViewById(R.id.reset_button);
        gameOverText = view.findViewById(R.id.gameoverText);
        undoButton = view.findViewById(R.id.undo_button);

        // Set game over text as invisible
        gameOverText.setVisibility(View.INVISIBLE);

        // AI's move if player doesn't go first (ABILITY FOR AI TO GO FIRST ISN'T ACTUALLY IMPLEMENTED YET)
        if(!isPlayerGoingFirst){
            aiMove(gameBoard);
            isPlayersTurn = true;
        }

        // Button One listener
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locI=0;
                locJ=0;
                isPlayersTurn = true;
                if(gameBoard[locI][locJ] == '-'){
                    gameBoard[locI][locJ] = playerMarker; // If the space is free, place player marker
                }
                else{
                    return; // If space is not free, don't do anything
                }
                buttonFunction(locI, locJ);
            }
        });

        // Button Two listener
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locI=0;
                locJ=1;
                isPlayersTurn = true;
                if(gameBoard[locI][locJ] == '-'){
                    gameBoard[locI][locJ] = playerMarker; // If the space is free, place player marker
                }
                else{
                    return; // If space is not free, don't do anything
                }
                buttonFunction(locI, locJ);
            }
        });

        // Button Three listener
        buttonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locI=0;
                locJ=2;
                isPlayersTurn = true;
                if(gameBoard[locI][locJ] == '-'){
                    gameBoard[locI][locJ] = playerMarker; // If the space is free, place player marker
                }
                else{
                    return; // If space is not free, don't do anything
                }
                buttonFunction(locI, locJ);
            }
        });

        // Button Four listener
        buttonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locI=1;
                locJ=0;
                isPlayersTurn = true;
                if(gameBoard[locI][locJ] == '-'){
                    gameBoard[locI][locJ] = playerMarker; // If the space is free, place player marker
                }
                else{
                    return; // If space is not free, don't do anything
                }
                buttonFunction(locI, locJ);
            }
        });

        // Button Five listener
        buttonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locI=1;
                locJ=1;
                isPlayersTurn = true;
                if(gameBoard[locI][locJ] == '-'){
                    gameBoard[locI][locJ] = playerMarker; // If the space is free, place player marker
                }
                else{
                    return; // If space is not free, don't do anything
                }
                buttonFunction(locI, locJ);
            }
        });

        // Button Six listener
        buttonSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locI=1;
                locJ=2;
                isPlayersTurn = true;
                if(gameBoard[locI][locJ] == '-'){
                    gameBoard[locI][locJ] = playerMarker; // If the space is free, place player marker
                }
                else{
                    return; // If space is not free, don't do anything
                }
                buttonFunction(locI, locJ);
            }
        });

        // Button Seven listener
        buttonSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locI=2;
                locJ=0;
                isPlayersTurn = true;
                if(gameBoard[locI][locJ] == '-'){
                    gameBoard[locI][locJ] = playerMarker; // If the space is free, place player marker
                }
                else{
                    return; // If space is not free, don't do anything
                }
                buttonFunction(locI, locJ);
            }
        });

        // Button Eight listener
        buttonEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locI=2;
                locJ=1;
                isPlayersTurn = true;
                if(gameBoard[locI][locJ] == '-'){
                    gameBoard[locI][locJ] = playerMarker; // If the space is free, place player marker
                }
                else{
                    return; // If space is not free, don't do anything
                }
                buttonFunction(locI, locJ);
            }
        });

        // Button Nine listener
        buttonNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locI=2;
                locJ=2;
                isPlayersTurn = true;
                if(gameBoard[locI][locJ] == '-'){
                    gameBoard[locI][locJ] = playerMarker; // If the space is free, place player marker
                }
                else{
                    return; // If space is not free, don't do anything
                }
                buttonFunction(locI, locJ);
            }
        });

        // Reset button listener
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starts the reset button animation - updated by Ryan
                Animation refresh = AnimationUtils.loadAnimation(getActivity(),R.anim.reset_rotation_anim);
                resetButton.startAnimation(refresh);

                resetGame(); //Reset the board
            }
        });

        // Undo button listener
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starts the undo button animation - updated by Ryan
                Animation undo = AnimationUtils.loadAnimation(getActivity(),R.anim.undo_rotation_anim);
                undoButton.startAnimation(undo);

                resetGame(); //Reset the board
            }
        });

        return view;
    }

    // This functions executes when each board button is pressed
    public void buttonFunction(int pLocI, int pLocJ) {

        // Sets the win condition in a row (set to 3 for now), TODO: attain from settings
        winConditionInput = 3;

        // Update the UI, and check if there is a winner
        updateBoard3x3(gameBoard);
        isThereAWinner = checkIfThereIsWinner(gameBoard, winConditionInput, pLocI, pLocJ, isPlayersTurn);

        // It is a draw if all spaces on the board are taken and there is no winner
        isDraw = ((isAllSpacesTaken(gameBoard)) && (!isThereAWinner));

        // If game is not over, AI moves, and check if there is a winner or draw
        if (!isThereAWinner && !isDraw) {
            isPlayersTurn = false;
            aiMove(gameBoard);
            updateBoard3x3(gameBoard);
            isThereAWinner = checkIfThereIsWinner(gameBoard, winConditionInput, aiLocI, aiLocJ, isPlayersTurn);
            isDraw = ((isAllSpacesTaken(gameBoard)) && (!isThereAWinner)); // It is a draw if all spaces on the board are taken and there is no winner
        }

        // If game is over, end game
        if (isThereAWinner || isDraw) {
            endGame(isPlayersTurn, isDraw);
        }
    }

    // Function for AI's marker placement
    public void aiMove(char[][] pGameBoard){
        // Set random seed
        Random rand = new Random();

        // Select a position on the board until an empty space is found then place ai marker
        do {
            int aiMarkerCordsRow = rand.nextInt(pGameBoard.length), aiMarkerCordsCol = rand.nextInt(pGameBoard.length); // Randomly select board position
            if(pGameBoard[aiMarkerCordsRow][aiMarkerCordsCol] != '-') validInput = false;
            else {
                pGameBoard[aiMarkerCordsRow][aiMarkerCordsCol] = aiMarker;
                aiLocI = aiMarkerCordsRow;
                aiLocJ = aiMarkerCordsCol;
                validInput = true;
            }
        } while(!validInput);
    }

    // Check's how many markers there are in a row, with row direction based on [pNextI,pNextJ]
    public boolean checkConsecutiveMarkers(char[][] pGameBoard, int pWinConditionInput, int pLocI, int pLocJ, boolean pIsPlayersTurn, int pNextI, int pNextJ){
        int playerMarkerCount;
        int aiMarkerCount;
        int indI;
        int indJ;
        boolean inARowForward;
        boolean inARowReverse;

        playerMarkerCount = 0;
        aiMarkerCount = 0;
        // If called for player, will check how many in a row both ways
        if(pIsPlayersTurn == true) {
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
            indI = aiLocI;
            indJ = aiLocJ;
            aiMarkerCount = 1; // ai counter = 1 because includes the currently placed marker
            while(inARowForward==true) { // counts how many consecutive ai counters forward
                if (((indI + pNextI) < pGameBoard.length) && ((indI + pNextI) >= 0) && ((indJ + pNextJ) < pGameBoard.length) && ((indJ + pNextJ) >= 0)) {
                    if (pGameBoard[(indI + pNextI)][(indJ + pNextJ)] == aiMarker) {
                        aiMarkerCount++; // if next forward consecutive space contains ai marker, increase count
                        indI = indI + pNextI;
                        indJ = indJ + pNextJ;
                    } else {
                        inARowForward = false;
                    }
                } else {
                    inARowForward = false;
                }
            }
            indI = aiLocI;
            indJ = aiLocJ;
            while(inARowReverse==true){ // counts how many consecutive ai counters reverse
                if (((indI - pNextI) < pGameBoard.length) && ((indI - pNextI) >= 0) && ((indJ - pNextJ) < pGameBoard.length) && ((indJ - pNextJ) >= 0)) {
                    if (pGameBoard[(indI - pNextI)][(indJ - pNextJ)] == aiMarker) {
                        aiMarkerCount++; // if next reverse consecutive space contains ai marker, increase count
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
        if(playerMarkerCount >= pWinConditionInput || aiMarkerCount >= pWinConditionInput) return true; // if either counter meets win condition, consecutive markers is true
        return false;
    }


    // Checks if there is a winner on the board
    public boolean checkIfThereIsWinner(char[][] pGameBoard, int pWinConditionInput, int pLocI, int pLocJ, boolean pIsPlayersTurn){
        // Checks if there are enough markers in a row to meet the win condition
        // Horizontal [0,+1]
        if((checkConsecutiveMarkers(pGameBoard,pWinConditionInput,pLocI,pLocJ,pIsPlayersTurn,0,1))==true){
            return true;
            // Vertical [+1,0]
        } else if ((checkConsecutiveMarkers(pGameBoard,pWinConditionInput,pLocI,pLocJ,pIsPlayersTurn,1,0))==true) {
            return true;
            // Diagonal Top Left Bottom Right [+1,+1]
        } else if ((checkConsecutiveMarkers(pGameBoard,pWinConditionInput,pLocI,pLocJ,pIsPlayersTurn,1,1))==true) {
            return true;
            // Diagonal Top Right Bottom Left [+1,-1]
        }  else if ((checkConsecutiveMarkers(pGameBoard,pWinConditionInput,pLocI,pLocJ,pIsPlayersTurn,1,-1))==true) {
            return true;
        } else {

        }
        return false;
    }

    // Updates the graphics on the UI board with the game board array
    public void updateBoard3x3(char[][] pGameBoard){
        // First Row, Fist Column
        if(pGameBoard[0][0] == playerMarker) buttonOne.setImageResource(R.drawable.cross);
        else if(pGameBoard[0][0] == aiMarker) buttonOne.setImageResource(R.drawable.circle);
        else buttonOne.setImageResource(0);

        // First Row, Second Column
        if(pGameBoard[0][1] == playerMarker) buttonTwo.setImageResource(R.drawable.cross);
        else if(pGameBoard[0][1] == aiMarker) buttonTwo.setImageResource(R.drawable.circle);
        else buttonTwo.setImageResource(0);

        // First Row, Third Column
        if(pGameBoard[0][2] == playerMarker) buttonThree.setImageResource(R.drawable.cross);
        else if(pGameBoard[0][2] == aiMarker) buttonThree.setImageResource(R.drawable.circle);
        else buttonThree.setImageResource(0);

        // Second Row, First Column
        if(pGameBoard[1][0] == playerMarker) buttonFour.setImageResource(R.drawable.cross);
        else if(pGameBoard[1][0] == aiMarker) buttonFour.setImageResource(R.drawable.circle);
        else buttonFour.setImageResource(0);

        // Second Row, Second Column
        if(pGameBoard[1][1] == playerMarker) buttonFive.setImageResource(R.drawable.cross);
        else if(pGameBoard[1][1] == aiMarker) buttonFive.setImageResource(R.drawable.circle);
        else buttonFive.setImageResource(0);

        // Second Row, Third Column
        if(pGameBoard[1][2] == playerMarker) buttonSix.setImageResource(R.drawable.cross);
        else if(pGameBoard[1][2] == aiMarker)  buttonSix.setImageResource(R.drawable.circle);
        else buttonSix.setImageResource(0);

        // Third Row, First Column
        if(pGameBoard[2][0] == playerMarker) buttonSeven.setImageResource(R.drawable.cross);
        else if(pGameBoard[2][0] == aiMarker) buttonSeven.setImageResource(R.drawable.circle);
        else buttonSeven.setImageResource(0);

        // Third Row, Second Column
        if(pGameBoard[2][1] == playerMarker) buttonEight.setImageResource(R.drawable.cross);
        else if(pGameBoard[2][1] == aiMarker) buttonEight.setImageResource(R.drawable.circle);
        else buttonEight.setImageResource(0);

        // Third Row, Third Column
        if(pGameBoard[2][2] == playerMarker) buttonNine.setImageResource(R.drawable.cross);
        else if(pGameBoard[2][2] == aiMarker) buttonNine.setImageResource(R.drawable.circle);
        else buttonNine.setImageResource(0);
    }

    // Ends the game
    // Disables all board buttons, and displays game over text
    public void endGame(boolean pIsPlayersTurn, boolean pIsDraw){

        // Sets game over text
        if(pIsDraw) gameOverText.setText("GAMEOVER: DRAW!");
        else if(pIsPlayersTurn)  gameOverText.setText("GAMEOVER: PLAYER WINS!");
        else gameOverText.setText("GAMEOVER: AI WINS!");

        // Disables board buttons
        buttonOne.setEnabled(false);
        buttonTwo.setEnabled(false);
        buttonThree.setEnabled(false);
        buttonFour.setEnabled(false);
        buttonFive.setEnabled(false);
        buttonSix.setEnabled(false);
        buttonSeven.setEnabled(false);
        buttonEight.setEnabled(false);
        buttonNine.setEnabled(false);

        // Sets game over text to visible
        gameOverText.setVisibility(View.VISIBLE);
    }

    // Restarts game
    // Enables board buttons and clears game board
    public void resetGame() {
        // Sets game over text to invisible
        gameOverText.setVisibility(View.INVISIBLE);

        // Clears board array
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                gameBoard[i][j] = '-';
            }
        }

        // Enables board buttons
        buttonOne.setEnabled(true);
        buttonTwo.setEnabled(true);
        buttonThree.setEnabled(true);
        buttonFour.setEnabled(true);
        buttonFive.setEnabled(true);
        buttonSix.setEnabled(true);
        buttonSeven.setEnabled(true);
        buttonEight.setEnabled(true);
        buttonNine.setEnabled(true);

        // Updates UI
        updateBoard3x3(gameBoard);
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
}