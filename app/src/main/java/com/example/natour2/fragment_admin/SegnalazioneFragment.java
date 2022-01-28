package com.example.natour2.fragment_admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.natour2.R;
import com.example.natour2.controller.ControllerAdminActivity;
import com.example.natour2.controller.ControllerItinerary;
import com.example.natour2.model.Report;


public class SegnalazioneFragment extends Fragment {

    private TextView textTitle;
    private TextView textItineraryName;
    private TextView textReporterName;
    private TextView textUserName;
    private TextView textReasonDescription;
    private TextView textReplayReport;
    private Button buttonEliminaSegnalazione;
    private Button buttonRimuoviItinerario;
    private ImageView imageViewBack;
    private Report report;


    private ControllerAdminActivity ctrl = ControllerAdminActivity.getInstance();
    private ControllerItinerary ctrlItinerary = ControllerItinerary.getInstance();


    public SegnalazioneFragment() {
        // Required empty public constructor
    }

    public SegnalazioneFragment(Report report){
        this.report = report;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctrl.setActivity(requireActivity());
        ctrl.setContext(requireContext());
        ctrl.setFragmentManager(getActivity().getSupportFragmentManager());

        ctrlItinerary.setActivity(requireActivity());
        ctrlItinerary.setContext(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_segnalazione, container, false);
        initComponentsView(view);
        loadData();
        setListeners();

        return view;
    }


    public void initComponentsView(View view){
        imageViewBack = view.findViewById(R.id.imageView_Back_NewsLetterFragment);
        buttonEliminaSegnalazione = view.findViewById(R.id.button_EliminaSegnalazione_SegnalazioneFragment);
        buttonRimuoviItinerario = view.findViewById(R.id.button_RimuoviItinerario_SegnalazioneFragment);

        textTitle = view.findViewById(R.id.textView_Titolo_ShowSegnalazioneFragment);
        textItineraryName = view.findViewById(R.id.textView_Itinerario_SegnalazioneFragment);
        textReporterName = view.findViewById(R.id.textView_Segnalatore_SegnalazioneFragment);
        textUserName = view.findViewById(R.id.textView_Utente_SegnalazioneFragment);
        textReasonDescription = view.findViewById(R.id.textView_Motivazione_SegnalazioneFragment);
        textReplayReport = view.findViewById(R.id.textView_Risposta_SegnalazioneFragment);

    }


    private void loadData(){
        textTitle.setText(report.getReasonTitle());
        textItineraryName.setText(report.getItinerary().getName());
        textReporterName.setText(report.getReporter().getUsername());
        textUserName.setText(report.getItinerary().getAuthor().getUsername());
        textReasonDescription.setText(report.getReasonDescription());
        textReplayReport.setText(report.getProvidedExplanation());


    }

    public void setListeners(){

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.showControlPanelFragment();
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
                                ctrlItinerary.deleteItinerary(report.getItinerary().getId());
                                ctrl.showControlPanelFragment();
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