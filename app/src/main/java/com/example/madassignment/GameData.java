package com.example.madassignment;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.*;

public class GameData  extends ViewModel {

    /* -----------------------------------------------------------------------------------------
    Function: Initialise adapter variables
    Author: Parakram + Jules + Yi Xiang
    Description: Initialises data within the fragment
    ---------------------------------------------------------------------------------------- */
    public MutableLiveData<Integer> boardSize; // Board size
    public MutableLiveData<Integer> winCondition; // How many markers in a row to win the game
    public MutableLiveData<Integer> gameMode; // Current game mode
    public MutableLiveData<Integer> player1Marker; // Drawable for player 1's marker
    public MutableLiveData<Character> player1MarkerSymbol; // Symbol for player 1 (Used by game board to discern whose marker is who)
    public MutableLiveData<Integer> player2Marker; // Drawable for player 2's marker
    public MutableLiveData<Character> player2MarkerSymbol; // Symbol for player 2 (Used by game board to discern whose marker is who)
    public MutableLiveData<Integer> aiMarker; // Drawable for AI's marker
    public MutableLiveData<Character> aiMarkerSymbol; // Symbol for player AI (Used by game board to discern whose marker is who)
    public MutableLiveData<Integer> whoseTurn; // Current players turn
    public MutableLiveData<Integer> player1Moves; // Player 1's move count
    public MutableLiveData<Integer> player2Moves; // Player 2's move count
    public MutableLiveData<Boolean> isPlayer1GoingFirst; // Is player 1 going moving first
    public MutableLiveData<Boolean> isInvalidMove; // Is the previous move invalid
    public MutableLiveData<Boolean> needSaveGameState; // Is SaveGameState required
    public MutableLiveData<Character[][]> gameBoard; // Array of the game board
    public MutableLiveData<ArrayList<Integer[]>> moveList; // ArrayList containing each move
    public MutableLiveData<Boolean> isGameOver; // Is the game over
    public MutableLiveData<Integer> timerLength; // Timer length

    public GameData(){
        boardSize = new MediatorLiveData<Integer>();
        boardSize.setValue(3);

        winCondition = new MediatorLiveData<Integer>();
        winCondition.setValue(3);

        gameMode = new MediatorLiveData<Integer>();
        gameMode.setValue(1);
        //gameType 1 = vs AI
        //gameType 2 = vs player2

        player1Marker = new MediatorLiveData<Integer>();
        player1Marker.setValue(R.drawable.cross);

        player2Marker = new MediatorLiveData<Integer>();
        player2Marker.setValue(R.drawable.circle);

        aiMarker = new MediatorLiveData<Integer>();
        aiMarker.setValue(R.drawable.circle);

        player1MarkerSymbol = new MediatorLiveData<Character>();
        player1MarkerSymbol.setValue('x');

        player2MarkerSymbol = new MediatorLiveData<Character>();
        player2MarkerSymbol.setValue('o');

        aiMarkerSymbol = new MediatorLiveData<Character>();
        aiMarkerSymbol.setValue('o');

        whoseTurn = new MediatorLiveData<Integer>();
        whoseTurn.setValue(1);
        //whoseTurn 1 = Player 1
        //whoseTurn 2 = Player 2
        //whoseTurn 3 = AI

        isPlayer1GoingFirst = new MediatorLiveData<Boolean>();
        isPlayer1GoingFirst.setValue(true);

        isInvalidMove = new MediatorLiveData<Boolean>();
        isInvalidMove.setValue(false);

        player1Moves = new MediatorLiveData<Integer>();
        player1Moves.setValue(0);

        player2Moves = new MediatorLiveData<Integer>();
        player2Moves.setValue(0);

        needSaveGameState = new MediatorLiveData<Boolean>();
        needSaveGameState.setValue(false);

        Character[][] placeholdGameBoard = new Character[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                placeholdGameBoard[i][j] = '-';
            }
        }

        gameBoard = new MediatorLiveData<Character[][]>();
        gameBoard.setValue(placeholdGameBoard);

        moveList = new MediatorLiveData<ArrayList<Integer[]>>();
        ArrayList<Integer[]> test = new ArrayList<Integer[]>();
        moveList.setValue(test);

        isGameOver = new MediatorLiveData<Boolean>();
        isGameOver.setValue(false);

