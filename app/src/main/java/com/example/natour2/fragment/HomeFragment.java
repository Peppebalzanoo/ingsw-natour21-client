package com.example.natour2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2.R;
import com.example.natour2.adapter.ItinerarioAdapter;
import com.example.natour2.controller.ControllerHomeActivity;
import com.example.natour2.controller.ControllerItinerary;
import com.example.natour2.utilities.PreferanceManager;

public class HomeFragment extends BaseFragment {

    private PreferanceManager preferanceManager;

    private RecyclerView recyclerView;
    private ItinerarioAdapter itinerarioAdapter;
    private ImageView addItinerario;
    private ImageView notification;
    private ProgressBar progessBar_SHomeFragment;

    private final ControllerHomeActivity ctrl = ControllerHomeActivity.getInstance();
    private final ControllerItinerary ctrlItinerary = ControllerItinerary.getInstance();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctrl.setActivity(getActivity());
        ctrl.setContext(getActivity().getApplicationContext());
        ctrl.setFragmentManager(getActivity().getSupportFragmentManager());

        ctrlItinerary.setActivity(getActivity());
        ctrlItinerary.setContext(getActivity().getApplicationContext());

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViewComponents(view);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        itinerarioAdapter = new ItinerarioAdapter(getActivity(), getContext(), savedInstanceState, recyclerView);
        recyclerView.setAdapter(itinerarioAdapter);


        readItinerari();
        return view;
    }

    private void readItinerari(){
        ctrlItinerary.getAllItineraries(itinerarioAdapter);
    }

    private void initViewComponents(View view){

        addItinerario = view.findViewById(R.id.addItinerario);
        notification = view.findViewById(R.id.notification);
        progessBar_SHomeFragment = view.findViewById(R.id.progessBar_SHomeFragment);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctrl.showNotificationFragment();
            }
        });
        addItinerario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctrl.showAddItinerarioFragment();
            }
        });
    }



    private void loading(Boolean isLoading){
        if(isLoading){
            progessBar_SHomeFragment.setVisibility(View.VISIBLE);
        }
        else{
            progessBar_SHomeFragment.setVisibility(View.INVISIBLE);
        }
    }

}