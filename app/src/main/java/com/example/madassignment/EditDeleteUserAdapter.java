package com.example.madassignment;

import static java.security.AccessController.getContext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.*;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

public class EditDeleteUserAdapter extends RecyclerView.Adapter<EditDeleteUserVH>  {

    // TODO: PK Commenting
    private List<User> data;

    private NavigationData navModel;

    private UserData userModel;

    private EditUser editUserModel;

    public EditDeleteUserAdapter(List<User> data, NavigationData navModel, EditUser editUserModel, UserData userModel){
        this.data = data;
        this.navModel = navModel;
        this.editUserModel = editUserModel;
        this.userModel = userModel;
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
        //only allowed to delete non-selected users
        if (userModel.getUserId() == singleRow.getId() || userModel.getUserId2() == singleRow.getId() ) {
            holder.userDeleteButton.setEnabled(false);
        }
        holder.userIcon.setImageResource(singleRow.getUserIcon());
        holder.userNameTextBox.setText(singleRow.getUserName());
        /* -----------------------------------------------------------------------------------------
                Function: userDelete Click Listener
                Author: Parakram
         ---------------------------------------------------------------------------------------- */
        holder.userDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(position, singleRow.getId(), holder, singleRow);
            }
        });

        /* -----------------------------------------------------------------------------------------
                Function: userEdit Click Listener
                Author: Parakram
         ---------------------------------------------------------------------------------------- */
        holder.userEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navModel.setClickedValue(3);
                navModel.setHistoricalClickedValue(6);
                editUserModel.setUserIcon(singleRow.getUserIcon());
                editUserModel.setUserName(singleRow.getUserName());
                editUserModel.setUserId(singleRow.getId());
            }
        });
    }


    public void deleteUser(int index, long id, EditDeleteUserVH holder, User singleRow) {
        editUserModel.setDeleteUserPosition(holder.getAdapterPosition());
        editUserModel.setDeleteUserId(id);

    }
    @Override
    public int getItemCount() {
        return data.size();
    }
}
