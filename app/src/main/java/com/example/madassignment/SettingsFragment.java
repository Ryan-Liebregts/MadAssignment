package com.example.madassignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Define Buttons & Spinners
    private Button profileButton;
    private Spinner boardSizeSpinner;
    private Spinner winConditionSpinner;
    private Button customizeButton;

    // Define Navigation Model
    private NavigationData navModel;

    // Define the list arrays for the spinners
    String[] boardSizes = {"3 x 3", "4 x 4", "5 x 5"};
    String[] winConditions = {"3", "4", "5"};

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        navModel = new ViewModelProvider(getActivity()).get(NavigationData.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        // Define Buttons
        profileButton = view.findViewById(R.id.profileButton);
        boardSizeSpinner = view.findViewById(R.id.boardSizeSpinner);
        winConditionSpinner = view.findViewById(R.id.winConditionSpinner);
        customizeButton = view.findViewById(R.id.customizeButton);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navModel.setClickedValue(3);
            }
        });

        customizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navModel.setClickedValue(4);
            }
        });

        // Create an ArrayAdapter to populate the boardSizeSpinner
        ArrayAdapter<String> boardSizeAdapter = new ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                boardSizes
        );

        // Set the dropdown layout style
        boardSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter for the Spinner
        boardSizeSpinner.setAdapter(boardSizeAdapter);

//        This code handles the logic of what to do when a particular item is selected in the spinner
//        boardSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                // Handle the selected item here
//                String selectedCity = boardSizes[position];
//                // Do ...
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // Handle case where nothing is selected (optional)
//            }
//        });

        // Create an ArrayAdapter to populate the boardSizeSpinner
        ArrayAdapter<String> winConditionAdapter = new ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                winConditions
        );

        // Set the dropdown layout style
        winConditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter for the Spinner
        winConditionSpinner.setAdapter(winConditionAdapter);

//        This code handles the logic of what to do when a particular item is selected in the spinner
//        winConditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                // Handle the selected item here
//                String selectedCity = winConditions[position];
//                // Do ...
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // Handle case where nothing is selected (optional)
//            }
//        });

        return view;
    }
}