package com.example.madassignment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

public class BoardButtonDataViewHolder extends RecyclerView.ViewHolder {

    public ImageButton boardButton;
    public GameData gameData = new ViewModelProvider((ViewModelStoreOwner) itemView.getContext()).get(GameData.class);

    /* -----------------------------------------------------------------------------------------
        Function: BoardButtonDataViewHolder
        Author: Jules
        Description: View holder for the recycler view buttons
    ---------------------------------------------------------------------------------------- */
    public BoardButtonDataViewHolder(@NonNull View boardButtonView, ViewGroup parent){
        super(boardButtonView);
        int hSize = parent.getMeasuredHeight() / gameData.getBoardSize(); //Set height to appropriate size based on board size
        int wSize = parent.getMeasuredWidth() / gameData.getBoardSize(); //Set width to appropriate size based on board size
        ViewGroup.LayoutParams lp = itemView.getLayoutParams(); //Obtain layout parameters
        lp.height = hSize; //Set new height of button
        lp.width = wSize; //Set new width of button
        View itemView;
        boardButton = boardButtonView.findViewById(R.id.boardButton);
    }

}
