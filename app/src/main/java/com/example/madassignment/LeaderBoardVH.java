package com.example.madassignment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LeaderBoardVH extends RecyclerView.ViewHolder{

    TextView userRankTextBox;
    TextView userNameTextBox;
    ImageView userIconImage;
    TextView userScoreTextBox;
    public LeaderBoardVH(@NonNull View itemView, ViewGroup parent) {
        super(itemView);
        int hSize = parent.getMeasuredHeight() /10;
        ViewGroup.LayoutParams lp = itemView.getLayoutParams();
//        lp.height = hSize;
        userRankTextBox = itemView.findViewById(R.id.user_rank);
        userNameTextBox = itemView.findViewById(R.id.user_name);
        userIconImage = itemView.findViewById(R.id.user_icon);
        userScoreTextBox = itemView.findViewById(R.id.user_score);
    }
}
