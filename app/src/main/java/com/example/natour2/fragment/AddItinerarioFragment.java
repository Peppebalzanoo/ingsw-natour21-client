package com.example.natour2.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.natour2.R;
import com.example.natour2.controller.ControllerHomeActivity;
import com.example.natour2.controller.ControllerItinerary;
import com.example.natour2.model.Itinerary;
import com.example.natour2.utilities.MapViewCustom;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Objects;

public class AddItinerarioFragment extends Fragment {

    private Button btnSfoglia;
    private Button btnSelectTime;
    private MapViewCustom mapView;
    private EditText txtNameItinerario;
    private ChipGroup chipGroupDiffAddItinerario;
    private EditText editTextTextMultiLineDescrizione;
    private Button btnAnnulla;
    private Button btnPubblica;
    private TextView textViewTime;
    private TextView textViewSelectedFile;
    private String[] items = {"Facile", "Normale", "Difficile"};
    private int hour, minute;

    private String readedTexFromUri = null;
    private ControllerHomeActivity ctrl = ControllerHomeActivity.getInstance();
    private final ControllerItinerary ctrlItinerary = ControllerItinerary.getInstance();

    public AddItinerarioFragment() {
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

        View view = inflater.inflate(R.layout.fragment_add_itinerario, container, false);
        mapView = view.findViewById(R.id.mapView2);
        mapView.onCreate(savedInstanceState);

        initViewComponents(view);

        return view;
    }

    private void initViewComponents(View view){

        btnSfoglia = view.findViewById(R.id.btnSfoglia);
        btnSelectTime = view.findViewById(R.id.btnSelectTime);
        textViewSelectedFile = view.findViewById(R.id.textView_File_AddItinerarioFragment);
        txtNameItinerario = view.findViewById(R.id.txtNameItinerario);
        textViewTime = view.findViewById(R.id.textView_Time_AddItinerarioFragment);
        editTextTextMultiLineDescrizione = view.findViewById(R.id.editTextTextMultiLineDescrizione);
        btnAnnulla = view.findViewById(R.id.btnAnnulla);
        btnPubblica = view.findViewById(R.id.btnPubblica);
        chipGroupDiffAddItinerario = view.findViewById(R.id.chipGroupDiffAddItinerario);


        btnSfoglia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, 10);

            }
        });

        btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTimePicker(view);
            }
        });

        btnAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctrl.showHomeFragment();
            }
        });

        btnPubblica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishItinerary();
            }
        });
    }


    private void publishItinerary(){
        String name = getNameFilter();
        Integer duration = getDurataFilter();
        Integer difficulty = getDifficultyFilter();
        //Boolean disabledAcces =  getDisabledAccesFilter();
        String description = getDescriptionFilter();

        if(name == null || name.equals("") || duration == null || difficulty == null || readedTexFromUri == null || readedTexFromUri.equals("")){
            ctrl.printToast("Errore! Inserisci campi obbligatori");
            return;
        }
        Itinerary itinerary  = new Itinerary(name, duration, difficulty, description, readedTexFromUri, true);
        ctrlItinerary.uploadItinerary(itinerary);
        ctrl.printToast("Itinerario pubblicato correttamente.");
        ctrl.showHomeFragment();
    }


    private String getNameFilter(){
        String name = txtNameItinerario.getText().toString();
        if(name == null || name.equals("")){
            return null;
        }
        return name;
    }

    private Integer getDurataFilter(){
        Integer durata = (hour*60)+minute;
        if(durata == 0){
            return null;
        }
        return durata;
    }

    private Integer getDifficultyFilter(){
        int chipsCount =  chipGroupDiffAddItinerario.getChildCount();
        int i = 0;
        while (i < chipsCount) {
            Chip chip = (Chip)  chipGroupDiffAddItinerario.getChildAt(i);
            if (chip.isChecked() ) {
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
/*
    private Boolean getDisabledAccesFilter(){
        if(checkBox_Accessibilità_BottomDialog.isChecked()){
            return true;
        }
        return null;
    }
    */

    private String getDescriptionFilter(){
        String description = editTextTextMultiLineDescrizione.getText().toString();
        if(description == null || description.equals("")){
            return null;
        }
        return description;
    }


    private void popTimePicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                //btnSelectTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                textViewTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {

                    Uri uri = data.getData();
                    textViewSelectedFile.setText(uri.toString());
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {

                            try {
                                readedTexFromUri = readTextFromUri(uri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            InputStream inputStream = convertStringToInputStream(readedTexFromUri);
                            mapView.getMap(googleMap, inputStream);
                            mapView.onResume();
                            mapView.onEnterAmbient(null);
                        }
                    });
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private String readTextFromUri(Uri uri) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream =
                     getActivity().getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    private InputStream convertStringToInputStream(String string) {
        InputStream inputStream = new ByteArrayInputStream(string.getBytes());
        return inputStream;

    }

}