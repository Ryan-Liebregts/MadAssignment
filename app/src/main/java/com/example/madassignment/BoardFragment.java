package com.example.madassignment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardFragment extends Fragment implements BoardButtonAdapter.AdapterCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button settingsButton;
    private NavigationData navModel;

    private UserData userModel;

    private GameData gameModel;


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
    ImageView player2Symbol;
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
        userModel = new ViewModelProvider(getActivity()).get(UserData.class);
        navModel = new ViewModelProvider(getActivity()).get(NavigationData.class);
        gameData = new ViewModelProvider(getActivity()).get(GameData.class);
    }

    // Variable declaration
    char[][] gameBoard;
    int boardSize;
    int winConditionInput;
    int locI;
    int locJ;
    int otherLocI;
    int otherLocJ;
    boolean isPlayer1GoingFirst, isThereAWinner, validInput = true, isPlayer1sTurn, isDraw;
    char playerMarker, otherMarker;
    ImageButton resetButton;
    TextView gameOverText;
    TextView invalidMoveText;
    ArrayList<BoardButtonData> data;
    private GameData gameData;
    BoardButtonAdapter adapter;
    RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board, container, false);

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


        //TODO - change all this over to use userData and not Game Data - PK (sorry Jules)
        // Is the player going first?
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

        // Win condition from game data
        winConditionInput = gameData.getWinCondition();

        // Create board filled with '-'
        gameBoard = new char[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                gameBoard[i][j] = '-';
            }
        }

        // Initialise button and text variables
        resetButton = view.findViewById(R.id.reset_button);
        gameOverText = view.findViewById(R.id.gameoverText);
        invalidMoveText = view.findViewById(R.id.invalidmoveText);

        // Set game over text as invisible
        gameOverText.setVisibility(View.INVISIBLE);

        // Grid Stuff
        data = new ArrayList<BoardButtonData>();

        for(int i = 0; i < gameData.getBoardSize() * gameData.getBoardSize(); i++) {
            data.add(new BoardButtonData(0, i));
        }

        rv = view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), boardSize, GridLayoutManager.VERTICAL,false);
        rv.setLayoutManager(gridLayoutManager);
        adapter = new BoardButtonAdapter(data, BoardFragment.this);
        rv.setAdapter(adapter);

        // If game mode is player vs ai, and player 1 doest not go first, AI moves
        if(!isPlayer1GoingFirst && gameData.getGameMode() == 1){
            gameData.setWhoseTurn(3); //Set whose turn to AI
            aiMove(gameBoard);
            isPlayer1sTurn = true;
        }

        // If game mode is pvp and player 1 is not going first, set whose turn to player 2
        // or if player 1 is going first, set whose turn to player 1
        if(!isPlayer1GoingFirst && gameData.getGameMode() == 2){
            gameData.setWhoseTurn(2); //Set whose turn to player 2
        }
        else if(isPlayer1GoingFirst && gameData.getGameMode() == 2){
            gameData.setWhoseTurn(1); //Set whose turn to player 1
        }

        // Reset button listener
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame(); //Reset the board
            }
        });

        return view;
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
        //TODO sort out the moves changing code - PK
        player1Moves.setText("0 Moves");
        player2Moves.setText("0 Moves");


        if (gameData.getGameMode() == 1) {
            player2Icon.setImageResource(R.drawable.robot_icon);
            player2IconDull.setImageResource(R.drawable.robot_icon);
            player2Name.setText("AI");
            player2Moves.setText("0 Moves");
        }
        else {
            player2Icon.setImageResource(userModel.getUserIcon2());
            player2IconDull.setImageResource(userModel.getUserIcon2());
            player2Name.setText(userModel.getUserName2());
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
                pGameBoard[aiMarkerCordsRow][aiMarkerCordsCol] = otherMarker;
                otherLocI = aiMarkerCordsRow;
                otherLocJ = aiMarkerCordsCol;
                validInput = true;
            }
        } while(!validInput);

        int adapterDataIndex = (otherLocI * pGameBoard.length) + otherLocJ; // Determine where AI placed marker in terms of adapter data arraylist index
        adapter.data.get(adapterDataIndex).setMarkerSymbol(gameData.getAIMarkerSymbol()); // Set board button data to appropriate symbol
        adapter.data.get(adapterDataIndex).setImageResource(gameData.getAIMarker()); // Set board button data to appropriate drawable
        adapter.notifyDataSetChanged(); //Notify adapter to update UI
        gameData.whoseTurn.setValue(1); //Set whoseTurn to 1 (Player 1's Turn)
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
        if (gameData.getGameMode() == 1) {
            userDao.updateUserGamesPlayed(userModel.getUserId());
        }
        else{
            userDao.updateUserGamesPlayed(userModel.getUserId());
            userDao.updateUserGamesPlayed(userModel.getUserId2());
        }
        // Sets game over text and player stats
        if(pIsDraw)  {
            System.out.println("Its a draw");
            gameOverText.setText(R.string.gameover_draw);
        }
        else if(pIsPlayer1sTurn && gameData.getGameMode() == 1 )  {
            System.out.println("Its game mdoe 1 and user 1 has won");
            gameOverText.setText(String.format("GAMEOVER: %s WINS!", userModel.getUserName()));
            userDao.updateUserWins(userModel.getUserId());
        }
        else if (!pIsPlayer1sTurn && gameData.getGameMode() == 1) {
            System.out.println("Its game mode 1 and user AI has won");
            gameOverText.setText(R.string.gameover_ai_wins);
            userDao.updateUserLosses(userModel.getUserId());
        }
        else if(pIsPlayer1sTurn && gameData.getGameMode() == 2)  {
            System.out.println("Its game mode 2 and user 1 has won");
            gameOverText.setText(String.format("GAMEOVER: %s WINS!", userModel.getUserName()));
            userDao.updateUserWins(userModel.getUserId());
            userDao.updateUserLosses(userModel.getUserId2());
        }
        else if (!pIsPlayer1sTurn && gameData.getGameMode() == 2) {
            System.out.println("Its game mode 2 and user 2 has won");
            gameOverText.setText(String.format("GAMEOVER: %s WINS!", userModel.getUserName2()));
            userDao.updateUserWins(userModel.getUserId2());
            userDao.updateUserLosses(userModel.getUserId());
        }

        // Disable all board buttons
        for(int i = 0; i < boardSize*boardSize; i++){
            adapter.data.get(i).setEnabledState(false);
        }

        // Notify adapter to update UI
        adapter.notifyDataSetChanged();

        // Sets game over text to visible
        gameOverText.setVisibility(View.VISIBLE);
    }

    // Restarts game
    // Enables board buttons and clears game board
    public void resetGame() {
        // Sets game over text to invisible
        gameOverText.setVisibility(View.INVISIBLE);
        invalidMoveText.setVisibility(View.INVISIBLE);

        // Clears board array
        for(int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                gameBoard[i][j] = '-';
            }
        }

        // Change all adapter data to default values, and enable all board buttons
        for(int i = 0; i < boardSize*boardSize; i++) {
            adapter.data.get(i).setMarkerSymbol('-');
            adapter.data.get(i).setImageResource(0);
            adapter.data.get(i).setEnabledState(true);
        }

        // Notify adapter to update UI
        adapter.notifyDataSetChanged();

        // If game mode is player vs ai and ai moves first, ai moves
        if(!isPlayer1GoingFirst && gameData.getGameMode() == 1){
            gameData.setWhoseTurn(3); //Set whose turn to AI
            aiMove(gameBoard); // AI moves
        }

        // If game mode is pvp, set to appropriate players turn
        if(!isPlayer1GoingFirst && gameData.getGameMode() == 2) gameData.setWhoseTurn(2); //Set whose turn to player 2
        else if(isPlayer1GoingFirst && gameData.getGameMode() == 2) gameData.setWhoseTurn(1); //Set whose turn to player 1

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

        invalidMoveText.setVisibility(View.INVISIBLE);

        //TODO: Printing game board for testing purposes, can be deleted
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                System.out.print(gameBoard[i][j]);
            }
            System.out.println("");
        }

        // Set isPlayer1sTurn to appropriate value and Determines button location in array from list position
        if(gameData.whoseTurn.getValue() == 1){
            locI = pPosition / gameData.getBoardSize();
            locJ = pPosition % gameData.getBoardSize();
            isPlayer1sTurn = true;
        }
        else if(gameData.whoseTurn.getValue() == 2){
            otherLocI = pPosition / gameData.getBoardSize();
            otherLocJ = pPosition % gameData.getBoardSize();
            isPlayer1sTurn = false;
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
            aiMove(gameBoard); // AI moves
            isThereAWinner = checkIfThereIsWinner(gameBoard, winConditionInput, otherLocI, otherLocJ, isPlayer1sTurn);
            isDraw = ((isAllSpacesTaken(gameBoard)) && (!isThereAWinner)); // It is a draw if all spaces on the board are taken and there is no winner
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
    }

    public UserDao initialiseDB() {
        return UserDbInstance.getDatabase(getContext()).userDao();
    }
}