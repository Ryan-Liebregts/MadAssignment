package com.example.madassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserIconAdapter extends RecyclerView.Adapter<UserIconVH>{
    ArrayList<Integer> data;

    public UserIconAdapter(ArrayList<Integer> data){
        this.data = data;
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

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
