package com.example.madassignment;

import static android.app.PendingIntent.getActivity;

import static java.security.AccessController.getContext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserIconAdapter extends RecyclerView.Adapter<UserIconVH>{
    private ArrayList<Integer> data;

    private CreateUser userModel;

    private EditUser editUserModel;

    private LifecycleOwner test;

    public UserIconAdapter(ArrayList<Integer> data, CreateUser userData, LifecycleOwner Test, EditUser editUserModel){
        this.data = data;
        this.userModel = userData;
        this.test = Test;
        this.editUserModel = editUserModel;
    }
    @NonNull
    @Override
    public UserIconVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_icon_list_item,parent,false);
        return new UserIconVH(view, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull UserIconVH holder, int position) {
        boolean isEdit = editUserModel.getUserId() != 0;
        int singleRow = data.get(position);
        holder.userIcon.setImageResource(singleRow);
        holder.userIconSelected.setImageResource(singleRow);


        holder.userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit){
                    editUserModel.setUserIcon(singleRow);
                }
                else {
                    userModel.setUserIcon(singleRow);
                }
            }
        });

        userModel.userIcon.observe(test, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer !=singleRow){
                    holder.userIcon.setVisibility(View.VISIBLE);
                    holder.userIconSelected.setVisibility(View.GONE);
                }
                else {
                    holder.userIcon.setVisibility(View.GONE);
                    holder.userIconSelected.setVisibility(View.VISIBLE);
                }
            }
        });

        editUserModel.userIcon.observe(test, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer !=singleRow){
                    holder.userIcon.setVisibility(View.VISIBLE);
                    holder.userIconSelected.setVisibility(View.GONE);
                }
                else {
                    holder.userIcon.setVisibility(View.GONE);
                    holder.userIconSelected.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
