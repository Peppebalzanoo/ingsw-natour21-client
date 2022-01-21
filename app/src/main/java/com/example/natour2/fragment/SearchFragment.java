package com.example.natour2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.example.natour2.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SearchFragment extends BaseFragment {

    /* ****************************************************************************************** */
    private FloatingActionButton buttonFiltersFloating;
    private Button buttonApply;
    private BottomSheetDialog dialog;
    /* ****************************************************************************************** */


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        /* ****************************************************************************************** */
        buttonFiltersFloating = view.findViewById(R.id.buttonFiltersFloating);

        dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.bottom_dialog);
        dialog.setCanceledOnTouchOutside(false);

        buttonApply = dialog.findViewById(R.id.buttonApply);

        buttonFiltersFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        return view;
    }


}