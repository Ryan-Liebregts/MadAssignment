package com.example.madassignment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderBoardFragment extends Fragment {

    private List<User> data;
    private RecyclerView recyclerView;
    public LeaderBoardFragment() {
        // Required empty public constructor
    }


    public static LeaderBoardFragment newInstance(String param1, String param2) {
        LeaderBoardFragment fragment = new LeaderBoardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_leader_board, container, false);

        /* -----------------------------------------------------------------------------------------
                Function: Initialise Recyclerview
                Author: Parakram
         ---------------------------------------------------------------------------------------- */
        recyclerView = view.findViewById(R.id.recView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1,
                GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        data = getLeaderBoardData();
        LeaderBoardDataAdapter leaderBoardDataAdapter =new LeaderBoardDataAdapter(data);
        recyclerView.setAdapter(leaderBoardDataAdapter);
        return view;
    }

        /* -----------------------------------------------------------------------------------------
                Function: getLeaderBoardData()
                Author: Parakram
         ---------------------------------------------------------------------------------------- */
    public List<User> getLeaderBoardData() {
        UserDao userDao = initialiseDB();

        // TODO: Remove if unnecessary
        User ryan = new User();
        ryan.setUserName("Ryan");
        ryan.setUserIcon(R.drawable.user_icon1);
        ryan.setUserLosses(0);
        ryan.setUserWins(10);

        User PK = new User();
        PK.setUserName("PK");
        PK.setUserIcon(R.drawable.user_icon16);
        PK.setUserLosses(4);
        PK.setUserWins(8);
//        userDao.insert(ryan, PK);
        //end of test data

        data = null;
        data = userDao.getAllUsers();
        return data;
    }
    public UserDao initialiseDB() {
        return UserDbInstance.getDatabase(getContext()).userDao();
    }

}