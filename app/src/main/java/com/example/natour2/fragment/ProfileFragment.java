package com.example.natour2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.natour2.R;
import com.example.natour2.adapter.ItinerarioAdapter;
import com.example.natour2.controller.ControllerHomeAcrtivity;
import com.example.natour2.model.Itinerario;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    private RecyclerView recyclerView;
    private ItinerarioAdapter itinerarioAdapter;
    private List<Itinerario> itinerarioList;

    private final ControllerHomeAcrtivity ctrl = new ControllerHomeAcrtivity();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        recyclerView = view.findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        itinerarioList = new ArrayList<>();
        itinerarioAdapter = new ItinerarioAdapter(getContext(), itinerarioList);
        recyclerView.setAdapter(itinerarioAdapter);

        readItinerari();
        return view;
    }

    private void readItinerari(){
        Itinerario itr1 = new Itinerario("sentiero", "01:14", "facile", "bello il sentiero", "antonio", "pippo");
        Itinerario itr2 = new Itinerario("sentiero2", "01:48", "difficile", "brutto il sentiero", "anto", "pippo2");
        itinerarioList.add(itr1);
        itinerarioList.add(itr2);

        itinerarioAdapter.notifyDataSetChanged();
    }

}