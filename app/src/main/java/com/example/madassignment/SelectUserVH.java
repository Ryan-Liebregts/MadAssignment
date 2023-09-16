package com.example.madassignment;

import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectUserVH extends RecyclerView.ViewHolder {
    ImageButton checkboxButton;
    ImageButton userIconButton;
    TextView userNameText;
    public SelectUserVH(@NonNull View itemView, ViewGroup parent) {
        super(itemView);

        userIconButton = itemView.findViewById(R.id.user_icon);
        checkboxButton = itemView.findViewById(R.id.checkbox);
        userNameText = itemView.findViewById(R.id.user_name);

    }
}

