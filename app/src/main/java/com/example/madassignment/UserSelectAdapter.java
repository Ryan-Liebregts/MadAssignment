package com.example.madassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserSelectAdapter extends RecyclerView.Adapter<LeaderBoardVH> {

    List<User> data;

    UserData userModel;

    GameData gameData;

    LifecycleOwner owner;
    public UserSelectAdapter(List<User> data, UserData userData, GameData gameData, LifecycleOwner cycleOwner){
        this.data = data;
        this.userModel = userData;
        this.owner = cycleOwner;
        this.gameData = gameData;
    }
    @NonNull
    @Override
    public LeaderBoardVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.leaderboard_list_item,parent,false);


        return new LeaderBoardVH(view, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardVH holder, int position) {
        User singleRow = data.get(position);
        //set the name
        holder.userNameTextBox.setText(singleRow.getUserName());

        //set icon
        int imageResId = singleRow.getUserIcon();
        holder.userIconImage.setImageResource(imageResId);

        //set rank
        holder.userRankTextBox.setText(String.valueOf(position +1));

        //hide this
        holder.userScoreTextBox.setVisibility(View.GONE);
        holder.userIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                System.out.println("Hello");
                processClick(holder, imageResId, singleRow);
            }
        });

        holder.userNameTextBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                System.out.println("Hello");
                processClick(holder, imageResId, singleRow);
            }
        });

        holder.playerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                System.out.println("Hello");
                processClick(holder, imageResId, singleRow);
            }
        });

        holder.playerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                System.out.println("Hello");
                processClick(holder, imageResId, singleRow);
            }
        });

        userModel.userId.observe(owner, new Observer<Long>() {
            @Override
            public void onChanged(Long integer) {
                if (integer != singleRow.getId()){
                    holder.playerOne.setVisibility(View.GONE);
                }
            }
        });

        userModel.userId2.observe(owner, new Observer<Long>() {
            @Override
            public void onChanged(Long integer) {
                if (integer != singleRow.getId()){
                    holder.playerTwo.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void processClick(LeaderBoardVH holder, int imageResId, User singleRow ) {
        System.out.println("Helllo I just got clicked");
        if (gameData.getGameMode() == 1) {
            if(userModel.getUserId()  == singleRow.getId()) {
                userModel.setUserName("");
                userModel.setUserIcon(0);
                userModel.setUserId(0);
                holder.playerOne.setVisibility(View.GONE);
            }
            else {
                userModel.setUserName(singleRow.getUserName());
                userModel.setUserIcon(imageResId);
                userModel.setUserId(singleRow.getId());
                holder.playerOne.setVisibility(View.VISIBLE);
            }
        }
        else{
            if(userModel.getUserId() == 0 && userModel.getUserId2() == 0) {
                userModel.setUserName(singleRow.getUserName());
                userModel.setUserIcon(imageResId);
                userModel.setUserId(singleRow.getId());
                holder.playerOne.setVisibility(View.VISIBLE);
            }
            else if(userModel.getUserId() != 0 && userModel.getUserId2() == 0) {
                if(userModel.getUserId()  == singleRow.getId()) {
                    userModel.setUserName("");
                    userModel.setUserIcon(0);
                    userModel.setUserId(0);
                    holder.playerOne.setVisibility(View.GONE);
                }
                else {
                    userModel.setUserName2(singleRow.getUserName());
                    userModel.setUserIcon2(imageResId);
                    userModel.setUserId2(singleRow.getId());
                    holder.playerTwo.setVisibility(View.VISIBLE);
                }
            } else if (userModel.getUserId() == 0 && userModel.getUserId2() != 0) {
                if(userModel.getUserId2()  == singleRow.getId()) {
                    userModel.setUserName2("");
                    userModel.setUserIcon2(0);
                    userModel.setUserId2(0);
                    holder.playerTwo.setVisibility(View.GONE);
                }
                else {
                    userModel.setUserName(singleRow.getUserName());
                    userModel.setUserIcon(imageResId);
                    userModel.setUserId(singleRow.getId());
                    holder.playerOne.setVisibility(View.VISIBLE);
                }
            }
            else {
                if(userModel.getUserId2()  == singleRow.getId()) {
                    userModel.setUserName2("");
                    userModel.setUserIcon2(0);
                    userModel.setUserId2(0);
                    holder.playerTwo.setVisibility(View.GONE);
                }
                else if(userModel.getUserId()  == singleRow.getId()) {
                    userModel.setUserName("");
                    userModel.setUserIcon(0);
                    userModel.setUserId(0);
                    holder.playerOne.setVisibility(View.GONE);
                }

            }
        }
    }
}
