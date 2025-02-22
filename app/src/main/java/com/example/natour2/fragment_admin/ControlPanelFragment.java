package com.example.natour2.fragment_admin;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.natour2.R;
import com.example.natour2.adapter.SegnalazioneAdapter;
import com.example.natour2.controller.ControllerAdminActivity;
import com.example.natour2.controller.ControllerReport;
import com.example.natour2.model.Report;
import com.example.natour2.utilities.SpacingItem;

import java.util.ArrayList;
import java.util.List;

public class ControlPanelFragment extends Fragment implements SegnalazioneAdapter.OnDrawableListener {

    private ImageView imageViewNewsLetters;
    private ImageView imageViewLogOut;
    private List<Report> reportList;
    private SegnalazioneAdapter customBaseAdapter;
    private RecyclerView recyclerView;

    private ControllerAdminActivity ctrl = ControllerAdminActivity.getInstance();
    private ControllerReport ctrlReport = ControllerReport.getInstance();


    public ControlPanelFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctrl.setActivity(requireActivity());
        ctrl.setContext(requireContext());
        ctrl.setFragmentManager(getActivity().getSupportFragmentManager());

        ctrlReport.setActivity(requireActivity());
        ctrlReport.setContext(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_controlpanel, container, false);

        initComponentsView(view);

        reportList = new ArrayList<>();

        customBaseAdapter = new SegnalazioneAdapter(this, getContext(), reportList, savedInstanceState, recyclerView);
        recyclerView.setAdapter(customBaseAdapter);
        SpacingItem spacingItem = new SpacingItem(20);
        recyclerView.addItemDecoration(spacingItem);

        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(requireActivity(), R.drawable.horizontal_divider);
        assert horizontalDivider != null;
        horizontalDecoration.setDrawable(horizontalDivider);
        recyclerView.addItemDecoration(horizontalDecoration);

        setListeners();
        addListItems();
        return view;
    }


    private void initComponentsView(View view){
        imageViewNewsLetters = view.findViewById(R.id.button_NewsLetters_ControlPanelFragment);
        imageViewLogOut = view.findViewById(R.id.imageView_LogOut_ControlPanelFragment);
        recyclerView = view.findViewById(R.id.recycler_view_ControlPanelFragment);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void addListItems(){
        List<Report> list = ctrlReport.getAllReport();
        if(list == null){
            return;
        }
        reportList.addAll(list);
        customBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDrawableClick(int position) {
        ctrl.showSegnalazioneFragment(reportList.get(position));
    }

    public void setListeners(){

        imageViewNewsLetters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.showNewsLettersFragment();
            }
        });

        imageViewLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.showMainActivityAndClearBackStack( getContext());
            }
        });
    }

}