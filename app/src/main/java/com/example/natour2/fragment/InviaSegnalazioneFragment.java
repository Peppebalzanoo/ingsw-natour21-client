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

import com.example.natour2.R;
import com.example.natour2.controller.ControllerHomeActivity;

public class InviaSegnalazioneFragment extends Fragment {

    private EditText editTextTitolo;
    private EditText editTextMotivazione;
    private Button buttonInvia;
    private Button buttonAzzera;
    private ImageView imageViewBack;

    private ControllerHomeActivity ctrl = ControllerHomeActivity.getInstance();



    public InviaSegnalazioneFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_invia_segnalazione, container, false);
        initComponentsView(view);
        setListeners();
        return view;
    }


    public void initComponentsView(View view){
        editTextTitolo = view.findViewById(R.id.editText_TitoloSegnalazione_InviaSegnalazioneFragment);
        editTextMotivazione = view.findViewById(R.id.editText_Motivazione_InviaSegnalazioneFragment);
        buttonAzzera = view.findViewById(R.id.buttonaAzzera_InviaSegnalazioneFragment);
        buttonInvia = view.findViewById(R.id.buttonInvia_InviaSegnalazioneFragment);
        imageViewBack = view.findViewById(R.id.imageBack_InviaSegnalazioneFragment);
    }

    public void setListeners(){

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.showHomeFragment();
            }
        });

       buttonInvia.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
               builder.setCancelable(true);
               builder.setTitle("Attenzione");
               builder.setMessage("Sei sicuro di voler inviare la segnalazione?");
               builder.setPositiveButton("Conferma",
                       new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               String titolo = editTextTitolo.getText().toString();
                               String motivazione = editTextMotivazione.getText().toString();
                               /* Codice per inviare la segnalazione */
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
        editTextTitolo.setText("");
        editTextMotivazione.setText("");
    }

}