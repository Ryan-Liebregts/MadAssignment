package com.example.madassignment;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameData  extends ViewModel {

    public MutableLiveData<Integer> boardSize;
    public MutableLiveData<Integer> winCondition;
    public MutableLiveData<Integer> gameMode;

    public GameData(){
        boardSize = new MediatorLiveData<Integer>();
        boardSize.setValue(3);

        winCondition = new MediatorLiveData<Integer>();
        winCondition.setValue(3);

        gameMode = new MediatorLiveData<Integer>();
        gameMode.setValue(1);
        //gameType 1 = vs AI
        //gameType 2 = vs player2
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


}
