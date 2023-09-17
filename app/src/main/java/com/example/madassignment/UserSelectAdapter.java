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

public class UserSelectAdapter extends RecyclerView.Adapter<SelectUserVH> {

    List<User> data;

    UserData userModel;

    GameData gameData;

    NavigationData navModel;

    LifecycleOwner owner;
    public UserSelectAdapter(List<User> data, UserData userData, GameData gameData, LifecycleOwner cycleOwner, NavigationData navModel){
        this.data = data;
        this.userModel = userData;
        this.owner = cycleOwner;
        this.gameData = gameData;
        this.navModel = navModel;
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
        User singleRow = data.get(position);
        if(singleRow.getUserIcon() == R.drawable.add_icon ) {
            holder.userNameText.setVisibility(View.GONE);
            holder.userIconButton.setImageResource(singleRow.getUserIcon());
            holder.userIconButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View view) {
                    navModel.setClickedValue(3);
                    navModel.setHistoricalClickedValue(5); // Updated by Ryan to reflect navigation modifications
                }
            });
        }
        else {
            //set the name
            holder.userNameText.setText(singleRow.getUserName());

            //set icon
            int imageResId = singleRow.getUserIcon();
            holder.userIconButton.setImageResource(imageResId);

            holder.userIconButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    processClick(holder, imageResId, singleRow);
                }
            });

            holder.userNameText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    processClick(holder, imageResId, singleRow);
                }
            });

            holder.checkboxButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    processClick(holder, imageResId, singleRow);
                }
            });

            userModel.userId.observe(owner, new Observer<Long>() {
                @Override
                public void onChanged(Long integer) {
                    if (integer != singleRow.getId()) {
                        if (holder.userNameText.getText() == "Player 1") {
                            holder.checkboxButton.setVisibility(View.GONE);
                            holder.userNameText.setText(singleRow.getUserName());
                        }
                    } else {
                        holder.checkboxButton.setVisibility(View.VISIBLE);
                        holder.userNameText.setText("Player 1");
                        holder.checkboxButton.setImageResource(R.drawable.number_one_icon);

                    }
                }
            });

            userModel.userId2.observe(owner, new Observer<Long>() {
                @Override
                public void onChanged(Long integer) {
                    if (integer != singleRow.getId()) {
                        if (holder.userNameText.getText() == "Player 2") {
                            holder.checkboxButton.setVisibility(View.GONE);
                            holder.userNameText.setText(singleRow.getUserName());
                        }
                    } else {
                        holder.checkboxButton.setVisibility(View.VISIBLE);
                        holder.checkboxButton.setImageResource(R.drawable.number_two_icon);
                        holder.userNameText.setText("Player 2");
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void processClick(SelectUserVH holder, int imageResId, User singleRow ) {
        System.out.println("Hello I just got clicked");
        if (gameData.getGameMode() == 1) {
            if(userModel.getUserId()  == singleRow.getId()) {
                userModel.setUserName("");
                userModel.setUserIcon(0);
                userModel.setUserId(0);
            }
            else {
                userModel.setUserName(singleRow.getUserName());
                userModel.setUserIcon(imageResId);
                userModel.setUserId(singleRow.getId());
            }
        }
        else{
            if(userModel.getUserId() == 0 && userModel.getUserId2() == 0) {
                userModel.setUserName(singleRow.getUserName());
                userModel.setUserIcon(imageResId);
                userModel.setUserId(singleRow.getId());
            }
            else if(userModel.getUserId() != 0 && userModel.getUserId2() == 0) {
                if(userModel.getUserId()  == singleRow.getId()) {
                    userModel.setUserName("");
                    userModel.setUserIcon(0);
                    userModel.setUserId(0);
                }
                else {
                    userModel.setUserName2(singleRow.getUserName());
                    userModel.setUserIcon2(imageResId);
                    userModel.setUserId2(singleRow.getId());
                }
            } else if (userModel.getUserId() == 0 && userModel.getUserId2() != 0) {
                if(userModel.getUserId2()  == singleRow.getId()) {
                    userModel.setUserName2("");
                    userModel.setUserIcon2(0);
                    userModel.setUserId2(0);
                }
                else {
                    userModel.setUserName(singleRow.getUserName());
                    userModel.setUserIcon(imageResId);
                    userModel.setUserId(singleRow.getId());
                }
            }
            else {
                if(userModel.getUserId2()  == singleRow.getId()) {
                    userModel.setUserName2("");
                    userModel.setUserIcon2(0);
                    userModel.setUserId2(0);
                }
                else if(userModel.getUserId()  == singleRow.getId()) {
                    userModel.setUserName("");
                    userModel.setUserIcon(0);
                    userModel.setUserId(0);
                }

            }
        }
    }
}
