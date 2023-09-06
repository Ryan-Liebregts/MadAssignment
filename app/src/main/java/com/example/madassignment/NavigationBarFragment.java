package com.example.madassignment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavigationBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationBarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    NavigationData navigationData;

    public NavigationBarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavigationBarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NavigationBarFragment newInstance(String param1, String param2) {
        NavigationBarFragment fragment = new NavigationBarFragment();
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
        navigationData = new ViewModelProvider(getActivity()).get(NavigationData.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_navigation_bar, container, false);
        NavigationData navigationData = new ViewModelProvider(getActivity()).get(NavigationData.class);
        ImageButton backButton = view.findViewById(R.id.backButton);
        ImageButton settingsButton = view.findViewById(R.id.settingsButton);
        ImageButton leaderBoardButton = view.findViewById(R.id.leaderBoardButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationData.setClickedValue(2);

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (navigationData.getClickedValue() == 4 || navigationData.getClickedValue() == 3) {
                    navigationData.setClickedValue(2);
                }
                else if (navigationData.getClickedValue() == 2){
                    navigationData.setClickedValue(1);
                }
            }
        });

        leaderBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationData.setClickedValue(5);
            }
        });

        navigationData.clickedValue.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1) { // This is the board
                    settingsButton.setVisibility(View.VISIBLE);
                    leaderBoardButton.setVisibility(View.VISIBLE);
                    backButton.setVisibility(View.GONE);
                }
                if (integer == 2) { //this is the settings page
                    settingsButton.setVisibility(View.GONE);
                    leaderBoardButton.setVisibility(View.GONE);
                    backButton.setVisibility(View.VISIBLE);

                }
                if (integer == 3) { // this is the profile page
                    settingsButton.setVisibility(View.GONE);
                    leaderBoardButton.setVisibility(View.GONE);
                    backButton.setVisibility(View.VISIBLE);
                }
                if (integer == 4) { // this is the customise profile page
                    settingsButton.setVisibility(View.GONE);
                    leaderBoardButton.setVisibility(View.GONE);
                    backButton.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }
}