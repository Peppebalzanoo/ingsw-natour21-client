package com.example.natour2.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2.R;
import com.example.natour2.adapter.NotificationAdapter;
import com.example.natour2.controller.ControllerHomeActivity;
import com.example.natour2.model.Notification;
import com.example.natour2.utilities.SpacingItem;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment implements NotificationAdapter.OnNotificationListener {

    private List<Notification> notificationList;

    private NotificationAdapter notificationAdapter;
    private RecyclerView recyclerView;
    private ImageView imageBack;

    private ControllerHomeActivity ctrl = ControllerHomeActivity.getInstance();


    public NotificationFragment() {
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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        initViewComponent(view);

        notificationList = new ArrayList<>();

        notificationList.add(new Notification("Il sentiero degli Dei", "Utente 1"));
        notificationList.add(new Notification("Il sentiero degli Inferi", "Utente 2"));
        notificationList.add(new Notification("Il sentiero degli Angeli", "Utente 3"));
        notificationList.add(new Notification("Il sentiero degli Zombie", "Utente 4"));
        notificationList.add(new Notification("Il sentiero degli Animali", "Utente 5"));

        notificationAdapter = new NotificationAdapter(this, getContext(), notificationList, savedInstanceState, recyclerView);
        recyclerView.setAdapter(notificationAdapter);
        SpacingItem spacingItem = new SpacingItem(20);
        recyclerView.addItemDecoration(spacingItem);

        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(requireActivity(), R.drawable.horizontal_divider);
        assert horizontalDivider != null;
        horizontalDecoration.setDrawable(horizontalDivider);
        recyclerView.addItemDecoration(horizontalDecoration);

        setListeners();

        return view;
    }

    public void initViewComponent(View view){
        imageBack = view.findViewById(R.id.imageBack_NotificationFragment);

        recyclerView = view.findViewById(R.id.recycler_view_NotificationFragment);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setListeners(){
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctrl.showHomeFragment();
            }
        });
    }

    @Override
    public void onNotificationClick(int position) {
        Notification not = notificationList.get(position);
        /* Codice per potare l'utente alla visualizzazione della segnalazione */
        Toast.makeText(requireContext(), "Hai cliccato sulla notifica dell'itinerario: "+not.getItinerario(), Toast.LENGTH_SHORT).show();
        notificationAdapter.notifyItemChanged(position);
    }
}