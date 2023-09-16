package com.example.madassignment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EditDeleteUserVH extends RecyclerView.ViewHolder  {
    ImageView userIcon;
    TextView userNameTextBox;
    ImageButton userEditButton;
    ImageButton userDeleteButton;

    public EditDeleteUserVH(@NonNull View itemView, ViewGroup parent) {
        super(itemView);
        userIcon = itemView.findViewById(R.id.user_icon);
        userNameTextBox = itemView.findViewById(R.id.user_name);
        userEditButton = itemView.findViewById(R.id.edit_user);
        userDeleteButton = itemView.findViewById(R.id.deleteUser);
    }
}
