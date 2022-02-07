package com.example.natour2.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.natour2.R;
import com.example.natour2.adapter.ItinerarioAdapter;
import com.example.natour2.controller.ControllerHomeActivity;
import com.example.natour2.controller.ControllerItinerary;
import com.example.natour2.model.Itinerary;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends BaseFragment {


    private RecyclerView recyclerView;
    private ItinerarioAdapter itinerarioAdapter;

    /* ****************************************************************************************** */
    private FloatingActionButton buttonFiltersFloating;
    private Button buttonApply;
    private BottomSheetDialog dialog;
    /* ****************************************************************************************** */
    private ChipGroup chipGroup_Difficoltà_BottomDialog;
    private CheckBox checkBox_Accessibilità_BottomDialog;
    private EditText editTextSearch;
    private ImageView searchItineraryIcon;
    private Button buttonAnnulla_BottomDialog;
    private Button btnSelectTimeFilter;
    private int hour, minute;

    ControllerHomeActivity ctrl = ControllerHomeActivity.getInstance();
    ControllerItinerary ctrlItinerary = ControllerItinerary.getInstance();

    public SearchFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        initViewComponents(view);

        itinerarioAdapter = new ItinerarioAdapter(getActivity(), getContext(), savedInstanceState, recyclerView);
        recyclerView.setAdapter(itinerarioAdapter);

        getAllItineraries();
        return view;
    }



    private void initViewComponents(View view){
        //RecyclerView settings
        recyclerView = view.findViewById(R.id.recycler_viewSearch);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        editTextSearch = view.findViewById(R.id.editTextSearch);
        searchItineraryIcon = view.findViewById(R.id.searchItineraryIcon);

        searchItineraryIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ù§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§ Sto in icon");
                searchItineraryByFilters();
            }
        });

        //Show Filter Dialog
        buttonFiltersFloating = view.findViewById(R.id.buttonFiltersFloating);
        buttonFiltersFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("****","************************************************************ HO PREMUTO");
                dialog.show();
            }
        });


        //Filter Dialog
        dialog = new BottomSheetDialog(requireContext());
        dialog.setContentView(R.layout.bottom_dialog);
        dialog.setCanceledOnTouchOutside(false);
        buttonApply = dialog.findViewById(R.id.buttonApplica_BottomDialog);
        btnSelectTimeFilter = dialog.findViewById(R.id.button_Durata_BottomDialog);
        chipGroup_Difficoltà_BottomDialog = dialog.findViewById(R.id.chipGroup_Difficoltà_BottomDialog);
        checkBox_Accessibilità_BottomDialog = dialog.findViewById(R.id.checkBox_Accessibilità_BottomDialog);
        buttonAnnulla_BottomDialog = dialog.findViewById(R.id.buttonAnnulla_BottomDialog);

        btnSelectTimeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTimePicker(view);
            }
        });

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchItineraryByFilters();
                dialog.dismiss();
            }
        });

        buttonAnnulla_BottomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }


    private void getAllItineraries(){
/*
        List<Itinerary> list = ctrlItinerary.getAllItinerariesByFilters(null, null, null, null, null, null, null, null, null);
        if(list == null){
            return;
        }
        itineraryList.addAll(list);
        itinerarioAdapter.notifyDataSetChanged();

 */
        ctrlItinerary.getAllItinerariesByFilters1(this, itinerarioAdapter,null, null, null, null, null, null, null, null, null);
    }


    private void searchItineraryByFilters(){
        String name = getNameFilter();
        Integer duration = getDurataFilter();
        Boolean disabledAcces =  getDisabledAccesFilter();
        try {
            Integer difficulty = getDifficultyFilter(chipGroup_Difficoltà_BottomDialog.getChildCount());
            getItinerariesByFilters(ctrlItinerary, name, duration, difficulty, disabledAcces);
        } catch (IllegalArgumentException e) {
            ctrl.printToast("Oops! Qualcosa è andato storto");
        }
    }

    public void getItinerariesByFilters(ControllerItinerary ctrlItinerary, String name, Integer duration, Integer difficulty, Boolean disabledAcces) throws IllegalArgumentException{

        ctrlItinerary.getAllItinerariesByFilters1(this, itinerarioAdapter, name, null, duration, null, difficulty, null, null, disabledAcces, null);

    }

    public void noResult(){
        ctrl.printToast("Nessun risultato per questi filtri.");
    }

    private String getNameFilter(){
        String name = editTextSearch.getText().toString();
        if(name == null || name.equals("")){
            return null;
        }
        return name;
    }

    private Integer getDurataFilter(){
        Integer durata = (hour*60)+minute;
        if(durata == 0){
            durata = null;
        }
        return durata;
    }

    public Integer getDifficultyFilter(Integer chipsCount) throws IllegalArgumentException{

        if(chipsCount == null || chipsCount <= 0 ){
            throw new IllegalArgumentException();
        }
        int i = 0;
        while (i < chipsCount) {
            if (verifyChipValidity(i)) {
                break;
            }
            i++;
        }
        i = i+1;
        if(i<=0 || i>=4){
            return null;
        }
        return i;
    }

    public boolean verifyChipValidity(int i){
        Chip chip = (Chip) chipGroup_Difficoltà_BottomDialog.getChildAt(i);
        if (chip.isChecked()) {
            return true;
        }
        return false;
    }

    private Boolean getDisabledAccesFilter(){
        if(checkBox_Accessibilità_BottomDialog.isChecked()){
            return true;
        }
        return null;
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


}