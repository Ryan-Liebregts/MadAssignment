package com.example.madassignment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LeaderBoardVH extends RecyclerView.ViewHolder{

    TextView userRankTextBox;
    TextView userNameTextBox;
    ImageView userIconImage;

    ImageView playerOne;

    ImageView playerTwo;

    TextView userScoreTextBox;

    LinearLayout userLayout;
    public LeaderBoardVH(@NonNull View itemView, ViewGroup parent) {
        super(itemView);
        int hSize = parent.getMeasuredHeight() /10;
        ViewGroup.LayoutParams lp = itemView.getLayoutParams();
        //this will be to dynamically set height according to screen size
        //TODO
//        lp.height = hSize;
        playerOne = itemView.findViewById(R.id.player_one);
        playerTwo= itemView.findViewById(R.id.player_two);
        userLayout = itemView.findViewById(R.id.user_layout);
        userRankTextBox = itemView.findViewById(R.id.user_rank);
        userNameTextBox = itemView.findViewById(R.id.user_name);
        userIconImage = itemView.findViewById(R.id.user_icon);
        userScoreTextBox = itemView.findViewById(R.id.user_score);



    }


}
