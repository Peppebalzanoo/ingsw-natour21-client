package com.example.natour2.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.natour2.R;
import com.example.natour2.adapter.ItinerarioAdapter;
import com.example.natour2.model.Itinerario;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends BaseFragment {


    private RecyclerView recyclerView;
    private ItinerarioAdapter itinerarioAdapter;
    private List<Itinerario> itinerarioList;
    /* ****************************************************************************************** */
    private FloatingActionButton buttonFiltersFloating;
    private Button buttonApply;
    private BottomSheetDialog dialog;
    /* ****************************************************************************************** */

    private Button btnSelectTimeFilter;
    private int hour, minute;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        recyclerView = view.findViewById(R.id.recycler_viewSearch);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        itinerarioList = new ArrayList<>();
        itinerarioAdapter = new ItinerarioAdapter(getContext(), itinerarioList, savedInstanceState, recyclerView);

        recyclerView.setAdapter(itinerarioAdapter);

        /* ****************************************************************************************** */

        buttonFiltersFloating = view.findViewById(R.id.buttonFiltersFloating);


        dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.bottom_dialog);
        dialog.setCanceledOnTouchOutside(false);

        buttonApply = dialog.findViewById(R.id.buttonApply);

        btnSelectTimeFilter = dialog.findViewById(R.id.btnSelectTimeFilter);

        btnSelectTimeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTimePicker(view);
            }
        });

        buttonFiltersFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        readItinerari();
        return view;
    }

    private void popTimePicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                btnSelectTimeFilter.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    private void readItinerari(){
        Itinerario itr1 = new Itinerario("sentiero", "01:14", "facile", "bello il sentiero", "antonio", "pippo");
        Itinerario itr2 = new Itinerario("sentiero2", "01:48", "difficile", "brutto il sentiero", "anto", "pippo2");
        itinerarioList.add(itr1);
        itinerarioList.add(itr2);

        itinerarioAdapter.notifyDataSetChanged();
    }


}