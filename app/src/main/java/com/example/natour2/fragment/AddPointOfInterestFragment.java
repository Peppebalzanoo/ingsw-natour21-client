package com.example.natour2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.natour2.R;
import com.example.natour2.controller.ControllerHomeActivity;
import com.example.natour2.controller.ControllerPointOfInterest;
import com.example.natour2.model.Itinerary;

import java.util.ArrayList;

public class AddPointOfInterestFragment extends Fragment {

    private Spinner spinner;
    private EditText editTextNumberDecimalLatitude;
    private EditText editTextNumberDecimalLongitude;
    private Button buttonPublishPointOfInterest;
    private Button buttonHomePointOfInterest;
    private Itinerary itr;

    private ControllerHomeActivity ctrl = ControllerHomeActivity.getInstance();
    private ControllerPointOfInterest ctrlPOI = ControllerPointOfInterest.getInstance();


    public AddPointOfInterestFragment() {
        // Required empty public constructor
    }

    public AddPointOfInterestFragment(Itinerary itr) {
        this.itr = itr;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctrl.setActivity(getActivity());
        ctrl.setContext(getActivity().getApplicationContext());
        ctrl.setFragmentManager(getActivity().getSupportFragmentManager());

        ctrlPOI.setActivity(getActivity());
        ctrlPOI.setContext(getActivity().getApplicationContext());
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


        ArrayList<String> list = new ArrayList<String>();
        list.add("SORGENTE");
        list.add("BAITA");
        ArrayAdapter adapter= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        editTextNumberDecimalLatitude = view.findViewById(R.id.editTextNumberDecimalLatitude);
        editTextNumberDecimalLongitude = view.findViewById(R.id.editTextNumberDecimalLongitude);
        buttonPublishPointOfInterest = view.findViewById(R.id.buttonPublishPointOfInterest);
        buttonHomePointOfInterest = view.findViewById(R.id.buttonHomePointOfInterest);

        buttonPublishPointOfInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishPointOfInterest();
                ctrl.showHomeFragment();
            }
        });

        buttonHomePointOfInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctrl.showHomeFragment();
            }
        });
    }


    private void publishPointOfInterest(){

        String type = getType();
        Double coordY = getLatitudine();
        Double coordX = getLongitudice();


        if(type == null || coordX == null || coordY == null){
            return;
        }
        ctrlPOI.uploadPointOfInterest(itr, type,coordY, coordX );

    }

    private Double getLatitudine() {
        String s = editTextNumberDecimalLatitude.getText().toString();
        if(s == null){
            return null;
        }
        return Double.parseDouble(editTextNumberDecimalLatitude.getText().toString());
    }

    private Double getLongitudice() {
        String s = editTextNumberDecimalLongitude.getText().toString();
        if(s == null){
            return null;
        }
        return Double.parseDouble(editTextNumberDecimalLongitude.getText().toString());
    }

    private String getType() {
        String type = spinner.getSelectedItem().toString();
        if(type == null || type.equals("")){
            return null;
        }
        return type;
    }

}