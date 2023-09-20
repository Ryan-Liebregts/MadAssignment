package com.example.madassignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;
import java.util.Objects;

public class UsersFragment extends Fragment {

    /* -----------------------------------------------------------------------------------------
            Function: Initialise View models + Elements
            Author: Parakram
            Description: TODO
     ---------------------------------------------------------------------------------------- */
    private List<User> data;
    private EditUser editUserModel;
    private UserData userDataModel;
    private Button createNewUser;
    private NavigationData navModel;
    private RecyclerView recyclerView;

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navModel = new ViewModelProvider(getActivity()).get(NavigationData.class);
        editUserModel = new ViewModelProvider(getActivity()).get(EditUser.class);
        userDataModel = new ViewModelProvider(getActivity()).get(UserData.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_users, container, false);

        /* -----------------------------------------------------------------------------------------
            Function: Initialise Recycler Grid elements
            Author: Parakram
            Description: TODO
         ---------------------------------------------------------------------------------------- */
        recyclerView = view.findViewById(R.id.users_recycler);
        createNewUser = view.findViewById(R.id.create_new_user_button);

        //set grid for recycler
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1,
                GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        data = getUsers();
        System.out.println("The size is" + data.size());
        //set adapter
        EditDeleteUserAdapter editDeleteUserAdapter =new EditDeleteUserAdapter(data, navModel, editUserModel, userDataModel);
        recyclerView.setAdapter(editDeleteUserAdapter);

        /* -----------------------------------------------------------------------------------------
            Function: Create User Click Listener
            Author: Parakram
            Description: TODO
         ---------------------------------------------------------------------------------------- */
        createNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navModel.setClickedValue(3);
                navModel.setHistoricalClickedValue(6);
            }
        });

        /* -----------------------------------------------------------------------------------------
            Function: Delete User Observer
            Author: Parakram
            Description: TODO
         ---------------------------------------------------------------------------------------- */
        editUserModel.deleteUserId.observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long integer) {
                if (integer != 0) {
                    //do remove logic
                        UserDao userDao = initialiseDB();
                        System.out.println("being deleted id " + integer);
                        userDao.deleteUser(integer);
                        System.out.println("The data being removed is at index at users" + editUserModel.getDeleteUserPosition());
                        data.remove(editUserModel.getDeleteUserPosition());
                        editDeleteUserAdapter.notifyItemRemoved(editUserModel.getDeleteUserPosition());
                        //restore state
                        editUserModel.setDeleteUserPosition(0);
                        editUserModel.setDeleteUserId(0L);
                }
            }
        });

        return view;
    }

    public List<User> getUsers() {
        UserDao userDao = initialiseDB();
        data = null;
        data = userDao.getAllUsers();
        return data;
    }
    public UserDao initialiseDB() {
        return UserDbInstance.getDatabase(getContext()).userDao();
    }
}