package com.example.madassignment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserIconVH extends RecyclerView.ViewHolder {
    ImageButton userIcon;

    ImageButton userIconSelected;
    public UserIconVH(@NonNull View itemView, ViewGroup parent) {
        super(itemView);
//        int hSize = parent.getMeasuredHeight()/10;
//        ViewGroup.LayoutParams lp = itemView.getLayoutParams();
////        lp.height = hSize;
        userIcon = itemView.findViewById(R.id.select_icon);
        userIconSelected = itemView.findViewById((R.id.select_icon_selected));

    }
}
