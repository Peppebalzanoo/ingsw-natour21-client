package com.example.natour2.fragment.loginSignin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.natour2.R;
import com.example.natour2.controller.ControllerLoginSignin;

public class ForgetPasswordFragment extends Fragment {

    private Button button;
    private EditText email;
    private EditText vecchiaPassword;
    private EditText nuovaPassowrd;
    private ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();

    public ForgetPasswordFragment() {
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
        View view = inflater.inflate(R.layout.fragment_forget, container, false);
        button = view.findViewById(R.id.buttonSendBlank);
        email = view.findViewById(R.id.editTextMailBlakFragment);
        vecchiaPassword = view.findViewById(R.id.editTextTextVecchiaPasswordBlank);
        nuovaPassowrd = view.findViewById(R.id.editTextTextNuovaPasswordBlank);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.forgetPassword(email.getText().toString(), vecchiaPassword.getText().toString(), nuovaPassowrd.getText().toString());
            }
        });

        return view;
    }
}