        timerLength = new MediatorLiveData<Integer>();
        timerLength.setValue(11000);

    }

    public int getBoardSize() {
        return boardSize.getValue();
    }
    public void setBoardSize(int size) {
        boardSize.setValue(size);
    }

    public int getWinCondition() {
        return winCondition.getValue();
    }

    public void setWinCondition(int condition) {
        winCondition.setValue(condition);
    }

    public int getGameMode() {
        return gameMode.getValue();
    }

    public void setGameMode(int gameModeValue) {
        gameMode.setValue(gameModeValue);
    }

    public void setPlayer1Marker(int pMarker) {
        player1Marker.setValue(pMarker);
    }

    public int getPlayer1Marker() {
        return player1Marker.getValue();
    }

    public void setPlayer2Marker(int pMarker) {
        player2Marker.setValue(pMarker);
    }

    public int getPlayer2Marker() {
        return player2Marker.getValue();
    }

    public void setAIMarker(int pMarker) {
        aiMarker.setValue(pMarker);
    }

    public int getAIMarker() {
        return aiMarker.getValue();
    }

    public void setWhoseTurn(int pWhoseTurn){
        whoseTurn.setValue(pWhoseTurn);
    }

    public int getWhoseTurn(){
        return whoseTurn.getValue();
    }

    public char getPlayer1MarkerSymbol(){
        return player1MarkerSymbol.getValue();
    }

    public void setPlayer1MarkerSymbol(char pPlayer1MarkerSymbol){
        player1MarkerSymbol.setValue(pPlayer1MarkerSymbol);
    }

    public char getPlayer2MarkerSymbol(){
        return player2MarkerSymbol.getValue();
    }

    public void setPlayer2MarkerSymbol(char pPlayer2MarkerSymbol){
        player2MarkerSymbol.setValue(pPlayer2MarkerSymbol);
    }

    public char getAIMarkerSymbol(){
        return aiMarkerSymbol.getValue();
    }

    public void setAIMarkerSymbol(char pAIMarkerSymbol){
        aiMarkerSymbol.setValue(pAIMarkerSymbol);
    }

    public boolean getIsPlayer1GoingFirst(){
        return isPlayer1GoingFirst.getValue();
    }

    public void setIsPlayer1GoingFirst(boolean pIsPlayer1GoingFirst){
        isPlayer1GoingFirst.setValue(pIsPlayer1GoingFirst);
    }

    public boolean getIsInvalidMove(){
        return isInvalidMove.getValue();
    }

    public void setIsInvalidMove(boolean pIsInvalidMove){
        isInvalidMove.setValue(pIsInvalidMove);
    }

    public void setPlayer1Moves(int moves) {
        player1Moves.setValue(moves);
    }

    public int getPlayer1Moves() {
        return player1Moves.getValue();
    }

    public void setPlayer2Moves(int moves) {
        player2Moves.setValue(moves);
    }

    public int getPlayer2Moves() {
        return player2Moves.getValue();
    }

    public boolean getNeedSaveGameState(){
        return needSaveGameState.getValue();
    }

    public void setNeedSaveGameState(boolean pNeedSaveGameState){
        needSaveGameState.setValue(pNeedSaveGameState);
    }

    public char[][] getGameBoard(){
        char[][] primitiveGameBoard = new char[gameBoard.getValue().length][gameBoard.getValue()[0].length];
        for (int i = 0; i < gameBoard.getValue().length; i++) {
            for (int j = 0; j < gameBoard.getValue()[0].length; j++) {
                primitiveGameBoard[i][j] = (char) gameBoard.getValue()[i][j];
            }
        }
        return primitiveGameBoard;
    }

    public void setGameBoard(char[][] pGameBoard){
        Character[][] arrayGameBoard = new Character[pGameBoard.length][pGameBoard[0].length];
        for (int i = 0; i < pGameBoard.length; i++) {
            for (int j = 0; j < pGameBoard[0].length; j++) {
                arrayGameBoard[i][j] = (Character) pGameBoard[i][j];
            }
        }
        gameBoard.setValue(arrayGameBoard);
    }

    public ArrayList<int[]> getMoveList() {
        ArrayList<int[]> primitiveMoveList = new ArrayList<int[]>();
        int[] move;

        if (moveList.getValue() != null) {
            System.out.println("getting move");
            if (moveList.getValue().size() > 0) {
                for (int i = 0; i < moveList.getValue().size(); i++) {
                    move = new int[moveList.getValue().get(0).length];
                    for (int j = 0; j < moveList.getValue().get(0).length; j++) {
                        move[j] = (int) moveList.getValue().get(i)[j];
                    }
                    primitiveMoveList.add(move);
                }
            }
            for (int i = 0; i < primitiveMoveList.size(); i++) {
                for (int j = 0; j < primitiveMoveList.get(0).length; j++) {
                    System.out.println(primitiveMoveList.get(i)[j]);
                }
            }
            return primitiveMoveList;
        }
        return primitiveMoveList;
    }

    public void setMoveList(ArrayList<int[]> pMoveList){
        ArrayList<Integer[]> wrapperMoveList = new ArrayList<Integer[]>();
        Integer[] move;

        System.out.println("setting move");
        if (pMoveList.size() > 0) {
            for (int i = 0; i < pMoveList.size(); i++) {
                move = new Integer[pMoveList.get(0).length];
                for (int j = 0; j < pMoveList.get(0).length; j++) {
                    move[j] = (Integer) pMoveList.get(i)[j];
                }
                wrapperMoveList.add(move);
            }
        }
        for (int i = 0; i < wrapperMoveList.size(); i++) {
            for (int j = 0; j < wrapperMoveList.get(0).length; j++) {
                System.out.println(wrapperMoveList.get(i)[j]);
            }
        }
        moveList.setValue(wrapperMoveList);
    }

    public boolean getIsGameOver(){
        return isGameOver.getValue();
    }

    public void setIsGameOver(boolean pIsGameOver){
        isGameOver.setValue(pIsGameOver);
    }

    public int getTimerLength(){
        return timerLength.getValue();
    }

    public void setTimerLength(int pTimerLength){
        timerLength.setValue(pTimerLength);
    }

}
