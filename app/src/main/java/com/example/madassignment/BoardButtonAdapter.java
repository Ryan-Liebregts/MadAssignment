package com.example.madassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BoardButtonAdapter extends RecyclerView.Adapter<BoardButtonDataViewHolder> {

    ArrayList<BoardButtonData> data;
    GameData gameData;
    UserData userData;
    AdapterCallback callback;

    // Interface to call back to the fragment
    public interface AdapterCallback{
        void onItemClicked(int position);
        void invalidMoveClicked();
    }
    public BoardButtonAdapter(ArrayList<BoardButtonData> data, AdapterCallback callback){
        this.data = data;
        this.callback = callback;
    }

    @NonNull
    @Override
    public BoardButtonDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        gameData = new ViewModelProvider((ViewModelStoreOwner) parent.getContext()).get(GameData.class);
        userData = new ViewModelProvider((ViewModelStoreOwner) parent.getContext()).get(UserData.class);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.board_button_layout, parent, false);
        BoardButtonDataViewHolder boardButtonDataViewHolder = new BoardButtonDataViewHolder(view, parent);
        return boardButtonDataViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BoardButtonDataViewHolder holder, int position){
        BoardButtonData singleData = data.get(position);
        holder.boardButton.setImageResource(singleData.getImageResource());
        holder.boardButton.setEnabled(singleData.getEnabledState());
        holder.boardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If position is empty place a marker
                if(singleData.getMarkerSymbol() == '-'){
                    System.out.println("space -");
                    if(gameData.getWhoseTurn() == 1){ //If player1s turn place their marker
                        holder.boardButton.setImageResource(userData.getUserSymbol1());
                        singleData.setImageResource(userData.getUserSymbol1());
                        singleData.setMarkerSymbol(gameData.getPlayer1MarkerSymbol());
                    }
                    else if(gameData.getWhoseTurn() == 2){ //If player2s turn place their marker
                        holder.boardButton.setImageResource(userData.getUserSymbol2());
                        singleData.setImageResource(userData.getUserSymbol2());
                        singleData.setMarkerSymbol(gameData.getPlayer2MarkerSymbol());
                    }
                }
                else{
                    System.out.println("space filled");
                    gameData.setIsInvalidMove(true);
                    System.out.println(Boolean.toString(gameData.getIsInvalidMove()));
                    callback.invalidMoveClicked();
                    return;// Do nothing if position is already filled
                }

                //Call back to Fragment through the interface
                callback.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

}
