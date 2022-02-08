package com.example.natour2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.natour2.R;
import com.example.natour2.controller.ControllerLoginSignin;

public class AdminFragment extends Fragment {

   private Button btnAccediAdmin;
   private EditText passwordAdmin;
   private EditText matricolaAdmin;
   private ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();


    public AdminFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_admin, container, false);

      initViewComponents(view);

      return view;
    }

    private void initViewComponents(View view){

        btnAccediAdmin = view.findViewById(R.id.buttonInviaAdminFragment);
        passwordAdmin = view.findViewById(R.id.editTextPasswordAdminFragment);
        matricolaAdmin = view.findViewById(R.id.editTextAdminFragment);

        btnAccediAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.loginAdmin(matricolaAdmin.getText().toString(), passwordAdmin.getText().toString());
            }
        });

    }
}