package com.example.madassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EditDeleteUserAdapter extends RecyclerView.Adapter<EditDeleteUserVH>  {

    List<User> data;

    NavigationData navModel;

    EditUser editUserModel;

    public EditDeleteUserAdapter(List<User> data, NavigationData navModel, EditUser editUserModel){
        this.data = data;
        this.navModel = navModel;
        this.editUserModel = editUserModel;
    }

    @NonNull
    @Override
    public EditDeleteUserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.edit_delete_user_item,parent,false);
        return new EditDeleteUserVH(view, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull EditDeleteUserVH holder, int position) {
        User singleRow = data.get(position);
        holder.userIcon.setImageResource(singleRow.getUserIcon());
        System.out.println(singleRow.getUserName());
        holder.userNameTextBox.setText(singleRow.getUserName());
        holder.userDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("You want to delete this huh?");
            }
        });

        holder.userEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("You want to edit this huh?");
                navModel.setClickedValue(3);
                navModel.setHistoricalClickedValue(6);
                editUserModel.setUserIcon(singleRow.getUserIcon());
                editUserModel.setUserName(singleRow.getUserName());
                editUserModel.setUserId(singleRow.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
