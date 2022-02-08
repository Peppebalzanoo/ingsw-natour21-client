package com.example.natour2.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.natour2.R;
import com.example.natour2.controller.ControllerHomeActivity;
import com.example.natour2.controller.ControllerReport;
import com.example.natour2.controller.ControllerUser;
import com.example.natour2.model.Itinerary;
import com.example.natour2.model.User;
import com.example.natour2.network.ApiClient;
import com.example.natour2.network.ApiService;
import com.example.natour2.utilities.Constants;
import com.example.natour2.utilities.PreferanceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InviaSegnalazioneFragment extends Fragment {

    private EditText editTextTitolo;
    private EditText editTextMotivazione;
    private Button buttonInvia;
    private Button buttonAzzera;
    private ImageView imageViewBack;
    private Itinerary itinerary;

    private PreferanceManager preferanceManager;
    private ControllerHomeActivity ctrl = ControllerHomeActivity.getInstance();
    private final ControllerReport ctrlReport = ControllerReport.getInstance();



    public InviaSegnalazioneFragment() {
        // Required empty public constructor
    }

    public InviaSegnalazioneFragment(Itinerary itinerary) {
        this.itinerary = itinerary;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctrl.setActivity(requireActivity());
        ctrl.setContext(requireContext());
        ctrl.setFragmentManager(requireFragmentManager());

        ctrlReport.setActivity(requireActivity());
        ctrlReport.setContext(requireContext());

        preferanceManager = new PreferanceManager(getActivity().getApplicationContext());
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
               String titolo = editTextTitolo.getText().toString();
               String motivazione = editTextMotivazione.getText().toString();

               if(isAviableReport(titolo, motivazione)){
                   AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                   builder.setCancelable(true);
                   builder.setTitle("Attenzione");
                   builder.setMessage("Sei sicuro di voler inviare la segnalazione?");
                   builder.setPositiveButton("Conferma",
                           new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   /* Codice per inviare la segnalazione */
                                    ctrlReport.setReport(itinerary, titolo, motivazione);

                                    setDataForNotification();

                                    ctrl.printToast("Segnalazione inviata con successo.");
                                    ctrl.showHomeFragment();
                               }
                           });
                   builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                       }
                   });
                   AlertDialog dialog = builder.create();
                   dialog.show();
               } else {
                   ctrl.printToast("Inserire tutti i campi obbligatori.");
               }


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

    private boolean isAviableReport(String titolo, String motivazione){
        if(titolo == null || titolo.equals("") || motivazione == null || motivazione.equals("")){
            return false;
        }
        return true;
    }

    public void clearAll(){
        editTextTitolo.setText("");
        editTextMotivazione.setText("");
    }

    private void sendNotificaiton(String messageBody){
        ApiClient.getInstance().create(ApiService.class).sendMessage(
                Constants.getInstanceRemoteMsgHeaders(),
                messageBody
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful()){
                    try{
                        if(response.body() != null){
                            JSONObject responseJson = new JSONObject(response.body());
                            JSONArray results = responseJson.getJSONArray("results");
                            if(responseJson.getInt("failure") == 1){
                                JSONObject error = (JSONObject) results.get(0);
                                showToast(error.getString("error"));
                                return;
                            }
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                else{
                    showToast("Error: "+ response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void setDataForNotification(){
        try{
            JSONArray tokens = new JSONArray();

            //FCM Token del/dei destinatario/destinatari ---> user.getToken()/users.getTokens();
            //tokens.put("");

            User user = itinerary.getAuthor();
            tokens.put(user.getFCMToken());



            JSONObject data = new JSONObject();
            data.put(Constants.KEY_USER_ID, preferanceManager.getString(Constants.KEY_USER_ID));
            data.put(Constants.KEY_NAME, preferanceManager.getString(Constants.KEY_NAME));
            data.put(Constants.KEY_FCM_TOKEN, preferanceManager.getString(Constants.KEY_FCM_TOKEN));
            data.put(Constants.KEY_MESSAGE, "Ops... Un tuo Itinerario è stato segnalato!");

            JSONObject body = new JSONObject();
            body.put(Constants.REMOTE_MSG_DATA, data);
            body.put(Constants.REMOTE_MSG_REGISTRATION_IDS, tokens);

            sendNotificaiton(body.toString());

        }catch(Exception e){
            showToast(e.getMessage());
        }
    }
}