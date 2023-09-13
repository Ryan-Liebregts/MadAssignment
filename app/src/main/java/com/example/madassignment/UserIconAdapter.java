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
    ArrayList<Integer> data;

    CreateUser userModel;

    LifecycleOwner test;


    public UserIconAdapter(ArrayList<Integer> data, CreateUser userData, LifecycleOwner Test){
        this.data = data;
        this.userModel = userData;
        this.test = Test;
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
        int singleRow = data.get(position);
        holder.userIcon.setImageResource(singleRow);
        holder.userIconSelected.setImageResource(singleRow);

        holder.userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userModel.setUserIcon(singleRow);
                holder.userIcon.setVisibility(View.GONE);
                holder.userIconSelected.setVisibility(View.VISIBLE);
            }
        });

        userModel.userIcon.observe(test, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer !=singleRow){
                    holder.userIcon.setVisibility(View.VISIBLE);
                    holder.userIconSelected.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
