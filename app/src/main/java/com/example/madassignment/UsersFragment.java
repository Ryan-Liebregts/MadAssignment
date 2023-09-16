package com.example.madassignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<User> data;

    EditUser editUserModel;

    NavigationData navModel;

    RecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UsersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UsersFragment newInstance(String param1, String param2) {
        UsersFragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navModel = new ViewModelProvider(getActivity()).get(NavigationData.class);
        editUserModel = new ViewModelProvider(getActivity()).get(EditUser.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView = view.findViewById(R.id.users_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1,
                GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        data = getLeaderBoardData();
        EditDeleteUserAdapter editDeleteUserAdapter =new EditDeleteUserAdapter(data, navModel, editUserModel);
        recyclerView.setAdapter(editDeleteUserAdapter);
        return view;
    }


    public List<User> getLeaderBoardData() {
        UserDao userDao = initialiseDB();

        //Test data will remove when we have functionality to add users
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