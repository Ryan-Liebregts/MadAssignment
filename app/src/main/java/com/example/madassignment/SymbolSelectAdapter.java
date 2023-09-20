package com.example.madassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SymbolSelectAdapter extends RecyclerView.Adapter<SelectUserVH> {

    // TODO: PK Commenting
    List<Integer> data;

    UserData userModel;

    String player2Name = "Player 2";

    GameData gameData;

    LifecycleOwner owner;

    public SymbolSelectAdapter(List<Integer> data, UserData userData, GameData gameData, LifecycleOwner cycleOwner){
        this.data = data;
        this.userModel = userData;
        this.owner = cycleOwner;
        this.gameData = gameData;
    }

    @NonNull
    @Override
    public SelectUserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.select_user_item,parent,false);
        return new SelectUserVH(view, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectUserVH holder, int position) {
        int singleRow = data.get(position);
        holder.userNameText.setVisibility(View.GONE);
        holder.userIconButton.setImageResource(singleRow);
        loadDefault(holder, singleRow);
        if (gameData.getGameMode() == 1) {
            player2Name = "AI";
        }

        holder.userIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                processClick(holder, singleRow);
            }
        });

        holder.checkboxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                processClick(holder, singleRow);
            }
        });

        userModel.userSymbol1.observe(owner, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != singleRow){
                    if (holder.userNameText.getText() == "Player 1") {
                        holder.checkboxButton.setVisibility(View.GONE);
                        holder.userNameText.setText("");
                        holder.userNameText.setVisibility(View.GONE);
                    }
                }
                else {
                    holder.checkboxButton.setVisibility(View.VISIBLE);
                    holder.userNameText.setText("Player 1");
                    // Modified by Ryan to use a new drawable icon
                    holder.checkboxButton.setImageResource(R.drawable.number_one_icon);
                    holder.userNameText.setVisibility(View.VISIBLE);
                }
            }
        });

        userModel.userSymbol2.observe(owner, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != singleRow){
                    if (holder.userNameText.getText() == player2Name) {
                        holder.checkboxButton.setVisibility(View.GONE);
                        holder.userNameText.setText("");
                        holder.userNameText.setVisibility(View.GONE);
                    }
                }
                else {
                    holder.checkboxButton.setVisibility(View.VISIBLE);
                    // Modified by Ryan to use a new drawable icon
                    holder.checkboxButton.setImageResource(R.drawable.number_two_icon);
                    holder.userNameText.setText(player2Name);
                    holder.userNameText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void processClick(SelectUserVH holder, int singleRow) {
            if(userModel.getUserSymbol1() == 0 && userModel.getUserSymbol2() == 0) {
                userModel.setUserSymbol1(singleRow);
            }
            else if(userModel.getUserSymbol1() != 0 && userModel.getUserSymbol2() == 0) {
                if(userModel.getUserSymbol1() == singleRow) {
                    userModel.setUserSymbol1(0);
                }
                else {
                    userModel.setUserSymbol2(singleRow);
                }
            } else if (userModel.getUserSymbol1() == 0 && userModel.getUserSymbol2() != 0) {
                if(userModel.getUserSymbol2()  == singleRow) {
                    userModel.setUserSymbol2(0);
                }
                else {
                    userModel.setUserSymbol1(singleRow);
                }
            }
            else {
                if(userModel.getUserSymbol2()  == singleRow) {
                    userModel.setUserSymbol2(0);
                }
                else if(userModel.getUserSymbol1()  == singleRow) {
                    userModel.setUserSymbol1(0);
                }
            }
    }

    public void loadDefault(SelectUserVH holder, int singleRow) {
        if (userModel.getUserSymbol1() == singleRow){
            userModel.setUserSymbol1(singleRow);
            holder.checkboxButton.setVisibility(View.VISIBLE);
            holder.userNameText.setText("Player 1");
            holder.userNameText.setVisibility(View.VISIBLE);
        }
        else if (userModel.getUserSymbol2() == singleRow){
            userModel.setUserSymbol2(singleRow);
            holder.checkboxButton.setVisibility(View.VISIBLE);
            holder.userNameText.setText(player2Name);
            holder.userNameText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
