package com.example.natour2.fragment_admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.natour2.R;
import com.example.natour2.controller.ControllerAdminActivity;


public class SegnalazioneFragment extends Fragment {


    private Button buttonEliminaSegnalazione;
    private Button buttonRimuoviItinerario;
    private ImageView imageViewBack;

    private String fruit;
    private TextView textView;

    private ControllerAdminActivity ctrl = ControllerAdminActivity.getInstance();


    public SegnalazioneFragment() {
        // Required empty public constructor
    }

    public SegnalazioneFragment(String fruit){
        this.fruit = fruit;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_segnalazione, container, false);
        initComponentsView(view);
        setListeners();

        return view;
    }


    public void initComponentsView(View view){
        imageViewBack = view.findViewById(R.id.imageView_Back_NewsLetterFragment);
        buttonEliminaSegnalazione = view.findViewById(R.id.button_EliminaSegnalazione_SegnalazioneFragment);
        buttonRimuoviItinerario = view.findViewById(R.id.button_RimuoviItinerario_SegnalazioneFragment);
        textView = view.findViewById(R.id.textView_Itinerario_SegnalazioneFragment);

        textView.setText(fruit);
    }


    public void setListeners(){

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.showControlPanelFragment(requireActivity().getSupportFragmentManager());
            }
        });
        buttonRimuoviItinerario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Attenzione");
                builder.setMessage("Sei sicuro di voler eliminare l'itinerario?");
                builder.setPositiveButton("Conferma",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /* Codice per eliminare l'itinerario */
                            }
                        });
                builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        buttonEliminaSegnalazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Attenzione");
                builder.setMessage("Sei sicuro di voler eliminare la segnalazione?");
                builder.setPositiveButton("Conferma",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /* Codice per eliminare la segnalazione */
                            }
                        });
                builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}