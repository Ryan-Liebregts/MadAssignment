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

    public BoardButtonDataViewHolder(@NonNull View boardButtonView, ViewGroup parent){
        super(boardButtonView);
        int hSize = parent.getMeasuredHeight() / gameData.getBoardSize();
        int wSize = parent.getMeasuredWidth() / gameData.getBoardSize();
        ViewGroup.LayoutParams lp = itemView.getLayoutParams();
        lp.height = hSize;
        lp.width = wSize;
        View itemView;
        boardButton = boardButtonView.findViewById(R.id.boardButton);
    }

}
