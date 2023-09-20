package com.example.madassignment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BoardButtonAdapter extends RecyclerView.Adapter<BoardButtonDataViewHolder> {

    ArrayList<BoardButtonData> data;
    GameData gameData;
    UserData userData;
    AdapterCallback callback;
    Handler handler = new Handler();

    /* -----------------------------------------------------------------------------------------
        Function: AdapterCallback
        Author: Jules
        Description: The interface allows communication to fragment when button is pressed
     ---------------------------------------------------------------------------------------- */
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
        BoardButtonData singleData = data.get(position); // Obtain board button
        holder.boardButton.setImageResource(singleData.getImageResource()); // Set board button image resource
        holder.boardButton.setEnabled(singleData.getEnabledState()); //Set board button enable state

        /* -----------------------------------------------------------------------------------------
                Function: Board Aesthetics
                Author: Ryan
                Description: Defines the checkerboard aesthetic by setting the background resource
                    as required
         ---------------------------------------------------------------------------------------- */
        if (position % 2 == 1 && (gameData.getBoardSize() == 3 || gameData.getBoardSize() == 5)) {
            // 3x3 and 5x5 board have an odd number of elements so we can utilize the modulus
            holder.boardButton.setBackgroundResource(R.drawable.wood_background_dark);
        }

        List<Integer> positionsToCheck = Arrays.asList(0, 2, 5, 7, 8, 10, 13, 15);

        if (gameData.getBoardSize() == 4 && positionsToCheck.contains(position)) {
            // 4x4 board has an even number of elements so we must hard code the list of elements
            // to be changed
            holder.boardButton.setBackgroundResource(R.drawable.wood_background_dark);
        }
        if (gameData.getBoardSize() == 4 && position == 4) {
            // Fix to weird bug where position 4 would turn dark brown after AI move
            holder.boardButton.setBackgroundResource(R.drawable.wood_background_light);
        }

        /* -----------------------------------------------------------------------------------------
                Function: onClick(View view)
                Author: Jules
                Description: Runs everytime a board button is clicked
         ---------------------------------------------------------------------------------------- */
        holder.boardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If it is not the ai's turn execute the code
                if(gameData.getWhoseTurn() != 3) {
                    // If position is empty place a marker
                    if (singleData.getMarkerSymbol() == '-') {
                        if (gameData.getWhoseTurn() == 1) { //If player1s turn place their marker
                            holder.boardButton.setImageResource(userData.getUserSymbol1()); // Sets image resource of the button to player 1's marker
                            singleData.setImageResource(userData.getUserSymbol1()); // Sets the button in adapter data to player 1's marker
                            singleData.setMarkerSymbol(gameData.getPlayer1MarkerSymbol()); // Sets the button in adapter data to player 1's symbol
                        } else if (gameData.getWhoseTurn() == 2) { //If player2s turn place their marker
                            holder.boardButton.setImageResource(userData.getUserSymbol2()); // Sets image resource of the button to player 2's marker
                            singleData.setImageResource(userData.getUserSymbol2()); // Sets the button in adapter data to player 2's marker
                            singleData.setMarkerSymbol(gameData.getPlayer2MarkerSymbol()); // Sets the button in adapter data to player 2's symbol
                        }
                    // If position is not empty
                    } else {
                        gameData.setIsInvalidMove(true); // notifies that move is invalid
                        callback.invalidMoveClicked(); //Call back to Fragment through the interface
                        return;
                    }

                    //Call back to Fragment through the interface
                    callback.onItemClicked(position);
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

}
