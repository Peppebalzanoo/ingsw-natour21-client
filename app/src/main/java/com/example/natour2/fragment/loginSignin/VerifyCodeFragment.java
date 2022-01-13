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


public class VerifyCodeFragment extends Fragment {

    private EditText editTextNumberPassword;
    private Button btnVerify;
    private final ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();


    public VerifyCodeFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ctrl.setActivity(getActivity());
        ctrl.setContext(getActivity().getApplicationContext());
        ctrl.setFragmentManager(getActivity().getSupportFragmentManager());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_verify_code, container, false);
        initViewComponents(view);
        return view;

    }


    private void initViewComponents(View view){

        editTextNumberPassword = view.findViewById(R.id.editTextNumberPassword);
        btnVerify = view.findViewById(R.id.btnVerify);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctrl.verifyCode(editTextNumberPassword.getText().toString().replace(" ", ""));
            }
        });
    }
}