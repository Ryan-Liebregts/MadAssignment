package com.example.madassignment;
import java.util.*;

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
import android.widget.EditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<Integer> data;
    RecyclerView recyclerView;

    EditText userNameTextBox;

    Button saveUserButton;

    UserData userModel;

    String userName;

    int userIcon;

    NavigationData navModel;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        userModel = new ViewModelProvider(getActivity()).get(UserData.class);
        navModel = new ViewModelProvider(getActivity()).get(NavigationData.class);
        UserDao userDao = initialiseDB();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerView = view.findViewById(R.id.recycler_icon);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4,
            GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        data = getIconData();
        UserIconAdapter userIconAdapter =new UserIconAdapter(data, userModel, this);
        recyclerView.setAdapter(userIconAdapter);

        saveUserButton = view.findViewById(R.id.save_user_button);
        userNameTextBox = view.findViewById(R.id.user_text);
        userModel.userIcon.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                System.out.println("HELLO YOU SELECTED AN ICON");
                if (integer != 0 && !Objects.equals(String.valueOf(userNameTextBox.getText()), "")){
                    saveUserButton.setEnabled(true);
                }
            }
        });

        //TODO add query to check for duplicated usernames

        saveUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               userName = String.valueOf(userNameTextBox.getText());
               userIcon = userModel.getUserIcon();
               saveUser(userName, userIcon);
               navModel.setClickedValue(2);
               userModel.setUserIcon(0);
               userModel.setUserName("");
            }
        });


        return view;
    }


    public ArrayList<Integer> getIconData() {
        data = new ArrayList<Integer>();
        data.add(R.drawable.user_icon1);
        data.add(R.drawable.user_icon2);
        data.add(R.drawable.user_icon3);
        data.add(R.drawable.user_icon4);
        data.add(R.drawable.user_icon5);
        data.add(R.drawable.user_icon6);
        data.add(R.drawable.user_icon7);
        data.add(R.drawable.user_icon8);
        data.add(R.drawable.user_icon9);
        data.add(R.drawable.user_icon10);
        data.add(R.drawable.user_icon11);
        data.add(R.drawable.user_icon12);
        data.add(R.drawable.user_icon13);
        data.add(R.drawable.user_icon14);
        data.add(R.drawable.user_icon15);
        data.add(R.drawable.user_icon16);

        // TODO: Add more user icons when time

        return data;
    }

    public UserDao initialiseDB() {
        return UserDbInstance.getDatabase(getContext()).userDao();
    }


    public void saveUser(String name, int value) {
        UserDao userDao = initialiseDB();
        User user = new User();
        user.setUserName(name);
        user.setUserIcon(value);
        user.setUserLosses(0);
        user.setUserWins(0);
        userDao.insert(user);

    }

}