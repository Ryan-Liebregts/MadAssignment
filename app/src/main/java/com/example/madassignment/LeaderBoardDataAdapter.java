package com.example.madassignment;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderBoardDataAdapter  extends RecyclerView.Adapter<LeaderBoardVH> {

    List<User> data;

    public LeaderBoardDataAdapter(List<User> data){
        this.data = data;
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

        //set wins
        holder.userScoreTextBox.setText(String.valueOf(singleRow.getUserWins()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
