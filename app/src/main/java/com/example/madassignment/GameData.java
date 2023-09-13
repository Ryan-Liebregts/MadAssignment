package com.example.madassignment;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameData  extends ViewModel {

    public MutableLiveData<Integer> boardSize;
    public MutableLiveData<Integer> winCondition;
    public MutableLiveData<Integer> gameMode;
    public MutableLiveData<Integer> player1Marker;
    public MutableLiveData<Character> player1MarkerSymbol;
    public MutableLiveData<Integer> player2Marker;
    public MutableLiveData<Character> player2MarkerSymbol;
    public MutableLiveData<Integer> aiMarker;
    public MutableLiveData<Character> aiMarkerSymbol;
    public MutableLiveData<Integer> whoseTurn;
    public MutableLiveData<Boolean> isPlayer1GoingFirst;

    public GameData(){
        boardSize = new MediatorLiveData<Integer>();
        boardSize.setValue(3);

        winCondition = new MediatorLiveData<Integer>();
        winCondition.setValue(3);

        gameMode = new MediatorLiveData<Integer>();
        gameMode.setValue(1);
        //gameType 1 = vs AI
        //gameType 2 = vs player2

        player1Marker = new MediatorLiveData<Integer>(); //TODO: Implement a method of players choosing marker
        player1Marker.setValue(R.drawable.cross);

        player2Marker = new MediatorLiveData<Integer>();
        player2Marker.setValue(R.drawable.circle);

        aiMarker = new MediatorLiveData<Integer>();
        aiMarker.setValue(R.drawable.circle);

        player1MarkerSymbol = new MediatorLiveData<Character>();  //TODO: Implement a method of players choosing marker
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


}
