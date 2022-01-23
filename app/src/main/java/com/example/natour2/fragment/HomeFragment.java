package com.example.natour2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2.R;
import com.example.natour2.adapter.ItinerarioAdapter;
import com.example.natour2.controller.ControllerHomeActivity;
import com.example.natour2.model.Itinerario;
import com.example.natour2.utilities.PreferanceManager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private PreferanceManager preferanceManager;

    private RecyclerView recyclerView;
    private ItinerarioAdapter itinerarioAdapter;
    private List<Itinerario> itinerarioList;
    private ImageView addItinerario;
    private ImageView notification;

    private final ControllerHomeActivity ctrl = ControllerHomeActivity.getInstance();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* ************************************************************************************** */
        //preferanceManager = new PreferanceManager(getActivity().getApplicationContext());
        //loadUserDetails();

        ctrl.setActivity(getActivity());
        ctrl.setContext(getActivity().getApplicationContext());
        ctrl.setFragmentManager(getActivity().getSupportFragmentManager());

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViewComponents(view);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        itinerarioList = new ArrayList<>();
        itinerarioAdapter = new ItinerarioAdapter(getContext(), itinerarioList, savedInstanceState, recyclerView);
        recyclerView.setAdapter(itinerarioAdapter);

        readItinerari();
        return view;
    }

    private void readItinerari(){
        Itinerario itr1 = new Itinerario("sentiero", "01:14", "facile", "bello il sentiero", "antonio", "pippo");
        Itinerario itr2 = new Itinerario("sentiero2", "01:48", "difficile", "brutto il sentiero", "Peppe", "pippo2");
        itinerarioList.add(itr1);
        itinerarioList.add(itr2);

        itinerarioAdapter.notifyDataSetChanged();
    }

    private void initViewComponents(View view){

        addItinerario = view.findViewById(R.id.addItinerario);
        notification = view.findViewById(R.id.notification);

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

    /* ****************************************************************************************** */


    /*
    CODICE CHE AL MOMENTO NON SEMBRA SERVICI

    private void loadUserDetails(){
        textName.setText(preferanceManager.getString(Constants.KEY_NAME));
        byte[] bytes = android.util.Base64.decode(preferanceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageProfile.setImageBitmap(bitmap);
    }
    */

/*
    private void showToast(String message){
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token){
        preferanceManager.putString(Constants.KEY_FCM_TOKEN, token);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS).document(preferanceManager.getString(Constants.KEY_USER_ID));
        documentReference.update(Constants.KEY_FCM_TOKEN, token)
                .addOnFailureListener(e -> showToast("Unable to update token"));
    }

*/

}