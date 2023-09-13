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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectUserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<User> data;

    GameData gameData;

    RecyclerView recyclerView;

    UserData userModel;

    NavigationData navModel;

    Button continueButton;

    Button newCharButton;

    public SelectUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectUserFragment newInstance(String param1, String param2) {
        SelectUserFragment fragment = new SelectUserFragment();
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
        gameData = new ViewModelProvider(getActivity()).get(GameData.class);
        userModel = new ViewModelProvider(getActivity()).get(UserData.class);
        navModel = new ViewModelProvider(getActivity()).get(NavigationData.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_select_user, container, false);
        //define components
        recyclerView = view.findViewById(R.id.recycler_user);
        continueButton = view.findViewById(R.id.continue_button);
        newCharButton = view.findViewById(R.id.create_user_button);


        //set recycler
        //get data
        data = getLeaderBoardData();
        if (data.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            continueButton.setVisibility(View.GONE);
        }
        else {
            //define grid
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1,
                    GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            //set adapter
            UserSelectAdapter userSelectAdapter =new UserSelectAdapter(data, userModel, gameData, this);
            recyclerView.setAdapter(userSelectAdapter);
        }


        userModel.userId2.observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long integer) {
                if (userModel.getUserId2() != 0 && userModel.getUserId() != 0) {
                    continueButton.setEnabled(true);
                }
                else{
                    continueButton.setEnabled(false);
                }
            }
        });

        userModel.userId.observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long integer) {
                if(gameData.getGameMode() == 1) {
                    if (userModel.getUserId() != 0) {
                        continueButton.setEnabled(true);
                    }
                    else{
                        continueButton.setEnabled(false);
                    }
                }
                else {
                    if (userModel.getUserId2() != 0 && userModel.getUserId() != 0) {
                        continueButton.setEnabled(true);
                    }
                    else{
                        continueButton.setEnabled(false);
                    }
                }
            }
        });


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                navModel.setClickedValue(1);
            }
        });

        newCharButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                navModel.setClickedValue(3);
                navModel.setHistoricalClickedValue(6);
            }
        });

        return view;
    }

    public List<User> getLeaderBoardData() {
        UserDao userDao = initialiseDB();


        data = null;
        data = userDao.getAllUsers();
        System.out.println(data);
        return data;
    }
    public UserDao initialiseDB() {
        return UserDbInstance.getDatabase(getContext()).userDao();
    }

}