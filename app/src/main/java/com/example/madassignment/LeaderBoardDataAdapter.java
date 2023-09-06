package com.example.madassignment;



import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LeaderBoardDataAdapter  extends RecyclerView.Adapter<LeaderBoardVH> {

    ArrayList<LeaderBoardData> data;

    public LeaderBoardDataAdapter(ArrayList<LeaderBoardData> data){
        this.data = data;
    }

    @NonNull
    @Override
    public LeaderBoardVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.leaderboard_list_item,parent,false);
        LeaderBoardVH leaderBoardVH = new LeaderBoardVH(view, parent);
        return leaderBoardVH;
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardVH holder, int position) {
        LeaderBoardData singleRow = data.get(position);
        holder.userNameTextBox.setText(singleRow.getUserName());
        int imageResId = singleRow.getUserIcon();
        holder.userIconImage.setImageResource(imageResId);
        holder.userRankTextBox.setText(String.valueOf(singleRow.getUserRank()));
        holder.userScoreTextBox.setText(String.valueOf(singleRow.getUserScore()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
