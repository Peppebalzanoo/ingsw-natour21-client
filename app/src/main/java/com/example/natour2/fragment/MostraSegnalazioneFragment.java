package com.example.natour2.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.natour2.R;
import com.example.natour2.controller.ControllerHomeActivity;
import com.example.natour2.model.Report;

public class MostraSegnalazioneFragment extends Fragment {

    private ImageView imageBack;
    private Button buttonInvia;
    private Button buttonAzzera;
    private Report report;
    private TextView textViewTitolo;
    private TextView textViewItinerario;
    private TextView textViewSegnalatore;
    private TextView textViewMotivazione;
    private EditText editTextRisposta;
    private ControllerHomeActivity ctrl = ControllerHomeActivity.getInstance();



    public MostraSegnalazioneFragment() {
        // Required empty public constructor
    }

    public MostraSegnalazioneFragment(Report report){
        this.report = report;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctrl.setActivity(requireActivity());
        ctrl.setContext(requireContext());
        ctrl.setFragmentManager(requireFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_segnalazione, container, false);
        initViewComponent(view);
        setListeners();
        return view;
    }



    public void initViewComponent(View view){
        imageBack = view.findViewById(R.id.imageBack_ShowSegnalazioneFragment);
        textViewTitolo = view.findViewById(R.id.textView_Titolo_ShowSegnalazioneFragment);
        textViewItinerario = view.findViewById(R.id.textView_Itinerario_ShowSegnalazioneFragment);
        textViewSegnalatore = view.findViewById(R.id.textView_Segnalatore_ShowSegnalazioneFragment);
        textViewMotivazione = view.findViewById(R.id.textView_Motivazione_ShowSegnalazioneFragment);
        editTextRisposta = view.findViewById(R.id.editText_Risposta_ShowSegnalazioneFragment);
        buttonInvia = view.findViewById(R.id.button_Invia_ShowSegnalazioneFragment);
        buttonAzzera = view.findViewById(R.id.button_Azzera_ShowSegnalazioneFragment);

        textViewTitolo.setText(report.getReasonTitle());
        textViewItinerario.setText("Nome Itinerario");
        textViewSegnalatore.setText(report.getReporter().getUsername());
        textViewMotivazione.setText(report.getReasonDescription());

    }

    public void setListeners(){
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.showNotificationFragment();
            }
        });
        buttonInvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Attenzione");
                builder.setMessage("Sei sicuro di voler inviare la risposta?");
                builder.setPositiveButton("Conferma",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /* Codice per inviare la risposta */
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
        buttonAzzera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Attenzione");
                builder.setMessage("Sei sicuro di voler azzerare tutti i campi?");
                builder.setPositiveButton("Conferma",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clearAll();
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

    public void clearAll(){
        editTextRisposta.setText("");
    }
}