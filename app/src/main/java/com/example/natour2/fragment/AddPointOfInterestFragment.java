package com.example.natour2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.natour2.R;
import com.example.natour2.controller.ControllerHomeActivity;

public class AddPointOfInterestFragment extends Fragment {

    private Spinner spinner;
    private EditText editTextNumberDecimalLatitude;
    private EditText editTextNumberDecimalLongitude;
    private Button buttonPublishPointOfInterest;
    private Button buttonHomePointOfInterest;

    private ControllerHomeActivity ctrl = ControllerHomeActivity.getInstance();


    public AddPointOfInterestFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctrl.setActivity(getActivity());
        ctrl.setContext(getActivity().getApplicationContext());
        ctrl.setFragmentManager(getActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_point_of_interest, container, false);

        initViewComponents(view);

        return view;
    }

    private void initViewComponents(View view){
        spinner = view.findViewById(R.id.planets_spinner);
        editTextNumberDecimalLatitude = view.findViewById(R.id.editTextNumberDecimalLatitude);
        editTextNumberDecimalLongitude = view.findViewById(R.id.editTextNumberDecimalLongitude);
        buttonPublishPointOfInterest = view.findViewById(R.id.buttonPublishPointOfInterest);
        buttonHomePointOfInterest = view.findViewById(R.id.buttonHomePointOfInterest);

        buttonPublishPointOfInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonHomePointOfInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctrl.showHomeFragment();
            }
        });
    }


}